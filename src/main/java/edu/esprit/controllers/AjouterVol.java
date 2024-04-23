package edu.esprit.controllers;

import edu.esprit.entities.Vol;
import edu.esprit.entities.Vol;
import edu.esprit.entities.Vol;
import edu.esprit.entities.Vol;
import edu.esprit.services.ServiceVol;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.lang.String.valueOf;
import static java.sql.Date.valueOf;


public class AjouterVol {

    @FXML
    private TextField tfAeroportDepart;

    @FXML
    private TextField tfAeroportArrive;

    @FXML
    private TextField tfDateDepart;

    @FXML
    private TextField tfDateArrive;

    @FXML
    private TextField tfPrix;

    @FXML
    private TextField tfCode;

    @FXML
    private TextField tfNombrePersonne;

    @FXML
    private TextField tfImage;


    @FXML
    private VBox VolContainer = new VBox();

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Pane selectedPane;
    private final ServiceVol sp = new ServiceVol();

    Map<Pane, Vol> paneVolMap = new HashMap<>();


    @FXML
    private void initialize() throws IOException {
        // Call method to populate TableView when the scene is loaded
        populateScrollPane();


    }

    private void populateScrollPane() throws IOException {
        // Get all the flights from the database
        Set<Vol> allVols = sp.getAll();

        // Clear the ticketContainer
        VolContainer.getChildren().clear();

        // Create a VBox to hold all the flight Panes
        VolContainer.setSpacing(50);

        // For each flight, load the FXML file and add it to the VBox
        for (Vol vol : allVols) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TicketVol.fxml"));
            Pane pane = loader.load();

            // Get the controller and update the ticket details
            TicketVol controller = loader.getController();
            controller.setVol(vol,pane,VolContainer);

            // Load and display the image
            String imagePath = vol.getImage();
            if (imagePath != null && !imagePath.isEmpty()) {
                File file = new File(imagePath);
                if (file.exists()) {
                    Image image = new Image(file.toURI().toString());
                    controller.setVolImage(image);
                }
            }

            controller.setAjouterVolController(this);

            // Add the pane to the VBox
            VolContainer.getChildren().add(pane);
        }

        // Set the VBox as the content of the ScrollPane
        scrollPane.setContent(VolContainer);
    }

    public  void populateForm(Vol vol) {
        System.out.println("Populating form with Vol: " + vol);
        tfAeroportDepart.setText(vol.getAeroport_depart());
        tfAeroportArrive.setText(vol.getGetAeroport_arrive());
        tfDateDepart.setText(vol.getDate_depart().toString());
        tfDateArrive.setText(vol.getGetDate_arrive().toString());
        tfPrix.setText(valueOf(vol.getPrix()));
        tfCode.setText(valueOf(vol.getCode()));
        tfNombrePersonne.setText(valueOf(vol.getNombre_personnes()));
        tfImage.setText(vol.getImage());
    }

    public void ajouterVolAction(ActionEvent actionEvent) throws SQLException, IOException {
        String imagePath = tfImage.getText();
        Vol newVol = new Vol(
                tfAeroportDepart.getText(),tfAeroportArrive.getText(),valueOf( tfDateArrive.getText()),valueOf(tfDateArrive.getText()),Float.parseFloat(tfPrix.getText()),Integer.parseInt( tfCode.getText()),Integer.parseInt(tfNombrePersonne.getText()),tfImage.getText() ) ;
        sp.ajouter(newVol);

        System.out.println("New Vol: " + newVol);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/TicketVol.fxml"));
        Pane pane = loader.load();


        // Get the controller and update the ticket details
        TicketVol controller = loader.getController();
        controller.setVol(newVol,pane,VolContainer);

        paneVolMap.put(pane, newVol);
        selectedPane= pane;

        //TicketVol.setAjouterVolController(this);

        VolContainer.setSpacing(50);
        VolContainer.getChildren().add(pane);
        scrollPane.setContent(VolContainer);
        clearFormFields();
    }

    public void uploadImageAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            tfImage.setText(file.getAbsolutePath());
        }
    }

    public void saveChanges(ActionEvent actionEvent) throws IOException {
        TicketVol ticketVol = TicketVol.getInstance();

        Vol currentVol = ticketVol.getCurrentVol();

        System.out.println(currentVol);

        Vol updatedVol = new Vol(
                currentVol.getId(),
                tfAeroportDepart.getText(),
                tfAeroportArrive.getText(),
                java.sql.Date.valueOf(tfDateDepart.getText()),
                java.sql.Date.valueOf(tfDateArrive.getText()),
                Float.parseFloat(tfPrix.getText()),
                Integer.parseInt(tfCode.getText()),
                Integer.parseInt(tfNombrePersonne.getText()),
                tfImage.getText()

        );
        System.out.println(updatedVol);

        // Update the Vol object in the database
        sp.modifier(updatedVol);
        populateScrollPane();
        clearFormFields();

    }

    private void clearFormFields() {
        tfAeroportDepart.clear();
        tfAeroportArrive.clear();
        tfDateDepart.clear();
        tfDateArrive.clear();
        tfPrix.clear();
        tfCode.clear();
        tfNombrePersonne.clear();
        tfImage.clear();
    }

    public void NavigateHomeAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/front.fxml"));
            tfAeroportDepart.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }

    }



    public void NavigateAfficherPaiementAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherPaiement.fxml"));
            // Getting the scene and setting the root
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
}
