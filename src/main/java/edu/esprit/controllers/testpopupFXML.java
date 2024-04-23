package com.example.reclamation.controllers;

import com.example.reclamation.models.Notification;
import com.example.reclamation.models.Reponse;
import com.example.reclamation.models.User;
import com.example.reclamation.services.ServiceNotification;
import com.example.reclamation.services.ServiceReponse;
import com.example.reclamation.services.ServiceUser;
import com.example.reclamation.test.FxMain;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class testpopupFXML implements Initializable{



    @FXML
    private VBox vbox;
    @FXML
    private ImageView button;
    ServiceNotification sn = new ServiceNotification();
    ServiceUser su = new ServiceUser();

    @FXML
    void show(MouseEvent event) {
        Scene scene = vbox.getScene();
        Stage stage = (Stage) scene.getWindow();
        ListView list = new ListView();
        list.setFixedCellSize(100);
        list.setOrientation(Orientation.VERTICAL);

        
        List<Notification> listNotif = new ArrayList<>();
        //ajouter condition sur l'utilisateur pour afficher le type
        User user = null;
        try {
            user = su.selectOne_by_id(1);
            System.out.println("role user="+user.getRoles());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            listNotif.addAll(sn.selectAllBy_id(user));

            } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

            for (Notification notif : listNotif) {
                TextFieldListCell cell = new TextFieldListCell<>();
                cell.setText(notif.getReponse().getContenu() + "\n" + notif.getDate() + "\n");
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
        popup.setX(stage.getX()+button.getLayoutX()-180);
        popup.setY(stage.getY()+button.getLayoutY()+60);

        if (!popup.isShowing())
            popup.show(stage);



    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventHandler<MouseEvent> event2 =
                new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e)
                    {

                        show(e);
                    }
                };
        button.setOnMouseClicked(event2);

    }


}
