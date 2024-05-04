package edu.esprit.controllers;

import edu.esprit.entities.Commentaire;
import edu.esprit.entities.Event;
import edu.esprit.services.ServiceCommentaire;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.File;

public class Comment {

    @FXML
    private Label LabelDateC;

    @FXML
    private Label LabelContenu;

    @FXML
    private Pane thisPane;

    @FXML
    private VBox CommentContainer;

    @FXML
    private Button saveButton; // Le bouton pour enregistrer les modifications


    private TextField editField; // Le champ de texte pour éditer le commentaire


    private static edu.esprit.controllers.Comment instance;
    private Commentaire currentComment;

    private UniqueEvent UniqueEvent;
    private final ServiceCommentaire sc = new ServiceCommentaire();


    public void setComment(Commentaire comment, Pane pane, VBox vbox) {
        LabelContenu.setText(comment.getContenu());





// Vérifiez si la date est nulle avant d'invoquer toString()
        if (comment.getDate() != null) {
            LabelDateC.setText(comment.getDate().toString());
        } else {
            LabelDateC.setText("No date available");
        }



        thisPane = pane;
        CommentContainer = vbox;
        currentComment = comment;
    }


    public void setUniqueEventController(UniqueEvent commentController) {
        this.UniqueEvent = commentController;

    }

    public void SupprimerAction(ActionEvent actionEvent) {
        // Delete the current comment from the database
        sc.supprimer(currentComment.getId());

        // Remove the comment pane from the VBox
        CommentContainer.getChildren().remove(thisPane);
    }


    public void modifierAction(ActionEvent actionEvent) {
        // Si le champ d'édition n'est pas déjà présent
        if (editField == null) {
            editField = new TextField(LabelContenu.getText());
            thisPane.getChildren().add(editField);
        } else {
            // Si le champ d'édition est déjà présent, mettez à jour le commentaire
            currentComment.setContenu(editField.getText());
            sc.modifier(currentComment);
            LabelContenu.setText(currentComment.getContenu());
            thisPane.getChildren().remove(editField);
            editField = null;
        }
    }
}
