package edu.esprit.controllers;

import edu.esprit.entities.Voyage;
import edu.esprit.services.ServiceVoyage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class AfficherVoyage {

    @FXML
    private HBox VoyageContainer = new HBox();

    @FXML
    private ScrollPane scrollPane;

    private final ServiceVoyage sp = new ServiceVoyage(); //  instance de ServiceVoyage


    @FXML
    private void initialize() throws IOException { //elle sera appelée automatiquement lorsque le fichier FXML associé est chargé
// Appel à la méthode pour remplir TableView lorsque la scène est chargée
        populateScrollPane();

    }




    private void populateScrollPane() throws IOException {
        // Get all the Voyages from the database
        Set<Voyage> allVoyages = sp.getAll();

        // Clear the VoyageContainer
        VoyageContainer.getChildren().clear();

// Crée une HBox pour contenir tous les Voyages
        VoyageContainer.setSpacing(50);

// Pour chaque voyage, chargez le fichier FXML et ajoutez-le à la HBox
        for (Voyage Voyage : allVoyages) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/VoyageFront.fxml"));
            Pane pane = loader.load();

// Récupère le contrôleur et met à jour les détails du Voyage
            VoyageFront controller = loader.getController();
            controller.setVoyage(Voyage,pane,VoyageContainer);

// Charge et affiche l'image
            String imagePath = Voyage.getPicture();
            if (imagePath != null && !imagePath.isEmpty()) {
                File file = new File(imagePath);
                if (file.exists()) {
                    Image image = new Image(file.toURI().toString());
                    controller.setVoyageImage(image);
                }
            }
            // Enregistrez la consultation de destination pour chaque voyage
            sp.registerDestinationVisit(Voyage.getDestination());


            controller.setAfficherVoyageController(this);

            // Ajout le  pane dans HBox
            VoyageContainer.getChildren().add(pane);
        }

// Définit la HBox comme contenu du ScrollPane
        scrollPane.setContent(VoyageContainer);
    }


    @FXML
    void openWeatherInterface(ActionEvent event) {
        try {
            // Chargez le fichier FXML de l'interface utilisateur de WeatherController
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Weather.fxml"));
            Parent root = loader.load();

            // Créez une nouvelle scène
            Scene scene = new Scene(root);

            // Obtenez la scène actuelle à partir de l'événement
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Créez une nouvelle fenêtre pour afficher l'interface utilisateur WeatherController
            Stage weatherStage = new Stage();
            weatherStage.setScene(scene);

            // Affichez la nouvelle fenêtre
            weatherStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void afficherStatistiques(ActionEvent event) {
        // Afficher les statistiques ici
        // Par exemple, vous pouvez ouvrir une nouvelle fenêtre pour afficher les statistiques
        try {
            // Chargez le fichier FXML de l'interface utilisateur des statistiques
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Stat.fxml"));
            Parent root = loader.load();

            // Créez une nouvelle scène
            Scene scene = new Scene(root);

            // Créez une nouvelle fenêtre pour afficher l'interface utilisateur des statistiques
            Stage stage = new Stage();
            stage.setScene(scene);

            // Affichez la nouvelle fenêtre
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}