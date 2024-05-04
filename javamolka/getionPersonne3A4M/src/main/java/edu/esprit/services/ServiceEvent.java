package edu.esprit.services;

import edu.esprit.entities.Event;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceEvent implements IService<Event> {

    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Event event) throws SQLException {

        String req = "INSERT INTO `event`( `Type`,`Datedebut`,`Datefin`,`lieux`,`Prix`,`image`) VALUES (?,?,?,?,?,?)";
        PreparedStatement ps = cnx.prepareStatement(req,Statement.RETURN_GENERATED_KEYS);
        ps.setString(1,event.getType());
        ps.setDate(2,event.getDatedebut());
        ps.setDate(3,event.getDatefin());
        ps.setString(4,event.getLieux());
        ps.setFloat(5,event.getPrix());
        ps.setString(6,event.getImage());

        ps.executeUpdate();
        System.out.println("Event added !");

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            int id = rs.getInt(1);

                event.setId(id);  // assuming you have a setId method in your Vol class
            }

            System.out.println("event added with ID: " + event.getId());
        }





    @Override
    public Event getOneById(int id) {
        return null;
    }

    @Override
    public Set<Event> getAll() {
        Set<Event> events = new HashSet<>();

        String req = "Select * from event";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                int id = res.getInt("id");
                String type = res.getString("type");
                Date datedebut = res.getDate("datedebut");
                Date datefin = res.getDate("datefin");
                String lieux = res.getString("lieux");
                float prix = res.getFloat("prix");
                String image = res.getString("image");


                Event e = new Event(id,type,datedebut,datefin,prix,lieux,image);
                events.add(e);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return events;
    }
    @Override
    public void modifier(Event event) {
        String req = "UPDATE event SET type = ?, datedebut = ?, datefin = ?, prix = ?, lieux = ?,`image` = ?   WHERE id = ?";
        try {
            PreparedStatement pmnt = cnx.prepareStatement(req);
            pmnt.setString(1,event.getType());
            pmnt.setDate(2,event.getDatedebut());
            pmnt.setDate(3,event.getDatefin());
            pmnt.setFloat(4,event.getPrix());
            pmnt.setString(5,event.getLieux());
            pmnt.setString(6,event.getImage());
            pmnt.setInt(7, event.getId()); // Assuming you have an ID field in your Event class
            pmnt.executeUpdate();
            System.out.println("event modified!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM event WHERE id = ?";
        try {
            PreparedStatement pmnt = cnx.prepareStatement(req);
            pmnt.setInt(1, id);
            pmnt.executeUpdate();
            System.out.println("event with ID " + id + " deleted!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
