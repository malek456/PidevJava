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
    private DatePicker TFDatedebut;
    @FXML
    private DatePicker TFDatefin;
    @FXML
    private TextField TFLieux;
    @FXML
    private TextField TFPrix;
    @FXML
    private TextField TFImage;
    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private ScrollPane scrollPane;
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

    private CharSequence startDate;


    @FXML
    private void initialize() throws IOException {
// Appel à la méthode pour remplir TableView lorsque la scène est chargée
        populateScrollPane();

    }

  private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Alerte");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public void initialize2() {
        typeComboBox.setItems(FXCollections.observableArrayList("festivale", "sportif", "danse"));
    }

    public boolean validateInput() {
        boolean valid=true;
        // Vérifiez si tous les champs sont remplis
        if (TFDatedebut.getValue()==null || TFDatefin.getValue()==null || TFLieux.getText().isEmpty() || TFPrix.getText().isEmpty() || TFImage.getText().isEmpty() || typeComboBox.getValue() == null) {
            showAlert("Veuillez remplir tous les champs.");
            valid= false;
        }

        // Vérifiez si le prix est valide
        if (!validatePrix(TFPrix.getText())) {
            valid= false;
        }
        // Vérifiez si le lieu est valide
        if (!validateLieux(TFLieux.getText())) {
            valid= false;
        }

        // Vérifiez si la plage de dates est valide
        if (!validateDateRange(TFDatedebut.getValue().toString(), TFDatefin.getValue().toString())) {
            valid= false;
        }

        // Si toutes les validations passent, retournez true;
        return valid;
    }
    public boolean validateDateRange(String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        // Vérifiez si la date de début est avant la date de fin
        if (start.isAfter(end) || start.isEqual(end)) {
            showAlert("La date de fin doit être supérieure à la date de début.");
            return false;
        }

        // Si la validation passe, retournez true
        return true;
    }


    public boolean validatePrix(String prix) {
        // Expression régulière pour valider un nombre entier
        String prixRegex = "^[0-9]+$";
        if (!prix.matches(prixRegex)) {
            // Afficher un message d'erreur si le format du prix est invalide
            showAlert("Veuillez saisir un prix valide (un nombre entier).");
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

    @FXML
    void ajouterEventAction(ActionEvent event) throws IOException, SQLException {
        if (!validateInput()) {
            // Si validateInput() retourne false, on arrête l'exécution de la méthode ici
            return;
        }
            String imagePath = TFImage.getText();
        // Vérifier si tous les champs du formulaire sont remplis

        Event newEvent = new Event(
        typeComboBox.getValue().toString(),valueOf(TFDatedebut.getValue()),valueOf(TFDatefin.getValue()),TFLieux.getText(),Float.parseFloat(TFPrix.getText()),TFImage.getText()) ;
        sp.ajouter(newEvent);
        System.out.println("New Event: " + newEvent);


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/evenement.fxml"));
        Pane pane = loader.load();
// Récupère le contrôleur et met à jour les détails EVENT
        Evenement controller = loader.getController();
        controller.setEvent(newEvent,pane,EventContainer);
        controller.setAjouterEventController(this);

        paneEventMap.put(pane, newEvent);
        selectedPane= pane;
        EventContainer.setSpacing(10);
        EventContainer.getChildren().add(pane);
        scrollPane.setContent(EventContainer);
        clearFormFields();


    }


    private void populateScrollPane() throws IOException {
// Récupère tous les events de la base de données
        Set<Event> allEvents = sp.getAll();

// Efface le EVENTContainer
        EventContainer.getChildren().clear();

// Crée une VBox pour contenir tous les events
        EventContainer.setSpacing(50);

// Pour chaque event, chargez le fichier FXML et ajoutez-le à la VBox
        for (Event event : allEvents) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/evenement.fxml"));
            Pane pane = loader.load();

// Récupère le contrôleur et met à jour les détails du event
            Evenement controller = loader.getController();
            controller.setEvent(event,pane,EventContainer);

// Charge et affiche l'image
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
        TFDatedebut.setValue(event.getDatedebut().toLocalDate());
        TFDatefin.setValue(event.getDatefin().toLocalDate());
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
                java.sql.Date.valueOf(TFDatedebut.getValue()),
                java.sql.Date.valueOf(TFDatefin.getValue()),
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
        TFDatedebut.setValue(null);
        TFDatefin.setValue(null);
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