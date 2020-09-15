package mainGUI;

import fr.femtost.disc.glcomp.m1comp6.analyser.mjj.Analyser;
import fr.femtost.disc.glcomp.m1comp6.analyser.mjj.AnalyserException;
import fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeInstrs;
import fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeJajaCode;
import fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMiniJaja;
import fr.femtost.disc.glcomp.m1comp6.compiler.Compiler;
import fr.femtost.disc.glcomp.m1comp6.compiler.CompilerException;
import fr.femtost.disc.glcomp.m1comp6.compiler.CompilerPrinterException;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BuildMJJController {

    private Stage stage;

    private MainController mainController;

    @FXML
    private TextArea compiledJJC;

    @FXML
    private URL location;

    @FXML
    private BorderPane borderPane;

    @FXML
    private TextArea errors;

    @FXML
    private ResourceBundle resources;

    @FXML
    private VBox vbox;

    @FXML
    private TextArea miniJajaToCompile;


    private NodeJajaCode jajaCodeTree;
    private SymbolTable symbolTable;

    //@FXML
    //private TextArea errors;

    //Empty constructor
    public BuildMJJController() {
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    //Border Pane testerBorder;
    //Set an initialize method /mandatory/
    @FXML
    public void initialize() {
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
    public void compileMiniJaja(TextArea miniJajaToCompile) {
        if (!miniJajaToCompile.getText().isEmpty()) {
            this.miniJajaToCompile.setText(miniJajaToCompile.getText());
            Analyser analyser = new Analyser();
            this.symbolTable = new SymbolTable();
            try {
                NodeMiniJaja parsedAnalyser = analyser.parseString(miniJajaToCompile.getText());
                TypeChecker typeChecker = new TypeChecker(parsedAnalyser);
                try {
                    typeChecker.typeCheck(symbolTable);
                    Compiler compiler = new Compiler(parsedAnalyser);
                    this.jajaCodeTree = new NodeInstrs();
                    try {
                        compiler.compile(symbolTable, ((NodeInstrs) this.jajaCodeTree));
                        try {
                            compiledJJC.setText(compiler.print(((NodeInstrs) this.jajaCodeTree)));
                            errors.setText("ヽ(・∀・)ﾉ\n" + "Build successful!");
                        } catch (CompilerPrinterException e) {
                            errors.setText("( ╯°□°)╯ ┻━━┻\n" + "ERROR : Compiler printer error\n" + e.toString());
                        }

                    } catch (CompilerException e) {
                        errors.setText("( ╯°□°)╯ ┻━━┻\n" + "ERROR : Compiler error error\n" + e.toString());
                    }

                } catch (TypeCheckerException e) {
                    //ERROR TYPECHECKER
                    errors.setText("( ╯°□°)╯ ┻━━┻\n" + "ERROR : TypeChecker error\n" + e.toString());
                }
            } catch (AnalyserException e) {
                errors.setText("( ╯°□°)╯ ┻━━┻\n" + "ERROR : Syntax error\n" + e.toString());
            }
        }
    }


    @FXML
    public void openMiniJajaFile() {
        this.mainController.openMiniJajaFile();
    }

    @FXML
    public void newMJJFile() {
        this.mainController.newMJJFile();
    }

    @FXML
    public void saveMinijajaFile() {
        this.mainController.saveMinijajaFile();
    }

    @FXML
    public void compileMiniJaja() {
        this.compileMiniJaja(this.miniJajaToCompile);
    }

    @FXML
    public void runJJC() {
        this.errors.setText("");
        if (!compiledJJC.getText().isEmpty()) {
            Memory memory = new Memory(symbolTable);
            fr.femtost.disc.glcomp.m1comp6.interpreters.jjc.Interpreter jjcInterpreter = new fr.femtost.disc.glcomp.m1comp6.interpreters.jjc.Interpreter(jajaCodeTree);
            try {
                jjcInterpreter.eval(memory);
                errors.setText("ヽ(・∀・)ﾉ\n" + "Run successful !");

            } catch (fr.femtost.disc.glcomp.m1comp6.interpreters.jjc.InterpreterException e) {
                //ERROR MEMORY
                errors.setText("( ╯°□°)╯ ┻━━┻\n" + "ERROR : Memory error \n" + e.toString());
            }

        }

    }

    @FXML
    public void closePanel() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main.fxml"));
        Parent build_page_parent = null;
        try {
            build_page_parent = (Parent) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.mainController = loader.getController();
        Scene build_page_scene = new Scene(build_page_parent);
        stage.setScene(build_page_scene);
        stage.show();
        this.mainController.setMiniJajaToCompile(this.miniJajaToCompile.getText());
    }

    /*----------------------------------------------------------
        NON FXML
     */


}
