package edu.esprit.controllers;
import edu.esprit.entities.Event;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import edu.esprit.entities.Commentaire;
import edu.esprit.services.ServiceCommentaire;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.awt.*;

public class AjouterCommentaire {
    @FXML

    private ListView<Commentaire> commentaireListView;
    @FXML
    private TextArea commentaireTextArea;

    private Event selectedEvent;
    private ServiceCommentaire serviceCommentaire;


    public void setSelectedEvent(Event event) {
        this.selectedEvent = event;
        refreshCommentaires();
    }


   // Cette méthode est utilisée pour définir le service de gestion des commentairespublic
    void setServiceCommentaire(ServiceCommentaire serviceCommentaire) {
        this.serviceCommentaire = serviceCommentaire;
    }

    private void refreshCommentaires() {
        commentaireListView.getItems().clear();
        commentaireListView.getItems().addAll(serviceCommentaire.getCommentairesByEvent(selectedEvent));
    }

    @FXML
    private void initialize() {
        commentaireListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                commentaireTextArea.setText(newValue.getContenu());
            }
        });
    }

    @FXML
    private void handleAjouterCommentaire() {
        String contenu = commentaireTextArea.getText();
        if (!contenu.isEmpty()) {
            /*Commentaire commentaire = new Commentaire();
            commentaire.setContenu(contenu);
            commentaire.setEvent(selectedEvent);
            serviceCommentaire.addCommentaire(commentaire);
            refreshCommentaires();*/


        }

    }

    private void ajouterCommentaire(String commentaire) {
    }

    private void showAlert(String s) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Alerte");
        alert.setHeaderText(null);
        String message = "";
        alert.setContentText("Veuillez remplir tous les champs");
        alert.showAndWait();
    }

    @FXML
    private void handleModifierCommentaire() {
        Commentaire selectedCommentaire = commentaireListView.getSelectionModel().getSelectedItem();
        if (selectedCommentaire != null) {
            String contenu = commentaireTextArea.getText();
            if (!contenu.isEmpty()) {
                selectedCommentaire.setContenu(contenu);
                serviceCommentaire.updateCommentaire(selectedCommentaire);
                refreshCommentaires();
            }
        }
    }

    @FXML
    private void handleSupprimerCommentaire() {
        Commentaire selectedCommentaire = commentaireListView.getSelectionModel().getSelectedItem();
        if (selectedCommentaire != null) {
            serviceCommentaire.deleteCommentaire(selectedCommentaire);
            refreshCommentaires();
        }
    }
}