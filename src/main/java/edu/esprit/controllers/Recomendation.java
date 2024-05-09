package edu.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Recomendation {

    @FXML
    private ImageView imageView;
    @FXML
    private Label label;

    public void setImageAndText(String imageUrl, String text) {
        Image image = new Image(imageUrl, true);
        imageView.setImage(image);
        label.setText(text);
    }
}
