package edu.esprit.controllers;

import edu.esprit.entities.Reclamation;
import edu.esprit.entities.Reponse;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceReclamation;
import edu.esprit.services.ServiceReponse;
import edu.esprit.tests.FxMain;
import com.itextpdf.kernel.pdf.colorspace.PdfDeviceCs;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
<<<<<<< HEAD
=======
import javafx.geometry.Pos;
>>>>>>> ba038a7 (metiers+api)
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
<<<<<<< HEAD
=======
import java.time.format.DateTimeFormatter;
>>>>>>> ba038a7 (metiers+api)
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
<<<<<<< HEAD
    @FXML
    private Label latype;

    @FXML
    private Label ladescription;
    @FXML
=======

    @FXML
>>>>>>> ba038a7 (metiers+api)
    private TextField tfetat;
    @FXML
    private TextArea tacontenu;
    private Button updaterep;
    private Button deleterep;
    @FXML
    private VBox vboxContenu;
    @FXML
    private VBox vboxEtat;
    @FXML
    private VBox reponseFormVbox;
<<<<<<< HEAD
=======
    @FXML
    private Label choicelabel;

    @FXML
    private Label datelabel;

    @FXML
    private Label descriptionlabel;

    @FXML
    private Label typelabel;
    private int i = 1;
>>>>>>> ba038a7 (metiers+api)
    UpdateReponseController updateReponseFXML;
    ServiceReponse sr = new ServiceReponse();
    ServiceReclamation srec = new ServiceReclamation();
    private Reclamation reclam;
    private List<Reponse> list;
<<<<<<< HEAD
    private int i = 0;
=======
    @FXML
    private Label rpslbl;
