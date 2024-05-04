package edu.esprit.controllers;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Sms {

    // Vos identifiants Twilio
   // private static final String ACCOUNT_SID = "AC7b7537cc50f2dde44ab0a8f3e14bb46c";
   // private static final String AUTH_TOKEN = "6ecc2848355a19a717f3d7fd29962983";

    @FXML
    private TextField toField;

    @FXML
    private TextArea messageArea;

    @FXML
    private Button sendButton;

    @FXML
    public void initialize() {
        // Initialisation de Twilio avec vos identifiants
        //Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

       // sendButton.setOnAction(event -> {
           // String to = toField.getText();
          //  String messageText = "Bonjour " + to + ", Nous vous remercions pour votre confiance et sommes ravis de vous confirmer votre réservation. Votre réservation a été enregistrée avec succès pour la date à l'hôtel  N'hésitez pas à nous contacter si vous avez des questions supplémentaires ou des demandes spécifiques. Nous nous réjouissons de vous accueillir bientôt.";

            // Envoi du SMS
          //  Message message = Message.creator(new PhoneNumber("+21650271538"), new PhoneNumber("+13254132262"), messageText).create() ;       });
    }
}