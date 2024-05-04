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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Collectors;
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




    public void setEventImage(Image image)// Cette méthode prend une image en entrée et l'affiche dans un ImageView.

    {
        labelImage.setImage(image);
    }
// Elle prend un objet AjouterEvent en paramètre et le stocke dans un attribut de cet objet.
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

// Récupère la source de l'événement d'action (le bouton "Supprimer")
        Button deleteButton = (Button) actionEvent.getSource();

// Récupère le volet auquel appartient le bouton "Supprimer"
        Pane pane = (Pane) deleteButton.getParent();


        if (currentEvent != null) {
            System.out.println("Current event: " + currentEvent);
            System.out.println("Event Controller: " + AjouterEvent);
// Supprime le event de la VBox
            EventContainer.getChildren().remove(pane);

// Supprime le event de la base de données
            sp.supprimer(currentEvent.getId()); // replace 'getId' with the method that gets the ID of the event

// Supprime le event de la carte
            EventContainer.getChildren().remove(thisPane);
        } else {
// Si aucun event n'est associé au event, gérez-le en conséquence
            System.out.println("No event associated with this pane.");
        }
    }


    public void updateAction(ActionEvent actionEvent) {
        // Get the source of the action event (the "Update" button)
        Button updateButton = (Button) actionEvent.getSource();

        Evenement evenement = new Evenement().getInstance();


// Récupère le event auquel appartient le bouton "Mettre à jour"
        Pane pane = (Pane) updateButton.getParent();

        if (currentEvent != null) {
// Remplit le formulaire avec les données du Vol sélectionné
            AjouterEvent.populateForm(currentEvent);

            evenement.setCurrentEvent(currentEvent);
        } else {
// Si aucun event n'est associé au event, gérez-le en conséquence
            System.out.println("Aucun événement associé .");
        }


    }

    public List<Event> search(String searchTerm) {
        // Obtenez tous les événements du service
        List<Event> allEvents = sp.getAllEvents(); // Remplacez 'getAllEvents' par la méthode qui obtient tous les événements

        // Filtrez les événements en fonction du terme de recherche
        List<Event> filteredEvents = allEvents.stream()
                .filter(event -> event.getType().contains(searchTerm)) // Remplacez 'getType' par la méthode qui obtient le champ sur lequel vous voulez rechercher
                .collect(Collectors.toList());

        return filteredEvents;
    }

    }