>>>>>>> ba038a7 (metiers+api)


    @FXML
    public void initialize(){
        if (FxMain.getGlobalUserData().getRoles().equals("[\"ROLE_ADMIN\"]")) {
            reponseFormVbox.setVisible(true);
        } else if (FxMain.getGlobalUserData().getRoles().equals("[\"ROLE_USER\"]")) {
            reponseFormVbox.setVisible(false);
            reponseFormVbox.setMaxHeight(0);

        }
    }

    public void afficherReponse(Reclamation Rec){
        this.reclam = Rec;
<<<<<<< HEAD
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
=======
        typelabel.setText(Rec.getType());
        descriptionlabel.setText(Rec.getDescription());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        datelabel.setText(Rec.getDate_envoi().toLocalDateTime().format(formatter));
        if(Rec.getType().equals("reservation"))
            choicelabel.setText("Date: "+Rec.getId_reservation().getDate_from().toLocalDateTime().format(formatter)+"\nNombre de personnes: "+Rec.getId_reservation().getNumber_of_persons());
        else if (Rec.getType().equals("paiement")) {
            choicelabel.setText("Mode: "+Rec.getId_paiement().getMode()+"\nNumero cart: "+Rec.getId_paiement().getNum_carte());
        }
        if(Rec.getStatut().equals("résolu")) {
            rpslbl.setVisible(true);
            try {
>>>>>>> ba038a7 (metiers+api)
                list = new ArrayList<>();
                list.addAll(sr.selectAll_by_idReclamation(Rec.getId()));
                    for (Reponse rep : list) {
                        Label labelrep = new Label("Reponse " + i);
<<<<<<< HEAD
                        Label label1 = new Label(rep.getContenu());
                        VBox vbox2 = new VBox();
                        vbox2.getChildren().addAll(labelrep, label1);
=======
                        labelrep.setStyle("-fx-background-color: rgba(0, 0, 250, 0.1);\n" +
                                "    -fx-background-radius: 20px;\n" +
                                "    -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 4);\n" +
                                "    -fx-padding: 20px;\n" +
                                "    -fx-spacing: 10px;\n" +
                                "    -fx-max-width: 400px;");
                        Label datelabel = new Label(rep.getDate().toLocalDateTime().format(formatter));
                        datelabel.setStyle("-fx-background-color: rgba(0, 0, 250, 0.1);\n" +
                                "    -fx-background-radius: 20px;\n" +
                                "    -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 4);\n" +
                                "    -fx-max-width: 400px;");
                        HBox rephb = new HBox(labelrep,datelabel);
                        HBox.setMargin(labelrep,new Insets(0,150,0,0));
                        rephb.setAlignment(Pos.CENTER_LEFT);
                        rephb.setPadding(new Insets(0,0,10,0));
                        Label labelcontenu = new Label(rep.getContenu());
                        labelcontenu.setStyle("-fx-background-color: rgba(0, 0, 250, 0.1);\n" +
                                "    -fx-background-radius: 20px;\n" +
                                "    -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 4);");
                        VBox vbox2 = new VBox(rephb,labelcontenu);
                        vbox2.setPadding(new Insets(10,0,0,20));
                        VBox.setMargin(labelcontenu, new Insets(0,0,0,20));
>>>>>>> ba038a7 (metiers+api)

                        if (FxMain.getGlobalUserData().getRoles().equals("[\"ROLE_ADMIN\"]")) {
                            updaterep = new Button("update");
                            deleterep = new Button("delete");
                            updaterep.setOnAction(event -> navigateToUpdateReponse(rep));
                            deleterep.setOnAction(event -> deleteAction(rep));
<<<<<<< HEAD
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
=======

                            HBox hboxbtns = new HBox();
                            hboxbtns.getChildren().addAll(updaterep, deleterep);
                            hboxbtns.setPadding(new Insets(10,200,0,0));
                            vbox2.getChildren().add(hboxbtns);
                        }

                        Pane pane1 = new Pane(vbox2);
                        pane1.setStyle("-fx-background-color: rgba(0, 0, 255, 0.1);\n" +
                                "    -fx-background-radius: 20px;\n" +
                                "    -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 4);\n" +
                                "    -fx-padding: 20px;\n" +
                                "    -fx-spacing: 10px;\n" +
                                "    -fx-max-width: 400px;");
                        pane1.setMinHeight(150);
                        reponseVbox.getChildren().add(pane1);
                        i++;
                        VBox.setMargin(pane1, new Insets(0,0,20,0));

>>>>>>> ba038a7 (metiers+api)
                    }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
<<<<<<< HEAD
=======
        else{
            reponseVbox.setPrefHeight(0);
            rpslbl.setVisible(false);
        }
>>>>>>> ba038a7 (metiers+api)

        if(FxMain.getGlobalUserData().getRoles().equals("[\"ROLE_ADMIN\"]")) {

            tfetat.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
            tacontenu.setPromptText("message");
            tacontenu.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

        }


<<<<<<< HEAD
        //VBox.setMargin(pane, new Insets(0,0,20,0));
=======

>>>>>>> ba038a7 (metiers+api)
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
            if(rep.getEtat().equals("") || rep.getEtat().length()>20 || rep.getContenu().equals("") || rep.getContenu().length()<10 || rep.getContenu().length()>2000 )
                throw new RuntimeException();
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

            Label errorEtatLabel = new Label("");
            Label errorContenuLabel = new Label("");
            errorEtatLabel.setTextFill(Color.RED);
            errorContenuLabel.setTextFill(Color.RED);
            clearErrors();

            if(rep.getEtat().isEmpty()){
                errorEtatLabel.setText("etat must not be null");
                vboxEtat.getChildren().add(errorEtatLabel);
            }
            else {
                if (rep.getEtat().length() > 20) {
                    errorEtatLabel.setText("etat must be less than 20 characters");
                    vboxEtat.getChildren().add(errorEtatLabel);
                }
            }

            if(rep.getContenu().isEmpty()){
                errorContenuLabel.setText("contenu must not be null");
                vboxContenu.getChildren().add(errorContenuLabel);
            }
            else{
                if(rep.getContenu().length() < 10 || rep.getContenu().length() > 1000){
                    errorContenuLabel.setText("contenu must be between 10 and 1000 characters");
                    vboxContenu.getChildren().add(errorContenuLabel);
                }
            }


            if(ex.getMessage()!=null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setContentText(ex.getMessage());
                alert.show();
            }
        }
    }

    private void clearErrors(){
        if(vboxEtat.getChildren().size() > 1)
            vboxEtat.getChildren().remove(1);
        if(vboxContenu.getChildren().size() > 1)
            vboxContenu.getChildren().remove(1);

    }
    public void showPopup(String text){
        Popup popup = new Popup();
<<<<<<< HEAD
        Image image = new Image("C:\\Users\\gasso\\IdeaProjects\\reclamation\\src\\main\\resources\\edu\\esprit\\images\\icons8-ok-48.png");
=======
        Image image = new Image("C:\\Users\\gasso\\IdeaProjects\\reclamation\\src\\main\\resources\\images\\icons8-ok-48.png");
>>>>>>> ba038a7 (metiers+api)
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
<<<<<<< HEAD

=======
        Scene scene = choicelabel.getScene();
        Stage stage = (Stage) scene.getWindow();
        popup.setX(stage.getX()+740);
        popup.setY(stage.getY()+125);
>>>>>>> ba038a7 (metiers+api)
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(e -> popup.hide());
        popup.show(reponseVbox.getScene().getWindow());
        delay.play();
    }

    @FXML
    void reload(ActionEvent event) {
<<<<<<< HEAD
        i=0;
=======
        i=1;
>>>>>>> ba038a7 (metiers+api)
        reponseVbox.getChildren().clear();
        afficherReponse(reclam);
    }

    public void clearFields(){

    }
    public void navigateToUpdateReponse(Reponse rep){

        try {

<<<<<<< HEAD
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/esprit/UpdateReponseFXML.fxml"));
=======
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateReponseFXML.fxml"));
>>>>>>> ba038a7 (metiers+api)
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
<<<<<<< HEAD
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/esprit/Home1.fxml"));
=======
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home1.fxml"));
>>>>>>> ba038a7 (metiers+api)
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
<<<<<<< HEAD
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/esprit/AfficherReclamationBackFXML.fxml"));
=======
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReclamationBackFXML.fxml"));
>>>>>>> ba038a7 (metiers+api)
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
