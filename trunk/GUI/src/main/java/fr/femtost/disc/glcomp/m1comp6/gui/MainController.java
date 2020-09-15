package fr.femtost.disc.glcomp.m1comp6.gui;

import fr.femtost.disc.glcomp.m1comp6.ast.common.CallStackElement;
import fr.femtost.disc.glcomp.m1comp6.ast.common.LocatableException;
import fr.femtost.disc.glcomp.m1comp6.ast.common.LocatableRuntimeException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.Stack;

public class MainController {
    private static final String FILE_NAME = "main.mjj";

    public static final String ERROR_PREFIX = "( ╯°□°)╯ ┻━━┻\n\n";
    public static final String SUCCESS_PREFIX = "ヽ(・∀・)ﾉ\n\n";

    private final GUIModel guiModel;
    private final VMModel vmModel;

    private MiniJajaController mjjController;
    private JajaCodeController jjcController;

    private GUIState state;

    private int debugMode;

    @FXML
    private FileChooser fileChooser;

    @FXML
    private BorderPane borderPane;

    @FXML
    private MenuItem menuNew;

    @FXML
    private MenuItem menuOpen;

    @FXML
    private MenuItem menuSave;

    @FXML
    private MenuItem menuExit;

    @FXML
    private MenuItem menuOpenJJC;

    @FXML
    private MenuItem menuCloseJJC;

    @FXML
    private MenuItem menuCompile;

    @FXML
    private MenuItem menuRunMJJ;

    @FXML
    private MenuItem menuDebugContinueMJJ;

    @FXML
    private MenuItem menuDebugStepIntoMJJ;

    @FXML
    private MenuItem menuRunJJC;

    @FXML
    private MenuItem menuDebugContinueJJC;

    @FXML
    private MenuItem menuDebugStepIntoJJC;

    @FXML
    private ToolBar toolBar;

    @FXML
    private Button btnNew;

    @FXML
    private Button btnOpen;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnCompile;

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
    private AnchorPane mjjPane;

    @FXML
    private AnchorPane jjcPane;

    @FXML
    private TextArea logs;

    public MainController() {
        guiModel = GUIModel.getInstance();
        vmModel = VMModel.getInstance();

        state = new IdleState();

        debugMode = 0;
    }

    @FXML
    private void initialize() throws IOException {
        // Load controllers
        FXMLLoader mjjLoader = new FXMLLoader(getClass().getResource("/mjj.fxml"));
        FXMLLoader jjcLoader = new FXMLLoader(getClass().getResource("/jjc.fxml"));

        mjjPane = mjjLoader.load();
        jjcPane = jjcLoader.load();

        mjjController = mjjLoader.getController();
        jjcController = jjcLoader.getController();

        mjjController.setMainController(this);
        mjjController.setJJCController(jjcController);

        jjcController.setMainController(this);
        jjcController.setMJJController(mjjController);

        fileChooser = new FileChooser();

        configFileChooser(fileChooser);

        createUI();
    }

    private static void configFileChooser(FileChooser fileChooser) {
        // Open /home/user
        File dir = new File(System.getProperty("user.home"));

        // Only .mjj files are allowed
        FileChooser.ExtensionFilter mjjFilter = new FileChooser.ExtensionFilter("MiniJaja (.mjj)", "*.mjj");

        fileChooser.setInitialDirectory(dir);
        fileChooser.getExtensionFilters().add(mjjFilter);
    }

