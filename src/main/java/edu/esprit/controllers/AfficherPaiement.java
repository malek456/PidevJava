package edu.esprit.controllers;

import edu.esprit.entities.Paiement;
import edu.esprit.services.ServicePaiement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AfficherPaiement {

    @FXML
    private HBox PaiementContainer = new HBox();

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Pane selectedPane;
    private final ServicePaiement sp = new ServicePaiement();

    Map<Pane, Paiement> panePaiementMap = new HashMap<>();

    @FXML
    private void initialize() throws IOException {
        // Call method to populate TableView when the scene is loaded
        populateScrollPane();


    }

    private void populateScrollPane() throws IOException {

        // Get all the flights from the database
        Set<Paiement> allPaiements = sp.getAll();

        // Clear the ticketContainer
        PaiementContainer.getChildren().clear();

        // Create a VBox to hold all the flight Panes
        PaiementContainer.setSpacing(50);

        // For each flight, load the FXML file and add it to the VBox
        for (Paiement paiement : allPaiements) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TicketPaiement.fxml"));
            Pane pane = loader.load();

            // Get the controller and update the ticket details
            TicketPaiement controller = loader.getController();
            controller.setPaiement(paiement,pane,PaiementContainer);
            controller.setAfficherPaiementController(this);

            // Add the pane to the VBox
            PaiementContainer.getChildren().add(pane);
        }

        // Set the VBox as the content of the ScrollPane
        scrollPane.setContent(PaiementContainer);
    }

    public void NavigateHomeAction(ActionEvent actionEvent) {
    }
}
