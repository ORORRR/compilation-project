package fr.femtost.disc.glcomp.m1comp6.gui;

import fr.femtost.disc.glcomp.m1comp6.ast.common.LocatableException;
import fr.femtost.disc.glcomp.m1comp6.ast.common.LocatableRuntimeException;
import fr.femtost.disc.glcomp.m1comp6.ast.common.Node;
import fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeInstrs;
import fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeJajaCode;
import fr.femtost.disc.glcomp.m1comp6.compiler.Compiler;
import fr.femtost.disc.glcomp.m1comp6.debuggers.Debugger;
import fr.femtost.disc.glcomp.m1comp6.debuggers.DebuggerContinue;
import fr.femtost.disc.glcomp.m1comp6.debuggers.DebuggerStepInto;
import fr.femtost.disc.glcomp.m1comp6.interpreters.jjc.Interpreter;
import fr.femtost.disc.glcomp.m1comp6.memory.HeapEntry;
import fr.femtost.disc.glcomp.m1comp6.memory.Memory;
import fr.femtost.disc.glcomp.m1comp6.memory.SymbolTable;
import fr.femtost.disc.glcomp.m1comp6.memory.ValueNode;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
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

public class JajaCodeController {
    private final VMModel vmModel;

    private MainController mainController;
    private MiniJajaController mjjController;

    private int debugMode;

    private ObservableList<Integer> checkedLines;
    private ObservableValue<Boolean> checkBoxesDisabled;

    private Debugger debugger;

    @FXML
    private BorderPane jjcPane;

    @FXML
    private VirtualizedScrollPane jjcContainer;

    @FXML
    private ToolBar toolBar;

    @FXML
    private Button btnRun;

    @FXML
    private ChoiceBox choiceDebug;

    @FXML
    private Button btnDebug;

    @FXML
    private HBox debugActions;

    @FXML
    private Separator separatorDebug;

    @FXML
    private Button btnDebugStart;

    @FXML
    private Button btnDebugStop;

    @FXML
    private Button btnDebugNext;

    @FXML
    private CodeArea jjcContent;

    @FXML
    private VBox stackContainer;

    @FXML
    private VBox heapContainer;

    private static final String[] INSTRUCTIONS = new String[]{
            "push", "new", "swap", "pop", "invoke", "newarray", "return",
            "load", "store", "if", "nop", "neg", "not", "add", "sub",
            "mul", "div", "and", "or", "cmp", "sup", "aload", "astore", "inc",
            "ainc", "goto"
    };

    private static final String[] STARTNSTOPINSTR = new String[]{
            "init", "jcstop"
    };

    private static final String[] PARAMETERS = new String[]{
            "INTEGER", "BOOLEAN", "VARIABLE", "CONSTANT", "METHOD"
    };

    private static final String[] BOOLEANS = new String[]{
            "true", "false"
    };

    private static final String INSTRUCTIONS_PATTERN = "\\b(" + String.join("|", INSTRUCTIONS) + ")\\b";
    private static final String PARAMETERS_PATTERN = "\\b(" + String.join("|", PARAMETERS) + ")\\b";
    private static final String PAREN_PATTERN = "[()]";
    private static final String SCOPE_PATTERN = "@[a-zA-Z0-9]+";
    private static final String BOOLEANS_PATTERN = "\\b(" + String.join("|", BOOLEANS) + ")\\b";
    private static final String INTEGER_PATTERN = "[0-9]+";
    private static final String STARTSTOPINSTRS_PATTERN = "\\b(" + String.join("|", STARTNSTOPINSTR) + ")\\b";


    private static final Pattern PATTERN = Pattern.compile(
            "(?<INSTRUCTIONS>" + INSTRUCTIONS_PATTERN + ")"
                    + "|(?<PARAMETERS>" + PARAMETERS_PATTERN + ")"
                    + "|(?<PAREN>" + PAREN_PATTERN + ")"
                    + "|(?<SCOPE>" + SCOPE_PATTERN + ")"
                    + "|(?<BOOLEANS>" + BOOLEANS_PATTERN + ")"
                    + "|(?<INTEGERS>" + INTEGER_PATTERN + ")"
                    + "|(?<STARTSTOP>" + STARTSTOPINSTRS_PATTERN + ")"
    );

