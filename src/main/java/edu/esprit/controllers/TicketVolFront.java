package edu.esprit.controllers;

import edu.esprit.entities.Vol;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import javafx.scene.control.Label;
import javafx.stage.Stage;

public class TicketVolFront {

    @FXML
    private Label labelAeroportDepart;

    @FXML
    private Label labelAeroportArrive;

    @FXML
    private Label labelDateDepart;

    @FXML
    private Label labelDateArrive;

    @FXML
    private Label labelPrix;

    @FXML
    private Label labelCode;

    @FXML
    private ImageView labelImage;


    @FXML
    private Pane thisPane;

    @FXML
    private VBox VolContainer;

    private Vol currentVol;

    private AfficherVol AfficherVol;



    public int getCode(){
        return currentVol.getCode();
    }

    public  void setCde(Vol currentVol) {
        this.currentVol= currentVol;
    }


    public void setVolImage(Image image) {
        labelImage.setImage(image);
    }
    public void setVol(Vol vol, Pane pane, VBox vbox) {

        labelAeroportDepart.setText(vol.getAeroport_depart());
        labelAeroportArrive.setText(vol.getGetAeroport_arrive());
        labelDateDepart.setText(vol.getDate_depart().toString());
        labelDateArrive.setText(vol.getDate_depart().toString());
        labelPrix.setText(String.valueOf(vol.getPrix()));
        labelCode.setText(String.valueOf(vol.getCode()));


        /* Load and set the image */
        String imagePath = vol.getImage();
        if (imagePath != null && !imagePath.isEmpty()) {
            File file = new File(imagePath);
            if (file.exists()) {
                javafx.scene.image.Image image = new Image(file.toURI().toString());
                labelImage.setImage(image);
            }
        }

        thisPane = pane;
        VolContainer = vbox;
        currentVol = vol;
    }




    public void setAfficherVolController(AfficherVol afficherVol) {
        this.AfficherVol = afficherVol;
    }

    public void NavigateAjouterPaimentAction(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterPaiement.fxml"));
            Parent root = loader.load();
            AjouterPaiement ajouterPaiementController = loader.getController();

            // Set the ticketVolFront
            ajouterPaiementController.setTicketVolFront(this);
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
    }

    public void AjouterPanierAcrion(ActionEvent actionEvent) {
        System.out.println("currentVol"+currentVol);
       AfficherVol.addToPanier(currentVol);
    }
}
