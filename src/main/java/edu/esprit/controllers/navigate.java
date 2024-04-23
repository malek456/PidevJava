package com.example.reclamation.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class navigate implements Initializable {

    @FXML
    private Label mylabel;
    @FXML
    private ScrollPane scroll;
    @FXML
    private VBox vbox;
private int i = 0;

    @FXML
    void navigateTo(ActionEvent event) {
Scene scene =null;
        try{
            //Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/reclamation/scene2.fxml"));
            Parent root = loader.load();
            // Show the scene
            scene = mylabel.getScene();
            scene.setRoot(root);
            scene.setUserData(2);
            Stage stage = (Stage) scene.getWindow();
            stage.setTitle("byeee");

        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        System.out.println("scene2="+scene.getUserData());
    }
    @FXML
    void navigateback(ActionEvent event) {
        Scene scene = null;
        try{
            //Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/reclamation/scene1.fxml"));
            Parent root = loader.load();
            // Show the scene
            //Scene scene = new Scene(root);
            scene = mylabel.getScene();
            scene.setRoot(root);
            Stage stage = (Stage) scene.getWindow();
            stage.setTitle("helllooo");
            scene.setUserData(1);


        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        System.out.println("scene1="+scene.getUserData());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        Pane pane=new Pane();
//        pane.setPrefHeight(100);
//        pane.setMinHeight(100);
//        pane.setStyle("-fx-background-color: red;" );
//        Pane pane1=new Pane();
//        pane1.setPrefWidth(100);
//        pane1.setStyle("-fx-background-color: green;" );
//        Pane pane2=new Pane();
//        pane2.setPrefWidth(100);
//        pane2.setStyle("-fx-background-color: blue;" );
//        vbox.getChildren().add(pane);
//        vbox.getChildren().add(pane1);
//        vbox.getChildren().add(pane2);


    }

    @FXML
    void add(ActionEvent event) {
//        Pane pane=new Pane();
//pane.setPrefHeight(100);
//pane.setMinHeight(100);
//         pane.setStyle("-fx-background-color: red;" );
//         Label label = new Label("Bonjour");
//         label.setFont(Font.font(20));
////        Pane pane1=new Pane();
////        pane.setPrefHeight(100);
////        pane.setMinHeight(100);
////        pane1.setStyle("-fx-background-color: green;" );
////        Pane pane2=new Pane();
////        pane.setPrefHeight(100);
////        pane.setMinHeight(100);
////        pane2.setStyle("-fx-background-color: blue;" );
//         vbox.getChildren().add(pane);
//        vbox.getChildren().add(label);
//        //vbox.getChildren().add(pane2);
        Label labelrec = new Label("Reclamation "+i);
        Label label = new Label("ALors comment ça ca");

        VBox vbox1 = new VBox();
        vbox1.getChildren().add(labelrec);
        vbox1.getChildren().add(label);

        Pane pane = new Pane();
        pane.getChildren().add(vbox1);
        pane.setUserData(i);
        pane.setPrefHeight(100);
pane.setMinHeight(100);
        i++;
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
                        System.out.println(pane.getUserData()+"  "+i);


//                        try {
//                            rec=sp.selectOne_by_id((int) pane.getUserData());
//                            System.out.println(rec.getDescription());
//                        } catch (SQLException ex) {
//                            throw new RuntimeException(ex);
//                        }
//                        System.out.println((int)pane.getUserData());
//
//                        ajouterReclamationFXML.fullFields(rec);
                    }
                };
        pane.setOnMouseClicked(event2);
    }
}