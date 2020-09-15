package mainGUI;

import fr.femtost.disc.glcomp.m1comp6.analyser.mjj.Analyser;
import fr.femtost.disc.glcomp.m1comp6.analyser.mjj.AnalyserException;
import fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMiniJaja;
import fr.femtost.disc.glcomp.m1comp6.interpreters.mjj.Interpreter;
import fr.femtost.disc.glcomp.m1comp6.interpreters.mjj.InterpreterException;
import fr.femtost.disc.glcomp.m1comp6.memory.Memory;
import fr.femtost.disc.glcomp.m1comp6.memory.SymbolTable;
import fr.femtost.disc.glcomp.m1comp6.typechecker.mjj.TypeChecker;
import fr.femtost.disc.glcomp.m1comp6.typechecker.mjj.TypeCheckerException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.Scanner;

public class MainController {

    public String defaultFileName = "main";
    private File openedFile;
    private BuildMJJController buildMJJController;

    private Stage stage;

    @FXML
    private Desktop desktop = Desktop.getDesktop();

    @FXML
    private FileChooser fileChooser = new FileChooser();

    @FXML
    private TextArea miniJajaToCompile;

    @FXML
    private TextArea runnedMiniJaja;

    @FXML
    private URL location;

    @FXML
    private BorderPane borderPane;

    @FXML
    private ResourceBundle resources;

    @FXML
    private VBox vbox;

    //@FXML
    //private TextArea errors;

    //Empty constructor
    public MainController() {

    }

    //Border Pane testerBorder;
    //Set an initialize method /mandatory/
    @FXML
    public void initialize() {
        //Initialize compiler
        configureFileChooser(fileChooser);
        borderPane.sceneProperty().addListener((observableScene, oldScene, newScene) -> {
            if (oldScene == null && newScene != null) {
                // scene is set for the first time. Now its the time to listen stage changes.
                newScene.windowProperty().addListener((observableWindow, oldWindow, newWindow) -> {
                    if (oldWindow == null && newWindow != null) {
                        // stage is set. now is the right time to do whatever we need to the stage in the controller.
                        stage = (Stage) borderPane.getScene().getWindow();
                    }
                });
            }
        });
    }

    //Function called by the compile minijaja button /TO DO/
    @FXML
    public void compileMiniJaja() {
        this.runnedMiniJaja.setText("");

        try {
            changeScene("/buildMJJ.fxml");
            buildMJJController.compileMiniJaja(miniJajaToCompile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Run minijaja
    @FXML
    public void runMiniJaja() {
        this.runnedMiniJaja.setText("");
        //Call compiler method
        if (!miniJajaToCompile.getText().isEmpty()) {
            Analyser analyser = new Analyser();
            SymbolTable symbolTable = new SymbolTable();
            try {
                NodeMiniJaja parsedAnalyser = analyser.parseString(miniJajaToCompile.getText());
                TypeChecker typeChecker = new TypeChecker(parsedAnalyser);
                try {
                    typeChecker.typeCheck(symbolTable);
                    Memory memory = new Memory(symbolTable);
                    //MiniJajaInterpreter miniJajaInterpreter = new MiniJajaInterpreter(parsedAnalyser);
                    Interpreter miniJajaInterpreter = new Interpreter(parsedAnalyser);
                    try {
                        miniJajaInterpreter.eval(memory);
                        runnedMiniJaja.setText("ヽ(・∀・)ﾉ\n" + "Run successful !");

                    } catch (InterpreterException e) {
                        //ERROR MEMORY
                        runnedMiniJaja.setText("( ╯°□°)╯ ┻━━┻\n" + "ERROR : Memory error \n" + e.toString());
                    }
                } catch (TypeCheckerException e) {
                    //ERROR TYPECHECKER
                    runnedMiniJaja.setText("( ╯°□°)╯ ┻━━┻\n" + "ERROR : TypeChecker error \n" + e.toString());
                }
            } catch (AnalyserException e) {
                runnedMiniJaja.setText("( ╯°□°)╯ ┻━━┻\n" + "ERROR : Syntax error \n" + e.toString());
            }
        }
    }


    //Open a miniJaja file
    @FXML
    public void openMiniJajaFile() {
        fileChooser.setTitle("Open file...");
        File file = fileChooser.showOpenDialog(vbox.getScene().getWindow());

        if (file != null) {
            newMJJFile();
            openedFile = file;
            openFile(file);
        }
    }

    //Save miniJajaFile
    @FXML
    public void saveMinijajaFile() {
        // Checks if a file is already opened and then
        // saves the new file
        if (openedFile != null) {
            try {
                this.saveExistingMJJFile(openedFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Else creates a new file
        } else {
            saveNewMJJFile();
        }
    }


    //Set the open file to null and empty the textarea
    @FXML
    public void newMJJFile() {
        //TO DO ADD ALERT
        this.openedFile = null;
        miniJajaToCompile.setText("");
    }

    @FXML
    public void writeErrors(String message) {
        //errors.setText(message);
    }


    /*----------------------------------------------------------
        NON FXML
     */


    private static void configureFileChooser(final FileChooser fileChooser) {
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Mini JaJa (MJJ)", "*.mjj"),
                new FileChooser.ExtensionFilter("JaJa Code (JJC)", "*.jjc")
        );
    }

    //Open a file
    public void openFile(final File file) {
        if (desktop.isDesktopSupported()) {

            EventQueue.invokeLater(() -> {
                try {
                    Scanner s = new Scanner(file).useDelimiter("\\s+");
                    while (s.hasNextLine()) {
                        miniJajaToCompile.appendText(s.nextLine() + "\n"); // else read the next token
                    }
                } catch (FileNotFoundException ex) {
                    System.err.println(ex);
                }
            });
        }
    }

    public void saveExistingMJJFile(File file) throws IOException {
        if (file == null)
            throw new IllegalArgumentException("file cannot be null");

        String filePath = file.getAbsolutePath();

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
            writer.write(miniJajaToCompile.getText());
        }
    }

    public void saveNewMJJFile() {
        fileChooser.setTitle("Choose location to save file");
        fileChooser.setInitialFileName(defaultFileName);
        File savedFile = fileChooser.showSaveDialog(vbox.getScene().getWindow());

        //Verification of the extension /Common bug in JavaFX/
        if (savedFile != null && !savedFile.getName().contains(".")) {
            savedFile = new File(savedFile.getAbsolutePath() + ".mjj");
        }

        //Save the content of the pane in a file /TO DO/

        if (savedFile != null) {
            PrintWriter outFile = null;
            try {
                outFile = new PrintWriter(savedFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            //Get content of file
            outFile.println(miniJajaToCompile.getText());
            outFile.close();
        }
    }

    public void setMiniJajaToCompile(String text) {
        this.miniJajaToCompile.setText(text);
    }

    public void changeScene(String resource) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
        Parent build_page_parent = (Parent) loader.load();
        BuildMJJController buildMJJController = loader.getController();
        //DO TREATMENT LIKE controller.set()

        buildMJJController.setMainController(this);
        this.buildMJJController = buildMJJController;
        Scene build_page_scene = new Scene(build_page_parent);
        stage.setScene(build_page_scene);
        stage.show();
    }

}
