<<<<<<< HEAD
package com.example.reclamation.controllers;

import com.example.reclamation.models.User;
=======
package edu.esprit.controllers;

import edu.esprit.entities.User;
>>>>>>> ba038a7 (metiers+api)
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
<<<<<<< HEAD
=======
import javafx.scene.control.Button;
>>>>>>> ba038a7 (metiers+api)
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {


    @FXML
<<<<<<< HEAD
    private Circle mycircle;
=======
    private Button reclamFXML;
    @FXML
    AjouterReclamationController ajouterReclamationController;
>>>>>>> ba038a7 (metiers+api)
    @FXML
    void navigateToReclamation(ActionEvent event) {
        Scene scene =null;
        try{
            //Stage stage = new Stage();
<<<<<<< HEAD
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/reclamation/AjouterReclamationFXML.fxml"));
            Parent root = loader.load();
            // Show the scene
            scene = mycircle.getScene();
            scene.setRoot(root);

            Stage stage = (Stage) scene.getWindow();
            stage.setTitle("byeee");
=======
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home1.fxml"));
            Parent root = loader.load();
            // Show the scene
            scene = reclamFXML.getScene();
            scene.setRoot(root);

            Stage stage = (Stage) scene.getWindow();
            stage.setTitle("afficher reclamation");
>>>>>>> ba038a7 (metiers+api)
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }

    }

}
