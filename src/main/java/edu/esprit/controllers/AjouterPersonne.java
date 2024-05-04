package edu.esprit.controllers;

import edu.esprit.entities.Personne;
import edu.esprit.services.ServicePersonne;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class AjouterPersonne {
    @FXML
    private TextField TFnom;

    @FXML
    private TextField TFprenom;
    private final ServicePersonne sp = new ServicePersonne();

    @FXML
    void ajouterPersonneAction(ActionEvent event) {
        try {
            sp.ajouter(new Personne(TFnom.getText(),TFprenom.getText()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("GG");
            alert.show();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Exception");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    public void navigatetoAfficherPersonneAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherPersonne.fxml"));
            TFnom.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }

    }


}
