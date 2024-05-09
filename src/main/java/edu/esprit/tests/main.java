package com.example.reclamation.test;

import com.example.reclamation.models.User;
import com.example.reclamation.services.ServiceUser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FxMain extends Application {
    static User globalUserData; // Store your user data here

    public static User getGlobalUserData() {
        return globalUserData;
    }

    public void setGlobalUserData(User data) {
        globalUserData = data;
    }

    public static void main(String[] args) {
        launch();
    }
    ServiceUser su = new ServiceUser();

    @Override
    public void start(Stage stage) throws Exception {


        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/reclamation/home.fxml"));
            //FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/reclamation/AfficherReclamationBackFXML.fxml"));
            Parent root = loader.load();
            User user  = su.selectOne_by_id(2);
            setGlobalUserData(user);
            // Show the scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
