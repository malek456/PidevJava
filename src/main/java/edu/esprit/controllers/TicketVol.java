package edu.esprit.controllers;

import edu.esprit.entities.Vol;
import edu.esprit.entities.Vol;
import edu.esprit.entities.Vol;
import edu.esprit.services.ServiceVol;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import java.awt.*;
import java.io.File;

public class TicketVol {

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
    private final ServiceVol sp = new ServiceVol();

    @FXML
    private Pane thisPane;

    @FXML
    private VBox VolContainer;

    private static TicketVol instance;

    private Vol currentVol;

    private AjouterVol AjouterVol;

    public TicketVol(){

    }






    public void setVol(Vol vol, Pane pane, VBox vbox) {
        
        labelAeroportDepart.setText(vol.getAeroport_depart());
        labelAeroportArrive.setText(vol.getGetAeroport_arrive());
        labelDateDepart.setText(vol.getDate_depart().toString());
        labelDateArrive.setText(vol.getGetDate_arrive().toString());
        labelPrix.setText(String.valueOf(vol.getPrix()));
        labelCode.setText(String.valueOf(vol.getCode()));
        

        // Load and set the image
        String imagePath = vol.getImage();
        if (imagePath != null && !imagePath.isEmpty()) {
            File file = new File(imagePath);
            if (file.exists()) {
                Image image = new Image(file.toURI().toString());
                labelImage.setImage(image);
            }
        }

        thisPane = pane;
        VolContainer = vbox;
        currentVol = vol;
    }

    public static TicketVol getInstance() {
        if (instance == null) {
            instance = new TicketVol();
        }
        return instance;
    }

    public Vol getCurrentVol(){
        return currentVol;
    }

    public  void setCurrentVol(Vol currentVol) {
        this.currentVol= currentVol;
    }

    public void setVolImage(Image image) {
        labelImage.setImage(image);
    }

    public  void setAjouterVolController(AjouterVol volController) {
        this.AjouterVol = volController;
    }




    public void UpdateAction(ActionEvent actionEvent) {
        // Get the source of the action event (the "Update" button)
        javafx.scene.control.Button updateButton = (javafx.scene.control.Button) actionEvent.getSource();

        // Use the correct singleton instance retrieval method
        TicketVol ticketVol = TicketVol.getInstance();

        // Ensure that the AjouterVol controller is not null before using it
        if (currentVol != null && AjouterVol != null) {
            // Populate the form with the data of the selected Vol
            AjouterVol.populateForm(currentVol);
            ticketVol.setCurrentVol(currentVol);

            AjouterVol.ActivateButton();



        } else {
            // Handle cases where no Vol is associated or AjouterVol is not set
            if (currentVol == null) {
                System.out.println("No Vol associated with this pane.");
            }
            if (AjouterVol == null) {
                System.out.println("AjouterVol controller is not set.");
            }
        }
    }


    public void DeleteAction(ActionEvent actionEvent) {

        // Get the source of the action event (the "Delete" button)
        javafx.scene.control.Button deleteButton = (javafx.scene.control.Button) actionEvent.getSource();

        // Get the Pane that the "Delete" button belongs to
        Pane pane = (Pane) deleteButton.getParent();


        if (currentVol != null) {
            System.out.println("Current Vol: " + currentVol);
            System.out.println("Vol Controller: " + AjouterVol);
            // Remove the pane from the VBox
            VolContainer.getChildren().remove(pane);

            // Delete the Vol from the database
            sp.supprimer(currentVol.getId()); // replace 'getId' with the method that gets the ID of the Vol

            // Remove the Vol from the map
            VolContainer.getChildren().remove(thisPane);
        } else {
            // If no Vol is associated with the pane, handle it accordingly
            System.out.println("No Vol associated with this pane.");
        }
    }
}
