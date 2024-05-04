package edu.esprit.controllers;

import edu.esprit.entities.Vol;
import edu.esprit.services.ServiceVol;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AfficherVol {

    @FXML
    private VBox VolContainer = new VBox();

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TableView TablePanier;

    @FXML
    private Button ButtonPanier;

    @FXML
    private Pane PanePanier;

    @FXML
    private Slider budgetSlider;

    @FXML
    private TextField tfSlider;

    private final ServiceVol sp = new ServiceVol();


    private int visible=0;




    @FXML
    private void initialize() throws IOException {
        // Call method to populate TableView when the scene is loaded
        populateScrollPane();

        if(visible==0)
        PanePanier.setVisible(false);

        TableColumn<ObservableList<String>, String> column1 = new TableColumn<>("Code");
        column1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));

        TableColumn<ObservableList<String>, String> column2 = new TableColumn<>("Aeroport Depart");
        column2.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(1)));

        TableColumn<ObservableList<String>, String> column3 = new TableColumn<>("Aeroport Arrive");
        column3.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(2)));

        TableColumn<ObservableList<String>, String> column4 = new TableColumn<>("Prix");
        column4.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(3)));



        // Add the columns to the TableView
        TablePanier.getColumns().addAll(column1, column2, column3,column4);

        budgetSlider.setMin(0);
        budgetSlider.setMax(1000); // Set this to the maximum possible budget
        budgetSlider.setValue(0);

        budgetSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            // Update the TextField with the new slider value
            tfSlider.setText(String.format("%.2f", newValue.doubleValue()));
        });
        budgetSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            try {
                BudgetSliderAction();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TicketVolFront.fxml"));
            Pane pane = loader.load();

            // Get the controller and update the ticket details
            TicketVolFront controller = loader.getController();
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

            controller.setAfficherVolController(this);

            // Add the pane to the VBox
            VolContainer.getChildren().add(pane);
        }

        // Set the VBox as the content of the ScrollPane
        scrollPane.setContent(VolContainer);
    }

    public void addToPanier(Vol vol) {
        // Create a new row with the vol's information
        ObservableList<String> row = FXCollections.observableArrayList();
        row.add(String.valueOf(vol.getCode()));
        row.add(vol.getAeroport_depart());
        row.add(vol.getGetAeroport_arrive());
        row.add(vol.getPrix().toString());
        TablePanier.getItems().add(row);
    }

    public void ShowPanierAction(ActionEvent actionEvent) {
        visible = 1 - visible;
        visible(visible);

    }

    public void visible(int i){
        if(i==0)
            PanePanier.setVisible(false);
        else
            PanePanier.setVisible(true);

    }

    public void PayerPanierAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterPaiement.fxml"));
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

    public void NavigateDashboardAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ajouterVol.fxml"));
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

    public void BudgetSliderAction() throws IOException {
        Float budget = (float) budgetSlider.getValue(); // Normalize search text to lower case
        Set<Vol> allVols = sp.getAll();

        // Clear the VolContainer to remove previous search results
        VolContainer.getChildren().clear();

        // Iterate over all Vols and add only those that match the search criteria
        for (Vol vol : allVols) {
            // Check if the departure airport contains the search text
            if (vol.getPrix()<=budget) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/TicketVolFront.fxml"));
                Pane pane = loader.load();

                // Get the controller and update the ticket details
                TicketVolFront controller = loader.getController();
                controller.setVol(vol, pane, VolContainer);

                // Optionally load and display the image
                String imagePath = vol.getImage();
                if (imagePath != null && !imagePath.isEmpty()) {
                    File file = new File(imagePath);
                    if (file.exists()) {
                        Image image = new Image(file.toURI().toString());
                        controller.setVolImage(image);
                    }
                }

                // Add the matching pane to the container
                VolContainer.getChildren().add(pane);
            }
        }
    }
}
