package edu.esprit.controllers;

import edu.esprit.entities.Hebergement;
import edu.esprit.services.ServiceHebergement;
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

public class AfficherHebergement {

    @FXML
    private VBox HebergementContainer = new VBox();

    @FXML
    private ScrollPane scrollPane;

    private final ServiceHebergement sp = new ServiceHebergement();

    @FXML
    private void initialize() throws IOException {
// Appel à la méthode pour remplir TableView lorsque la scène est chargée
        populateScrollPane();

    }

    private void populateScrollPane() throws IOException {
        // Get all the Hebergements from the database
        Set<Hebergement> allHebergements = sp.getAll();

        // Clear the HebergementContainer
        HebergementContainer.getChildren().clear();

// Crée une VBox pour contenir tous les Hebergements
        HebergementContainer.setSpacing(50);

// Pour chaque hebergement, chargez le fichier FXML et ajoutez-le à la VBox
        for (Hebergement Hebergement : allHebergements) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/HebergementFront.fxml"));
            Pane pane = loader.load();

// Récupère le contrôleur et met à jour les détails du Hebergement
            HebergementDesign controller = loader.getController();
            controller.setHebergement(Hebergement,pane,HebergementContainer);

// Charge et affiche l'image
            String imagePath = Hebergement.getPicture();
            if (imagePath != null && !imagePath.isEmpty()) {
                File file = new File(imagePath);
                if (file.exists()) {
                    Image image = new Image(file.toURI().toString());
                    controller.setHebergementImage(image);
                }
            }

            controller.setAfficherHebergementController(this);

            // Ajout le  pane dans VBox
            HebergementContainer.getChildren().add(pane);
        }

// Définit la VBox comme contenu du ScrollPane
        scrollPane.setContent(HebergementContainer);
    }

}