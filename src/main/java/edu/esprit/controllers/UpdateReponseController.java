package com.example.reclamation.controllers;

import com.example.reclamation.models.Reclamation;
import com.example.reclamation.models.Reponse;
import com.example.reclamation.services.ServiceReponse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class UpdateReponseController {

    @FXML
    private TextArea tacontenu;

    @FXML
    private TextField tfetat;
    ServiceReponse sr = new ServiceReponse();
    Reponse Rep;
    AfficherReponseController afficherReponseController;

    public void setAfficherReponseController(AfficherReponseController afficherReponseController) {
        this.afficherReponseController = afficherReponseController;
    }

    public void updateFields(Reponse rep){
        tfetat.setText(rep.getEtat());
        tacontenu.setText(rep.getContenu());
        Rep=rep;
    }
    @FXML
    void updateReponse(ActionEvent event) {
        Reponse Rep2 = new Reponse();
        Rep2.setId(Rep.getId());
        Rep2.setEtat(tfetat.getText());
        Rep2.setContenu(tacontenu.getText());
        Rep2.setDate(Rep.getDate());
        Rep2.setReclamation(Rep.getReclamation());
        try {
            if(Rep2.getEtat().equals(""))
                throw new RuntimeException("etat must not be null");
            if(Rep2.getEtat().length()>20)
                throw new RuntimeException("etat must be less than 20 characters");
            if(Rep2.getContenu().equals(""))
                throw new RuntimeException("contenu must not be null");
            if(Rep2.getContenu().length()>2000)
                throw new RuntimeException("contenu must be less than 2000 characters");
            if(Rep2.equals(Rep))
                throw new RuntimeException("No modifications made");
            sr.updateOne(Rep2);
            Stage stage = (Stage) tfetat.getScene().getWindow();
            stage.close();
            afficherReponseController.reload(new ActionEvent());
            afficherReponseController.showPopup("Response modified");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }catch(Exception ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText(ex.getMessage());
            alert.show();
        }

    }

}
