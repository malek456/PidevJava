package edu.esprit.controllers;

import edu.esprit.entities.Vol;
import edu.esprit.services.ServiceVol;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AfficherVol {

    @FXML
    private VBox VolContainer = new VBox();

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Pane selectedPane;
    private final ServiceVol sp = new ServiceVol();

    Map<Pane, Vol> paneVolMap = new HashMap<>();


    @FXML
    private void initialize() throws IOException {
        // Call method to populate TableView when the scene is loaded
        populateScrollPane();


    }

    private void populateScrollPane() throws IOException {
        // Get all the flights from the database
        Set<Vol> allVols = sp.getAll();

        // Clear the ticketContainer
        VolContainer.getChildren().clear();

        // Create a VBox to hold all the flight Panes
        VolContainer.setSpacing(50);

        // For each flight, load the FXML file and add it to the VBox
        for (Vol vol : allVols) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TicketVolFront.fxml"));
            Pane pane = loader.load();

            // Get the controller and update the ticket details
            TicketVolFront controller = loader.getController();
            controller.setVol(vol,pane,VolContainer);

            // Load and display the image
            String imagePath = vol.getImage();
            if (imagePath != null && !imagePath.isEmpty()) {
                File file = new File(imagePath);
                if (file.exists()) {
                    Image image = new Image(file.toURI().toString());
                    controller.setVolImage(image);
                }
            }

            controller.setAfficherVolController(this);

            // Add the pane to the VBox
            VolContainer.getChildren().add(pane);
        }

        // Set the VBox as the content of the ScrollPane
        scrollPane.setContent(VolContainer);
    }

}
