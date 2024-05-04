package edu.esprit.controllers;

import edu.esprit.entities.Hebergement;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;

public class HotelFront {

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
    private HBox HotelContainer;


    private Hebergement currentHotel;
    private AfficherHebergement AfficherHotel;


    public void setHebergement(Hebergement hotel, Pane pane, HBox vbox) throws FileNotFoundException {
        labelName.setText(hotel.getName());
        labelLocation.setText(hotel.getLocation());
        labelDescription.setText(hotel.getDescription());
        labelType.setText(hotel.getSelectedType());
        labelActivities.setText(hotel.getActivities());
        labelPrice.setText(String.valueOf(hotel.getPrice()));



        // Load and set the image
        String imagePath = hotel.getPicture();
        if (imagePath != null && !imagePath.isEmpty()) {
            File file = new File(imagePath);
            if (file.exists()) {
                Image image = new Image(file.toURI().toString());
                labelImage.setImage(image);
            }
        }

        thisPane = pane;
        HotelContainer = vbox;
        currentHotel = hotel;
    }


    public void setHotelImage(Image image) {
        labelImage.setImage(image);
    }

    public void setAfficherHotelController(AfficherHebergement HotelController) {
        this.AfficherHotel = HotelController;
    }

}
