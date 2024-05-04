package edu.esprit.controllers;

import edu.esprit.entities.Voyage;
import edu.esprit.services.ServiceVoyage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import edu.esprit.controllers.VoyageDesign;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AjouterVoyage {
    @FXML
    private TextField TFDestination;

    @FXML
    private TextField TFPicture;

    @FXML
    private TextField TFOffer;

    @FXML
    private HBox VoyageContainer = new HBox();

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Pane selectedPane;
    private final ServiceVoyage sp = new ServiceVoyage();

    Map<Pane, Voyage> paneVolMap = new HashMap<>();


    @FXML
    private void initialize() throws IOException {
        // Call method to populate TableView when the scene is loaded
        populateScrollPane();


    }

    public void Front(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherVoyage.fxml"));
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


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Attention");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void ajouterVoyageAction(ActionEvent event) throws IOException, SQLException {
        String destination = TFDestination.getText().trim();
        String picturePath = TFPicture.getText().trim();
        String offer = TFOffer.getText().trim();

        // Vérification des champs obligatoires
        if (destination.isEmpty() || picturePath.isEmpty() || offer.isEmpty()) {
            showAlert("Veuillez remplir tous les champs.");
            return;
        }



        // Vérification des autres champs obligatoires
        if (picturePath.isEmpty() || offer.isEmpty()) {
            showAlert("Veuillez remplir tous les champs.");
            return;
        }

        // Vérification du format de l'offre
        try {
            double offerAmount = Double.parseDouble(offer);
            // Vérifiez ici si offerAmount est valide selon vos critères
        } catch (NumberFormatException e) {
            showAlert("L'offre doit être un nombre valide.");
            return;
        }

        // Vérification de l'existence de l'image
        File imageFile = new File(picturePath);
        if (!imageFile.exists() || !imageFile.isFile()) {
            showAlert("Le chemin de l'image spécifié est invalide.");
            return;
        }

        // Vérification si la destination existe déjà
        Set<Voyage> allVoyages = sp.getAll();
        for (Voyage existingVoyage : allVoyages) {
            if (existingVoyage.getDestination().equalsIgnoreCase(destination)) {
                showAlert("La destination est déjà utilisée.");
                return;
            }
        }


        String imagePath = TFPicture.getText();
        Voyage newVoyage = new Voyage(
                TFDestination.getText(),TFPicture.getText(),TFOffer.getText() ) ;
        sp.ajouter(newVoyage);

        System.out.println("New Voyage: " + newVoyage);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/voyage.fxml"));
        Pane pane = loader.load();


        // Get the controller and update the ticket details
        VoyageDesign controller = loader.getController();
        controller.setVoyage(newVoyage,pane,VoyageContainer);

        controller.setAjouterVoyageController(this);

        paneVolMap.put(pane, newVoyage);
        selectedPane= pane;

        //TicketVol.setAjouterVolController(this);

        VoyageContainer.setSpacing(50);
        VoyageContainer.getChildren().add(pane);
        scrollPane.setContent(VoyageContainer);
        clearFormFields();
    }

    private void populateScrollPane() throws IOException {
        // Get all the flights from the database
        Set<Voyage> allVoyages = sp.getAll();

        // Clear the ticketContainer
        VoyageContainer.getChildren().clear();

        // Create a VBox to hold all the flight Panes
        VoyageContainer.setSpacing(50);

        // For each flight, load the FXML file and add it to the VBox
        for (Voyage voyage : allVoyages) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/voyage.fxml"));
            Pane pane = loader.load();

            // Get the controller and update the ticket details
            VoyageDesign controller = loader.getController();
            controller.setVoyage(voyage,pane,VoyageContainer);

            // Load and display the image
            String imagePath = voyage.getPicture();
            if (imagePath != null && !imagePath.isEmpty()) {
                File file = new File(imagePath);
                if (file.exists()) {
                    Image image = new Image(file.toURI().toString());
                    controller.setVoyageImage(image);
                }
            }

            controller.setAjouterVoyageController(this);

            // Add the pane to the VBox
            VoyageContainer.getChildren().add(pane);
        }

        // Set the VBox as the content of the ScrollPane
        scrollPane.setContent(VoyageContainer);
    }





    private void crud() {
        // Get all the flights from the database
        Set<Voyage> allVoyages = sp.getAll();
        System.out.println("hiiii");

        System.out.println(allVoyages);

        System.out.println("byeeeee");

        // Create a HBox to hold all the flight Panes
        HBox hbox = new HBox();
        hbox.setSpacing(50);
        Map<Pane, Voyage> paneVoyagelMap = new HashMap<>();

        // For each flight, create a new VBox inside a Pane and add it to the HBox
        for (Voyage Voyage : allVoyages) {
            VBox flightvbox = new VBox();
            // Add details to the flightvbox
            Label label = new Label(Voyage.getDestination());
            flightvbox.getChildren().add(label);

            Label label1 = new Label(Voyage.getPicture());
            flightvbox.getChildren().add(label1);

            // Create a new Pane and add the flightvbox to it
            Pane pane = new Pane();
            pane.getChildren().add(flightvbox);
            System.out.println(pane);
            Button delete_btn = new Button("delete");
            delete_btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    // Get the Vol associated with the pane
                    Voyage selectedVoyage = paneVoyagelMap.get(pane);
                    System.out.println(selectedVoyage);

                    // Remove the pane from the HBox
                    hbox.getChildren().remove(pane);

                    // Delete the Vol from the database
                    sp.supprimer(selectedVoyage.getId());
                }
            });
            flightvbox.getChildren().add(delete_btn);

            paneVoyagelMap.put(pane, Voyage);

            Button updateButton = new Button("update");
            updateButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    // Get the Vol associated with the pane
                    Voyage selectedVoyage = paneVoyagelMap.get(pane);

                    // Set the text of the form fields to the details of the selected flight
                    TFDestination.setText(selectedVoyage.getDestination());
                    TFPicture.setText(selectedVoyage.getPicture());
                    TFOffer.setText(selectedVoyage.getOffer());

                }
            });
            flightvbox.getChildren().add(updateButton);

            // Add the pane to the HBox
            hbox.getChildren().add(pane);
        }

        // Set the HBox as the content of the ScrollPane
        scrollPane.setContent(hbox);
    }

    public void navigatetoAfficherVoyageAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherVoyage.fxml"));
            TFDestination.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }

    }







    public void updateAction(ActionEvent actionEvent) {
    }





    public void deleteVoyageAction(ActionEvent actionEvent) {
    }

    @FXML
    void uploadImageAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            TFPicture.setText(file.getAbsolutePath());
        }
    }

    public  void populateForm(Voyage voyage) {
        System.out.println("Populating form with Vol: " + voyage);
        TFDestination.setText(voyage.getDestination());
        TFOffer.setText(voyage.getOffer());
        TFPicture.setText(voyage.getPicture());
    }

    @FXML
    void saveChanges(ActionEvent event) throws IOException {
        VoyageDesign voyageDesign = VoyageDesign.getInstance();

        Voyage currentVoyage = voyageDesign.getCurrentVoyage();

        System.out.println(currentVoyage);

        Voyage updatedVol = new Voyage(
                currentVoyage.getId(),
                TFDestination.getText(),
                TFPicture.getText(),
                TFOffer.getText()

        );
        System.out.println(updatedVol);

        TFDestination.setText(TFDestination.getText());
        TFOffer.setText(TFOffer.getText());
        TFPicture.setText(TFPicture.getText());


        // Update the Vol object in the database
        sp.modifier(updatedVol);
        populateScrollPane();
        clearFormFields();

// Repopulate the ScrollPane

    }

    private void clearFormFields() {
        TFDestination.clear();
        TFOffer.clear();
        TFPicture.clear();
    }
}
