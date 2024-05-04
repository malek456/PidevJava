package edu.esprit.controllers;

import edu.esprit.entities.Voyage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class VoyageFront {

    @FXML
    private Label labelDestination;

    @FXML
    private Label labelOffer;

    @FXML
    private ImageView labelImage;

    @FXML
    private Pane thisPane;

    @FXML
    private HBox VoyageContainer;





    private static VoyageDesign instance;
    private Voyage currentVoyage;
    private AfficherVoyage AfficherVoyage;


    public void setVoyage(Voyage voyage, Pane pane, HBox vbox) throws FileNotFoundException {

        labelOffer.setText(voyage.getOffer());
        labelDestination.setText(voyage.getDestination());

        // Load and set the image
        String imagePath = voyage.getPicture();
        if (imagePath != null && !imagePath.isEmpty()) {
            File file = new File(imagePath);
            if (file.exists()) {
                javafx.scene.image.Image image = new Image(file.toURI().toString());
                labelImage.setImage(image);
            }
        }

        thisPane = pane;
        VoyageContainer = vbox;
        currentVoyage = voyage;
    }


    public void setVoyageImage(Image image) {
        labelImage.setImage(image);
    }

    public void setAfficherVoyageController(AfficherVoyage VoyageController) {
        this.AfficherVoyage = VoyageController;
    }

    public void Hebergement_B(javafx.event.ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherHebergement.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading FXML file: " + e.getMessage());
        }

    }
}
