package com.example.reclamation.controllers;

import com.example.reclamation.models.Reclamation;
import com.example.reclamation.models.Reponse;
import com.example.reclamation.models.User;
import com.example.reclamation.services.ServiceReclamation;
import com.example.reclamation.services.ServiceReponse;
import com.example.reclamation.test.FxMain;
import com.itextpdf.kernel.pdf.colorspace.PdfDeviceCs;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Duration;

import javafx.scene.image.ImageView;

public class AfficherReponseController {

    @FXML
    private Pane reclamPane;

    @FXML
    private VBox reponseVbox;
    @FXML
    private Label latype;

    @FXML
    private Label ladescription;
    @FXML
    private TextField tfetat;
    @FXML
    private TextArea tacontenu;
    private Button updaterep;
    private Button deleterep;
    UpdateReponseController updateReponseFXML;
    ServiceReponse sr = new ServiceReponse();
    ServiceReclamation srec = new ServiceReclamation();
    private Reclamation reclam;
    private List<Reponse> list;
    private int i = 0;



    public void afficherReponse(Reclamation Rec){
        this.reclam = Rec;
        latype.setText(Rec.getType());
        ladescription.setText(Rec.getDescription());
        if(Rec.getStatut().equals("résolu")) {
            try {

                Label labelrec = new Label("Reclamation ");
                Label label = new Label(Rec.getDescription());
                VBox vbox1 = new VBox();
                vbox1.getChildren().add(labelrec);
                vbox1.getChildren().add(label);
                reclamPane.getChildren().add(vbox1);
                Label test = new Label();
                list = new ArrayList<>();
                list.addAll(sr.selectAll_by_idReclamation(Rec.getId()));
                    for (Reponse rep : list) {
                        Label labelrep = new Label("Reponse " + i);
                        Label label1 = new Label(rep.getContenu());
                        VBox vbox2 = new VBox();
                        vbox2.getChildren().addAll(labelrep, label1);

                        if (FxMain.getGlobalUserData().getRoles().equals("[\"ROLE_ADMIN\"]")) {
                            updaterep = new Button("update");
                            deleterep = new Button("delete");
                            updaterep.setOnAction(event -> navigateToUpdateReponse(rep));
                            deleterep.setOnAction(event -> deleteAction(rep));
    //                        Alert a = new Alert(AlertType.CONFIRMATION);
    //                        a.setContentText("Are you sure you want to delete the claim?");
    //                        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>(){
    //                            public void handle(ActionEvent e)
    //                            {
    //
    //                                Optional<ButtonType> result = a.showAndWait();
    //                                if(result.get() == ButtonType.OK)
    //                                    System.out.println("ok");
    //                                else if (result.get() == ButtonType.CANCEL) {
    //                                    System.out.println("cancel");
    //
    //                                }
    //                            }
    //                        };
                            //              deleterep.setOnAction(event);
                            HBox hboxbtns = new HBox();
                            hboxbtns.getChildren().addAll(updaterep, deleterep);
                            vbox2.getChildren().add(hboxbtns);
                        }

                        Pane pane1 = new Pane();
                        pane1.setPrefHeight(100);
                        pane1.setMinHeight(100);
                        pane1.getChildren().add(vbox2);
                        reponseVbox.getChildren().add(pane1);
                        i++;
                    }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if(FxMain.getGlobalUserData().getRoles().equals("[\"ROLE_ADMIN\"]")) {

            tfetat.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
            tacontenu.setPromptText("message");
            tacontenu.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

        }


        //VBox.setMargin(pane, new Insets(0,0,20,0));
    }

    public void deleteAction(Reponse rep){
        try {
            sr.deleteOne(rep);
            list.remove(rep);
            showPopup("Response deleted");
            if(list.size() == 0){
                System.out.println("list is empty");
                reclam.setStatut("en cours");
                srec.updateOne(reclam);
            }
        reload(new ActionEvent());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void addResponse(ActionEvent event) {
        Reponse rep = new Reponse(new Timestamp(System.currentTimeMillis()), tacontenu.getText(), tfetat.getText(), reclam);
        try {
            if(list != null && list.indexOf(rep) != -1)
                throw new RuntimeException("Response exists");
            if(rep.getEtat().equals(""))
                throw new RuntimeException("etat must not be null");
            if(rep.getEtat().length()>20)
                throw new RuntimeException("etat must be less than 20 characters");
            if(rep.getContenu().equals(""))
                throw new RuntimeException("contenu must not be null");
            if(rep.getContenu().length()>2000)
                throw new RuntimeException("contenu must be less than 2000 characters");
            if(list == null) {
                list = new ArrayList<>();
            }
            list.add(rep);
            sr.insertOne(rep);
            if (reclam.getStatut().equals("en cours")) {
                reclam.setStatut("résolu");
                srec.updateOne(reclam);
            }
            showPopup("Response added");
            tfetat.clear();
            tacontenu.clear();
           // reload(new ActionEvent());
            Label labelrep = new Label("Reponse " + i);
            Label label1 = new Label(rep.getContenu());
            VBox vbox2 = new VBox();
            vbox2.getChildren().addAll(labelrep, label1);
            HBox hboxbtns = new HBox();
            updaterep = new Button("update");
            deleterep = new Button("delete");
            updaterep.setOnAction(event2 -> navigateToUpdateReponse(rep));
            deleterep.setOnAction(event2 -> deleteAction(rep));
            hboxbtns.getChildren().addAll(updaterep, deleterep);
            vbox2.getChildren().add(hboxbtns);
            Pane pane1 = new Pane();
            pane1.setPrefHeight(100);
            pane1.setMinHeight(100);
            pane1.getChildren().add(vbox2);
            reponseVbox.getChildren().add(pane1);
            i++;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        catch (Exception ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText(ex.getMessage());
            alert.show();
        }
    }

    public void showPopup(String text){
        Popup popup = new Popup();
        Image image = new Image("C:\\Users\\gasso\\IdeaProjects\\reclamation\\src\\main\\resources\\com\\example\\reclamation\\images\\icons8-ok-48.png");
        ImageView popupimg = new ImageView();
        popupimg.setImage(image);
        Label label2 = new Label(text);
        label2.setPadding(new Insets(15,0,0,10));
        label2.setTextFill(Color.GREEN);
        label2.setFont(new Font("Helvetica", 14));
        label2.setPadding(new Insets(15,0,0,0));
        HBox hbox = new HBox(popupimg,label2);
        hbox.setPadding(new Insets(10,20,10,10));
        hbox.setStyle("-fx-background-color: white");
        popup.getContent().addAll(hbox);
        popup.setAutoHide(true);

        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(e -> popup.hide());
        popup.show(reponseVbox.getScene().getWindow());
        delay.play();
    }

    @FXML
    void reload(ActionEvent event) {
        i=0;
        reponseVbox.getChildren().clear();
        afficherReponse(reclam);
    }

    public void clearFields(){

    }
    public void navigateToUpdateReponse(Reponse rep){

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/reclamation/UpdateReponseFXML.fxml"));
            Parent root = loader.load();
            // Show the scene
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            //scene.setUserData(2);
            stage.initOwner(reponseVbox.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setScene(scene);
            stage.setTitle("update Reponse");
            stage.show();
            updateReponseFXML = loader.getController();
            updateReponseFXML.updateFields(rep);
            updateReponseFXML.setAfficherReponseController(this);

        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void navigateBack(ActionEvent event) {
        if(FxMain.getGlobalUserData().getRoles().equals("[\"ROLE_USER\"]")) {
            Scene scene =null;
            try {
                //Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/reclamation/AjouterReclamationFXML.fxml"));
                Parent root = loader.load();
                // Show the scene
                scene = reponseVbox.getScene();
                scene.setRoot(root);
                //scene.setUserData(2);
                Stage stage = (Stage) scene.getWindow();
                stage.setTitle("Ajouter réclamation");


            }
            catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
        else if (FxMain.getGlobalUserData().getRoles().equals("[\"ROLE_ADMIN\"]")){
            Scene scene =null;
            try {
                //Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/reclamation/AfficherReclamationBackFXML.fxml"));
                Parent root = loader.load();
                // Show the scene
                scene = reponseVbox.getScene();
                scene.setRoot(root);
                //scene.setUserData(2);
                Stage stage = (Stage) scene.getWindow();
                stage.setTitle("Afficher réclamation");


            }
            catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
    }

}
