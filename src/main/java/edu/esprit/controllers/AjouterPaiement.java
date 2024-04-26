package edu.esprit.controllers;

import edu.esprit.entities.Paiement;
import edu.esprit.entities.User;
import edu.esprit.entities.Vol;
import edu.esprit.services.ServicePaiement;
import edu.esprit.services.ServiceVol;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static java.sql.Date.valueOf;

public class AjouterPaiement {

    @FXML
    private ComboBox comboBoxVol;

    @FXML
    private ComboBox comboBoxEmail;

    @FXML
    private ComboBox comboBoxMode;

    @FXML
    private TextField tfNumCarte;

    @FXML
    private Label labelErrorNumC;

    @FXML
    private Label labelErrorMode;

    @FXML
    private Label labelErrorEmail;

    @FXML
    private Label labelErrorVol;



    private final ServicePaiement sp = new ServicePaiement();

    private TicketVolFront ticketVolFront;

    public void setTicketVolFront(TicketVolFront ticketVolFront) {
        this.ticketVolFront = ticketVolFront;

        // Now you can use ticketVolFront to initialize your ComboBox or perform other actions
        initializeVolComboBox();
    }




    private void initializeVolComboBox() {
        if (ticketVolFront != null) {
            comboBoxVol.getItems().add(String.valueOf(ticketVolFront.getCode()));
            comboBoxVol.getSelectionModel().selectFirst();
        }
    }



    @FXML
    public void initialize() throws SQLException {

        List<String> paymentMethods = Arrays.asList( "Credit Card", "Debit Card", "PayPal"); // Add your payment methods here
        comboBoxMode.setItems(FXCollections.observableArrayList(paymentMethods));

        // Populate ComboBox with vol numbers
        initializeVolComboBox();

        // Populate ComboBox with emails
        List<String> emails = sp.getAllUserEmails();
        comboBoxEmail.setItems(FXCollections.observableArrayList(emails));

    }


    public boolean checkErrors() {
        boolean isValid = true;
        String numeroC = tfNumCarte.getText();

        // Check if the card number is a valid integer
        try {
            Integer.parseInt(numeroC);
        } catch (NumberFormatException e) {
            labelErrorNumC.setText("Card Number must be a valid integer.");
            isValid = false;
        }

        // Check if the Vol code ComboBox is empty
        if (comboBoxVol.getValue() == null) {
            labelErrorVol.setText("Please select a flight.");  // Assume labelError is a Label for showing error messages
            isValid = false;
        }

        // Check if the Email ComboBox is empty
        if (comboBoxEmail.getValue() == null) {
            labelErrorEmail.setText("Please select an email.");
            isValid = false;
        }

        // Check if the Payment Mode ComboBox is empty
        if (comboBoxMode.getValue() == null) {
            labelErrorMode.setText("Please select a payment mode.");
            isValid = false;
        }

        return isValid;
    }

    public void ajouterPaiementAction(ActionEvent actionEvent) throws SQLException, IOException {

        checkErrors();
        // Retrieve the selected vol code from the ComboBox
        String selectedVolCode = String.valueOf(comboBoxVol.getValue());

        // Retrieve the selected user email from the ComboBox
        String selectedUserEmail = comboBoxEmail.getValue().toString();

        // Retrieve other payment details from the text fields
        String selectedMode = comboBoxMode.getValue().toString();

        int numCarte = Integer.parseInt(tfNumCarte.getText());

        // Retrieve the corresponding vol and user objects based on the selected vol code and user email
        Vol selectedVol = sp.getVolByCode(selectedVolCode);
        User selectedUser = sp.getUserByEmail(selectedUserEmail);

        // Create a new Paiement object with the retrieved objects and payment details
        Paiement newPaiement = new Paiement(selectedVol, selectedUser, selectedMode, numCarte);

        // Add the new payment to the database
        sp.ajouter(newPaiement);

        // Optionally, you can display a message or perform other actions after adding the payment
        System.out.println("New Paiement added: " + newPaiement);

        // Clear the form fields
        showSuccessAlert();
    }

    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Operation Successful");
        alert.setHeaderText(null);
        alert.setContentText("The new flight has been added successfully!");
        alert.showAndWait();
    }
    }






