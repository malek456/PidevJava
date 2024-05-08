package edu.esprit.tests;

import edu.esprit.controllers.AdminController;
import edu.esprit.controllers.UserController;
import edu.esprit.controllers.indexController;
import edu.esprit.entities.SessionManager;
import edu.esprit.utils.connexion;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //SessionManager.endSession();
        if (SessionManager.hasSession())
        {
            if (SessionManager.getUserRole().contains("ROLE_ADMIN")) {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/dashAdmin.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 1535, 857);
                stage.setTitle("Hello!");
            AdminController adminController = fxmlLoader.getController();
            adminController.setStage(stage);
                connexion.getInstance();
                stage.setScene(scene);
                stage.show();
            } else {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/front.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 1535, 857);
                stage.setTitle("Hello!");
                indexController indexController0 = fxmlLoader.getController();
                indexController0.setStage(stage);
                connexion.getInstance();
                stage.setScene(scene);
                stage.show();
            }
        }
        else {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);
            stage.setTitle("Hello!");
            UserController userController = fxmlLoader.getController();
            userController.setStage(stage);
            connexion.getInstance();
            stage.setScene(scene);
            stage.show();
        }
    }
    public static void main(String[] args) {
        launch();
    }
}