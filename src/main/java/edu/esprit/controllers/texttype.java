package com.example.reclamation.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

import java.net.URL;
import java.util.ResourceBundle;

public class texttype implements Initializable {
    @FXML
    private ChoiceBox<String> ctype;
    private String[] types={"reservation","paiement"};
    @FXML
    private ComboBox<Integer> hidden;
    private Integer[] types2={12,18};

    @FXML
    private Label label;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ctype.getItems().addAll(types);
        hidden.getItems().addAll(types2);
        ctype.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Call your method here, passing the newValue if needed
                test();
            }
        });

    }
    @FXML
    void test() {


        if(ctype.getValue()=="reservation"){
            hidden.setVisible(true);
            hidden.setPrefHeight(Region.USE_COMPUTED_SIZE);
            hidden.setMinHeight(Region.USE_COMPUTED_SIZE);
            label.setMinHeight(Region.USE_COMPUTED_SIZE);
        }
        else{
            hidden.setVisible(false);
            hidden.setMinHeight(0);
            label.setMinHeight(0);
        }
    }
}
