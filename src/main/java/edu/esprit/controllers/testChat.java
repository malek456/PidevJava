package com.example.reclamation.controllers;

import com.example.reclamation.chatbot.BotResponse;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;

import java.net.URL;
import java.util.ResourceBundle;

public class testChat implements Initializable {
    @FXML
    private VBox graphic;
    @FXML
    private Label tainput;
    @FXML
    private TextArea chatBox;

    @FXML
    private ScrollPane scrollpane;
    private int index=0;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chatBox.setScrollLeft(0);
        chatBox.setWrapText(true);
        tainput= new Label();
        tainput.setText("hello i am you bor");
        tainput.setAlignment(Pos.CENTER_LEFT);
        graphic.getChildren().add(tainput);
       // bot("Hello I am a chatbot. Ask me anything by typing below. Type 'Quit' to end the program.\n");
        chatBox.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                // Call the conversation function when Enter key is pressed
                try {
                    conversation(new ActionEvent());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    @FXML
    public void conversation(ActionEvent event) throws Exception {
        String gtext = chatBox.getText();
        //
//        DoubleProperty fontSize = new SimpleDoubleProperty(14);
//        DoubleProperty paddingTop = new SimpleDoubleProperty();
//        DoubleProperty paddingBottom = new SimpleDoubleProperty();
//        paddingTop.bind(tainput.heightProperty().subtract(fontSize).divide(2));
//        paddingBottom.bind(tainput.heightProperty().subtract(fontSize).divide(2));
//        tainput.paddingProperty().bind(
//                Bindings.createObjectBinding(() -> new Insets(paddingTop.get(), 10, paddingBottom.get(), 10),
//                        tainput.heightProperty())
//        );

        // Calculate the padding dynamically based on text size
//        DoubleBinding padding = Bindings.createDoubleBinding(() ->
//                        (tainput.getFont().getSize() - tainput.getLayoutBounds().getHeight()) / 2,
//                tainput.fontProperty(), tainput.textProperty(),tainput.layoutBoundsProperty());
//
//        // Bind the padding to the label
//        tainput.paddingProperty().bind(
//                Bindings.createObjectBinding(() ->
//                        new javafx.geometry.Insets(padding.get()), padding)
//        );

 HBox hBox = new HBox();

hBox.setMaxWidth(150);
hBox.setMinWidth(150);

hBox.setAlignment(index % 2 == 0? Pos.CENTER_LEFT: Pos.CENTER_RIGHT);
hBox.setTranslateX(index % 2 == 0? 0 : 130);

        Text text = new Text(gtext);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setPadding(new Insets(5, 10, 5, 10));
        HBox.setMargin(textFlow,new Insets(10,0,10,0));

if(index%2==0)
        textFlow.setStyle("-fx-background-color: rgb(15,125,242);-fx-background-radius: 20px;");
else
    textFlow.setStyle("-fx-background-color: rgb(195,195,195);-fx-background-radius: 20px;");
    hBox.getChildren().add(textFlow);
        index++;
        System.out.println(index);
        graphic.getChildren().add(hBox);
        chatBox.setText("");
       // bot(response);
        if (gtext.equals("QUIT\n")) {
            // Node n = (Node) event.getSource();
            Stage stage = (Stage) chatBox.getScene().getWindow();
            stage.close();
        }


    }

    private void bot(String string) {
        TextField tfinput = new TextField("string");
        tfinput.setMaxWidth(280);

        graphic.getChildren().add(tfinput);
    }
}
