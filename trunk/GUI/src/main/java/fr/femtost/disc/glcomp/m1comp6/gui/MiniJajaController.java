package fr.femtost.disc.glcomp.m1comp6.gui;

import fr.femtost.disc.glcomp.m1comp6.analyser.mjj.Analyser;
import fr.femtost.disc.glcomp.m1comp6.ast.common.LocatableException;
import fr.femtost.disc.glcomp.m1comp6.ast.common.LocatableRuntimeException;
import fr.femtost.disc.glcomp.m1comp6.ast.common.Node;
import fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMiniJaja;
import fr.femtost.disc.glcomp.m1comp6.compiler.Compiler;
import fr.femtost.disc.glcomp.m1comp6.debuggers.Debugger;
import fr.femtost.disc.glcomp.m1comp6.debuggers.DebuggerContinue;
import fr.femtost.disc.glcomp.m1comp6.debuggers.DebuggerStepInto;
import fr.femtost.disc.glcomp.m1comp6.interpreters.mjj.Interpreter;
import fr.femtost.disc.glcomp.m1comp6.memory.HeapEntry;
import fr.femtost.disc.glcomp.m1comp6.memory.Memory;
import fr.femtost.disc.glcomp.m1comp6.memory.SymbolTable;
import fr.femtost.disc.glcomp.m1comp6.memory.ValueNode;
import fr.femtost.disc.glcomp.m1comp6.typechecker.mjj.TypeChecker;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.reactfx.Subscription;

