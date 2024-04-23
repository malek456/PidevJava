package com.example.reclamation.controllers;
import com.example.reclamation.models.Reponse;
import com.example.reclamation.models.User;
import com.example.reclamation.services.ServiceReponse;
import com.example.reclamation.test.FxMain;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
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

    private AfficherReponseController afficherReponseFXML;
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

    public void afficherReclamation(ActionEvent event) {
//        User user = (User) vbox.getScene().getUserData();
//        System.out.println("userdata="+user.getId()+"  "+user.getRoles());
        User user = FxMain.getGlobalUserData();
        System.out.println("userdata="+user.getId()+"  "+user.getRoles());
        try {

            list = new ArrayList<>();
            list.addAll(sr.selectAll_ByIdUser(user.getId()));

           // ObservableList<Reclamation> observableList = FXCollections.observableList(list);
            int i=1;
            for(Reclamation reclam : list){

                Label labelrec = new Label("Reclamation "+i);
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
                        "    -fx-background-radius: 8px;\n" +
                        "    -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 4);\n" +
                        "    -fx-padding: 20px;\n" +
                        "    -fx-spacing: 10px;\n" +
                        "    -fx-max-width: 200px;");
                VBox.setMargin(pane, new Insets(0,0,20,0));
                vbox.getChildren().add(pane);
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

            }


//            tableview.setItems(observableList);
//
//            id.setCellValueFactory(new PropertyValueFactory<Reclamation, Integer>("id"));
//            type.setCellValueFactory(new PropertyValueFactory<Reclamation, String>("type"));
//            description.setCellValueFactory(new PropertyValueFactory<Reclamation, String>("description"));
//            date.setCellValueFactory(new PropertyValueFactory<Reclamation, Date>("date"));
//            date_envoi.setCellValueFactory(new PropertyValueFactory<Reclamation, Date>("date_envoi"));
//            statut.setCellValueFactory(new PropertyValueFactory<Reclamation, String>("statut"));

        }catch(SQLException e){
            System.err.println("Erreur: "+e.getMessage());
        }

    }

    public void clearVbox(){
        vbox.getChildren().clear();
    }



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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/reclamation/afficherReponseFXML.fxml"));
            Parent root = loader.load();
            // Show the scene
            scene = vbox.getScene();
            scene.setRoot(root);
            //scene.setUserData(2);
            Stage stage = (Stage) scene.getWindow();
            stage.setTitle("afficher Reponses");
            afficherReponseFXML = loader.getController();
            afficherReponseFXML.afficherReponse(Rec);

        }
        catch (IOException e){
            System.out.println(e.getMessage());
    }
    }
}
