package edu.esprit.services;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import edu.esprit.entities.Hebergement;
import edu.esprit.entities.Voyage;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServiceHebergement implements IService<Hebergement> {

    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Hebergement hebergement) throws SQLException {
        String req = "INSERT INTO `Hebergement`(`Name`, `Picture`,`Location`,`Description`, `Type`,`Activities`, `voyage_id_id`, `Price`, `Latitude`, `Longitude`) VALUES (?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1,hebergement.getName());
        ps.setString(2,hebergement.getPicture());
        ps.setString(3,hebergement.getLocation());
        ps.setString(4,hebergement.getDescription());
        ps.setString(5,hebergement.getSelectedType());
        ps.setString(6,hebergement.getActivities());
        ps.setInt(7,hebergement.getVoyage().getId());
        ps.setFloat(8,hebergement.getPrice());
        ps.setDouble(9,hebergement.getLatitude());
        ps.setDouble(10,hebergement.getLongitude());

        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            int id = rs.getInt(1);
            hebergement.setId(id);  // assuming you have a setId method in your Vol class
        }

        System.out.println("hebergement added with ID: " + hebergement.getId());
    }

    @Override
    public Hebergement getOneById(int id) {
        return null;
    }

    @Override
    public Set<Hebergement> getAll() {
        Set<Hebergement> hebergements = new HashSet<>();

        String req = "Select * from hebergement";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                int id = res.getInt("id");
                String Name = res.getString("Name");
                String Picture = res.getString("Picture");
                String Location = res.getString("Location");
                String Description = res.getString("Description");
                String Type = res.getString("Type");
                String Activities = res.getString("Activities");
                int voyage_id_id = res.getInt(8);
                Float Price = res.getFloat("Price");
                Double Latitude = res.getDouble("Latitude");
                Double Longitude = res.getDouble("Longitude");


                // Fetch the corresponding Hebergement object based on Hebergement_id_id
                Voyage voyage = getVoyageById(voyage_id_id);


                Hebergement p = new Hebergement(id,Name,Picture,Location,Description,Type,Activities,Price,Latitude,Longitude,voyage);
                hebergements.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return hebergements;
    }
    // Method to fetch Vol object based on volId
    private Voyage getVoyageById(int voyageId) throws SQLException {
        String req = "SELECT * FROM voyage WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, voyageId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            // Create and return Vol object based on ResultSet values
            // You need to adjust this part based on your Vol class structure
            return new Voyage(rs.getInt("id"), rs.getString("Destination"), rs.getString("Picture"),rs.getString("Offer"));
        }
        return null;
    }


    @Override
    public void modifier(Hebergement hebergement) {
        String req = "UPDATE hebergement SET Name = ?,`Picture` = ?, `Location` = ?, `Description` = ?, Type = ?, `Activities` = ?, `voyage_id_id`= ?, `Price` = ? , `Latitude` = ? , `Longitude` = ? WHERE id = ?";
        try {
            PreparedStatement pmnt = cnx.prepareStatement(req);

            pmnt.setString(1,hebergement.getName());
            pmnt.setString(2,hebergement.getPicture());
            pmnt.setString(3,hebergement.getLocation());
            pmnt.setString(4,hebergement.getDescription());
            pmnt.setString(5, hebergement.getSelectedType());
            pmnt.setString(6,hebergement.getActivities());
            pmnt.setInt(7, hebergement.getVoyage().getId());
            pmnt.setFloat(8,hebergement.getPrice());
            pmnt.setDouble(9,hebergement.getLatitude());
            pmnt.setDouble(10,hebergement.getLongitude());

            // Ajouter le champ SelectedType
            pmnt.setInt(11, hebergement.getId()); // Assuming you have an ID field in your Hebergement class
            pmnt.executeUpdate();
            System.out.println("Hebergement modified!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM hebergement WHERE id = ?";
        try {
            PreparedStatement pmnt = cnx.prepareStatement(req);
            pmnt.setInt(1, id);
            pmnt.executeUpdate();
            System.out.println("flight with ID " + id + " deleted!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<String> getAllDestination() throws SQLException {
        List<String> destinqtions = new ArrayList<>();
        String req = "SELECT destination FROM voyage"; // Assuming 'email' is the column name for user emails
        try (PreparedStatement ps = cnx.prepareStatement(req);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                destinqtions.add(rs.getString("destination"));
            }
        }
        return destinqtions;
    }


    public Voyage getVoyageByDestination(String VoyageDestination) throws SQLException {
        String req = "SELECT * FROM voyage WHERE destination = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, VoyageDestination);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            // Create and return User object based on ResultSet values
            return new Voyage(rs.getInt("id"), rs.getString("destination"), rs.getString("picture"), rs.getString("offer"));
        }
        return null;
    }




}
