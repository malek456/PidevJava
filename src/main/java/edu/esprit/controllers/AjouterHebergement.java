package edu.esprit.controllers;

import edu.esprit.entities.Hebergement;
import edu.esprit.entities.Voyage;
import edu.esprit.services.ServiceHebergement;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


import  edu.esprit.controllers.HebergementDesign;
import javafx.scene.control.ComboBox;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import edu.esprit.controllers.HebergementDesign;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.print.attribute.standard.Destination;

public class AjouterHebergement {
    @FXML
    private TextField TFName;
    @FXML
    private TextField TFPicture;

    @FXML
    private TextField TFLocation;

    @FXML
    private TextField TFDescription;
    @FXML
    private TextField TFType;

    @FXML
    private TextField TFActivities;


    @FXML
    private ComboBox ComboBoxDestination;



    @FXML
    private TextField TFPrice;

    @FXML
    private VBox HebergementContainer = new VBox();

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private ComboBox<String> ComboBoxType;


    @FXML
    private Pane selectedPane;
    private final ServiceHebergement sp = new ServiceHebergement();

    Map<Pane, Hebergement> paneVolMap = new HashMap<>();

    public void FrontH(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherHebergement.fxml"));
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
    private void initialize() throws IOException, SQLException {

        populateScrollPane();


        // Populate ComboBox with Destinations
        List<String> Destinations = sp.getAllDestination();
        ComboBoxDestination.setItems(FXCollections.observableArrayList(Destinations));

        List<String> types = Arrays.asList("1 étoile ", "2 étoiles", "3 étoiles" , "4 étoiles", "5 étoiles"); // Ajoutez les types nécessaires
        ComboBoxType.setItems(FXCollections.observableArrayList(types));





    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Attention");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    void ajouterHebergementAction(ActionEvent event) throws IOException, SQLException {

        if (validateForm()) {

        String imagePath = TFPicture.getText();

        String selectedType = ComboBoxType.getValue();
        String selectedVoyageDestination = ComboBoxDestination.getValue().toString();



        // Création du nouvel objet Hebergement
        Voyage selectedDestination = sp.getVoyageByDestination(selectedVoyageDestination);
        Hebergement newHebergement = new Hebergement(
                TFName.getText(),TFPicture.getText(),TFLocation.getText() ,  TFDescription.getText(),  selectedType, TFActivities.getText(),  Float.parseFloat( TFPrice.getText()),selectedDestination ) ;
        System.out.println(TFPrice.getText());
        sp.ajouter(newHebergement);



        System.out.println("New Hebergement: " + newHebergement);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Hebergement.fxml"));
        Pane pane = loader.load();





        // Get the controller and update the ticket details
        HebergementDesign controller = loader.getController();
        controller.setHebergement(newHebergement,pane,HebergementContainer);
        controller.setAjouterHebergementController(this);

        paneVolMap.put(pane, newHebergement);
        selectedPane= pane;



        //TicketVol.setAjouterVolController(this);

        HebergementContainer.setSpacing(50);
        HebergementContainer.getChildren().add(pane);
        scrollPane.setContent(HebergementContainer);

        // Affichez un message de confirmation
        showAlert("Hébergement ajouté avec succès.");

        // Effacez les champs du formulaire après l'ajout
        clearFormFields();
    }
    }









    private void populateScrollPane() throws IOException {
        // Get all the flights from the database
        Set<Hebergement> allHebergements = sp.getAll();

        // Clear the ticketContainer
        HebergementContainer.getChildren().clear();

        // Create a VBox to hold all the flight Panes
        HebergementContainer.setSpacing(50);

        // For each flight, load the FXML file and add it to the VBox
        for (Hebergement Hebergement : allHebergements) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Hebergement.fxml"));
            Pane pane = loader.load();



            // Get the controller and update the ticket details
            HebergementDesign controller = loader.getController();
            controller.setHebergement(Hebergement,pane,HebergementContainer);



            // Load and display the image
            String imagePath = Hebergement.getPicture();
            if (imagePath != null && !imagePath.isEmpty()) {
                File file = new File(imagePath);
                if (file.exists()) {
                    Image image = new Image(file.toURI().toString());
                    controller.setHebergementImage(image);
                }
            }

            controller.setAjouterHebergementController(this);



            // Add the pane to the VBox
            HebergementContainer.getChildren().add(pane);
        }

        // Set the VBox as the content of the ScrollPane
        scrollPane.setContent(HebergementContainer);
    }


