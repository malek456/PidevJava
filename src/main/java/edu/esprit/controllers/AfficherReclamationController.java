<<<<<<< HEAD
package com.example.reclamation.controllers;
import com.example.reclamation.models.Reponse;
import com.example.reclamation.models.User;
import com.example.reclamation.services.ServiceReponse;
import com.example.reclamation.test.FxMain;
=======
package edu.esprit.controllers;
import edu.esprit.entities.Reponse;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceReponse;
import edu.esprit.tests.FxMain;
>>>>>>> ba038a7 (metiers+api)
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
<<<<<<< HEAD
=======
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
>>>>>>> ba038a7 (metiers+api)
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
<<<<<<< HEAD
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import com.example.reclamation.models.Reclamation;
import com.example.reclamation.services.ServiceReclamation;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

=======
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

>>>>>>> ba038a7 (metiers+api)
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

<<<<<<< HEAD
    private AfficherReponseController afficherReponseFXML;
=======

    private AfficherReponseController afficherReponseController;

>>>>>>> ba038a7 (metiers+api)
    ServiceReclamation sr = new ServiceReclamation();
    @FXML
    Pane pane;
    @FXML
    private Pane reclamPane;
    @FXML
    private VBox reponseVbox;
<<<<<<< HEAD
=======

>>>>>>> ba038a7 (metiers+api)
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

<<<<<<< HEAD
    public void afficherReclamation(ActionEvent event) {
//        User user = (User) vbox.getScene().getUserData();
//        System.out.println("userdata="+user.getId()+"  "+user.getRoles());
        User user = FxMain.getGlobalUserData();
        System.out.println("userdata="+user.getId()+"  "+user.getRoles());
=======
    @FXML
    void initialize() {
        afficherReclamation(new ActionEvent());
    }

    public void afficherReclamation(ActionEvent event) {

        User user = FxMain.getGlobalUserData();
        clearVbox();
>>>>>>> ba038a7 (metiers+api)
        try {

            list = new ArrayList<>();
            list.addAll(sr.selectAll_ByIdUser(user.getId()));

<<<<<<< HEAD
           // ObservableList<Reclamation> observableList = FXCollections.observableList(list);
=======
>>>>>>> ba038a7 (metiers+api)
            int i=1;
            for(Reclamation reclam : list){

                Label labelrec = new Label("Reclamation "+i);
<<<<<<< HEAD
                Label label = new Label(reclam.getDescription());
                VBox vbox1 = new VBox();
                vbox1.getChildren().add(labelrec);
                vbox1.getChildren().add(label);
                Button btnrep;
                if(reclam.getStatut().equals("résolu")) {
                    btnrep = new Button("Voir réponse");
                    btnrep.setOnAction(event2 -> navigateToAfficherReponse(reclam));
                    vbox1.getChildren().add(btnrep);
                }
                i++;

               // vbox1.getChildren().addAll(labelrec,label,button);
                Button btndelete = new Button("delete");
                btndelete.setOnAction(event2 ->{
                    supprimerReclamation(reclam);
                });
                vbox1.getChildren().add(btndelete);
                Pane pane = new Pane();
                pane.setPrefHeight(100);
                pane.setMinHeight(100);
                pane.getChildren().add(vbox1);
                pane.setUserData(reclam.getId());
                pane.setStyle("-fx-background-color: #ffffff;\n" +
=======
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
>>>>>>> ba038a7 (metiers+api)
                        "    -fx-background-radius: 8px;\n" +
                        "    -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 4);\n" +
                        "    -fx-padding: 20px;\n" +
                        "    -fx-spacing: 10px;\n" +
<<<<<<< HEAD
                        "    -fx-max-width: 200px;");
                VBox.setMargin(pane, new Insets(0,0,20,0));
                vbox.getChildren().add(pane);
=======
                        "    -fx-max-width: 600px;\n" +
                        "    -fx-max-height: 80px;");




>>>>>>> ba038a7 (metiers+api)
                EventHandler<MouseEvent> event2 =
                        new EventHandler<MouseEvent>() {
                            public void handle(MouseEvent e)
                            {
<<<<<<< HEAD


=======
>>>>>>> ba038a7 (metiers+api)
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
<<<<<<< HEAD
=======
                VBox vbox1 = new VBox(labelrec,pane);
                VBox.setMargin(pane, new Insets(0,0,20,0));
                vbox.getChildren().add(vbox1);
>>>>>>> ba038a7 (metiers+api)

            }


<<<<<<< HEAD
//            tableview.setItems(observableList);
//
//            id.setCellValueFactory(new PropertyValueFactory<Reclamation, Integer>("id"));
//            type.setCellValueFactory(new PropertyValueFactory<Reclamation, String>("type"));
//            description.setCellValueFactory(new PropertyValueFactory<Reclamation, String>("description"));
//            date.setCellValueFactory(new PropertyValueFactory<Reclamation, Date>("date"));
//            date_envoi.setCellValueFactory(new PropertyValueFactory<Reclamation, Date>("date_envoi"));
//            statut.setCellValueFactory(new PropertyValueFactory<Reclamation, String>("statut"));
=======
>>>>>>> ba038a7 (metiers+api)

        }catch(SQLException e){
            System.err.println("Erreur: "+e.getMessage());
        }

<<<<<<< HEAD
=======


>>>>>>> ba038a7 (metiers+api)
    }

    public void clearVbox(){
        vbox.getChildren().clear();
    }


<<<<<<< HEAD

    @FXML
    void initialize() {
        afficherReclamation(new ActionEvent());
    }
    @FXML
    public void afficherRow(){
        //rec = tableview.getItems().get(index);
        //System.out.println("afficher row"+rec.getId()+"  "+rec.getId_reservation().getId()+"id_client"+rec.getId_client()+rec.getId_reservation()+rec.getId_paiement());
        try {
            rec=sr.selectOne_by_id((int) pane.getUserData());
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
        this.ajouterReclamationFXML.fullFields(rec);
    }


=======
>>>>>>> ba038a7 (metiers+api)
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
<<<<<<< HEAD
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/reclamation/afficherReponseFXML.fxml"));
=======
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherReponse2FXML.fxml"));
>>>>>>> ba038a7 (metiers+api)
            Parent root = loader.load();
            // Show the scene
            scene = vbox.getScene();
            scene.setRoot(root);
            //scene.setUserData(2);
            Stage stage = (Stage) scene.getWindow();
            stage.setTitle("afficher Reponses");
<<<<<<< HEAD
            afficherReponseFXML = loader.getController();
            afficherReponseFXML.afficherReponse(Rec);
=======
            afficherReponseController = loader.getController();
            afficherReponseController.afficherReponse(Rec);
>>>>>>> ba038a7 (metiers+api)

        }
        catch (IOException e){
            System.out.println(e.getMessage());
    }
    }
}
