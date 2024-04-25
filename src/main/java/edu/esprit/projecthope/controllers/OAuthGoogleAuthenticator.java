package edu.esprit.projecthope.controllers;

import edu.esprit.projecthope.entities.SessionUtilisateur;
import edu.esprit.projecthope.entities.User;
import edu.esprit.projecthope.tests.HelloApplication;
import edu.esprit.projecthope.utils.connexion;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class OAuthGoogleAuthenticator extends OAuthAuthenticator{

    private String GOOGLE_apiScope = "https://www.googleapis.com/auth/userinfo.profile";

    public OAuthGoogleAuthenticator(String clientID, String redirectUri, String clientSecret, String apiScope) {
        super(clientID, redirectUri, clientSecret);
        GOOGLE_apiScope = apiScope;
    }

    @Override
    String getWebUrl() {
        return "https://accounts.google.com/o/oauth2/v2/auth?scope=" + GOOGLE_apiScope + "&access_type=offline&redirect_uri=" + getRedirectUri() + "&response_type=code&client_id=" + getClientID();
    }

    @Override
    String getApiTokenUrl() {
        return "https://www.googleapis.com/oauth2/v2/userinfo?access_token=" + getAccessToken();
    }

    @Override
    String getApiAccessUrl() {
        return "https://www.googleapis.com/oauth2/v4/token";
    }

    @Override
    String getApiAccessParams() {
        return "client_id=" + getClientID() + "&redirect_uri=" + getRedirectUri() + "&client_secret=" + getClientSecret() + "&grant_type=authorization_code&code=" + getAccessCode();
    }
    @Override
    protected void notifyLoginViewCompleted() throws SQLException, IOException {
        super.notifyLoginViewCompleted();

        if (hasFinishedSuccessfully()) {
            JSONObject jsonData = getJsonData();
            if (jsonData != null) {
                String email = jsonData.getString("email");
                String firstName = jsonData.getString("given_name");
                String lastName = jsonData.getString("family_name");
                String googleId = jsonData.getString("id");
                String[] roles = {"ROLE_USER"};
                JSONArray rolesArray = new JSONArray(roles);
                String rolesJson = rolesArray.toString();
                LocalDateTime createdAt = LocalDateTime.now(); // Current timestamp
                String query = "INSERT INTO user (nom, prenom,email,roles, created_at,is_verified,google_id,image_name) VALUES (?, ?, ?, ?, ?, ?,?,?)";
                Connection connection = connexion.getInstance().getCnx();
                PreparedStatement statement = connection.prepareStatement(query);
                User user = new User(email,firstName,lastName,rolesJson,true,createdAt,googleId);
                statement.setString(1, user.getNom());
                statement.setString(2, user.getPrenom());
                statement.setString(3, user.getEmail());
                statement.setString(4, user.getRoles());
                statement.setBoolean(6, user.isVerified());
                statement.setTimestamp(5, Timestamp.valueOf(createdAt));
                statement.setString(7, user.getGoogleId());
                statement.setString(8, "defaultProfile.png");
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("User from google signed up successfully!");
                    SessionUtilisateur.demarrerSession(user);
                    redirectToFrontend(new Stage());
                }
                else {
                    System.out.println("Failed to sign up user.");
                }
            }
        }

    }
    private void redirectToFrontend(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("../front.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1920, 1000);
        stage.setTitle("Acceuil!");
        stage.setScene(scene);
        stage.show();
    }
}