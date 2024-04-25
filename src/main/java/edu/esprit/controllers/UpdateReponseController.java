package edu.esprit.controllers;

import edu.esprit.entities.Reclamation;
import edu.esprit.entities.Reponse;
import edu.esprit.services.ServiceReponse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
    @FXML
    private VBox vboxContenu;
    @FXML
    private VBox vboxEtat;
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
            if(Rep2.getEtat().equals("") || Rep2.getEtat().length()>20 || Rep2.getContenu().equals("") || Rep2.getContenu().length()>2000 || Rep2.getContenu().length() < 10)
                throw new RuntimeException();

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
            Label errorEtatLabel = new Label("");
            Label errorContenuLabel = new Label("");
            errorEtatLabel.setTextFill(Color.RED);
            errorContenuLabel.setTextFill(Color.RED);
            clearErrors();

            if(Rep2.getEtat().isEmpty()){
                errorEtatLabel.setText("etat must not be null");
                vboxEtat.getChildren().add(errorEtatLabel);
            }
            else {
                if (Rep2.getEtat().length() > 20) {
                    errorEtatLabel.setText("etat must be less than 20 characters");
                    vboxEtat.getChildren().add(errorEtatLabel);
                }
            }

            if(Rep2.getContenu().isEmpty()){
                errorContenuLabel.setText("contenu must not be null");
                vboxContenu.getChildren().add(errorContenuLabel);
            }
            else{
                if(Rep2.getContenu().length() < 10 || Rep2.getContenu().length() > 1000){
                    errorContenuLabel.setText("contenu must be between 10 and 1000 characters");
                    vboxContenu.getChildren().add(errorContenuLabel);
                }
            }
            if(ex.getMessage()!=null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setContentText(ex.getMessage());
                alert.show();
            }
        }

    }

    private void clearErrors(){
        if(vboxEtat.getChildren().size() > 1)
            vboxEtat.getChildren().remove(1);
        if(vboxContenu.getChildren().size() > 1)
            vboxContenu.getChildren().remove(1);
    }

}
