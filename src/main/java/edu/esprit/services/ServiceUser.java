package edu.esprit.services;

<<<<<<< HEAD
<<<<<<< HEAD
import edu.esprit.entities.Reclamation;
import edu.esprit.entities.User;
import edu.esprit.utils.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ServiceUser implements CRUD<User>{
    private Connection cnx ;

    public ServiceUser() {
        this.cnx = DBConnection.getInstance().getCnx();
    }

    @Override
    public void insertOne(User uer) throws SQLException {

    }

    @Override
    public void updateOne(User uer) throws SQLException {

    }

    @Override
    public void deleteOne(User uer) throws SQLException {

    }

    @Override
    public List<User> selectAll() throws SQLException {
        return null;
    }

    @Override
    public User selectOne_by_id(int id) throws SQLException {
        String req = "SELECT * FROM `User` WHERE id="+id;
        Statement st = cnx.createStatement();

        ResultSet rs = st.executeQuery(req);
        User u = new User();
//
//
        if (rs.next()) {
            u.setId(rs.getInt(("id")));
            u.setRoles(rs.getString("roles"));
            u.setNom(rs.getString("nom"));
            u.setPrenom(rs.getString("prenom"));
            u.setImage_name(rs.getString("image_name"));
        }
//
        return u;
=======
=======
>>>>>>> 63ad2e38ef884dc8eb7bcb09afe72c2fb43e4b25
import edu.esprit.controllers.UserController;
import edu.esprit.entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import edu.esprit.utils.connexion;
import javafx.scene.control.Alert;
import org.json.JSONArray;

import static edu.esprit.controllers.UserController.hashPassword;

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
    public  User getUserByEmail(String email) {

        User user = null;
        try {
            String query2 = "SELECT * FROM  user where email=? ";
            PreparedStatement smt = connection.prepareStatement(query2);
            smt.setString(1, email);
            ResultSet resultSet = smt.executeQuery();
            while (resultSet.next()) {
                user = new User(resultSet.getInt("id"), resultSet.getString("email"), resultSet.getString("nom"), resultSet.getString("prenom"),
                        resultSet.getString("password"), resultSet.getString("phone_number"), resultSet.getString("roles"), resultSet.getBoolean("is_verified"), resultSet.getTimestamp("created_at").toLocalDateTime(), resultSet.getString("pays"), resultSet.getString("description_user"), resultSet.getString("image_name"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return user;
    }
    public  ObservableList<User> SearchByNomOrPrenom(String nomOrPrenom) {
        ObservableList<User> UserList = FXCollections.observableArrayList();
        try {
            String query2 = "SELECT * FROM user WHERE nom LIKE CONCAT('%', ? , '%') OR prenom LIKE CONCAT('%' , ? , '%');";
            PreparedStatement smt = connection.prepareStatement(query2);
            smt.setString(1, nomOrPrenom);
            smt.setString(2, nomOrPrenom);
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
    public int modiferPass(String password,String email) throws SQLException{
        String query = "UPDATE user SET password=? WHERE email =?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, UserController.hashPassword(password));
        statement.setString(2, email);
        return statement.executeUpdate();
    }
    public ResultSet StateNbrPays() throws SQLException {
        String query = "SELECT pays, COUNT(*) AS nombre_utilisateurs FROM user GROUP BY pays";
        PreparedStatement statement = connection.prepareStatement(query);
        return statement.executeQuery();
    }
    public Map<String, Integer> getUserStatistics() throws SQLException {
        Map<String, Integer> userStats = new HashMap<>();
        String query = "SELECT MONTHNAME(created_at) as month, COUNT(*) as userCount FROM user GROUP BY month ORDER BY MONTH(created_at);";
             PreparedStatement pst = connection.prepareStatement(query);
             ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String month = rs.getString("month");
                int count = rs.getInt("userCount");
                userStats.put(month, count);
            }
        return userStats;
<<<<<<< HEAD
>>>>>>> main
=======
>>>>>>> 63ad2e38ef884dc8eb7bcb09afe72c2fb43e4b25
    }
}
