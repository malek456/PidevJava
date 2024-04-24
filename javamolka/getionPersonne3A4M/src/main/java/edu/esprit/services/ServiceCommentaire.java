package edu.esprit.services;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import edu.esprit.entities.Commentaire;
import edu.esprit.entities.Event;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ServiceCommentaire implements IService<Commentaire> {

    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Commentaire commentaire) throws SQLException {
        /*String req = "INSERT INTO `commentaire`(`date`, `contenu`) VALUES ('"+commentaire.getNom()+"','"+commentaire.getPrenom()+"')";
        try {
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Commentaire added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/
        String req = "INSERT INTO `commentaire`(`event_id`,`date`, `contenu`) VALUES (?,?,?)";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1,commentaire.getEvent().getId());

        ps.setDate(2,commentaire.getDate());
        ps.setString(3,commentaire.getContenu());
        ps.executeUpdate();
        System.out.println("Commentaire added !");
    }

    @Override
    public Commentaire getOneById(int id) {
        return null;
    }

    @Override
    public Set<Commentaire> getAll() {
        Set<Commentaire> commentaires = new HashSet<>();

        String req = "SELECT * FROM commentaire";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                int id = res.getInt(1);
                int eventId = res.getInt(2);
                Date date = res.getDate(3);
                String contenu = res.getString(4);

                // Fetch the corresponding Vol object based on volId
                Event event = getEventById(eventId);
                // Assuming you have a constructor for Commentaire class that takes instances of Vol and User
                Commentaire commentaire = new Commentaire(id, event, date, contenu);
                commentaires.add(commentaire);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return commentaires;
    }

    private Event getEventById(int eventId) throws SQLException {
        String req = "SELECT * FROM event WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, eventId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            // Create and return User object based on ResultSet values
            return new Event(rs.getInt("id"), rs.getString("Type"), rs.getDate("Datedebut"), rs.getDate("Datefin"),rs.getFloat("prix"),rs.getString("lieux"),rs.getString("image"));
        }
        return null;
    }

    public Set<Commentaire> getAllByEventId(int eventId) {
        Set<Commentaire> commentaires = new HashSet<>();

        String req = "SELECT * FROM commentaire WHERE event_id = ?";
        try {
            PreparedStatement st = cnx.prepareStatement(req);
            st.setInt(1, eventId);
            ResultSet res = st.executeQuery();
            while (res.next()) {
                int id = res.getInt(1);
                Date date = res.getDate(3);
                String contenu = res.getString(4);

                // Fetch the corresponding Event object based on eventId
                Event event = getEventById(eventId);
                Commentaire commentaire = new Commentaire(id, event, date, contenu);
                commentaires.add(commentaire);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return commentaires;
    }



    @Override
    public void modifier(Commentaire commentaire) {
        String req = "UPDATE commentaire SET date = ?, contenu = ? WHERE id = ?";
        try {
            PreparedStatement pmnt = cnx.prepareStatement(req);
            pmnt.setDate(1,commentaire.getDate());
            pmnt.setString(2,commentaire.getContenu());

            pmnt.setInt(3, commentaire.getId()); // Assuming you have an ID field in your Commentaire class
            pmnt.executeUpdate();
            System.out.println("commentaire modified!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM commentaire WHERE id = ?";
        try {
            PreparedStatement pmnt = cnx.prepareStatement(req);
            pmnt.setInt(1, id);
            pmnt.executeUpdate();
            System.out.println("commentaire with ID " + id + " deleted!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteCommentaire(Commentaire selectedCommentaire) {
    }

    public Commentaire getCommentairesByEvent(Event selectedEvent) {
        return null;
    }

    public void addCommentaire(Commentaire commentaire) {
    }

    public void updateCommentaire(Commentaire selectedCommentaire) {
    }
}

