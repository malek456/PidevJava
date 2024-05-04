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
import javafx.scene.control.*;
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
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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
    @FXML
    private VBox root;

    @FXML
    private Pane RatingPane;
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
        FXMLLoader ratingLoader = new FXMLLoader(getClass().getResource("/Rating.fxml"));
        Pane rating = ratingLoader.load();

        // Add the rating to the ratingPane
        RatingPane.getChildren().add(rating);




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

    //affichage des commentaires dans l'aevent
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
        // Vérifier si le commentaire satisfait les critères requis
        if(!commentIsValid(commentContent)) {
            // Afficher un message d'erreur ou une boîte de dialogue
            // Vous pouvez adapter ce qui suit à votre interface utilisateur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir un commentaire valide (au moins 5 caractères alphanumériques).");
            alert.showAndWait();
            return; // Arrêter l'exécution de la méthode
        }

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

        // Clear the TextField pour nfara8 il
        tfAjouter.clear();
    }

    // Méthode pour vérifier si le commentaire satisfait les critères requis
    private boolean commentIsValid(String comment) {
        // Utilisation d'une expression régulière pour vérifier si le commentaire contient au moins 5 caractères alphanumériques
        return comment.length() >= 5;

    }

    /*public void initialize() {
        // Créez un EmojisLabel avec l'emoji de votre choix
        EmojisLabel emojiLabel = new EmojisLabel("😊"); // Remplacez par l'emoji de votre choix
        emojiLabel.setFont(Font.font(24)); // Définissez la taille de police
        root.getChildren().add(emojiLabel); // Ajoutez-le à votre interface
    }
    @Override
    public void start(Stage primaryStage) {
        Button likeButton = new Button("Like");
        likeButton.setOnAction(event -> showEmojiDialog());

        VBox root = new VBox(10);
        root.getChildren().add(likeButton);

        Scene scene = new Scene(root, 300, 200);
        primaryStage.setTitle("Emoji Chooser");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void showEmojiDialog() {
        List<String> emojis = new ArrayList<>();
        emojis.add("😊"); // Smiley
        emojis.add("❤️"); // Heart
        emojis.add("👍"); // Thumbs up

        ChoiceDialog<String> dialog = new ChoiceDialog<>(emojis.get(0), emojis);
        dialog.setTitle("Choose an Emoji");
        dialog.setHeaderText("Select your favorite emoji:");
        dialog.setContentText("Emoji:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(selectedEmoji -> System.out.println("Selected emoji: " + selectedEmoji));
    }*/

    public void Rating(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Rating.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading FXML file: " + e.getMessage());
        }
    }
    @FXML
    private void showRatingStars(ActionEvent event) {
        // Créez un tableau d'ImageView pour les étoiles
        ImageView[] starImages = new ImageView[5];

        // Remplacez les URL des images par vos propres images d'étoiles pleines et vides
        String emptyStarUrl = "/path/to/icons8_star_50px.png";
        String filledStarUrl = "/path/to/icons8_star_50px.png";

        // Ajoutez les ImageView au tableau
        for (int i = 0; i < starImages.length; i++) {
            starImages[i] = new ImageView();
            starImages[i].setFitHeight(20); // Ajustez la taille des étoiles
            starImages[i].setFitWidth(20);
        }

        // Exemple d'ajout à un Pane :
        Pane starsPane = new Pane();
        for (int i = 0; i < starImages.length; i++) {
            starImages[i].setLayoutX(i * 25); // Espacement entre les étoiles
            starsPane.getChildren().add(starImages[i]);
        }
    }

    }






