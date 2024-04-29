package edu.esprit.controllers;

import edu.esprit.entities.SessionManager;
import edu.esprit.tests.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.DatagramPacket;
import static edu.esprit.controllers.ClientThread.socket;
public class indexController {
    private Stage stage;
    @FXML
    private void OpenCommunity() throws IOException {
        ClientThread clientThread = new ClientThread(ChatClient.socket, ChatClient.messageArea);
        clientThread.start();
        // send initialization message to the server
        byte[] uuid = ("init;" + ChatClient.identifier).getBytes();
        DatagramPacket initialize = new DatagramPacket(uuid, uuid.length, ChatClient.address, ChatClient.SERVER_PORT);
        socket.send(initialize);
        ChatClient chatClient = new ChatClient();
        chatClient.start(new Stage());
    }
    @FXML
    private void logout() throws IOException {
        SessionManager.endSession();
        redirectToLogin();
    }
    private void redirectToLogin() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/edu/esprit/login.fxml"));
        stage.getScene().setRoot(fxmlLoader.load());
        UserController userController = fxmlLoader.getController();
        userController.setStage(stage);
        stage.setTitle("Login");
    }

    public void setStage(Stage stage) {
        this.stage =stage;
    }
}
