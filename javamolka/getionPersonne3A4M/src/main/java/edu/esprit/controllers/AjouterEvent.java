package edu.esprit.controllers;

import edu.esprit.entities.Event;
import edu.esprit.services.ServiceEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.File;
import java.lang.reflect.Type;
import  java.sql.*;

import java.io.IOException;
import java.sql.SQLException;

import static java.sql.Date.valueOf;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import edu.esprit.controllers.Evenement;
import javafx.stage.FileChooser;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

public  class AjouterEvent {


    @FXML
    private TextField TFDatedebut;
    @FXML
    private TextField TFDatefin;
    @FXML
    private TextField TFLieux;
    @FXML
    private TextField TFPrix;
    @FXML
    private TextField TFImage;
    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private  ScrollPane scrollPane;
    @FXML
    private DatePicker dateDebutPicker;
    @FXML
    private DatePicker dateFinPicker;

    @FXML
    private ComboBox<String> eventTypeComboBox;

    private final ServiceEvent sp = new ServiceEvent();




    @FXML
    private Pane selectedPane;

    @FXML
    private HBox EventContainer = new HBox();

    Map<Pane, Event> paneEventMap = new HashMap<>();
    // Méthode appelée lors de la sélection d'une date de début
    @FXML
    private void handleDateDebutAction(ActionEvent event) {
        LocalDate selectedDate = dateDebutPicker.getValue();
        // Mettez à jour votre logique en conséquence
    }

    // Méthode appelée lors de la sélection d'une date de fin
    @FXML
    private void handleDateFinAction(ActionEvent event) {
        LocalDate selectedDate = dateFinPicker.getValue();
        // Mettez à jour votre logique en conséquence
    }


    @FXML
    private void initialize() throws IOException {
        // Call method to populate TableView when the scene is loaded
        populateScrollPane();

    }
    private void showAlert(String s) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Alerte");
        alert.setHeaderText(null);
        String message = "";
        alert.setContentText("Veuillez remplir tous les champs");
        alert.showAndWait();
    }
    public boolean validatePrix(String prix) {
        // Expression régulière pour valider un nombre décimal
        String prixRegex = "^[0-9]+(\\.[0-9]{1,2})?$";
        if (!prix.matches(prixRegex)) {
            // Afficher un message d'erreur si le format du prix est invalide
            showAlert("Veuillez saisir un prix valide (ex: 123.45).");
            return false;
        }
        return true;
    }
    public boolean validateLieux(String lieux) {
        // Expression régulière pour autoriser les lettres et les espaces uniquement
        String lieuxRegex = "^[a-zA-Z\\s]+$";
        if (!lieux.matches(lieuxRegex)) {
            // Afficher un message d'erreur si le format du lieu est invalide
            showAlert("Veuillez saisir un lieu valide (lettres et espaces uniquement).");
            return false;
        }
        return true;
    }
    public boolean validateDateRange(String startDate, String endDate) {
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



    public void initialize2() {
        typeComboBox.setItems(FXCollections.observableArrayList("festivale", "sportif", "danse"));
    }








   /* @FXML
    void ajouterEventAction(ActionEvent event) {
        try {
            sp.ajouter(new Event(TFType.getText(),valueOf(TFDatedebut.getText()),valueOf(TFDatefin.getText()),TFLieux.getText(),Float.parseFloat(TFPrix.getText()),TFImage.getText()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Ajout avec succés");
            alert.show();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Exception");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }*/

    @FXML
    void ajouterEventAction(ActionEvent event) throws IOException, SQLException {
        String imagePath = TFImage.getText();
        // Vérifier si tous les champs du formulaire sont remplis
        if (typeComboBox.getValue() == null || TFDatedebut.getText().isEmpty() || TFDatefin.getText().isEmpty() || TFLieux.getText().isEmpty() || TFPrix.getText().isEmpty() || TFImage.getText().isEmpty()) {
            // Afficher une alerte si un champ est vide
            showAlert("Veuillez remplir tous les champs du formulaire.");
            return; // Sortir de la méthode si un champ est vide
        }
        Event newEvent = new Event(
        typeComboBox.getValue().toString(),valueOf(TFDatedebut.getText()),valueOf(TFDatefin.getText()),TFLieux.getText(),Float.parseFloat(TFPrix.getText()),TFImage.getText()) ;
        sp.ajouter(newEvent);
        System.out.println("New Event: " + newEvent);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/evenement.fxml"));
        Pane pane = loader.load();
        // Get the controller and update the ticket details
        Evenement controller = loader.getController();
        controller.setEvent(newEvent,pane,EventContainer);
        controller.setAjouterEventController(this);

        paneEventMap.put(pane, newEvent);
        selectedPane= pane;
        EventContainer.setSpacing(50);
        EventContainer.getChildren().add(pane);
        scrollPane.setContent(EventContainer);
        clearFormFields();


    }


    private void populateScrollPane() throws IOException {
        // Get all the flights from the database
        Set<Event> allEvents = sp.getAll();

        // Clear the ticketContainer
        EventContainer.getChildren().clear();

        // Create a VBox to hold all the flight Panes
        EventContainer.setSpacing(50);

        // For each flight, load the FXML file and add it to the VBox
        for (Event event : allEvents) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/evenement.fxml"));
            Pane pane = loader.load();

            // Get the controller and update the ticket details
            Evenement controller = loader.getController();
            controller.setEvent(event,pane,EventContainer);

            // Load and display the image
            String imagePath = event.getImage();
            if (imagePath != null && !imagePath.isEmpty()) {
                File file = new File(imagePath);
                if (file.exists()) {
                    Image image = new Image(file.toURI().toString());
                    controller.setEventImage(image);
                }
            }

            controller.setAjouterEventController(this);

            // Add the pane to the VBox
            EventContainer.getChildren().add(pane);
        }

        // Set the VBox as the content of the ScrollPane
        scrollPane.setContent(EventContainer);
    }

    public void navigatetoAfficherEventAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherEvent.fxml"));
            typeComboBox.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }

    }


    public  void populateForm(Event event) {
        System.out.println("Populating form with event: " + event);
        typeComboBox.setValue(event.getType());
        TFDatedebut.setText(event.getDatedebut().toString());
        TFDatefin.setText(event.getDatefin().toString());
        TFPrix.setText(String.valueOf(event.getPrix()));
        TFLieux.setText(event.getLieux());
        TFImage.setText(event.getImage());
    }

    @FXML
    void saveChanges(ActionEvent event) throws IOException {
        Evenement ticketVol = Evenement.getInstance();

        Event currentVol = ticketVol.getCurrentEvent();

        System.out.println(currentVol);

        Event updatedVol = new Event(
                currentVol.getId(),
                typeComboBox.getValue().toString(),
                java.sql.Date.valueOf(TFDatedebut.getText()),
                java.sql.Date.valueOf(TFDatefin.getText()),
                Float.parseFloat(TFPrix.getText()),
                TFLieux.getText(),
                TFImage.getText()
        );





        // Update the Vol object in the database
        sp.modifier(updatedVol);
        populateScrollPane();
        clearFormFields();

// Repopulate the ScrollPane

    }

    private void clearFormFields() {
        TFDatedebut.clear();
        TFDatefin.clear();
        TFPrix.clear();
        TFLieux.clear();
        TFImage.clear();
    }

    @FXML
    void uploadImageAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            TFImage.setText(file.getAbsolutePath());
        }
    }

    public void showEventTypeComboBox(ActionEvent actionEvent) {
        eventTypeComboBox.setVisible(!eventTypeComboBox.isVisible());

    }

}