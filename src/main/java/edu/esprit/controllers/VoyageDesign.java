package edu.esprit.controllers;

import edu.esprit.entities.Voyage;
import edu.esprit.services.ServiceVoyage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileNotFoundException;

public class VoyageDesign {

    @FXML
    private Label labelDestination;

    @FXML
    private Label labelOffer;

    @FXML
    private ImageView labelImage;

    @FXML
    private final ServiceVoyage sp = new ServiceVoyage();

    @FXML
    private Pane thisPane;

    @FXML
    private HBox VoyageContainer;


    private static VoyageDesign instance;
    private Voyage currentVoyage;



    private AjouterVoyage AjouterVoyage;

    public VoyageDesign() {
    }

    public void setVoyage(Voyage voyage, Pane pane, HBox vbox) throws FileNotFoundException {

        labelOffer.setText(voyage.getOffer());
        labelDestination.setText(voyage.getDestination());

        // Load and set the image
        String imagePath = voyage.getPicture();
        if (imagePath != null && !imagePath.isEmpty()) {
            File file = new File(imagePath);
            if (file.exists()) {
                Image image = new Image(file.toURI().toString());
                labelImage.setImage(image);
            }
        }

        thisPane = pane;
        VoyageContainer = vbox;
        currentVoyage = voyage;
    }

    public static VoyageDesign getInstance() {
        if (instance == null) {
            instance = new VoyageDesign();
        }
        return instance;
    }

    public Voyage getCurrentVoyage(){
        return currentVoyage;
    }

    public  void setCurrentVoyage(Voyage currentVoyage) {
        this.currentVoyage= currentVoyage;
    }

    public void setVoyageImage(Image image) {
        labelImage.setImage(image);
    }

    public  void setAjouterVoyageController(AjouterVoyage voyageController) {
        this.AjouterVoyage = voyageController;
    }

    public void deleteAction(ActionEvent actionEvent) {

        // Get the source of the action event (the "Delete" button)
        Button deleteButton = (Button) actionEvent.getSource();

        // Get the Pane that the "Delete" button belongs to
        Pane pane = (Pane) deleteButton.getParent();


        if (currentVoyage != null) {
            System.out.println("Current Vol: " + currentVoyage);
            System.out.println("Vol Controller: " + AjouterVoyage);
            // Remove the pane from the VBox
            VoyageContainer.getChildren().remove(pane);

            // Delete the Vol from the database
            sp.supprimer(currentVoyage.getId()); // replace 'getId' with the method that gets the ID of the Vol

            // Remove the Vol from the map
            VoyageContainer.getChildren().remove(thisPane);
        } else {
            // If no Vol is associated with the pane, handle it accordingly
            System.out.println("No Vol associated with this pane.");
        }
    }

    public void updateAction(ActionEvent actionEvent) {
        // Get the source of the action event (the "Update" button)
        Button updateButton = (Button) actionEvent.getSource();

        VoyageDesign ticketVol = new VoyageDesign().getInstance();


        // Get the Pane that the "Update" button belongs to
        Pane pane = (Pane) updateButton.getParent();

        if (currentVoyage != null) {
            // Populate the form with the data of the selected Vol
            AjouterVoyage.populateForm(currentVoyage);
            ticketVol.setCurrentVoyage(currentVoyage);
        } else {
            // If no Vol is associated with the pane, handle it accordingly
            System.out.println("No Vol associated with this pane.");
        }
    }

}
