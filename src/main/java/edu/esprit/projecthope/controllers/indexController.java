package edu.esprit.projecthope.controllers;

/*import com.example.myjavafxapp.client.ChatClient;
import com.example.myjavafxapp.client.ClientThread;*/
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.DatagramPacket;

//import static com.example.myjavafxapp.client.ClientThread.socket;

public class indexController {
    @FXML
    private void OpenCommunity() throws IOException {
        /*ClientThread clientThread = new ClientThread(ChatClient.socket, ChatClient.messageArea);
        clientThread.start();
        // send initialization message to the server
        byte[] uuid = ("init;" + ChatClient.identifier).getBytes();
        DatagramPacket initialize = new DatagramPacket(uuid, uuid.length, ChatClient.address, ChatClient.SERVER_PORT);
        socket.send(initialize);
        ChatClient chatClient = new ChatClient();
        chatClient.start(new Stage());*/
    }
}
