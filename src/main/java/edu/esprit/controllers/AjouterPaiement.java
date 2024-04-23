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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
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

    private final ServicePaiement sp = new ServicePaiement();


    @FXML
    public void initialize() throws SQLException {

        List<String> paymentMethods = Arrays.asList( "Credit Card", "Debit Card", "PayPal"); // Add your payment methods here
        comboBoxMode.setItems(FXCollections.observableArrayList(paymentMethods));

        // Populate ComboBox with vol numbers
        List<String> volNumbers = sp.getAllVolNumbers();
        comboBoxVol.setItems(FXCollections.observableArrayList(volNumbers));

        // Populate ComboBox with emails
        List<String> emails = sp.getAllUserEmails();
        comboBoxEmail.setItems(FXCollections.observableArrayList(emails));
    }

    public void ajouterPaiementAction(ActionEvent actionEvent) throws SQLException, IOException {
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
        //clearFormFields();
    }





}