    private boolean validateForm() {
        if (TFName.getText().isEmpty() || TFPicture.getText().isEmpty() || TFLocation.getText().isEmpty() ||
                TFDescription.getText().isEmpty() || ComboBoxType.getValue() == null || TFActivities.getText().isEmpty() ||
                TFPrice.getText().isEmpty() || ComboBoxDestination.getValue() == null) {
            showAlert("Veuillez remplir tous les champs.");
            return false;
        }

        try {
            Float.parseFloat(TFPrice.getText());
        } catch (NumberFormatException e) {
            showAlert("Le prix doit être un nombre valide.");
            return false;
        }

        return true;
    }





    public void navigatetoAfficherHebergementAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherHebergement.fxml"));
            TFName.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }

    }







    public void updateAction(ActionEvent actionEvent) {
    }





    public void deleteHebergementAction(ActionEvent actionEvent) {
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

    public void populateForm(Hebergement hebergement) {
        System.out.println("Populating form with Hebergement: " + hebergement);
        TFName.setText(hebergement.getName());
        TFLocation.setText(hebergement.getLocation());
        TFDescription.setText(hebergement.getDescription());

        // Set selected type as the value of ComboBoxType
        ComboBoxType.setValue(hebergement.getSelectedType());

        TFActivities.setText(hebergement.getActivities());
        TFPrice.setText(String.valueOf(hebergement.getPrice()));
        TFPicture.setText(hebergement.getPicture());
        ComboBoxDestination.setValue(hebergement.getVoyage().getDestination());
    }


    @FXML
    void saveChanges(ActionEvent event) throws IOException, SQLException {
        HebergementDesign HebergementDesign = edu.esprit.controllers.HebergementDesign.getInstance();

        Hebergement currentHebergement = HebergementDesign.getCurrentHebergement();

        System.out.println(currentHebergement);

        // Récupérer la destination sélectionnée
        String selectedVoyageDestination = ComboBoxDestination.getValue().toString();
        Voyage selectedDestination = sp.getVoyageByDestination(selectedVoyageDestination);

        // Mettre à jour l'objet Hebergement avec la destination sélectionnée

        Hebergement updatedHebergement = new Hebergement(
                currentHebergement.getId(),
                TFName.getText(),
                TFPicture.getText(),
                TFLocation.getText(),
                TFDescription.getText(),
                ComboBoxType.getValue().toString(), // Utilisez la valeur sélectionnée du ComboBoxType
                TFActivities.getText(),
                Float.parseFloat(TFPrice.getText()),
                selectedDestination



        );
        System.out.println("aaaaa"+updatedHebergement);

        // Mise à jour de l'interface utilisateur avec les nouvelles valeurs
        populateForm(updatedHebergement);


        TFName.setText(TFName.getText());
        TFLocation.setText(TFLocation.getText());
        TFDescription.setText(TFDescription.getText());
        ComboBoxType.setValue(ComboBoxType.getValue());
        TFActivities.setText(TFActivities.getText());
        TFPrice.setText(TFPrice.getText());
        TFPicture.setText(TFPicture.getText());


        // Update the Vol object in the database
        sp.modifier(updatedHebergement);
        populateScrollPane();
        clearFormFields();
        // Affichez un message de confirmation
        showAlert("Hébergement modifié avec succès.");

// Repopulate the ScrollPane+

    }

    private void clearFormFields() {
        TFName.clear();
        TFPicture.clear();
        TFLocation.clear();
        TFDescription.clear();
        ComboBoxType.setValue(null); // Clear the selection of ComboBoxType
        TFActivities.clear();
        TFPrice.clear();
    }

}
