<<<<<<< HEAD
<<<<<<< HEAD
package com.example.reclamation.controllers;
import com.example.reclamation.chatbot.BotResponse;
import com.example.reclamation.models.*;
import com.example.reclamation.services.*;
import com.example.reclamation.test.FxMain;
=======
=======
>>>>>>> GestionReclamations
package edu.esprit.controllers;
import edu.esprit.chatbot.BotResponse;
import edu.esprit.entities.*;
import edu.esprit.services.*;
import edu.esprit.tests.FxMain;
<<<<<<< HEAD
>>>>>>> ba038a7 (metiers+api)
=======
>>>>>>> GestionReclamations
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;


public class AjouterReclamationController implements Initializable {

    @FXML
    private DatePicker datepicker;

    @FXML
    private ComboBox<String> ctype;
    private String[] types={"reservation","paiement"};
    @FXML
    private ComboBox choicecombo;
    @FXML
    private HBox hboxchoice;
    @FXML
<<<<<<< HEAD
<<<<<<< HEAD
    private Text choicelabel;
=======
    private Label choicelabel;
>>>>>>> ba038a7 (metiers+api)
=======
    private Label choicelabel;
>>>>>>> GestionReclamations

    @FXML
    private TextArea tfDescription;
    @FXML
    private ImageView notif;
    @FXML
    private ImageView Chatbtn;
<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> GestionReclamations
    @FXML
    private VBox vboxDate;
    @FXML
    private VBox vboxType;
    @FXML
    private VBox vboxChoice;
    @FXML
    private VBox vboxDescription;

<<<<<<< HEAD
>>>>>>> ba038a7 (metiers+api)
=======
>>>>>>> GestionReclamations
    TextArea chatArea;
    VBox vbox;

    List<Reservation> listr;
    List<Paiement> listp;
    @FXML
    private AfficherReclamationController afficherReclamationFXMLController;
    ServiceNotification sn = new ServiceNotification();
    ServiceReclamation sr = new ServiceReclamation();
    ServiceReservation sres = new ServiceReservation();
    ServicePaiement sp = new ServicePaiement();

