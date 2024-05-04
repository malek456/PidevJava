package edu.esprit.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
public class Mail {
    @FXML
    private TextField recipientField;

    @FXML
    private TextField subjectField;

    @FXML
    private TextArea bodyArea;

    public Mail(String recipient, String subject, String body) {
    }

    @FXML
    private void sendMail() {
        String recipient = recipientField.getText();
        String subject = subjectField.getText();
        String body = bodyArea.getText();

        Mail mail = new Mail(recipient, subject, body);

        // Call your email sending API here
        // Example: MailAPI.sendMail(mail);

        // Clear fields after sending
        recipientField.clear();
        subjectField.clear();
        bodyArea.clear();
    }
}

