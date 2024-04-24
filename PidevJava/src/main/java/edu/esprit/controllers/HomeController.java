package com.example.reclamation.controllers;

import com.example.reclamation.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {


    @FXML
    private Circle mycircle;
    @FXML
    void navigateToReclamation(ActionEvent event) {
        Scene scene =null;
        try{
            //Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/reclamation/AjouterReclamationFXML.fxml"));
            Parent root = loader.load();
            // Show the scene
            scene = mycircle.getScene();
            scene.setRoot(root);

            Stage stage = (Stage) scene.getWindow();
            stage.setTitle("byeee");
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }

    }

}
