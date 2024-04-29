package edu.esprit.controllers;

import edu.esprit.entities.SessionManager;
import edu.esprit.entities.SessionUtilisateur;
import edu.esprit.entities.User;
import edu.esprit.utils.connexion;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class OAuthFacebookAuthenticator extends OAuthAuthenticator{
    private String FACEBOOK_fieldsString = "email,name,gender,id";

    public OAuthFacebookAuthenticator(String clientID, String redirectUri, String clientSecret, String apiFields) {
        super(clientID, redirectUri, clientSecret);
        FACEBOOK_fieldsString = apiFields;
    }

    @Override
    String getWebUrl() {
        return "https://www.facebook.com/dialog/oauth?client_id=" + getClientID() + "&redirect_uri=" + getRedirectUri();
    }

    @Override
    String getApiTokenUrl() {
        return "https://graph.facebook.com/me?fields=" + FACEBOOK_fieldsString + "&access_token=" + getAccessToken();
    }

    @Override
    String getApiAccessUrl() {
        return "https://graph.facebook.com/oauth/access_token";
    }

    @Override
    String getApiAccessParams() {
        return  "client_id=" + getClientID() + "&redirect_uri=" + getRedirectUri() + "&client_secret=" + getClientSecret() + "&code=" + getAccessCode();
    }
    @Override
    protected void notifyLoginViewCompleted() throws SQLException, IOException {
        super.notifyLoginViewCompleted();

        if (hasFinishedSuccessfully()) {
            JSONObject jsonData = getJsonData();
            if (jsonData != null) {
                String email = jsonData.getString("name");
                String googleId = jsonData.getString("id");
                String[] roles = {"ROLE_USER"};
                JSONArray rolesArray = new JSONArray(roles);
                String rolesJson = rolesArray.toString();
                LocalDateTime createdAt = LocalDateTime.now(); // Current timestamp
                String query = "INSERT INTO user (email,roles, created_at,is_verified,google_id,image_name) VALUES (?, ?, ?, ?, ?,?)";
                Connection connection = connexion.getInstance().getCnx();
                PreparedStatement statement = connection.prepareStatement(query);
                User user = new User(email,rolesJson,true,createdAt,googleId);
                statement.setString(1, user.getEmail());
                statement.setString(2, user.getRoles());
                statement.setBoolean(4, user.isVerified());
                statement.setTimestamp(3, Timestamp.valueOf(createdAt));
                statement.setString(5, user.getGoogleId());
                statement.setString(6, "defaultProfile.png");
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    SessionUtilisateur.demarrerSession(user);
                    SessionManager.createSession();
                    SessionManager.setUserEmail(SessionUtilisateur.getUtilisateurActuel().getEmail());
                    System.out.println(SessionManager.getUserEmail());
                    System.out.println("User from facebook signed up successfully!");
                    UserController.redirectToFrontend();
                }
                else {
                    System.out.println("Failed to sign up user.");
                }
            }
        }

    }

}
