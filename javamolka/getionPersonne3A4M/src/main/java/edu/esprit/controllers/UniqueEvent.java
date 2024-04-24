package edu.esprit.controllers;

import edu.esprit.entities.Commentaire;
import edu.esprit.entities.Event;
import edu.esprit.services.ServiceCommentaire;
import edu.esprit.services.ServiceEvent;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.*;

import static java.sql.Date.valueOf;


public class UniqueEvent {

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
    private ScrollPane scrollPane;

    @FXML
    private TextField tfAjouter;
    private final ServiceCommentaire sc = new ServiceCommentaire();



    @FXML
    private Pane selectedPane;

    @FXML
    private VBox CommentContainer = new VBox();

    Map<Pane, Commentaire> paneCommentMap = new HashMap<>();

    @FXML
    private Pane thisPane;

    @FXML
    private VBox EventContainer;

    private static edu.esprit.controllers.Evenement instance;
    private Event currentEvent;

    private AfficherEvenement AfficherEvenement;

    @FXML
    private final ServiceEvent sp = new ServiceEvent();

    @FXML
    private void initialize() throws IOException {


    }

    public void setEventImage(Image image)// Cette méthode prend une image en entrée et l'affiche dans un ImageView.

    {
        labelImage.setImage(image);
    }

    public void setAfficherEvenementController(AfficherEvenement eventController) {
        this.AfficherEvenement = eventController;
    }


    public static edu.esprit.controllers.Evenement getInstance() {
        if (instance == null) {
            instance = new edu.esprit.controllers.Evenement();
        }
        return instance;
    }

    public Event getCurrentEvent() {
        return currentEvent;
    }

    public void setCurrentEvent(Event currentEvent) {
        this.currentEvent = currentEvent;
    }

    public void setUniqueEvent(Event Event, Pane pane, VBox vbox) throws FileNotFoundException {
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
    public void populateScrollPane(int eventId) throws IOException {
        // Get all the comments for the specific event from the database
        Set<Commentaire> allComments = sc.getAllByEventId(eventId);

        // Create a new VBox for this event's comments
        VBox eventCommentContainer = new VBox();
        eventCommentContainer.setSpacing(50);

        // For each comment, load the FXML file and add it to the VBox
        for (Commentaire comment : allComments) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/comment.fxml"));
            Pane pane = loader.load();

            // Get the controller and update the comment details
            Comment controller = loader.getController();
            controller.setComment(comment, pane, eventCommentContainer);

            controller.setUniqueEventController(this);

            // Add the pane to the VBox
            eventCommentContainer.getChildren().add(pane);
        }

        // Add this event's comment container to the ScrollPane
        scrollPane.setContent(eventCommentContainer);
    }


    public void ajouterCommentAction(ActionEvent actionEvent) throws IOException, SQLException {
        // Get the comment content from the TextField
        String commentContent = tfAjouter.getText();

        // Create a new Commentaire object with the current event, current date, and comment content
        Commentaire newComment = new Commentaire(currentEvent, java.sql.Date.valueOf(LocalDate.now()), commentContent);

        // Save the new comment to the database
        sc.ajouter(newComment);

        System.out.println("New Comment: " + newComment);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Comment.fxml"));
        Pane pane = loader.load();

        // Get the controller and update the comment details
        Comment controller = loader.getController();
        controller.setComment(newComment, pane, CommentContainer);

        paneCommentMap.put(pane, newComment);
        selectedPane = pane;

        // Add the pane to the VBox
        CommentContainer.getChildren().add(pane);

        // Set the VBox as the content of the ScrollPane
        scrollPane.setContent(CommentContainer);

        // Clear the TextField
        tfAjouter.clear();
    }

}






