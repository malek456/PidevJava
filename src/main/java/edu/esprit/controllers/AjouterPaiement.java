package edu.esprit.controllers;

import edu.esprit.entities.Paiement;
import edu.esprit.entities.User;
import edu.esprit.entities.Vol;
import edu.esprit.services.ServicePaiement;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.stream.Stream;

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

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private Pane paneProgressor;



    private final ServicePaiement sp = new ServicePaiement();

    private TicketVolFront ticketVolFront;

    public void setTicketVolFront(TicketVolFront ticketVolFront) {
        this.ticketVolFront = ticketVolFront;

        // Now you can use ticketVolFront to initialize your ComboBox or perform other actions
        initializeVolComboBox();

        paneProgressor.setVisible(false);
        progressIndicator.setProgress(-1);
        progressIndicator.setVisible(false);
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
        sendEmail(progressIndicator,paneProgressor,selectedUserEmail,numCarte);

    }



    private void sendEmail(ProgressIndicator progressIndicator , Pane paneProgressor,String selectedUserEmail, int numCarte  ){
        final String username = "bouzidimalek01@gmail.com";
        final String password = "njir jqdr dvwo cale";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        new Thread(() -> {
            try {
                Platform.runLater(() -> {
                    paneProgressor.setVisible(true);
                    progressIndicator.setVisible(true);
                    progressIndicator.setProgress(-1);
                });

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("bouzidimalek01@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("bouzidimalek01@gmail.com"));
                message.setSubject("Payment Confirmation");

                // Load HTML content from a file
                String htmlContent = loadEmailTemplate("src/main/resources/email_Template.html");
                htmlContent = htmlContent.replace("{{name}}", selectedUserEmail)
                        .replace("{{cardNumber}}", String.valueOf(numCarte));
                message.setContent(htmlContent, "text/html");

                Transport.send(message);

                System.out.println("Done");
                Platform.runLater(this::showSuccessAlert);

            } catch (MessagingException e) {
                e.printStackTrace();
                System.out.println("Failed to send email");
            } finally {
                Platform.runLater(() -> {
                    progressIndicator.setVisible(false);
                    paneProgressor.setVisible(false);
                });
            }
        }).start();

    }

    private String loadEmailTemplate(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }



    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Operation Successful");
        alert.setHeaderText(null);
        alert.setContentText("you payment has bee confirmed check you email Box");
        alert.showAndWait();
    }

    public void NavigateDashboardAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ajouterVol.fxml"));
            // Getting the scene and setting the root
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
    }
}






