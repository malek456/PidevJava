package edu.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import javafx.scene.control.Button;
import java.awt.Desktop;
import java.net.URI;
import java.net.URL;

public class StripePaymentAjout {

    @FXML
    private WebView paymentWebView;

    @FXML
    private Button actionButton;

    public void initialize() {
        try {
            // Assuming payment.html is in the same directory as your FXML file or specified path
            URL url = getClass().getResource("/payment.html");
            if (url != null) {
                paymentWebView.getEngine().load(((URL) url).toExternalForm());
            } else {
                System.out.println("Resource not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        paymentWebView.getEngine().setJavaScriptEnabled(true);

        actionButton.setOnAction(event -> handleActionButton());
    }

    @FXML
    private void handleActionButton() {
        // Action handler code here
        System.out.println("Button was clicked!");
        // Open the payment form in the user's default web browser
        openWebPage("http://localhost:63342/pidevJava/getionPersonne3A4/payment.html?_ijt=v13k6bspmhcskqu69j6q0rmn53&_ij_reload=RELOAD_ON_SAVE");
    }

    private void openWebPage(String url) {
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(url));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
