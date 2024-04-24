package edu.esprit.services;

import edu.esprit.entities.SessionUtilisateur;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.*;

public class ChatClient extends Application {
    public static final DatagramSocket socket;
    static {
        try {
            socket = new DatagramSocket(); // init to any available port
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }
    public static final InetAddress address;

    static {
        try {
            address = InetAddress.getByName("localhost");
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public static final String identifier = SessionUtilisateur.getUtilisateurActuel().getNom();

    public static final int SERVER_PORT = 8000; // send to server

    public static final TextArea messageArea = new TextArea();

    private static final TextField inputBox = new TextField();
    private  static final VBox ImagePhoto = new VBox(10);



    public static void main(String[] args) throws IOException {

        // thread for receiving messages
        ClientThread clientThread = new ClientThread(socket, messageArea);
        clientThread.start();
        // send initialization message to the server
        byte[] uuid = ("init;" + identifier).getBytes();
        DatagramPacket initialize = new DatagramPacket(uuid, uuid.length, address, SERVER_PORT);
        socket.send(initialize);

        launch(); // launch GUI

    }

    @Override
    public void start(Stage primaryStage) {
        messageArea.setMaxWidth(500);
        ImagePhoto.setMaxWidth(100);
        messageArea.setEditable(false);
        inputBox.setMaxWidth(500);
        inputBox.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String temp = identifier + " : " + inputBox.getText(); // message to send
                messageArea.setText(messageArea.getText() + inputBox.getText() + "\n"); // update messages on screen
                byte[] msg = temp.getBytes(); // convert to bytes
                inputBox.setText(""); // remove text from input box
                // create a packet & send
                DatagramPacket send = new DatagramPacket(msg, msg.length, address, SERVER_PORT);
                try {
                    socket.send(send);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        // put everything on screen
        Scene scene = new Scene(new VBox(35, messageArea, inputBox,ImagePhoto), 1000,500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
