package edu.esprit.controllers;

import edu.esprit.entities.Event;
import edu.esprit.services.ServiceEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class AfficherEvenement {

    @FXML
    private VBox EventContainer = new VBox();

    @FXML
    private ScrollPane scrollPane;

    private final ServiceEvent sp = new ServiceEvent();

    @FXML
    private void initialize() throws IOException {
// Appel à la méthode pour remplir TableView lorsque la scène est chargée
        populateScrollPaneEvent();

    }

    private void populateScrollPaneEvent() throws IOException {
        // Get all the events from the database
        Set<Event> allEvents = sp.getAll();

        // Clear the EventContainer
        EventContainer.getChildren().clear();

        // Create a VBox to hold all the events
        EventContainer.setSpacing(50);

        // For each event, load the FXML file and add it to the VBox
        for (Event event : allEvents) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UniqueEvent.fxml"));
            Pane pane = loader.load();

            // Get the controller and update the event details
            UniqueEvent controller = loader.getController();
            controller.setUniqueEvent(event, pane, EventContainer);

            // Load and display the image
            String imagePath = event.getImage();
            if (imagePath != null && !imagePath.isEmpty()) {
                File file = new File(imagePath);
                if (file.exists()) {
                    Image image = new Image(file.toURI().toString());
                    controller.setEventImage(image);
                }
            }

            // Populate the ScrollPane with comments for this event
            controller.populateScrollPane(event.getId());

            controller.setAfficherEvenementController(this);

            // Add the pane to the VBox
            EventContainer.getChildren().add(pane);
        }

        // Set the VBox as the content of the ScrollPane
        scrollPane.setContent(EventContainer);
    }


}
