package edu.esprit.controllers;

import edu.esprit.entities.Hebergement;
import edu.esprit.services.ServiceHebergement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.ArrayList;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import java.io.File;
import java.io.IOException;
import java.util.*;
import javafx.scene.control.ComboBox ;

public class AfficherHebergement {

    @FXML
    private VBox HebergementContainer = new VBox();

    @FXML
    private ComboBox<String> comboBoxSortItem;

    @FXML
    private ComboBox<String> comboBoxOrder;


    @FXML
    private TextField tfSearch;


    @FXML
    private ScrollPane scrollPane;

    private final ServiceHebergement sp = new ServiceHebergement();



    @FXML
    private void initialize() throws IOException {
// Appel à la méthode pour remplir TableView lorsque la scène est chargée
        populateScrollPane();

        // Remplir comboBoxSortItem avec les options Name et Location
        comboBoxSortItem.getItems().addAll("Name", "Location");

        // Remplir comboBoxOrder avec les options Croissant et Décroissant
        comboBoxOrder.getItems().addAll("Croissant", "Décroissant");

    }





    public void handleSortChange() throws IOException {
        if (comboBoxSortItem.getValue() == null || comboBoxOrder.getValue() == null) {
            return; // Do nothing if either combo box has no selected value
        }

        String sortField = comboBoxSortItem.getValue().toString();
        String sortOrder = comboBoxOrder.getValue().toString();

        Comparator<Hebergement> comparator = (sortField.equals("Name"))
                ? Comparator.comparing(Hebergement::getName)
                : Comparator.comparing(Hebergement::getLocation);

        if ("Décroissant".equals(sortOrder)) {
            comparator = comparator.reversed();
        }

        Set<Hebergement> allHebergements = sp.getAll();

        // Use Stream to sort hebergements based on the comparator
        List<Hebergement> sortedHebergements = allHebergements.stream()
                .sorted(comparator)
                .collect(Collectors.toList());

        updateUI(sortedHebergements);
    }

    private void updateUI(List<Hebergement> sortedHebergements) throws IOException {
        // Clear the HebergementContainer to remove old entries
        HebergementContainer.getChildren().clear();
        HebergementContainer.setSpacing(50);

        // Use the sorted list of Hebergements to update the UI
        for (Hebergement hebergement : sortedHebergements) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/HebergementFront.fxml"));
            Pane pane = loader.load();

            HebergementDesign controller = loader.getController();
            controller.setHebergement(hebergement, pane, HebergementContainer);

            String imagePath = hebergement.getPicture();
            if (imagePath != null && !imagePath.isEmpty()) {
                File file = new File(imagePath);
                if (file.exists()) {
                    Image image = new Image(file.toURI().toString());
                    controller.setHebergementImage(image);
                }
            }

          //  controller.setAjouterHebergementController(this); // Ensure the controller is properly initialized
            HebergementContainer.getChildren().add(pane);
        }
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

    public void searchAction(javafx.scene.input.KeyEvent keyEvent) {
        String searchText = tfSearch.getText().trim().toLowerCase(); // Normalize search text to lower case
        Set<Hebergement> allHebergements = sp.getAll();

        // Clear the VolContainer to remove previous search results
        HebergementContainer.getChildren().clear();

        // Use Stream to filter vols based on search criteria and process them
        allHebergements.stream()
                // Filter vols that match the search criteria
                .filter(Hebergement -> Hebergement.getName().toLowerCase().contains(searchText))
                .forEach(Hebergement -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/HebergementFront.fxml"));
                        Pane pane = loader.load();

                        // Get the controller and update the ticket details
                        HebergementDesign controller = loader.getController();
                        controller.setHebergement(Hebergement, pane, HebergementContainer);

                        // Optionally load and display the image
                        String imagePath = Hebergement.getPicture();
                        if (imagePath != null && !imagePath.isEmpty()) {
                            File file = new File(imagePath);
                            if (file.exists()) {
                                Image image = new Image(file.toURI().toString());
                                controller.setHebergementImage(image);
                            }
                        }


                        // Add the matching pane to the container
                        HebergementContainer.getChildren().add(pane);
                    } catch (IOException e) {
                        e.printStackTrace(); // Handle or log the exception
                    }
                });
    }
}