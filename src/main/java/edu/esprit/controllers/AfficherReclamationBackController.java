package com.example.reclamation.controllers;

import com.example.reclamation.models.Notification;
import com.example.reclamation.models.Reclamation;
import com.example.reclamation.models.User;
import com.example.reclamation.services.ServiceNotification;
import com.example.reclamation.services.ServiceReclamation;
import com.example.reclamation.test.FxMain;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherReclamationBackController implements Initializable {
    Button button;
    @FXML
    private VBox vbox;
    @FXML
    private ImageView notif;
    private AfficherReponseController afficherReponseFXML;
    ServiceNotification sn = new ServiceNotification();
    ServiceReclamation sr = new ServiceReclamation();
    public void afficherReclamation(ActionEvent event) {

        try {
            List<Reclamation> list = new ArrayList<>();
            list.addAll(sr.selectAll());

            // ObservableList<Reclamation> observableList = FXCollections.observableList(list);
            int i=1;
            for(Reclamation reclam : list){
                Label labelrec = new Label("Reclamation "+i);
                Label label = new Label(reclam.getDescription());
                VBox vbox1 = new VBox();
                vbox1.getChildren().add(labelrec);
                vbox1.getChildren().add(label);
                if(reclam.getStatut().equals("résolu")) {
                    button = new Button("Voir réponse");
                    button.setOnAction(event2 -> navigateToAfficherReponse(reclam));
                    vbox1.getChildren().add(button);
                }
                else {
                    button = new Button("répondre");
                    button.setOnAction(event2 -> navigateToAfficherReponse(reclam));
                    vbox1.getChildren().add(button);

                }
                i++;

                // vbox1.getChildren().addAll(labelrec,label,button);

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

            }


        }catch(SQLException e){
            System.err.println("Erreur: "+e.getMessage());
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
            if(Rec.getStatut().equals("en attente")){
                Rec.setStatut("en cours");
                sr.updateOne(Rec);
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        afficherReclamation(new ActionEvent());
    }

    @FXML
    void showNotif(MouseEvent event) {

        Scene scene = notif.getScene();
        Stage stage = (Stage) scene.getWindow();
        ListView list = new ListView();
        list.setFixedCellSize(100);
        list.setOrientation(Orientation.VERTICAL);

        User user = FxMain.getGlobalUserData();
        List<Notification> listNotif = new ArrayList<>();
        //ajouter condition sur l'utilisateur pour afficher le type
        System.out.println("id from notif=" + user.getId() + "   "+ user.getRoles());
        try {
            listNotif.addAll(sn.selectAllBy_id(user));

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        for (Notification notif : listNotif) {
            TextFieldListCell cell = new TextFieldListCell<>();
            cell.setText(notif.getReclamation().getDescription() + "\n" + notif.getDate() + "\n");
            // set background
            cell.setStyle(" -fx-background-color: white;");
            // set size of label
            cell.setMinWidth(200);
            cell.setMinHeight(100);
            //layout.getChildren().add(notif);
            list.getItems().add(cell);
        }

        EventHandler<MouseEvent> listevent =
                new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e)
                    {

                        try {
                            int selectedIdx = list.getSelectionModel().getSelectedIndex();
                            sn.updateOne(listNotif.get(selectedIdx));
                            final int newSelectedIdx =
                                    (selectedIdx == list.getItems().size() - 1)
                                            ? selectedIdx - 1
                                            : selectedIdx;
                            //System.out.println(selectedIdx+"    "+newSelectedIdx);
                            list.getItems().remove(selectedIdx);
                            listNotif.remove(selectedIdx);
                        } catch (Exception ex) {
                            System.err.println(ex.getMessage());
                        }



                    }
                };
        if(listNotif.size()>0)
            list.setOnMouseClicked(listevent);
        else{
            Label msg = new Label("Aucune reponse");
            list.getItems().add(msg);
        }

        // create a popup
        Popup popup = new Popup();
        popup.getContent().add(list);



        // set auto hide
        popup.setAutoHide(true);
        popup.setX(stage.getX()+notif.getLayoutX()-180);
        popup.setY(stage.getY()+notif.getLayoutY()+60);

        if (!popup.isShowing())
            popup.show(stage);



    }
}
