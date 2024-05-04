package edu.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class TravelInfo {
    @FXML
    private ComboBox<String> countryComboBox;
    @FXML
    private TextArea advisoryTextArea;

    @FXML
    private void onCountrySelected() {
        String countryCode = countryComboBox.getValue();
        try {
            String advisoryInfo = TravelAdvisory.fetchTravelAdvisory(countryCode);
            advisoryTextArea.setText(advisoryInfo);
        } catch (IOException | InterruptedException e) {
            advisoryTextArea.setText("Failed to fetch travel advisory: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
