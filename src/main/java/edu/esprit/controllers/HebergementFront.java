package edu.esprit.controllers;

import edu.esprit.entities.Hebergement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
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

public class HebergementFront {

    @FXML
    private Label labelName;

    @FXML
    private Label labelLocation;

    @FXML
    private Label labelDescription;

    @FXML
    private Label labelType;

    @FXML
    private Label labelActivities;

    @FXML
    private Label labelPrice;

    @FXML
    private ImageView labelImage;

    @FXML
    private Pane thisPane;

    @FXML
    private VBox HebergementContainer;


    private Hebergement currentHebergement;
    private AfficherHebergement AfficherHebergement;


    public void setHebergement(Hebergement Hebergement, Pane pane, VBox vbox) throws FileNotFoundException {
        labelName.setText(Hebergement.getName());
        labelLocation.setText(Hebergement.getLocation());
        labelDescription.setText(Hebergement.getDescription());
        labelType.setText(Hebergement.getSelectedType());
        labelActivities.setText(Hebergement.getActivities());
        labelPrice.setText(String.valueOf(Hebergement.getPrice()));



        // Load and set the image
        String imagePath = Hebergement.getPicture();
        if (imagePath != null && !imagePath.isEmpty()) {
            File file = new File(imagePath);
            if (file.exists()) {
                Image image = new Image(file.toURI().toString());
                labelImage.setImage(image);
            }
        }

        thisPane = pane;
        HebergementContainer = vbox;
        currentHebergement = Hebergement;
    }


    public void setHebergementImage(Image image) {
        labelImage.setImage(image);
    }

    public void setAfficherHebergementController(AfficherHebergement HebergementController) {
        this.AfficherHebergement = HebergementController;
    }


}