import java.time.Duration;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MiniJajaController {
    private final VMModel vmModel;

    private MainController mainController;
    private JajaCodeController jjcController;

    @FXML
    private AnchorPane mjjPane;

    @FXML
    private VirtualizedScrollPane mjjContainer;

    @FXML
    private CodeArea mjjContent;

    @FXML
    private VBox stackContainer;

    @FXML
    private VBox heapContainer;

    private ObservableList<Integer> checkedLines;
    private ObservableValue<Boolean> checkBoxesDisabled;

    private Debugger debugger;

    private static final String[] KEYWORDS = new String[]{
            "class", "final", "return", "while", "if", "else", "int", "boolean", "void"
    };

    private static final String[] BOOLEANS = new String[]{
            "true", "false"
    };

    private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
    private static final String BOOLEANS_PATTERN = "\\b(" + String.join("|", BOOLEANS) + ")\\b";
    private static final String BRACE_PATTERN = "[{}]";
    private static final String SEMICOLON_PATTERN = ";";
    private static final String COMMA_PATTERN = ",";
    private static final String INTEGER_PATTERN = "[0-9]+";
    private static final String MAIN_PATTERN = "main";
    private static final String COMMENT_PATTERN = "//[^\n]*|/\\*(.|\\R)*?\\*/";

    private static final Pattern PATTERN = Pattern.compile(
            "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
                    + "|(?<BOOLEAN>" + BOOLEANS_PATTERN + ")"
                    + "|(?<BRACE>" + BRACE_PATTERN + ")"
                    + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
                    + "|(?<COMMA>" + COMMA_PATTERN + ")"
                    + "|(?<INTEGER>" + INTEGER_PATTERN + ")"
                    + "|(?<MAIN>" + MAIN_PATTERN + ")"
                    + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
    );

    public MiniJajaController() {
        vmModel = VMModel.getInstance();

        checkedLines = FXCollections.observableArrayList();
        checkBoxesDisabled = new SimpleBooleanProperty(false);
    }

    @FXML
    private void initialize() {
        createUI();
    }

    /**
     * Initialize dynamic user interface objects.
     */
    private void createUI() {
        setCodeArea();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setJJCController(JajaCodeController jjcController) {
        this.jjcController = jjcController;
    }

    public String getContent() {
        return mjjContent.getText();
    }

    public void setContent(String content) {
        mjjContent.replaceText(content);
    }

    public boolean parse() {
        // Reset AST
        vmModel.setMjjAST(null);

        String content = mjjContent.getText();

        if (!content.isEmpty()) {
            Analyser analyser = new Analyser();

            try {
                NodeMiniJaja AST = analyser.parseString(content);

                vmModel.setMjjAST(AST);

                return true;
            } catch (LocatableRuntimeException exception) {
                mainController.logError(exception);
            }
        }

        return false;
    }

    public boolean typeCheck() {
        // Reset symbol table
        vmModel.setSymbolTable(null);

        NodeMiniJaja AST = vmModel.getMjjAST();

        if (AST != null) {
            TypeChecker typeChecker = new TypeChecker(AST);
            SymbolTable symbolTable = new SymbolTable();

            typeChecker.setSymbolTable(symbolTable);

            try {
                typeChecker.typeCheck();

                vmModel.setSymbolTable(symbolTable);

                return true;
            } catch (LocatableException exception) {
                mainController.logError(exception);
            }
        }

        return false;
    }

    public boolean eval() {
        NodeMiniJaja AST = vmModel.getMjjAST();
        SymbolTable symbolTable = vmModel.getSymbolTable();

        if (AST != null && symbolTable != null) {
            Interpreter interpreter = new Interpreter(AST);
            Memory memory = new Memory(symbolTable);

            interpreter.setMemory(memory);

            try {
                interpreter.eval();

                return true;
            } catch (LocatableRuntimeException exception) {
                mainController.logError(exception);
            }
        }

        return false;
    }

    public void debugStart() {
        debugger.start();
    }

    public void debugStop() {
        debugger.stop();
    }

    public void debugNext() {
        debugger.next();
    }

    public boolean compile() {
        // Reset AST
        vmModel.setJjcAST(null);

        NodeMiniJaja AST = vmModel.getMjjAST();

        if (AST != null) {
            fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeInstrs instrs = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeInstrs();

            Compiler compiler = new Compiler(AST);

            compiler.setInstrs(instrs);

            try {
                compiler.compile();

                vmModel.setJjcAST(instrs);

                return true;
            } catch (LocatableException exception) {
                mainController.logError(exception);
            }
        }

        return false;
    }

    private void createDebuggerContinue() {
        NodeMiniJaja AST = vmModel.getMjjAST();
        SymbolTable symbolTable = vmModel.getSymbolTable();

        if (AST != null && symbolTable != null) {
            debugger = new DebuggerContinue();

            Interpreter interpreter = new Interpreter(AST);
            Memory memory = new Memory(symbolTable);

            interpreter.setMemory(memory);

            debugger.setInterpreter(interpreter);

            debugger.setDebuggerHandler((isFinished, node) -> Platform.runLater(() -> {
                resetLineHighlighting();

                updateMemoryPane(memory);

                if (isFinished) {
                    mainController.setState(new DebugStoppingState());
                    mainController.executeState();
                } else {
                    setLineHighlighting(node.getLine() - 1);
                }
            }));

            debugger.setExceptionHandler((thread, exception) -> Platform.runLater(() -> {
                mainController.logError((LocatableRuntimeException) exception);

                mainController.setState(new DebugStoppingState());
                mainController.executeState();
            }));
        }
    }

    private void createDebuggerStepInto() {
        NodeMiniJaja AST = vmModel.getMjjAST();
        SymbolTable symbolTable = vmModel.getSymbolTable();

        if (AST != null && symbolTable != null) {
            debugger = new DebuggerStepInto();

            Interpreter interpreter = new Interpreter(AST);
            Memory memory = new Memory(symbolTable);

            interpreter.setMemory(memory);

            debugger.setInterpreter(interpreter);

            debugger.setDebuggerHandler((isFinished, node) -> Platform.runLater(() -> {
                resetLineHighlighting();

                updateMemoryPane(memory);

                if (isFinished) {
                    mainController.setState(new DebugStoppingState());
                    mainController.executeState();
                } else {
                    setLineHighlighting(node.getLine() - 1);
                }
            }));

            debugger.setExceptionHandler((thread, exception) -> Platform.runLater(() -> {
                mainController.logError((LocatableRuntimeException) exception);

                mainController.setState(new DebugStoppingState());
                mainController.executeState();
            }));
        }
    }

    private void createDebuggerBreakpoints() {
        NodeMiniJaja AST = vmModel.getMjjAST();

        if (AST != null) {
            for (int checkedLine : checkedLines) {
                createNodeBreakpoint(checkedLine, AST);
            }
        }
    }

    private boolean createNodeBreakpoint(int line, Node node) {
        List<Node> children = node.getChildren();

        if (!(node instanceof fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeDecls) &&
                !(node instanceof fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeVars) &&
                !(node instanceof fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeInstrs) &&
                node.getLine() == line) {
            debugger.addBreakpoint(node);

            return true;
        }

        for (Node child : children) {
            if (createNodeBreakpoint(line, child)) {
                return true;
            }
        }

        return false;
    }

    /*
     * UI
     */

    public void setCheckBoxesDisable(boolean isDisabled) {
        ((SimpleBooleanProperty) checkBoxesDisabled).set(isDisabled);
    }

    public void resetBreakpoints() {
        checkedLines.clear();
    }

    public void resetLineHighlighting() {
        for (int i = 0; i < mjjContent.getParagraphs().size(); ++i) {
            mjjContent.setParagraphStyle(i, mjjContent.getInitialParagraphStyle());
        }
    }

    public void setLineHighlighting(int line) {
        if (line >= 0 && line < mjjContent.getParagraphs().size()) {
            mjjContent.setParagraphStyle(line, Collections.singletonList("debug-line"));
        }
    }

    public void openMemoryPane() {
        stackContainer = new VBox();
        heapContainer = new VBox();

        stackContainer.getStyleClass().add("memory-stack");
        heapContainer.getStyleClass().add("memory-heap");

        HBox.setHgrow(stackContainer, Priority.ALWAYS);
        HBox.setHgrow(heapContainer, Priority.ALWAYS);

        HBox memoryContent = new HBox();

        memoryContent.getChildren().addAll(stackContainer, heapContainer);

        SplitPane splitPane = new SplitPane();

        splitPane.setOrientation(Orientation.VERTICAL);

        AnchorPane.setBottomAnchor(splitPane, 0.0);
        AnchorPane.setLeftAnchor(splitPane, 0.0);
        AnchorPane.setRightAnchor(splitPane, 0.0);
        AnchorPane.setTopAnchor(splitPane, 0.0);

        ScrollPane memoryContainer = new ScrollPane();

        memoryContainer.setFitToHeight(true);
        memoryContainer.setFitToWidth(true);

        memoryContainer.setContent(memoryContent);
        memoryContainer.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        memoryContainer.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        memoryContainer.getStyleClass().add("memory");

        splitPane.getItems().addAll(mjjContainer, memoryContainer);

        mjjPane.getChildren().setAll(splitPane);
    }

    public void closeMemoryPane() {
        mjjPane.getChildren().setAll(mjjContainer);
    }

    public void updateMemoryPane(Memory memory) {
        stackContainer.getChildren().clear();
        heapContainer.getChildren().clear();

        HBox stackTitleContainer = new HBox();
        Text stackTitle = new Text();

        stackTitleContainer.getStyleClass().add("memory-stack-title");

        stackTitle.setText("STACK");
        stackTitle.getStyleClass().add("memory-stack-title-txt");

        stackTitleContainer.getChildren().add(stackTitle);

        stackContainer.getChildren().add(stackTitleContainer);

        HBox heapTitleContainer = new HBox();
        Text heapTitle = new Text();

        heapTitleContainer.getStyleClass().add("memory-heap-title");

        heapTitle.setText("HEAP");
        heapTitle.getStyleClass().add("memory-heap-title-txt");

        heapTitleContainer.getChildren().add(heapTitle);

        heapContainer.getChildren().add(heapTitleContainer);

        List<ValueNode> values = memory.getStack().getValues();
        HashMap<Integer, HeapEntry> entries = memory.getHeap().getSymbolTable();

        for (int i = 0, sz = values.size(); i < sz; ++i) {
            ValueNode valueNode = values.get(sz - i - 1);

            HBox container = new HBox();

            container.getStyleClass().add("memory-stack-element");

            Text ident = new Text();
            Text value = new Text();
            Text kind = new Text();
            Text type = new Text();

            ident.getStyleClass().add("memory-stack-ident");
            value.getStyleClass().add("memory-stack-value");
            kind.getStyleClass().add("memory-stack-kind");
            type.getStyleClass().add("memory-stack-type");

            ident.setWrappingWidth(150.0);
            value.setWrappingWidth(150.0);
            kind.setWrappingWidth(100.0);
            type.setWrappingWidth(100.0);

            ident.setText(valueNode.getIdent() == null ? "-" : valueNode.getIdent());
            value.setText(valueNode.getValue() == null ? "-" : valueNode.toString());
            kind.setText(valueNode.getKind() == null ? "-" : valueNode.getKind().toString());
            type.setText(valueNode.getType() == null ? "-" : valueNode.getType().toString());

            container.getChildren().addAll(ident, value, kind, type);

            stackContainer.getChildren().add(container);
        }

        for (Map.Entry<Integer, HeapEntry> entry : entries.entrySet()) {
            HeapEntry heapEntry = entry.getValue();

            VBox container = new VBox();

            container.getStyleClass().add("memory-heap-element");

            HBox table = new HBox();

            Text id = new Text();
            Text adr = new Text();
            Text size = new Text();
            Text ref = new Text();

            id.getStyleClass().add("memory-heap-id");
            adr.getStyleClass().add("memory-heap-adr");
            size.getStyleClass().add("memory-heap-size");
            ref.getStyleClass().add("memory-heap-ref");

            id.setWrappingWidth(100.0);
            adr.setWrappingWidth(100.0);
            size.setWrappingWidth(100.0);
            ref.setWrappingWidth(100.0);

            id.setText("Key: " + entry.getKey());
            adr.setText("Address: " + String.valueOf(heapEntry.getAddress()));
            size.setText("Size: " + String.valueOf(heapEntry.getSize()));
            ref.setText("Refs: " + String.valueOf(heapEntry.getNbRef()));

            table.getChildren().addAll(id, adr, size, ref);

            FlowPane indices = new FlowPane();

            for (int i = 0, sz = heapEntry.getSize(); i < sz; ++i) {
                Object value = memory.getHeap().getValues()[heapEntry.getAddress() + i];

                Text index = new Text();

                index.getStyleClass().add("memory-heap-index");
                index.setWrappingWidth(25.0);
                index.setText(String.valueOf(value));

                indices.getChildren().add(index);
            }

            container.getChildren().addAll(table, indices);

            heapContainer.getChildren().add(container);
        }
    }

    /*
     * States
     */

    public class BuildingState implements GUIState {
        @Override
        public void execute() {
            if (parse() && typeCheck() && compile()) {
                mainController.setMainActionsDisable(true);
                mainController.setActionsDisable(true);
                jjcController.setActionsDisable(true);

                mainController.logSuccess("Build successful!");
                mainController.openJJCPane();
                jjcController.print();

                jjcController.setActionsDisable(false);
                mainController.setActionsDisable(false);
                mainController.setMainActionsDisable(false);
            }

            mainController.setState(mainController.new IdleState());
            mainController.executeState();
        }
    }

    public class RunningState implements GUIState {
        @Override
        public void execute() {
            if (parse() && typeCheck() && eval()) {
                mainController.setMainActionsDisable(true);
                mainController.setActionsDisable(true);
                jjcController.setActionsDisable(true);

                mainController.logSuccess("Run successful!");

                jjcController.setActionsDisable(false);
                mainController.setActionsDisable(false);
                mainController.setMainActionsDisable(false);
            }

            mainController.setState(mainController.new IdleState());
            mainController.executeState();
        }
    }

    public class DebugContinueIdleState implements GUIState {
        @Override
        public void execute() {
            if (parse() && typeCheck()) {
                mainController.setMainActionsDisable(true);
                mainController.setActionsDisable(true);
                jjcController.setBasicActionsDisable(true);

                mainController.setDebugMode(0);

                mainController.showDebugButtons();
                mainController.setButtonDebugStartDisable(false);
                mainController.setButtonDebugStopDisable(false);
                mainController.setButtonDebugNextDisable(true);

                mjjContent.setEditable(false);

                createDebuggerContinue();

                vmModel.addDebugger(debugger);
            } else {
                mainController.setState(mainController.new IdleState());
                mainController.executeState();
            }
        }
    }

    public class DebugStepIntoIdleState implements GUIState {
        @Override
        public void execute() {
            if (parse() && typeCheck()) {
                mainController.setMainActionsDisable(true);
                mainController.setActionsDisable(true);
                jjcController.setBasicActionsDisable(true);

                mainController.setDebugMode(1);

                mainController.showDebugButtons();
                mainController.setButtonDebugStartDisable(false);
                mainController.setButtonDebugStopDisable(false);
                mainController.setButtonDebugNextDisable(true);

                mjjContent.setEditable(false);

                createDebuggerStepInto();

                vmModel.addDebugger(debugger);
            } else {
                mainController.setState(mainController.new IdleState());
                mainController.executeState();
            }
        }
    }

    public class DebugStartingState implements GUIState {
        @Override
        public void execute() {
            mainController.setButtonDebugStartDisable(true);
            mainController.setButtonDebugStopDisable(false);
            mainController.setButtonDebugNextDisable(false);

            setCheckBoxesDisable(true);
            openMemoryPane();

            createDebuggerBreakpoints();

            debugStart();
        }
    }

    public class DebugStoppingState implements GUIState {
        @Override
        public void execute() {
            vmModel.removeDebugger(debugger);

            debugStop();

            mjjContent.setEditable(true);

            resetLineHighlighting();
            closeMemoryPane();
            setCheckBoxesDisable(false);

            mainController.hideDebugButtons();

            if (vmModel.getDebuggers().size() == 0) {
                jjcController.setActionsDisable(false);
                mainController.setActionsDisable(false);
                mainController.setMainActionsDisable(false);
            }

            mainController.setState(mainController.new IdleState());
            mainController.executeState();
        }
    }

    public class DebugNextState implements GUIState {
        @Override
        public void execute() {
            debugNext();
        }
    }

    /*
     * Syntax highlighting
     */

    private void setCodeArea() {
        mjjContent.setParagraphGraphicFactory(BreakpointFactory.get(mjjContent, checkedLines, checkBoxesDisabled));

        Subscription colorationSubscription = mjjContent
                .richChanges()
                .successionEnds(Duration.ofMillis(500))
                .subscribe(ignore -> mjjContent.setStyleSpans(0, computeHighlighting(mjjContent.getText())));
    }

    private static StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);

        int lastKwEnd = 0;

        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();

        while (matcher.find()) {
            String styleClass =
                    matcher.group("KEYWORD") != null ? "keyword" :
                            matcher.group("BOOLEAN") != null ? "boolean" :
                                    matcher.group("BRACE") != null ? "brace" :
                                            matcher.group("COMMA") != null ? "comma" :
                                                    matcher.group("SEMICOLON") != null ? "semicolon" :
                                                            matcher.group("COMMENT") != null ? "comment" :
                                                                    matcher.group("MAIN") != null ? "main" :
                                                                            matcher.group("INTEGER") != null ? "integer" :
                                                                                    null;

            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());

            lastKwEnd = matcher.end();
        }

        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);

        return spansBuilder.create();
    }
}
