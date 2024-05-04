package edu.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import java.net.URL;
import java.util.ResourceBundle;

public class Rating implements Initializable {

    @FXML
    private ToggleButton star1;

    @FXML
    private ToggleButton star2;

    @FXML
    private ToggleButton star3;

    @FXML
    private ToggleButton star4;

    @FXML
    private ToggleButton star5;

    @FXML
    private TextField tfRating;
    private ToggleButton[] stars;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stars = new ToggleButton[]{star1, star2, star3, star4, star5};
    }

    @FXML
    void handleRating(ActionEvent event) {
        ToggleButton selectedStar = (ToggleButton) event.getSource();
        int rating = 0;
        for (int i = 0; i < stars.length; i++) {
            if (stars[i] == selectedStar) {
                rating = i + 1;
            }
            stars[i].setSelected(stars[i] == selectedStar);
        }
        System.out.println("Rating: " + rating);
        tfRating.setText(String.valueOf(rating));
    }
}

