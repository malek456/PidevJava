package edu.esprit.services;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import edu.esprit.entities.Voyage;
import edu.esprit.entities.Voyage;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceVoyage implements IService<Voyage> {

    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Voyage voyage) throws SQLException {
        /*String req = "INSERT INTO `voyage`(`nom`, `prenom`) VALUES ('"+voyage.getNom()+"','"+voyage.getPrenom()+"')";
        try {
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Voyage added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/







        String req = "INSERT INTO `voyage`(`destination`, `Picture`,`Offer`) VALUES (?,?,?)";
        PreparedStatement ps = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1,voyage.getDestination());
        ps.setString(2,voyage.getPicture());
        ps.setString(3,voyage.getOffer());

        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            int id = rs.getInt(1);
            voyage.setId(id);  // assuming you have a setId method in your Vol class
        }

        System.out.println("Voyage added with ID: " + voyage.getId());
    }





   

    @Override
    public Voyage getOneById(int id) {
        return null;
    }

    @Override
    public Set<Voyage> getAll() {
        Set<Voyage> voyages = new HashSet<>();

        String req = "Select * from voyage";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                int id = res.getInt("id");
                String Destination = res.getString("destination");
                String Picture = res.getString("Picture");
                String Offer = res.getString("Offer");

                Voyage p = new Voyage(id,Destination,Picture,Offer);
                voyages.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return voyages;
    }
    @Override
    public void modifier(Voyage voyage) {
        String req = "UPDATE voyage SET Destination = ?, `Picture` = ?, `Offer` = ?   WHERE id = ?";
        try {
            PreparedStatement pmnt = cnx.prepareStatement(req);

            pmnt.setString(1,voyage.getDestination());
            pmnt.setString(2,voyage.getPicture());
            pmnt.setString(3,voyage.getOffer());
            pmnt.setInt(4, voyage.getId()); // Assuming you have an ID field in your Voyage class
            pmnt.executeUpdate();
            System.out.println("flight modified!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM voyage WHERE id = ?";
        try {
            PreparedStatement pmnt = cnx.prepareStatement(req);
            pmnt.setInt(1, id);
            pmnt.executeUpdate();
            System.out.println("flight with ID " + id + " deleted!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

