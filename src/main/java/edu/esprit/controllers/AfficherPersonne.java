package edu.esprit.controllers;

import edu.esprit.services.ServicePersonne;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class AfficherPersonne implements Initializable {
    @FXML
    private Label LLpersonnes;
    private final ServicePersonne sp = new ServicePersonne();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LLpersonnes.setText(sp.getAll().toString());
    }
}

