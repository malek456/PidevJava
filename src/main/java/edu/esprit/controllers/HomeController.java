package edu.esprit.controllers;

import edu.esprit.entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {


    @FXML
    private Button reclamFXML;
    @FXML
    AjouterReclamationController ajouterReclamationController;
    @FXML
    void navigateToReclamation(ActionEvent event) {
        Scene scene =null;
        try{
            //Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home1.fxml"));
            Parent root = loader.load();
            // Show the scene
            scene = reclamFXML.getScene();
            scene.setRoot(root);

            Stage stage = (Stage) scene.getWindow();
            stage.setTitle("afficher reclamation");
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }

    }

}
