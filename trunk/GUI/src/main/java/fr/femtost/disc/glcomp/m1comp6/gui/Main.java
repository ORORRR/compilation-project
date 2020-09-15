package fr.femtost.disc.glcomp.m1comp6.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main.fxml"));

        Parent root = loader.load();
        Scene scene = new Scene(root);

        MainController mainController = loader.getController();

        root.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());

        stage.setTitle("MiniJaja Virtual Machine");
        stage.setScene(scene);
        stage.setMaximized(true);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
