package edu.esprit.controllers;
import edu.esprit.entities.Reponse;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceReponse;
import edu.esprit.tests.FxMain;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import edu.esprit.entities.Reclamation;
import edu.esprit.services.ServiceReclamation;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;

public class AfficherReclamationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    public Reclamation rec;
    @FXML
    private VBox vbox;

    @FXML
    private AjouterReclamationController ajouterReclamationFXML;


    private AfficherReponseController afficherReponseController;

    ServiceReclamation sr = new ServiceReclamation();
    @FXML
    Pane pane;
    @FXML
    private Pane reclamPane;
    @FXML
    private VBox reponseVbox;

    private List<Reclamation> list;
    public void setControllerA(AjouterReclamationController controllerA) {
        this.ajouterReclamationFXML = controllerA;
    }

    public Reclamation getRec() {
        return rec;
    }

    public List<Reclamation> getList() {
        return list;
    }

    @FXML
    void initialize() {
        afficherReclamation(new ActionEvent());
    }

    public void afficherReclamation(ActionEvent event) {

        User user = FxMain.getGlobalUserData();
        clearVbox();
        try {

            list = new ArrayList<>();
            list.addAll(sr.selectAll_ByIdUser(user.getId()));

            int i=1;
            for(Reclamation reclam : list){

                Label labelrec = new Label("Reclamation "+i);
                labelrec.setFont(Font.font("System",FontWeight.BOLD, FontPosture.ITALIC , 15));
                labelrec.setStyle("-fx-background-color: rgba(0,0,240,0.3)");
                int maxCharacters = 20;
                String longText = reclam.getDescription();

                // Truncate the text if it exceeds the maximum number of characters and add ellipsis
                String truncatedText = longText.length() > maxCharacters ?
                        longText.substring(0, maxCharacters) + "..." :
                        longText;

                Label label = new Label();
                label.setPrefWidth(200);
                label.setWrapText(true);
                label.setMaxWidth(200);
                label.setText(truncatedText);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                Label labeldate = new Label(reclam.getDate_envoi().toLocalDateTime().format(formatter));
                labeldate.setPadding(new Insets(0,50,0,0));
                Label labelstatus = new Label(reclam.getStatut());
                HBox hboxlabel = new HBox(labelstatus);
                hboxlabel.setPadding(new Insets(0,70,0,0));
                hboxlabel.setPrefWidth(150);
                if (reclam.getStatut().equals("en attente")) {
                    labelstatus.setTextFill(Color.ORANGE);
                    labelstatus.setStyle("-fx-background-color: rgba(255, 165, 0, 0.1); -fx-background-radius: 30");
                } else if (reclam.getStatut().equals("en cours")) {
                    labelstatus.setTextFill(Color.BLUE);
                    labelstatus.setStyle("-fx-background-color: rgba(0, 0, 255, 0.1); -fx-background-radius: 30");
                } else if (reclam.getStatut().equals("résolu")) {
                    labelstatus.setTextFill(Color.GREEN);
                    labelstatus.setStyle("-fx-background-color: rgba(0, 128, 0, 0.1); -fx-background-radius: 30");
                }


                HBox hbox = new HBox(label,labeldate,hboxlabel);

                i++;

                Button btnview = new Button();
                Image viewIcon = new Image(String.valueOf(getClass().getResource("/images/icons8-open-24.png")));
                btnview.setStyle("-fx-background-color: transparent");
                btnview.setGraphic(new ImageView(viewIcon));
                btnview.setOnAction(event2 -> navigateToAfficherReponse(reclam));

                Button btndelete = new Button();
                Image delIcon = new Image(String.valueOf(getClass().getResource("/images/icons8-delete-30.png")));
                btndelete.setStyle("-fx-background-color: transparent");
                btndelete.setGraphic(new ImageView(delIcon));
                btndelete.setOnAction(event2 ->{
                    supprimerReclamation(reclam);
                });


                hbox.getChildren().addAll(btndelete,btnview);
                hbox.setPadding(new Insets(30,0,0,0));
                Pane pane = new Pane(hbox);
                pane.setUserData(reclam.getId());
                pane.setStyle("-fx-background-color: rgba(0, 0, 255, 0.1);\n" +
                        "    -fx-background-radius: 8px;\n" +
                        "    -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 4);\n" +
                        "    -fx-padding: 20px;\n" +
                        "    -fx-spacing: 10px;\n" +
                        "    -fx-max-width: 600px;\n" +
                        "    -fx-max-height: 80px;");




                EventHandler<MouseEvent> event2 =
                        new EventHandler<MouseEvent>() {
                            public void handle(MouseEvent e)
                            {
                                try {
                                    rec=sr.selectOne_by_id((int) pane.getUserData());
                                } catch (SQLException ex) {
                                    throw new RuntimeException(ex);
                                }
                                System.out.println((int)pane.getUserData());

                                ajouterReclamationFXML.fullFields(rec);
                            }
                        };
                pane.setOnMouseClicked(event2);
                VBox vbox1 = new VBox(labelrec,pane);
                VBox.setMargin(pane, new Insets(0,0,20,0));
                vbox.getChildren().add(vbox1);

            }



        }catch(SQLException e){
            System.err.println("Erreur: "+e.getMessage());
        }



    }

    public void clearVbox(){
        vbox.getChildren().clear();
    }


    public void supprimerReclamation(Reclamation rec){
        try {
            if(rec.getStatut().equals("résolu"))
                throw new RuntimeException("Delete is impossible");
            sr.deleteOne(rec);
            ajouterReclamationFXML.showPopup("Reclamation deleted");
            vbox.getChildren().clear();
            afficherReclamation(new ActionEvent());
        }catch(Exception ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText(ex.getMessage());
            alert.show();
        }
    }

    public void navigateToAfficherReponse(Reclamation Rec){
        Scene scene =null;
        try {
            //Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherReponse2FXML.fxml"));
            Parent root = loader.load();
            // Show the scene
            scene = vbox.getScene();
            scene.setRoot(root);
            //scene.setUserData(2);
            Stage stage = (Stage) scene.getWindow();
            stage.setTitle("afficher Reponses");
            afficherReponseController = loader.getController();
            afficherReponseController.afficherReponse(Rec);

        }
        catch (IOException e){
            System.out.println(e.getMessage());
    }
    }
}
