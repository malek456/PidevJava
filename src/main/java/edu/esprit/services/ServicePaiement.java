package edu.esprit.services;

import edu.esprit.entities.Paiement;
import edu.esprit.utils.DataSource;
import edu.esprit.entities.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServicePaiement {

    Connection cnx = DataSource.getInstance().getCnx();
    public void ajouter(Paiement paiement) throws SQLException {

        String req = "INSERT INTO `paiement`(`vol_id`, `user_id`,`mode`,`num_carte`) VALUES (?,?,?,?)";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1,paiement.getVol().getId());
        ps.setInt(2,paiement.getUser().getId());
        ps.setString(3,paiement.getMode());
        ps.setInt(4,paiement.getNum_carte());

        ps.executeUpdate();
        System.out.println("Paiement added !");
    }

    public Set<Paiement> getAll() {
        Set<Paiement> paiements = new HashSet<>();

        String req = "SELECT * FROM paiement";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                int id = res.getInt(1);
                int volId = res.getInt(2);
                int userId = res.getInt(3);
                String mode = res.getString(4);
                int num_carte = res.getInt(5);

                // Fetch the corresponding Vol object based on volId
                Vol vol = getVolById(volId);
                // Fetch the corresponding User object based on userId
                User user = getUserById(userId);

                // Assuming you have a constructor for Paiement class that takes instances of Vol and User
                Paiement paiement = new Paiement(id, vol, user, mode, num_carte);
                paiements.add(paiement);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return paiements;
    }

    // Method to fetch Vol object based on volId
    private Vol getVolById(int volId) throws SQLException {
        String req = "SELECT * FROM vol WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, volId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            // Create and return Vol object based on ResultSet values
            // You need to adjust this part based on your Vol class structure
            return new Vol(rs.getInt("id"), rs.getString("aeroport_depart"), rs.getString("aeroport_arrive"), rs.getDate("date_depart"), rs.getDate("date_arrive"), rs.getFloat("prix"), rs.getInt("code"), rs.getInt("nombre_personnes"), rs.getString("image"));
        }
        return null;
    }

    // Method to fetch User object based on userId
    private User getUserById(int userId) throws SQLException {
        String req = "SELECT * FROM user WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            // Create and return User object based on ResultSet values
            // You need to adjust this part based on your User class structure
            return new User(rs.getInt("id"), rs.getString("email"), rs.getString("nom"), rs.getString("prenom"));
        }
        return null;
    }

    public void modifier(Paiement paiement) {
        String req = "UPDATE paiement SET vol_id = ?, user_id = ?, mode = ?, num_carte = ? WHERE id = ?";
        try {
            PreparedStatement pmnt = cnx.prepareStatement(req);

            pmnt.setInt(1, paiement.getVol().getId());
            pmnt.setInt(2, paiement.getUser().getId());
            pmnt.setString(3, paiement.getMode());
            pmnt.setInt(4, paiement.getNum_carte());
            pmnt.setInt(5, paiement.getId());

            pmnt.executeUpdate();
            System.out.println("Paiement modified!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void supprimer(int paiementId) {
        String req = "DELETE FROM paiement WHERE id = ?";
        try {
            PreparedStatement pmnt = cnx.prepareStatement(req);
            pmnt.setInt(1, paiementId);
            pmnt.executeUpdate();
            System.out.println("Paiement with ID " + paiementId + " deleted!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Vol getVolByCode(String volCode) throws SQLException {
        String req = "SELECT * FROM vol WHERE code = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, volCode);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            // Create and return Vol object based on ResultSet values
            return new Vol(rs.getInt("id"), rs.getString("aeroport_depart"), rs.getString("aeroport_arrive"), rs.getDate("date_depart"), rs.getDate("date_arrive"), rs.getFloat("prix"), rs.getInt("code"), rs.getInt("nombre_personnes"), rs.getString("image"));
        }
        return null;
    }

    // Method to retrieve User object by user email
    public User getUserByEmail(String userEmail) throws SQLException {
        String req = "SELECT * FROM user WHERE email = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, userEmail);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            // Create and return User object based on ResultSet values
            return new User(rs.getInt("id"), rs.getString("email"), rs.getString("nom"), rs.getString("prenom"));
        }
        return null;
    }

    public List<String> getAllVolNumbers() throws SQLException {
        List<String> volNumbers = new ArrayList<>();
        String req = "SELECT code FROM vol"; // Assuming 'code' is the column name for vol numbers
        try (PreparedStatement ps = cnx.prepareStatement(req);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                volNumbers.add(rs.getString("code"));
            }
        }
        return volNumbers;
    }

    // Method to retrieve all user emails from the database
    public List<String> getAllUserEmails() throws SQLException {
        List<String> userEmails = new ArrayList<>();
        String req = "SELECT email FROM user"; // Assuming 'email' is the column name for user emails
        try (PreparedStatement ps = cnx.prepareStatement(req);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                userEmails.add(rs.getString("email"));
            }
        }
        return userEmails;
    }

}
