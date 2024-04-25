package edu.esprit.projecthope.services;

import edu.esprit.projecthope.entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDateTime;

import edu.esprit.projecthope.utils.connexion;
import javafx.scene.control.Alert;
import org.json.JSONArray;

import static edu.esprit.projecthope.controllers.UserController.hashPassword;

public class ServiceUser implements IService<User> {
    private static final Connection connection = connexion.getInstance().getCnx();
    public  ObservableList<User> getAll() {

        ObservableList<User> UserList = FXCollections.observableArrayList();
        try {
            String query2 = "SELECT * FROM  user ";
            PreparedStatement smt = connection.prepareStatement(query2);
            User user;
            ResultSet resultSet = smt.executeQuery();
            while (resultSet.next()) {
                user = new User(resultSet.getInt("id"), resultSet.getString("email"), resultSet.getString("nom"), resultSet.getString("prenom"),
                        resultSet.getString("password"), resultSet.getString("phone_number"), resultSet.getString("roles"), resultSet.getBoolean("is_verified"), resultSet.getTimestamp("created_at").toLocalDateTime(), resultSet.getString("pays"), resultSet.getString("description_user"),resultSet.getString("image_name"));
                UserList.add(user);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return UserList;
    }
    public int ajouter(User user) throws SQLException, NoSuchAlgorithmException {
        String[] roles = {"ROLE_USER"};
        JSONArray rolesArray = new JSONArray(roles);
        String rolesJson = rolesArray.toString();
        LocalDateTime createdAt = LocalDateTime.now(); // Current timestamp
        String query = "INSERT INTO user (nom, prenom, phone_number, email, password, roles, created_at,is_verified,image_name) VALUES (?, ?, ?, ?, ?, ?, ?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user.getNom());
        statement.setString(2, user.getPrenom());
        statement.setString(3, user.getPhoneNumber());
        statement.setString(4, user.getEmail());
        statement.setString(5, hashPassword(user.getPassword()));
        statement.setString(6, rolesJson);
        statement.setTimestamp(7, Timestamp.valueOf(createdAt)); // Set the created_at parameter
        statement.setBoolean(8, false);
        statement.setString(9, "defaultProfile.png");
        return statement.executeUpdate();
    }
    public void supprimer(User user) throws SQLException {
        String query = "delete from user where email=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user.getEmail());
        int row_delete = statement.executeUpdate();
        if (row_delete > 0) {
            System.out.println("User deleted  succesufully");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("WeTransfet :: Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Cannot delete User !!");
            alert.showAndWait();
        }
    }
    public int ajouterAdmin(User user) throws SQLException, NoSuchAlgorithmException {
        String[] roles = {"ROLE_USER","ROLE_ADMIN"};
        JSONArray rolesArray = new JSONArray(roles);
        String rolesJson = rolesArray.toString();
        LocalDateTime createdAt = LocalDateTime.now(); // Current timestamp
        String query = "INSERT INTO user (nom, prenom, phone_number, email, password, roles, created_at,is_verified,pays,description_user,image_name) VALUES (?, ?, ?, ?, ?, ?, ?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user.getNom());
        statement.setString(2, user.getPrenom());
        statement.setString(3, user.getPhoneNumber());
        statement.setString(4, user.getEmail());
        statement.setString(5, hashPassword(user.getPassword()));
        statement.setString(6, rolesJson);
        statement.setTimestamp(7, Timestamp.valueOf(createdAt)); // Set the created_at parameter
        statement.setBoolean(8, false);
        statement.setString(9, user.getPays());
        statement.setString(10,user.getDescriptionUser());
        statement.setString(11, user.getImageName());
        return statement.executeUpdate();
    }
    public int modifyIsVerify(User user) throws SQLException {
        String query = "UPDATE user SET is_verified = ? where email = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setBoolean(1, true);
        statement.setString(2, user.getEmail());
        return statement.executeUpdate();
    }
    public int modifer(User user,User userModify) throws SQLException {
        String query = "UPDATE user SET email=?,nom=?,prenom=?,phone_number=?,pays=?,description_user=?,image_name=? WHERE email =?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(2, user.getNom());
        statement.setString(3, user.getPrenom());
        statement.setString(4, user.getPhoneNumber());
        statement.setString(1, user.getEmail());
        statement.setString(5, user.getPays());
        statement.setString(6,user.getDescriptionUser());
        statement.setString(7, user.getImageName());
        statement.setString(8, userModify.getEmail());
        return statement.executeUpdate();
    }

}
