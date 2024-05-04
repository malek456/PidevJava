package edu.esprit.services;

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
    }
}
