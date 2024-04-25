package edu.esprit.projecthope.tests;

import edu.esprit.projecthope.controllers.UserController;
import edu.esprit.projecthope.utils.connexion;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("../login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1920, 1000);
        stage.setTitle("Hello!");
        UserController userController = fxmlLoader.getController();
        userController.setStage(stage);
        connexion.getInstance();
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}