    /**
     * Initialize dynamic user interface objects.
     */
    private void createUI() {
        // File menu items
        menuNew.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        menuOpen.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        menuSave.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        menuExit.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));

        // View menu items
        menuOpenJJC.setAccelerator(new KeyCodeCombination(KeyCode.J, KeyCombination.CONTROL_DOWN));
        menuCloseJJC.setAccelerator(new KeyCodeCombination(KeyCode.K, KeyCombination.CONTROL_DOWN));

        // Build menu items
        menuCompile.setAccelerator(new KeyCodeCombination(KeyCode.F9, KeyCombination.CONTROL_DOWN));

        // Run menu items
        menuRunMJJ.setAccelerator(new KeyCodeCombination(KeyCode.F10, KeyCombination.SHIFT_DOWN));
        menuDebugContinueMJJ.setAccelerator(new KeyCodeCombination(KeyCode.F9, KeyCombination.SHIFT_DOWN));
        menuDebugStepIntoMJJ.setAccelerator(new KeyCodeCombination(KeyCode.F8, KeyCombination.SHIFT_DOWN));

        menuRunJJC.setAccelerator(new KeyCodeCombination(KeyCode.F10, KeyCombination.SHIFT_DOWN, KeyCombination.ALT_DOWN));
        menuDebugContinueJJC.setAccelerator(new KeyCodeCombination(KeyCode.F9, KeyCombination.SHIFT_DOWN, KeyCombination.ALT_DOWN));
        menuDebugStepIntoJJC.setAccelerator(new KeyCodeCombination(KeyCode.F8, KeyCombination.SHIFT_DOWN, KeyCombination.ALT_DOWN));

        borderPane.setCenter(mjjPane);

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
    }

    public GUIState getState() {
        return state;
    }

    public void setState(GUIState state) {
        this.state = state;
    }

    public void executeState() {
        state.execute();
    }

    /*
     * Event handlers
     */

    @FXML
    public void onNewClick() {
        setState(new NewFileState());
        executeState();
    }

    @FXML
    public void onOpenClick() {
        setState(new OpeningFileState());
        executeState();
    }

    @FXML
    public void onSaveClick() {
        setState(new SavingFileState());
        executeState();
    }

    @FXML
    public void onExitClick() {
        Platform.exit();
    }

    @FXML
    public void onOpenJJCClick() {
        openJJCPane();
    }

    @FXML
    public void onCloseJJCClick() {
        closeJJCPane();
    }

    @FXML
    public void onCompileClick() {
        setState(mjjController.new BuildingState());
        executeState();
    }

    @FXML
    public void onRunMJJClick() {
        setState(mjjController.new RunningState());
        executeState();
    }

    @FXML
    public void onDebugContinueMJJClick() {
        setState(mjjController.new DebugContinueIdleState());
        executeState();
    }

    @FXML
    public void onDebugStepIntoMJJClick() {
        setState(mjjController.new DebugStepIntoIdleState());
        executeState();
    }

    @FXML
    public void onRunJJCClick() {
        jjcController.onRunJJCClick();
    }

    @FXML
    public void onDebugContinueJJCClick() {
        jjcController.onDebugContinueJJCClick();
    }

    @FXML
    public void onDebugStepIntoJJCClick() {
        jjcController.onDebugStepIntoJJCClick();
    }

    @FXML
    public void onDebugChoice() {
        debugMode = choiceDebug.getSelectionModel().getSelectedIndex();
    }

    @FXML
    public void onDebugClick() {
        switch (debugMode) {
            case 0:
                setState(mjjController.new DebugContinueIdleState());

                break;

            case 1:
                setState(mjjController.new DebugStepIntoIdleState());

                break;

            default:
                break;
        }

        executeState();
    }

    @FXML
    public void onDebugStartClick() {
        setState(mjjController.new DebugStartingState());
        executeState();
    }

    @FXML
    public void onDebugStopClick() {
        setState(mjjController.new DebugStoppingState());
        executeState();
    }

    @FXML
    public void onDebugNextClick() {
        setState(mjjController.new DebugNextState());
        executeState();
    }

    @FXML
    public void onUserGuideClick() {
        try {
            String command = "evince ";
            String os = System.getProperty("os.name");

            if (os.toLowerCase().startsWith("win")) {
                command = "start ";
            }

            Runtime.getRuntime().exec(command + "./GUI/src/main/resources/user_guide.pdf");
        } catch (java.io.IOException e) {
            System.out.println("Openning User Guide error.");
        }
    }

    @FXML
    public void onShortcutsClick() {
        Group root = new Group();
        Scene scene = new Scene(root, 500, 250, Color.WHITE);
        Stage shortcutStage = new Stage();
        shortcutStage.setTitle("Shortcuts");
        shortcutStage.setScene(scene);

        String shorcuts =
                "Ctrl + N : crée un nouveau fichier MiniJaja\n" +
                        "Ctrl + O : ouvre un fichier MiniJaja\n" +
                        "Ctrl + S : sauvegarde le fichier MiniJaja ouvert\n" +
                        "Ctrl + X : ferme l'IDE\n" +
                        "Ctrl + J : ouvre le panel JajaCode\n" +
                        "Ctrl + K : ferme le panel JajaCode\n" +
                        "Ctrl + F9 : compile le MiniJaja en JajaCode\n" +
                        "Shift + F10 : exécute le programme MiniJaja\n" +
                        "Shift + F9 : lance le debugger MiniJaja en mode continue\n" +
                        "Shift + F8 : lance le debugger MiniJaja en mode pas à pas\n" +
                        "Alt + Shift + F10 : exécute le programme JajaCode\n" +
                        "Alt + Shift + F9 : lance le debugger JajaCode en mode continue\n" +
                        "Alt + Shift + F8 : lance le debugger JajaCode en mode pas à pas";

        Text text = new Text(85, 50, shorcuts);
        text.setCache(true);
        text.setFont(Font.font("Verdana", 15));
        root.getChildren().add(text);

        shortcutStage.show();
    }

    @FXML
    public void onAboutClick() {
        Group root = new Group();
        Scene scene = new Scene(root, 500, 300, Color.WHITE);
        Stage aboutStage = new Stage();
        aboutStage.setTitle("About");
        aboutStage.setScene(scene);

        String text1 = "Projet Semestriel Master 1 Informatique\nUniversité de Franche-Comté";

        String text2 = "Autors";

        String text3 = "Alexandre Cardot\n" +
                "Aurore Vandroux\n" +
                "Jérémy Roussey\n" +
                "Thierry Eya\n" +
                "Antoine Pegeot\n" +
                "Xueqian Li\n" +
                "Anthony Dugois";

        Text t1 = new Text(50, 50, text1);
        t1.setCache(true);
        t1.setFont(Font.font(null, FontWeight.BOLD, 18));
        t1.setTextAlignment(TextAlignment.CENTER);

        Text t2 = new Text(220, 100, text2);
        t2.setCache(true);
        t2.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        t2.setTextAlignment(TextAlignment.CENTER);

        Text t3 = new Text(185, 130, text3);
        t3.setCache(true);
        t3.setFont(Font.font("Verdana", FontWeight.MEDIUM, 15));
        t3.setTextAlignment(TextAlignment.CENTER);

        root.getChildren().add(t1);
        root.getChildren().add(t2);
        root.getChildren().add(t3);
        aboutStage.show();
    }

    /**
     * Load the content of a file in the MiniJaja content.
     *
     * @param file The file to load.
     * @throws IOException Something wrong happened during the process.
     */
    public void openFile(File file) throws IOException {
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder buffer = new StringBuilder();

        String line;

        while ((line = bufferedReader.readLine()) != null) {
            buffer.append(line).append("\n");
        }

        bufferedReader.close();
        fileReader.close();

        mjjController.setContent(buffer.toString());
        mjjController.resetBreakpoints();
        jjcController.resetBreakpoints();

        log("");

        // Update opened file
        guiModel.setOpenedFile(file);
    }

    /**
     * Save MiniJaja content in a file.
     *
     * @param file The file in which to save content.
     * @throws IOException Something wrong happened during the process.
     */
    public void saveFile(File file) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        bufferedWriter.write(mjjController.getContent());

        // Important: close the writer otherwise the content won't be saved
        bufferedWriter.close();
        fileWriter.close();
    }

    /**
     * Save MiniJaja content in a new file.
     *
     * @throws IOException Something wrong happened during the process.
     */
    public void saveNewFile() throws IOException {
        fileChooser.setTitle("Save file...");
        fileChooser.setInitialFileName(FILE_NAME);

        File file = fileChooser.showSaveDialog(borderPane.getScene().getWindow());

        // Save file
        saveFile(file);

        // Update opened file
        guiModel.setOpenedFile(file);
    }

    /*
     * UI
     */

    /**
     * Generic logger.
     *
     * @param text The message to log.
     */
    public void log(String text) {
        logs.setText(text);
    }

    /**
     * Log an error.
     *
     * @param exception The locatable exception to log.
     */
    public void logError(LocatableException exception) {
        StringBuilder buffer = new StringBuilder(ERROR_PREFIX);

        buffer
                .append("[")
                .append(exception.getLine())
                .append(",")
                .append(exception.getColumn())
                .append("] ")
                .append(exception.getClass().getSimpleName())
                .append(": ")
                .append(exception.getMessage());

        log(buffer.toString());
    }

    /**
     * Log an error.
     *
     * @param exception The locatable exception to log.
     */
    public void logError(LocatableRuntimeException exception) {
        StringBuilder buffer = new StringBuilder(ERROR_PREFIX);
        Stack<CallStackElement> callStack = exception.getCallStack();

        buffer
                .append("[")
                .append(exception.getLine())
                .append(",")
                .append(exception.getColumn())
                .append("] ")
                .append(exception.getClass().getSimpleName())
                .append(": ")
                .append(exception.getMessage());

        if (callStack != null) {
            for (int i = 0, sz = callStack.size(); i < sz; ++i) {
                CallStackElement callStackElement = callStack.get(sz - i - 1);

                buffer
                        .append("\n\t\tat ")
                        .append(callStackElement.getElement())
                        .append(" line ")
                        .append(callStackElement.getLine());
            }
        }

        log(buffer.toString());
    }

    /**
     * Log a success.
     *
     * @param text The success message to log.
     */
    public void logSuccess(String text) {
        log(SUCCESS_PREFIX + text);
    }

    /**
     * Open the JajaCode pane.
     */
    public void openJJCPane() {
        if (!guiModel.isJJCPaneOpened()) {
            SplitPane splitPane = new SplitPane();

            splitPane.getItems().addAll(mjjPane, jjcPane);

            borderPane.setCenter(splitPane);

            guiModel.setJJCPaneOpened(true);
        }
    }

    /**
     * Close the JajaCode pane.
     */
    public void closeJJCPane() {
        if (guiModel.isJJCPaneOpened()) {
            borderPane.setCenter(mjjPane);

            guiModel.setJJCPaneOpened(false);
        }
    }

    public void setDebugMode(int debugMode) {
        this.debugMode = debugMode;

        choiceDebug.getSelectionModel().select(debugMode);
    }

    /**
     * Set main actions state.
     *
     * @param isDisabled A flag indicating if the actions should be disabled or not.
     */
    public void setMainActionsDisable(boolean isDisabled) {
        btnNew.setDisable(isDisabled);
        btnOpen.setDisable(isDisabled);
        btnSave.setDisable(isDisabled);

        menuNew.setDisable(isDisabled);
        menuOpen.setDisable(isDisabled);
        menuSave.setDisable(isDisabled);
    }

    /**
     * Set basic actions state.
     *
     * @param isDisabled A flag indicating if the actions should be disabled or not.
     */
    public void setBasicActionsDisable(boolean isDisabled) {
        btnCompile.setDisable(isDisabled);
        btnRun.setDisable(isDisabled);

        menuCompile.setDisable(isDisabled);
        menuRunMJJ.setDisable(isDisabled);
    }

    /**
     * Set actions state.
     *
     * @param isDisabled A flag indicating if the actions should be disabled or not.
     */
    public void setActionsDisable(boolean isDisabled) {
        btnCompile.setDisable(isDisabled);
        btnRun.setDisable(isDisabled);
        choiceDebug.setDisable(isDisabled);
        btnDebug.setDisable(isDisabled);

        menuCompile.setDisable(isDisabled);
        menuRunMJJ.setDisable(isDisabled);
        menuDebugContinueMJJ.setDisable(isDisabled);
        menuDebugStepIntoMJJ.setDisable(isDisabled);
    }

    public void setBasicActionsJJCDisable(boolean isDisabled) {
        menuRunJJC.setDisable(isDisabled);
    }

    public void setActionsJJCDisable(boolean isDisabled) {
        menuRunJJC.setDisable(isDisabled);
        menuDebugContinueJJC.setDisable(isDisabled);
        menuDebugStepIntoJJC.setDisable(isDisabled);
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

    /*
     * States
     */

    public class IdleState implements GUIState {
        @Override
        public void execute() {
            // Nothing to do
        }
    }

    public class NewFileState implements GUIState {
        @Override
        public void execute() {
            setMainActionsDisable(true);
            setActionsDisable(true);
            jjcController.setActionsDisable(true);

            guiModel.reset();
            vmModel.reset();

            closeJJCPane();

            mjjController.setContent("");
            mjjController.resetBreakpoints();
            jjcController.resetBreakpoints();

            log("");

            jjcController.setActionsDisable(false);
            setActionsDisable(false);
            setMainActionsDisable(false);

            setState(new IdleState());
            executeState();
        }
    }

    public class OpeningFileState implements GUIState {
        @Override
        public void execute() {
            setMainActionsDisable(true);
            setActionsDisable(true);
            jjcController.setActionsDisable(true);

            fileChooser.setTitle("Open file...");

            File file = fileChooser.showOpenDialog(borderPane.getScene().getWindow());

            if (file != null) {
                try {
                    guiModel.reset();
                    vmModel.reset();

                    closeJJCPane();

                    openFile(file);
                } catch (Exception exception) {
                    // Nothing to do
                }
            }

            jjcController.setActionsDisable(false);
            setActionsDisable(false);
            setMainActionsDisable(false);

            setState(new IdleState());
            executeState();
        }
    }

    public class SavingFileState implements GUIState {
        @Override
        public void execute() {
            setMainActionsDisable(true);
            setActionsDisable(true);
            jjcController.setActionsDisable(true);

            File openedFile = guiModel.getOpenedFile();

            if (openedFile != null) {
                // Check if a file is already opened and then save the file
                try {
                    saveFile(openedFile);
                } catch (Exception exception) {
                    // Nothing to do
                }
            } else {
                // Else create a new file
                try {
                    saveNewFile();
                } catch (Exception exception) {
                    // Nothing to do
                }
            }

            jjcController.setActionsDisable(false);
            setActionsDisable(false);
            setMainActionsDisable(false);

            setState(new IdleState());
            executeState();
        }
    }
}
