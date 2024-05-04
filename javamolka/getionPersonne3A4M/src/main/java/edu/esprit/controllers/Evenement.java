package edu.esprit.controllers;

import edu.esprit.entities.Event;
import edu.esprit.services.ServiceEvent;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.time.LocalDate;

public class Evenement {

    @FXML
    private Label labelType;

    @FXML
    private Label labelDateDebut;

    @FXML
    private Label labelDateFin;

    @FXML
    private Label labelLieu;

    @FXML
    private Label labelPrix;


    @FXML
    private ImageView labelImage;

    @FXML
    private Pane thisPane;

    private ComboBox<String> eventTypeComboBox;


    @FXML
    private HBox EventContainer;
    @FXML
    private ComboBox<String> typeComboBox;

    private static Evenement instance;
    private Event currentEvent;

    private AjouterEvent AjouterEvent;


    @FXML
    private final ServiceEvent sp = new ServiceEvent();


    private void showAlert(String s) {
    }

    private boolean validateLieux(String lieux) {
        // Expression régulière pour autoriser les lettres et les espaces uniquement
        String lieuxRegex = "^[a-zA-Z\\s]+$";
        return lieux.matches(lieuxRegex);
    }
    private boolean validatePrix(String prix) {
        // Expression régulière pour valider un nombre décimal
        String prixRegex = "^[0-9]+(\\.[0-9]{1,2})?$";
        return prix.matches(prixRegex);
    }
    private boolean validateDateRange(String startDate, String endDate) {
        // Convertir les chaînes de date en objets LocalDate
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        // Vérifier si la date de fin est postérieure à la date de début
        if (end.isBefore(start)) {
            // Afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("La date de fin doit être postérieure à la date de début.");
            alert.showAndWait();
            return false;
        }

        return true;
    }

    public void setEventImage(Image image)// Cette méthode prend une image en entrée et l'affiche dans un ImageView.

    {
        labelImage.setImage(image);
    }

    public void setAjouterEventController(AjouterEvent eventController) {
        this.AjouterEvent = eventController;
    }


    public static Evenement getInstance() {
        if (instance == null) {
            instance = new Evenement();
        }
        return instance;
    }

    public Event getCurrentEvent() {
        return currentEvent;
    }

    public void setCurrentEvent(Event currentEvent) {
        this.currentEvent = currentEvent;
    }

    public void setEvent(Event Event, Pane pane, HBox vbox) throws FileNotFoundException {
        labelType.setText(Event.getType());
        labelDateDebut.setText(Event.getDatedebut().toString());
        labelDateFin.setText(Event.getDatefin().toString());
        labelLieu.setText(Event.getLieux());
        labelPrix.setText(String.valueOf(Event.getPrix()));


        // Load and set the image
        String imagePath = Event.getImage();
        if (imagePath != null && !imagePath.isEmpty()) {
            File file = new File(imagePath);
            if (file.exists()) {
                Image image = new Image(file.toURI().toString());
                labelImage.setImage(image);
            }
        }

        thisPane = pane;
        EventContainer = vbox;
        currentEvent = Event;
    }

    public void deleteAction(ActionEvent actionEvent) {

        // Get the source of the action event (the "Delete" button)
        Button deleteButton = (Button) actionEvent.getSource();

        // Get the Pane that the "Delete" button belongs to
        Pane pane = (Pane) deleteButton.getParent();


        if (currentEvent != null) {
            System.out.println("Current Vol: " + currentEvent);
            System.out.println("Vol Controller: " + AjouterEvent);
            // Remove the pane from the VBox
            EventContainer.getChildren().remove(pane);

            // Delete the Vol from the database
            sp.supprimer(currentEvent.getId()); // replace 'getId' with the method that gets the ID of the Vol

            // Remove the Vol from the map
            EventContainer.getChildren().remove(thisPane);
        } else {
            // If no Vol is associated with the pane, handle it accordingly
            System.out.println("No Vol associated with this pane.");
        }
    }


    public void updateAction(ActionEvent actionEvent) {
        // Get the source of the action event (the "Update" button)
        Button updateButton = (Button) actionEvent.getSource();

        Evenement evenement = new Evenement().getInstance();


        // Get the Pane that the "Update" button belongs to
        Pane pane = (Pane) updateButton.getParent();

        if (currentEvent != null) {
            // Populate the form with the data of the selected Vol
            AjouterEvent.populateForm(currentEvent);

            evenement.setCurrentEvent(currentEvent);
        } else {
// Si aucun event n'est associé au event, gérez-le en conséquence
            System.out.println("Aucun événement associé .");
        }


    }



}