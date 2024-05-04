package edu.esprit.controllers;

import edu.esprit.entities.Paiement;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;


public class TicketPaiement {

    @FXML
    private Label labelNumVol;

    @FXML
    private Label labelEmail;

    @FXML
    private Label labelMode;

    @FXML
    private Label labelNumCarte;

    @FXML
    private Pane thisPane;

    @FXML
    private HBox PaiementContainer;

    private Paiement currentPaiement;

    private AfficherPaiement AfficherPaiement;


    public void setPaiement(Paiement paiement, Pane pane, HBox hbox) {

        labelEmail.setText(paiement.getUser().getEmail());
        labelMode.setText(paiement.getMode());
        labelNumCarte.setText(String.valueOf(paiement.getNum_carte()));
        labelNumVol.setText(String.valueOf(paiement.getVol().getCode()));


        thisPane = pane;
        PaiementContainer = hbox;
        currentPaiement = paiement;
    }

    public void setAfficherPaiementController(AfficherPaiement afficherPaiement) {
        this.AfficherPaiement = afficherPaiement;
    }
}
