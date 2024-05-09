package edu.esprit.controllers;
import edu.esprit.entities.Hebergement;
import edu.esprit.services.ServiceHebergement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class HebergementDesign {



    @FXML
    private Label labelName;

    @FXML
    private ImageView labelImage;

    @FXML
    private Label labelLocation;

    @FXML
    private Label labelDescription;

    @FXML
    private Label labelType;

    @FXML
    private Label labelActivities;

    @FXML
    private Label labelPrice;

    @FXML
    private Label labelLatitude;

    @FXML
    private Label labelLongitude;


    @FXML
    private final ServiceHebergement sp = new ServiceHebergement();

    @FXML
    private Pane thisPane;

    @FXML
    private VBox HebergementContainer;


    private static HebergementDesign instance;
    private Hebergement currentHebergement;



    private AjouterHebergement AjouterHebergement;
    private edu.esprit.controllers.AfficherHebergement AfficherHebergement;


    public HebergementDesign() {
    }

    public void setHebergement(Hebergement Hebergement, Pane pane, VBox vbox) throws FileNotFoundException {
        if (Hebergement != null) {

           // labelLongitude.setText(Double.toString(Hebergement.getLongitude()));
           // labelLatitude.setText(Double.toString(Hebergement.getLatitude()));
            labelPrice.setText(String.valueOf(Hebergement.getPrice()));
            labelActivities.setText(Hebergement.getActivities());
            labelType.setText(Hebergement.getSelectedType());
            labelDescription.setText(Hebergement.getDescription());
            labelLocation.setText(Hebergement.getLocation());
            labelName.setText(Hebergement.getName());
        }
        // Load and set the image
        String imagePath = Hebergement.getPicture();
        if (imagePath != null && !imagePath.isEmpty()) {
            File file = new File(imagePath);
            if (file.exists()) {
                Image image = new Image(file.toURI().toString());
                labelImage.setImage(image);
            }
        }

        thisPane = pane;
        HebergementContainer = vbox;
        currentHebergement = Hebergement;
    }

    public static HebergementDesign getInstance() {
        if (instance == null) {
            instance = new HebergementDesign();
        }
        return instance;
    }

    public Hebergement getCurrentHebergement(){
        return currentHebergement;
    }

    public  void setCurrentHebergement(Hebergement currentHebergement) {
        this.currentHebergement= currentHebergement;
    }

    public void setHebergementImage(Image image) {
        labelImage.setImage(image);
    }

    public  void setAjouterHebergementController(AjouterHebergement HebergementController) {
        this.AjouterHebergement = HebergementController;
    }

    public void deleteAction(ActionEvent actionEvent) {

        // Get the source of the action event (the "Delete" button)
        Button deleteButton = (Button) actionEvent.getSource();

        // Get the Pane that the "Delete" button belongs to
        Pane pane = (Pane) deleteButton.getParent();


        if (currentHebergement != null) {
            System.out.println("Current Vol: " + currentHebergement);
            System.out.println("Vol Controller: " + AjouterHebergement);
            // Remove the pane from the VBox
            HebergementContainer.getChildren().remove(pane);

            // Delete the Vol from the database
            sp.supprimer(currentHebergement.getId()); // replace 'getId' with the method that gets the ID of the Vol

            // Remove the Vol from the map
            HebergementContainer.getChildren().remove(thisPane);
        } else {
            // If no Vol is associated with the pane, handle it accordingly
            System.out.println("No Vol associated with this pane.");
        }
    }

    public void setAfficherHebergementController(AfficherHebergement HebergementController) {
        this.AfficherHebergement = HebergementController;
    }


    public void updateAction(ActionEvent actionEvent) {
        // Get the source of the action event (the "Update" button)
        Button updateButton = (Button) actionEvent.getSource();

        HebergementDesign ticketVol = new HebergementDesign().getInstance();


        // Get the Pane that the "Update" button belongs to
        Pane pane = (Pane) updateButton.getParent();

        if (currentHebergement != null) {
            // Populate the form with the data of the selected Vol
            AjouterHebergement.populateForm(currentHebergement);
            ticketVol.setCurrentHebergement(currentHebergement);
        } else {
            // If no Vol is associated with the pane, handle it accordingly
            System.out.println("No Hotel associated with this pane.");
        }
    }


    public void return_V(ActionEvent actionEvent) {
        try {
            // Charger la racine à partir du fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherVoyage.fxml"));
            Parent root = loader.load();

            // Obtenir la scène actuelle
            Scene currentScene = ((Node) actionEvent.getSource()).getScene();

            // Remplacer la racine de la scène actuelle par la nouvelle racine chargée à partir du FXML
            currentScene.setRoot(root);

            // Obtenir la fenêtre actuelle à partir de la scène
            Stage stage = (Stage) currentScene.getWindow();

            // Mettre à jour la scène sur la fenêtre
            stage.setScene(currentScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void googlemap(ActionEvent actionEvent) {
        double latitude = currentHebergement.getLatitude();
        double longitude = currentHebergement.getLongitude();

        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        // Construction de l'URL avec les coordonnées de latitude et de longitude
        String url = "file:///C:/Users/user/IdeaProjects/getionPersonneFP22/getionPersonneFP2/getionPersonne3A4F/src/main/java/edu/esprit/controllers/map.html?latitude=" + latitude + "&longitude=" + longitude;
        webView.getEngine().load(url);

        // Création d'une nouvelle scène pour afficher la WebView
        Scene scene = new Scene(webView, 800, 600);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }



}