    @FXML
    void ajouterReclamation(ActionEvent event) {
<<<<<<< HEAD
<<<<<<< HEAD
//        user = (User)notif.getScene().getUserData();
//
//        System.out.println("id="+user.getId()+"  role="+user.getRoles());
        User user = FxMain.getGlobalUserData();
        System.out.println("userdata="+user.getId()+"  "+user.getRoles());
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        try {

            if(datepicker.getValue() == null)
                throw new RuntimeException("date must not be null");
            Reclamation recl = new Reclamation(ctype.getValue(),Date.valueOf(datepicker.getValue()), tfDescription.getText(), "en attente",currentTimestamp,user);

            if(recl.getDate().after(currentTimestamp))
                throw new RuntimeException("date must be before today");
            if(recl.getType() == null)
                throw new RuntimeException("type must not be empty");
=======
=======
>>>>>>> GestionReclamations

        User user = FxMain.getGlobalUserData();
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        Reclamation recl = null;
        try {
            recl = new Reclamation(ctype.getValue(), tfDescription.getText(), "en attente",currentTimestamp,user);
<<<<<<< HEAD
>>>>>>> ba038a7 (metiers+api)
=======
>>>>>>> GestionReclamations
            int index = choicecombo.getSelectionModel().getSelectedIndex();
            if(index != -1 && listr!=null)
                recl.setId_reservation(listr.get(choicecombo.getSelectionModel().getSelectedIndex()));
            else if (index != -1 && listp!=null) {
                recl.setId_paiement(listp.get(choicecombo.getSelectionModel().getSelectedIndex()));
            }
<<<<<<< HEAD
<<<<<<< HEAD
            if(recl.getId_reservation() == null && recl.getId_paiement() == null)
                throw new RuntimeException("select a type");


            if(recl.getDescription().equals(""))
                throw new RuntimeException("description must not be empty");
            if(recl.getDescription().length() > 2000)
                throw new RuntimeException("description must be less than 2000 characters");

=======
=======
>>>>>>> GestionReclamations
            if(datepicker.getValue() == null)
                throw new RuntimeException();
            recl.setDate(Date.valueOf(datepicker.getValue()));


            if(recl.getDate().after(currentTimestamp) || recl.getType() == null || recl.getDescription().equals("") || recl.getDescription().length() > 1000 || recl.getDescription().length() < 10 || (recl.getId_reservation() == null && recl.getId_paiement() == null))
                throw new RuntimeException();
<<<<<<< HEAD
>>>>>>> ba038a7 (metiers+api)
=======
>>>>>>> GestionReclamations
            if(afficherReclamationFXMLController.getList().indexOf(recl) != -1)
                throw new RuntimeException("Reclamation exists");

            sr.insertOne(recl);
            showPopup("Reclamation added");
            clearFields(event);
            afficherReclamationFXMLController.afficherReclamation(event);
        }catch(SQLException e){
            System.err.println("Erreur: "+e.getMessage());
        }catch(Exception ex){
<<<<<<< HEAD
<<<<<<< HEAD
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText(ex.getMessage());
            alert.show();
=======
=======
>>>>>>> GestionReclamations
            Label errorDateLabel = new Label("");
            Label errorTypeLabel = new Label("");
            Label errorChoiceLabel = new Label("");
            Label errorDescriptionLabel = new Label("");
            errorDateLabel.setTextFill(Color.RED);
            errorTypeLabel.setTextFill(Color.RED);
            errorChoiceLabel.setTextFill(Color.RED);
            errorDescriptionLabel.setTextFill(Color.RED);
            errorDateLabel.setPadding(new Insets(0,0,0,93));
            errorTypeLabel.setPadding(new Insets(0,0,0,93));
            errorChoiceLabel.setPadding(new Insets(0,0,0,93));
            errorDescriptionLabel.setPadding(new Insets(0,0,0,93));
            clearErrors();

            if(datepicker.getValue() == null){
                errorDateLabel.setText("Date must not be null");
                vboxDate.getChildren().add(errorDateLabel);
            }
            else if(recl.getDate().after(currentTimestamp)){
                errorDateLabel.setText("Date must be before today");
                vboxDate.getChildren().add(errorDateLabel);
            }
            if(recl.getType() == null){
                errorTypeLabel.setText("Type must not be empty");
                vboxType.getChildren().add(errorTypeLabel);
            }
            else {
                if (recl.getId_reservation() == null && recl.getId_paiement() == null) {
                    errorChoiceLabel.setText("Choose a type");
                    vboxChoice.getChildren().add(errorChoiceLabel);
                }
            }
            if(recl.getDescription().isEmpty()){
                errorDescriptionLabel.setText("Description must not be empty");
                vboxDescription.getChildren().add(errorDescriptionLabel);
            }
            else if(recl.getDescription().length() > 1000 || recl.getDescription().length() < 10){
                errorDescriptionLabel.setText("Description must be between 10 and 1000 characters");
                vboxDescription.getChildren().add(errorDescriptionLabel);
            }
            if(ex.getMessage()!=null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setContentText(ex.getMessage());
                alert.show();
            }
<<<<<<< HEAD
>>>>>>> ba038a7 (metiers+api)
=======
>>>>>>> GestionReclamations
        }

    }


<<<<<<< HEAD
<<<<<<< HEAD
    public void showPopup(String text){
        Popup popup = new Popup();
        Image image = new Image("C:\\Users\\gasso\\IdeaProjects\\reclamation\\src\\main\\resources\\com\\example\\reclamation\\images\\icons8-ok-48.png");
=======
=======
>>>>>>> GestionReclamations

    public void showPopup(String text){
        Popup popup = new Popup();
        Image image = new Image("C:\\Users\\gasso\\IdeaProjects\\reclamation\\src\\main\\resources\\images\\icons8-ok-48.png");
<<<<<<< HEAD
>>>>>>> ba038a7 (metiers+api)
=======
>>>>>>> GestionReclamations
        ImageView popupimg = new ImageView();
        popupimg.setImage(image);
        Label label2 = new Label(text);
        label2.setPadding(new Insets(15,0,0,10));
        label2.setTextFill(Color.GREEN);
        label2.setFont(new Font("Helvetica", 14));
        label2.setPadding(new Insets(15,0,0,0));
        HBox hbox = new HBox(popupimg,label2);
        hbox.setPadding(new Insets(10,20,10,10));
<<<<<<< HEAD
<<<<<<< HEAD
        hbox.setStyle("-fx-background-color: white");
        popup.getContent().addAll(hbox);
        popup.setAutoHide(true);

=======
=======
>>>>>>> GestionReclamations
        hbox.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(47,242,104), white);");
        popup.getContent().addAll(hbox);
        popup.setAutoHide(true);
        Scene scene = choicelabel.getScene();
        Stage stage = (Stage) scene.getWindow();
        popup.setX(stage.getX()+740);
        popup.setY(stage.getY()+115);
<<<<<<< HEAD
>>>>>>> ba038a7 (metiers+api)
=======
>>>>>>> GestionReclamations
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(e -> popup.hide());
        popup.show(notif.getScene().getWindow());
        delay.play();
    }

    public void clearFields(ActionEvent event){
        ctype.setValue(null);
        tfDescription.clear();
        datepicker.setValue(null);
<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> GestionReclamations
        clearErrors();
    }
    private void clearErrors(){
        if(vboxDate.getChildren().size() > 1)
            vboxDate.getChildren().remove(1);
        if(vboxType.getChildren().size() > 1)
            vboxType.getChildren().remove(1);
        if(vboxChoice.getChildren().size() > 1)
            vboxChoice.getChildren().remove(1);
        if(vboxDescription.getChildren().size() > 1)
            vboxDescription.getChildren().remove(1);
<<<<<<< HEAD
>>>>>>> ba038a7 (metiers+api)
=======
>>>>>>> GestionReclamations
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        afficherReclamationFXMLController.setControllerA(this);
        ctype.getItems().addAll(types);
        ctype.setOnAction(this::onChoiceType);
//        this.tfDescription.textProperty().addListener((observable, oldValue, newValue) -> {
//            System.out.println("Text changed: " + newValue);
//            // You can do whatever you want with the new value here
//        });
//        ctype.valueProperty().addListener((observable, oldValue, newValue) -> {
//            System.out.println("Selected option: " + newValue);
//        });
//
//        datepicker.valueProperty().addListener((observable, oldValue, newValue) -> {
//            System.out.println("Selected date: " + newValue);
//        });
        EventHandler<MouseEvent> event2 =
                new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e)
                    {
                        showNotif(e);
                    }
                };
        notif.setOnMouseClicked(event2);


    }

    @FXML
    public void fullFields(Reclamation rec){
        this.ctype.setValue(rec.getType());
        this.tfDescription.setText(rec.getDescription());
        this.datepicker.setValue(rec.getDate().toLocalDate());
        if(rec.getType().equals("reservation")) {
            try {
                choicecombo.setValue(rec.getId_reservation());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        else if (rec.getType().equals("paiement")) {
            try {
                choicecombo.setValue(rec.getId_paiement());

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    public void modifierReclamation(ActionEvent event){

<<<<<<< HEAD
<<<<<<< HEAD


//        int index = choicecombo.getSelectionModel().getSelectedIndex();
//        if(index != -1 && listr!=null)
//            recl.setId_reservation(listr.get(choicecombo.getSelectionModel().getSelectedIndex()));
//        else if (index != -1 && listp!=null) {
//            recl.setId_paiement(listp.get(choicecombo.getSelectionModel().getSelectedIndex()));
//        }
//        if(recl.getId_reservation() == null && recl.getId_paiement() == null)
//            throw new RuntimeException("select a type");



        try {
            Reclamation recl = afficherReclamationFXMLController.getRec();
            if(recl == null)
                throw new RuntimeException("No Reclamation selected");
            if(datepicker.getValue() == null)
                throw new RuntimeException("date must not be null");

            Reclamation recl2 = new Reclamation(recl.getId(),ctype.getValue(),Date.valueOf(datepicker.getValue()), tfDescription.getText(), recl.getStatut(),recl.getDate_envoi(),recl.getId_client());

            if(recl2.getDate().after(new Timestamp(System.currentTimeMillis())))
                throw new RuntimeException("date must be before today");
            if(recl2.getType() == null)
                throw new RuntimeException("type must not be empty");
            if(recl2.getDescription().equals(""))
                throw new RuntimeException("description must not be empty");
            if(recl2.getDescription().length() > 2000)
                throw new RuntimeException("description must be less than 2000 characters");
            Reservation res = new Reservation();
            Paiement pm = new Paiement();
            int index = choicecombo.getSelectionModel().getSelectedIndex();
            if(index == -1)
                throw new RuntimeException("select a type");
            if (listr != null) {
                res = listr.get(index);
                recl2.setId_reservation(res);
            }
            if (listp != null) {
                pm = listp.get(index);
                recl2.setId_paiement(pm);
            }

            if(recl2.equals(recl))
                throw new RuntimeException("No modifications made");


//            if(!recl.getType().equals(ctype.getValue()) || recl.getId_reservation().getId()!=res.getId() || recl.getId_paiement().getId()!=pm.getId()) {
//                if (ctype.getValue().equals("reservation")) {
//                    recl2.setId_reservation(res);
//                } else if (ctype.getValue().equals("paiement")) {
//                    recl2.setId_paiement(pm);
//                }
//            }

=======
=======
>>>>>>> GestionReclamations
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        Reclamation recl2 = null;
        try {
            Reclamation rec = afficherReclamationFXMLController.getRec();
            if(rec == null)
                throw new RuntimeException("No Reclamation selected");

            recl2 = new Reclamation(rec.getId(),ctype.getValue(),rec.getDate(), tfDescription.getText(), rec.getStatut(),rec.getDate_envoi(),rec.getId_client());
            Reservation res = null;
            Paiement pm = null;
            int index = choicecombo.getSelectionModel().getSelectedIndex();
            if(index != -1) {
                if (listr != null) {
                    res = listr.get(index);
                    recl2.setId_reservation(res);
                }
                if (listp != null) {
                    pm = listp.get(index);
                    recl2.setId_paiement(pm);
                }
            }
            if(datepicker.getValue() == null)
                throw new RuntimeException();
            recl2.setDate(Date.valueOf(datepicker.getValue()));


            if(recl2.getDate().after(currentTimestamp) || recl2.getType() == null || recl2.getDescription().equals("") || recl2.getDescription().length() > 1000 || recl2.getDescription().length() < 10 || (recl2.getId_reservation() == null && recl2.getId_paiement() == null) || index == -1)
                throw new RuntimeException();

            if(recl2.equals(rec))
                throw new RuntimeException("No modifications made");

<<<<<<< HEAD
>>>>>>> ba038a7 (metiers+api)
=======
>>>>>>> GestionReclamations
            sr.updateOne(recl2);
            clearFields(event);
            showPopup("Reclamation modified");
            afficherReclamationFXMLController.clearVbox();
            afficherReclamationFXMLController.afficherReclamation(event);
        }catch(SQLException e){
            System.err.println("Erreur: "+e.getMessage());
        }
        catch(Exception ex){
<<<<<<< HEAD
<<<<<<< HEAD
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText(ex.getMessage());
            alert.show();
=======
=======
>>>>>>> GestionReclamations
            Label errorDateLabel = new Label("");
            Label errorTypeLabel = new Label("");
            Label errorChoiceLabel = new Label("");
            Label errorDescriptionLabel = new Label("");
            errorDateLabel.setTextFill(Color.RED);
            errorTypeLabel.setTextFill(Color.RED);
            errorChoiceLabel.setTextFill(Color.RED);
            errorDescriptionLabel.setTextFill(Color.RED);
            errorDateLabel.setPadding(new Insets(0,0,0,93));
            errorTypeLabel.setPadding(new Insets(0,0,0,93));
            errorChoiceLabel.setPadding(new Insets(0,0,0,93));
            errorDescriptionLabel.setPadding(new Insets(0,0,0,93));

            clearErrors();

            if(datepicker.getValue() == null){
                errorDateLabel.setText("Date must not be null");
                vboxDate.getChildren().add(errorDateLabel);
            }
            else if(recl2.getDate().after(currentTimestamp)){
                errorDateLabel.setText("Date must be before today");
                vboxDate.getChildren().add(errorDateLabel);
            }
            if(recl2.getType() == null){
                errorTypeLabel.setText("Type must not be empty");
                vboxType.getChildren().add(errorTypeLabel);
            }
            else {
                if (choicecombo.getSelectionModel().getSelectedIndex() == -1) {
                    errorChoiceLabel.setText("Choose a type");
                    vboxChoice.getChildren().add(errorChoiceLabel);
                }
            }
            System.out.println(choicecombo.getSelectionModel().getSelectedIndex());
            if(recl2.getDescription().isEmpty()){
                errorDescriptionLabel.setText("Description must not be empty");
                vboxDescription.getChildren().add(errorDescriptionLabel);
            }
            else {
                if(recl2.getDescription().length() > 1000 || recl2.getDescription().length() < 10){
                    errorDescriptionLabel.setText("Description must be between 10 and 1000 characters");
                    vboxDescription.getChildren().add(errorDescriptionLabel);
                }
            }

            if(ex.getMessage()!=null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setContentText(ex.getMessage());
                alert.show();
            }
<<<<<<< HEAD
>>>>>>> ba038a7 (metiers+api)
=======
>>>>>>> GestionReclamations
        }

    }

public void onChoiceType(ActionEvent event) {
    listr=null;
    listp=null;
    hboxchoice.setVisible(true);
<<<<<<< HEAD
<<<<<<< HEAD
=======
    vboxChoice.setPadding(new Insets(0,0,20,0));
>>>>>>> ba038a7 (metiers+api)
=======
    vboxChoice.setPadding(new Insets(0,0,20,0));
>>>>>>> GestionReclamations
    hboxchoice.setMinHeight(Region.USE_COMPUTED_SIZE);
    choicecombo.setValue(null);
    if(ctype.getValue()=="reservation"){
        choicelabel.setText("Reservation");
        try {
            listr = new ArrayList<>();
            listr.addAll(sres.selectAll());
            ObservableList<Reservation> observableListr = FXCollections.observableList(listr);
            choicecombo.setItems(observableListr);


        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    else if(ctype.getValue()=="paiement"){

        choicelabel.setText("Paiement");
        try {
            listp = new ArrayList<>();
            listp.addAll(sp.selectAll());
            ObservableList<Paiement> observableListp = FXCollections.observableList(listp);
            choicecombo.setItems(observableListp);
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    else {
        hboxchoice.setVisible(false);
        hboxchoice.setMinHeight(0);
    }

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
        popup.setX(stage.getX()+notif.getLayoutX()-180);
        popup.setY(stage.getY()+notif.getLayoutY()+60);

        if (!popup.isShowing())
            popup.show(stage);



    }


    @FXML
    public void startChat(MouseEvent event){

        Label label = new Label("ChatBot");
        HBox hbox = new HBox(label);
        Label bubble = new Label("  Hello I am a chatbot. Ask me anything by typing below. Type 'Quit' to end the program");
        bubble.setWrapText(true);
        bubble.setWrapText(true);
        vbox = new VBox(bubble);
        vbox.setMaxWidth(200);
        chatArea = new TextArea();
        chatArea.setPrefHeight(50);
        chatArea.setPromptText("Send a message");
        chatArea.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
        chatArea.setWrapText(true);
        ScrollPane scroll = new ScrollPane(vbox);
        scroll.setMaxWidth(250);
        scroll.setPrefHeight(450);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        VBox graphic = new VBox();
        graphic.getChildren().addAll(hbox,scroll,chatArea);
        graphic.setStyle("-fx-background-color: white");
        graphic.setPrefHeight(500);
        graphic.setPrefWidth(250);
        Popup popup = new Popup();
        popup.getContent().add(graphic);
        chatArea.setOnKeyPressed(event2 -> {
            if (event2.getCode() == KeyCode.ENTER) {
                // Call the conversation function when Enter key is pressed
                try {
                    conversation(new ActionEvent());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });


        Stage stage = (Stage) notif.getScene().getWindow();
        popup.setAutoHide(true);
        popup.setX(stage.getX()+Chatbtn.getLayoutX()-250);
        popup.setY(stage.getY()+Chatbtn.getLayoutY()-100);
        popup.show(stage);
//        Stage stage = new Stage();
//        try{
<<<<<<< HEAD
<<<<<<< HEAD
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/reclamation/testChatFXML.fxml"));
=======
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/testChatFXML.fxml"));
>>>>>>> ba038a7 (metiers+api)
=======
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/testChatFXML.fxml"));
>>>>>>> GestionReclamations
//            Parent root = loader.load();
//            // Show the scene
//            Scene scene = new Scene(root);
//            stage.setScene(scene);
//
//            stage.show();
//        }
//        catch (IOException e){
//            System.out.println(e.getMessage());
//        }
    }


    @FXML
    public void conversation(ActionEvent event) throws Exception {
        String gtext = chatArea.getText();
        HBox hBox = new HBox();

        hBox.setMaxWidth(150);
        hBox.setMinWidth(150);

        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setTranslateX(130);

        Text text = new Text(gtext);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setPadding(new Insets(5, 10, 5, 10));
        HBox.setMargin(textFlow,new Insets(10,0,10,0));

        textFlow.setStyle("-fx-background-color: rgb(15,125,242);-fx-background-radius: 20px;");

        hBox.getChildren().add(textFlow);
        vbox.getChildren().add(hBox);
        chatArea.setText("");
        String category = BotResponse.findCategory(gtext);
        String response = respond(category);

        bot(response);
        if (gtext.equals("QUIT\n")) {
            // Node n = (Node) event.getSource();
            Stage stage = (Stage) chatArea.getScene().getWindow();
            stage.close();
        }


    }

    private void bot(String string) {

        HBox hBox = new HBox();

        hBox.setMaxWidth(150);
        hBox.setMinWidth(150);

        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setTranslateX(0);

        Text text = new Text(string);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setPadding(new Insets(5, 10, 5, 10));
        HBox.setMargin(textFlow,new Insets(10,0,10,0));
        textFlow.setStyle("-fx-background-color: rgb(195,195,195);-fx-background-radius: 20px;");
        hBox.getChildren().add(textFlow);
        vbox.getChildren().add(hBox);
    }

    private static String respond(String category) {
        switch (category) {
            case "Greeting":
                String[] greetings = {
                        "Hello! How may I assist you today?",
                        "Hi there! What can I help you with?",
                        "Good morning! Welcome to our tourism agency. How can I help you?"
                };
                return getRandomResponse(greetings);
            case "FlightInformation":
                String[] flightResponses = {
                        "We offer a range of flights from New York to Paris. Would you like me to check availability for specific dates?",
                        "Certainly! I can help you find the best flight options from New York to Paris.",
                        "Let me assist you in finding the most suitable flight from New York to Paris."
                };
                return getRandomResponse(flightResponses);
            case "HotelReservation":
                String[] hotelResponses = {
                        "I can assist you with booking a hotel room in Barcelona. Please provide me with your travel dates.",
                        "Certainly! Let me help you find the perfect hotel room in Barcelona.",
                        "I'm here to assist you in booking a hotel room in Barcelona. When are you planning to stay?"
                };
                return getRandomResponse(hotelResponses);
            case "TourPackage":
                String[] tourResponses = {
                        "We have various guided tours available in Rome. Which specific attractions are you interested in?",
                        "Explore Rome with our guided tours! What type of tour are you looking for?",
                        "Let me provide you with information about our guided tours in Rome. What are your preferences?"
                };
                return getRandomResponse(tourResponses);
            case "CarRental":
                String[] carResponses = {
                        "Yes, we provide car rental services in London. What type of vehicle are you looking for?",
                        "Certainly! Let me assist you in renting a car for your trip to London.",
                        "I'm here to help you with car rental services in London. What are your requirements?"
                };
                return getRandomResponse(carResponses);
            case "VisaInformation":
                String[] visaResponses = {
                        "Visa requirements vary depending on your nationality and destination. I can help you with specific information.",
                        "Certainly! Let me provide you with information about visa requirements for your destination.",
                        "I'm here to assist you with visa information. Which country are you planning to visit?"
                };
                return getRandomResponse(visaResponses);
            case "LocalAttractions":
                String[] attractionsResponses = {
                        "Tokyo offers a variety of must-see attractions, from historic temples to modern skyscrapers.",
                        "Explore the vibrant city of Tokyo with our list of top attractions.",
                        "Let me recommend some must-see attractions in Tokyo for your itinerary."
                };
                return getRandomResponse(attractionsResponses);
            case "TravelInsurance":
                String[] insuranceResponses = {
                        "Our travel insurance covers medical emergencies, trip cancellations, and more. Would you like to receive a quote?",
                        "Certainly! Let me provide you with information about our travel insurance options.",
                        "I'm here to assist you with travel insurance. What type of coverage are you interested in?"
                };
                return getRandomResponse(insuranceResponses);
            case "CruiseInformation":
                String[] cruiseResponses = {
                        "We offer a variety of cruises to different destinations. What specific information are you looking for?",
                        "Explore the world with our cruise options! Where would you like to sail?",
                        "Let me provide you with information about our cruise packages. What are your preferences?"
                };
                return getRandomResponse(cruiseResponses);
            case "LanguageTranslation":
                String[] translationResponses = {
                        "Yes, we provide translation services for travel documents. Let me know how I can assist you.",
                        "Certainly! Let me help you with translation services for your travel documents.",
                        "I'm here to assist you with language translation. What documents do you need translated?"
                };
                return getRandomResponse(translationResponses);
            case "CurrencyExchange":
                String[] currencyResponses = {
                        "You can exchange currency at our office or at local banks in Tokyo. We offer competitive exchange rates.",
                        "Certainly! Let me provide you with information about currency exchange services.",
                        "I'm here to assist you with currency exchange. What currency are you looking to exchange?"
                };
                return getRandomResponse(currencyResponses);
            case "EmergencyAssistance":
                String[] emergencyResponses = {
                        "In case of emergencies during your trip, you can reach us at our 24/7 emergency contact number.",
                        "Don't worry! We're here to assist you in case of emergencies. What do you need help with?",
                        "I'm here to help you with any emergencies that may arise during your trip. What assistance do you require?"
                };
                return getRandomResponse(emergencyResponses);
            case "PriceInquiry":
                String[] priceResponses = {
                        "The cost of a round-trip flight from London to Paris depends on various factors such as travel dates and airline choice.",
                        "Certainly! Let me check the price for you. Could you please provide me with more details?",
                        "I can assist you with price inquiries. What specific information are you looking for?"
                };
                return getRandomResponse(priceResponses);
            case "ServiceQualityClaim":
                String[] qualityResponses = {
                        "We apologize for any inconvenience you experienced. Please provide more details so we can address your concerns.",
                        "I'm sorry to hear that. Please tell me more about the issue so we can rectify it.",
                        "Your feedback is important to us. Could you please provide more information about your experience?"
                };
                return getRandomResponse(qualityResponses);
            case "ServiceDelayClaim":
                String[] delayResponses = {
                        "We're sorry to hear about the delay. Let us know how we can assist you further.",
                        "Please accept our apologies for the inconvenience. How can we make it right?",
                        "I understand the frustration caused by the delay. Let's work together to find a solution."
                };
                return getRandomResponse(delayResponses);
            case "PromotionInquiry":
                String[] promotionResponses = {
                        "Yes, we currently have promotions available for hotel bookings. Would you like more information?",
                        "Certainly! Let me provide you with information about our current promotions and discounts.",
                        "I'm here to assist you with promotional offers. What type of promotion are you interested in?"
                };
                return getRandomResponse(promotionResponses);

            case "AIInquiry":
                String[] aiResponses = {
                        "Our AI technology assists in providing personalized travel recommendations based on your preferences.",
                        "We utilize advanced AI algorithms to optimize your travel experience. How can I help you further?",
                        "Our AI system analyzes your travel preferences to offer tailored recommendations. What else would you like to know?"
                };
                return getRandomResponse(aiResponses);
            case "BotInquiry":
                String[] botResponses = {
                        "I am a virtual assistant designed to assist you with your travel inquiries. How can I assist you today?",
                        "As a bot, I'm here to help you with any questions you may have about our services. What can I do for you?",
                        "I'm a chatbot programmed to provide assistance with travel-related queries. What do you need help with?"
                };
                return getRandomResponse(botResponses);
            case "ConversationContinue":
                String[] continueResponses = {
                        "Ok, that's great. What else can I assist you with?",
                        "Excellent! Is there anything else you'd like to know?",
                        "Wonderful! Do you have any other questions?"
                };
                return getRandomResponse(continueResponses);
            case "ConversationComplete":
                String[] completeResponses = {
                        "Thank you for contacting us. Feel free to reach out if you need further assistance.",
                        "It was a pleasure assisting you. Have a great day!",
                        "If you have any more questions in the future, don't hesitate to contact us."

                };
                return getRandomResponse(completeResponses);

        }
        return "Sorry, I'm not sure how to respond to that.";

    }

    private static String getRandomResponse(String[] responses) {
        return (responses) [(int) (Math.random()* responses.length)];
    }


    @FXML
    public void startCamera(ActionEvent event){


        Stage stage = new Stage();
        try{
<<<<<<< HEAD
<<<<<<< HEAD
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/reclamation/cameraFXML.fxml"));
=======
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cameraFXML.fxml"));
>>>>>>> ba038a7 (metiers+api)
=======
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cameraFXML.fxml"));
>>>>>>> GestionReclamations
            Parent root = loader.load();
            // Show the scene
            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.show();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void navigateToHome(ActionEvent event){
        Scene scene =null;
        try{
            //Stage stage = new Stage();
<<<<<<< HEAD
<<<<<<< HEAD
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/reclamation/home.fxml"));
=======
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/front.fxml"));
>>>>>>> ba038a7 (metiers+api)
=======
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/front.fxml"));
>>>>>>> GestionReclamations
            Parent root = loader.load();
            // Show the scene
            scene = notif.getScene();
            scene.setRoot(root);
            Stage stage = (Stage) scene.getWindow();
            stage.setTitle("byeee");

        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }


}
