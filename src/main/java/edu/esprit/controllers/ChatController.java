package edu.esprit.controllers;

import ai.rev.speechtotext.RevAiWebSocketListener;
import ai.rev.speechtotext.StreamingClient;
import ai.rev.speechtotext.models.asynchronous.Element;
import ai.rev.speechtotext.models.streaming.ConnectedMessage;
import ai.rev.speechtotext.models.streaming.Hypothesis;
import ai.rev.speechtotext.models.streaming.SessionConfig;
import ai.rev.speechtotext.models.streaming.StreamContentType;
import edu.esprit.tests.StreamingFromLocalFile;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import okhttp3.Response;
import okio.ByteString;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

public class ChatController {

    @FXML
    private VBox chatvbox;

    @FXML
    private TextArea tachat;
    @FXML
    private ScrollPane scroll;
    File audioFile;
    private MediaPlayer mediaPlayer;
    @FXML
    private Slider slider;
    @FXML
    private Label transcriptlabel;

    @FXML
    void sendMessageAction(ActionEvent event) {
        sendMessage();
    }


    void sendMessage() {
        String gtext = tachat.getText();
        Text text = new Text("user:+\n"+gtext);
        addCell(text);
        Text response = new Text("chat: \n"+"Hello how are you");//respond(gtext);
        chatResponse(response);
    }

    void addCell(Text text){
        TextFlow textFlow = new TextFlow(text);
        textFlow.setPadding(new Insets(5, 10, 5, 10));
        textFlow.setPrefWidth(600);
        textFlow.setMinHeight(40);
        HBox hBox = new HBox();
        hBox.setPrefWidth(600);


        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setMinHeight(40);
        hBox.setTranslateX(0);
        HBox.setMargin(textFlow,new Insets(5,0,5,0));

        textFlow.setStyle("-fx-background-color: rgba(243,243,243,0.2);-fx-background-radius: 10px;");
        textFlow.setOpacity(10);

        hBox.getChildren().add(textFlow);
        chatvbox.getChildren().add(hBox);
        scroll.setVvalue(1);
        tachat.clear();


    }

    private void chatResponse(Text text) {

        addCell(text);
    }

    @FXML
    void upload(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\Users\\gasso\\IdeaProjects\\reclamation"));
        fileChooser.setTitle("Select Audio File");
        audioFile = fileChooser.showOpenDialog(tachat.getScene().getWindow());

    }

    @FXML
    private void playAudio(ActionEvent event) {
        Media media = new Media(audioFile.toURI().toString());
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();



        String accessToken = "02yUiDLQKrKTS2NdZh2us0rcEN2d8q0HWt54o3ERfcNlNXcj7-6636jZc9SIEW3iinvZi3Fah5Flc4tDmrMPis_UuAiWE";

        // Configure the streaming content type
        StreamContentType streamContentType = new StreamContentType();
        streamContentType.setContentType("audio/x-raw"); // audio content type
        streamContentType.setLayout("interleaved"); // layout
        streamContentType.setFormat("S16LE"); // format
        streamContentType.setRate(16000); // sample rate
        streamContentType.setChannels(1); // channels

        // Setup the SessionConfig with any optional parameters
        SessionConfig sessionConfig = new SessionConfig();
        sessionConfig.setMetaData("Streaming from the Java SDK");
        sessionConfig.setFilterProfanity(true);
        sessionConfig.setRemoveDisfluencies(true);
        sessionConfig.setDeleteAfterSeconds(2592000); // 30 days in seconds
        sessionConfig.setDetailedPartials(false);
        sessionConfig.setStartTs(0.0);
        sessionConfig.setTranscriber("machine");
        sessionConfig.setLanguage("en");
        sessionConfig.setSkipPostprocessing(false);

        // Initialize your client with your access token
        StreamingClient streamingClient = new StreamingClient(accessToken);

        // Initialize your WebSocket listener
        WebSocketListener webSocketListener = new WebSocketListener();

        // Begin the streaming session
        streamingClient.connect(webSocketListener, streamContentType, sessionConfig);




        // Convert file into byte array
        byte[] fileByteArray = new byte[(int) audioFile.length()];
        try (final FileInputStream fileInputStream = new FileInputStream(audioFile)) {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            try (final DataInputStream dataInputStream = new DataInputStream(bufferedInputStream)) {
                dataInputStream.readFully(fileByteArray, 0, fileByteArray.length);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        // Set the number of bytes to send in each message
        int chunk = 8000;

        // Stream the file in the configured chunk size
        for (int start = 0; start < fileByteArray.length; start += chunk) {

            streamingClient.sendAudioData(
                    ByteString.of(
                            ByteBuffer.wrap(
                                    Arrays.copyOfRange(
                                            fileByteArray, start, Math.min(fileByteArray.length, start + chunk)))));
            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.play();
        }

        // Wait to make sure all responses are received
        //Thread.sleep(5000);
        // Close the WebSocket
        streamingClient.close();
        transcriptlabel.setText("");
    }



//    @FXML
//    void openwindow(ActionEvent event) {
//        Scene scene = tachat.getScene();
//        scene.widthProperty().addListener(new ChangeListener<Number>() {
//            @Override
//            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//                // Adjust layout based on the new width
//                root.setPrefWidth(newValue.doubleValue());
//            }
//        });
//
//        scene.heightProperty().addListener(new ChangeListener<Number>() {
//            @Override
//            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//                // Adjust layout based on the new height
//                root.setPrefHeight(newValue.doubleValue());
//            }
//        });
//    }

    @FXML
    void initialize(){
        tachat.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                // Call the conversation function when Enter key is pressed
                try {
                    sendMessage();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });



        scroll.setContent(chatvbox);

        // Add listener to always set scrollbar to bottom when content changes
        chatvbox.heightProperty().addListener((obs, oldVal, newVal) -> {
            scroll.setVvalue(1.0); // Set scrollbar value to bottom
        });
    }
    // Your WebSocket listener for all streaming responses
    public class WebSocketListener implements RevAiWebSocketListener {

        @Override
        public void onConnected(ConnectedMessage message) {
            System.out.println(message);
        }

        @Override
        public void onHypothesis(Hypothesis hypothesis) {
            // Check if hypothesis is not null
            if (hypothesis != null && hypothesis.getType().getMessageType().equals("final")) {
                // Get the elements from the hypothesis
                List<Element> elements = List.of(hypothesis.getElements());
                // Iterate over elements and extract the "value" field
                for (Element element : elements) {
                    // Get the value from the element and print it if not null
                    String value = element.getValue();
                    if (value != null) {
                        System.out.print(value);
                        Platform.runLater(() -> {
                            transcriptlabel.setText(transcriptlabel.getText() + value);
                        });
                    }
                }
            }

        }


        @Override
        public void onError(Throwable t, Response response) {
            System.out.println(response);
        }

        @Override
        public void onClose(int code, String reason) {
            System.out.println(reason);
        }

        @Override
        public void onOpen(Response response) {
            System.out.println(response.toString());
        }
    }

}