    public JajaCodeController() {
        vmModel = VMModel.getInstance();

        debugMode = 0;

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
        btnDebugStart = new Button();

        btnDebugStart.setText("Start");
        btnDebugStart.setOnAction(event -> onDebugStartClick());

        btnDebugStop = new Button();

        btnDebugStop.setText("Stop");
        btnDebugStop.setOnAction(event -> onDebugStopClick());

        btnDebugNext = new Button();

        btnDebugNext.setText("Next");
        btnDebugNext.setOnAction(event -> onDebugNextClick());

        separatorDebug = new Separator();

        separatorDebug.setOrientation(Orientation.VERTICAL);

        setCodeArea();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setMJJController(MiniJajaController mjjController) {
        this.mjjController = mjjController;
    }

    public String getContent() {
        return jjcContent.getText();
    }

    public void setContent(String content) {
        jjcContent.replaceText(content);
    }

    public boolean print() {
        NodeInstrs instrs = (NodeInstrs) vmModel.getJjcAST();

        if (instrs != null) {
            Compiler compiler = new Compiler(null);

            try {
                jjcContent.replaceText(compiler.print(instrs));

                return true;
            } catch (LocatableException exception) {
                mainController.logError(exception);
            }
        }

        return false;
    }

    public boolean eval() {
        NodeJajaCode AST = vmModel.getJjcAST();
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

    private void createDebuggerContinue() {
        NodeJajaCode AST = vmModel.getJjcAST();
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
        NodeJajaCode AST = vmModel.getJjcAST();
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
        NodeJajaCode AST = vmModel.getJjcAST();

        if (AST != null) {
            for (int checkedLine : checkedLines) {
                createNodeBreakpoint(checkedLine, AST);
            }
        }
    }

    private boolean createNodeBreakpoint(int line, Node node) {
        List<Node> children = node.getChildren();

        if (node.getLine() == line) {
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
     * Event handlers
     */

    @FXML
    public void onRunJJCClick() {
        mainController.setState(new RunningState());
        mainController.executeState();
    }

    @FXML
    public void onDebugContinueJJCClick() {
        mainController.setState(new DebugContinueIdleState());
        mainController.executeState();
    }

    @FXML
    public void onDebugStepIntoJJCClick() {
        mainController.setState(new DebugStepIntoIdleState());
        mainController.executeState();
    }

    @FXML
    public void onDebugChoice() {
        debugMode = choiceDebug.getSelectionModel().getSelectedIndex();
    }

    @FXML
    public void onDebugClick() {
        switch (debugMode) {
            case 0:
                mainController.setState(new DebugContinueIdleState());

                break;

            case 1:
                mainController.setState(new DebugStepIntoIdleState());

                break;

            default:
                break;
        }

        mainController.executeState();
    }

    @FXML
    public void onDebugStartClick() {
        mainController.setState(new DebugStartingState());
        mainController.executeState();
    }

    @FXML
    public void onDebugStopClick() {
        mainController.setState(new DebugStoppingState());
        mainController.executeState();
    }

    @FXML
    public void onDebugNextClick() {
        mainController.setState(new DebugNextState());
        mainController.executeState();
    }

    @FXML
    public void onCloseClick() {
        mainController.closeJJCPane();
    }

    /*
     * UI
     */

    public void setCheckBoxesDisable(boolean isDisabled) {
        ((SimpleBooleanProperty) checkBoxesDisabled).setValue(isDisabled);
    }

    public void resetBreakpoints() {
        checkedLines.clear();
    }

    public void resetLineHighlighting() {
        for (int i = 0; i < jjcContent.getParagraphs().size(); ++i) {
            jjcContent.setParagraphStyle(i, jjcContent.getInitialParagraphStyle());
        }
    }

    public void setLineHighlighting(int line) {
        if (line >= 0 && line < jjcContent.getParagraphs().size()) {
            jjcContent.setParagraphStyle(line, Collections.singletonList("debug-line"));
        }
    }

    public void setDebugMode(int debugMode) {
        this.debugMode = debugMode;

        choiceDebug.getSelectionModel().select(debugMode);
    }

    /**
     * Set basic actions state.
     *
     * @param isDisabled A flag indicating if the actions should be disabled or not.
     */
    public void setBasicActionsDisable(boolean isDisabled) {
        btnRun.setDisable(isDisabled);

        mainController.setBasicActionsJJCDisable(isDisabled);
    }

    /**
     * Set actions state.
     *
     * @param isDisabled A flag indicating if the actions should be disabled or not.
     */
    public void setActionsDisable(boolean isDisabled) {
        btnRun.setDisable(isDisabled);
        choiceDebug.setDisable(isDisabled);
        btnDebug.setDisable(isDisabled);

        mainController.setActionsJJCDisable(isDisabled);
    }

    /**
     * Set debug start button state.
     *
     * @param isDisabled A flag indicating if the button should be disabled or not.
     */
    public void setButtonDebugStartDisable(boolean isDisabled) {
        btnDebugStart.setDisable(isDisabled);
    }

    /**
     * Set debug stop button state.
     *
     * @param isDisabled A flag indicating if the button should be disabled or not.
     */
    public void setButtonDebugStopDisable(boolean isDisabled) {
        btnDebugStop.setDisable(isDisabled);
    }

    /**
     * Set debug next button state.
     *
     * @param isDisabled A flag indicating if the button should be disabled or not.
     */
    public void setButtonDebugNextDisable(boolean isDisabled) {
        btnDebugNext.setDisable(isDisabled);
    }

    /**
     * Show debug buttons.
     */
    public void showDebugButtons() {
        debugActions.getChildren().addAll(separatorDebug, btnDebugStart, btnDebugStop, btnDebugNext);
    }

    /**
     * Hide debug buttons.
     */
    public void hideDebugButtons() {
        debugActions.getChildren().removeAll(separatorDebug, btnDebugStart, btnDebugStop, btnDebugNext);
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

        splitPane.getItems().addAll(jjcContainer, memoryContainer);

        jjcPane.setCenter(splitPane);
    }

    public void closeMemoryPane() {
        jjcPane.setCenter(jjcContainer);
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

    public class RunningState implements GUIState {
        @Override
        public void execute() {
            if (eval()) {
                mainController.setMainActionsDisable(true);
                mainController.setActionsDisable(true);
                setActionsDisable(true);

                mainController.logSuccess("Run successful!");
                mainController.openJJCPane();

                setActionsDisable(false);
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
            if (vmModel.getJjcAST() != null && vmModel.getSymbolTable() != null) {
                mainController.setMainActionsDisable(true);
                mainController.setBasicActionsDisable(true);
                setActionsDisable(true);

                setDebugMode(0);

                showDebugButtons();
                setButtonDebugStartDisable(false);
                setButtonDebugStopDisable(false);
                setButtonDebugNextDisable(true);

                mainController.openJJCPane();

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
            if (vmModel.getJjcAST() != null && vmModel.getSymbolTable() != null) {
                mainController.setMainActionsDisable(true);
                mainController.setBasicActionsDisable(true);
                setActionsDisable(true);

                setDebugMode(1);

                showDebugButtons();
                setButtonDebugStartDisable(false);
                setButtonDebugStopDisable(false);
                setButtonDebugNextDisable(true);

                mainController.openJJCPane();

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
            setButtonDebugStartDisable(true);
            setButtonDebugStopDisable(false);
            setButtonDebugNextDisable(false);

            mainController.openJJCPane();

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

            resetLineHighlighting();
            closeMemoryPane();
            setCheckBoxesDisable(false);

            hideDebugButtons();

            if (vmModel.getDebuggers().size() == 0) {
                setActionsDisable(false);
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
            mainController.openJJCPane();

            debugNext();
        }
    }

    /*
     * Syntax highlighting
     */

    private void setCodeArea() {
        jjcContent.setParagraphGraphicFactory(BreakpointFactory.get(jjcContent, checkedLines, checkBoxesDisabled));

        Subscription colorationSubscription = jjcContent
                .richChanges()
                .successionEnds(Duration.ofMillis(500))
                .subscribe(ignore -> jjcContent.setStyleSpans(0, computeHighlighting(jjcContent.getText())));
    }

    private static StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);

        int lastKwEnd = 0;

        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();

        while (matcher.find()) {
            String styleClass =
                    matcher.group("INSTRUCTIONS") != null ? "instructions" :
                            matcher.group("PAREN") != null ? "paren" :
                                    matcher.group("SCOPE") != null ? "scope" :
                                            matcher.group("PARAMETERS") != null ? "parameters" :
                                                    matcher.group("BOOLEANS") != null ? "boolean" :
                                                            matcher.group("STARTSTOP") != null ? "startstop" :
                                                                    matcher.group("INTEGERS") != null ? "integer" :
                                                                            null;

            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());

            lastKwEnd = matcher.end();
        }

        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);

        return spansBuilder.create();
    }
}
