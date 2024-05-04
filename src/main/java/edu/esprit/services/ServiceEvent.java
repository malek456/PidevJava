package edu.esprit.services;

import edu.esprit.entities.Event;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class ServiceEvent implements IService<Event> {

    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Event event) throws SQLException {

        String req = "INSERT INTO `event`( `Type`,`Datedebut`,`Datefin`,`Prix`,`lieux`,`image`) VALUES (?,?,?,?,?,?)";
        PreparedStatement ps = cnx.prepareStatement(req,Statement.RETURN_GENERATED_KEYS);
        ps.setString(1,event.getType());
        ps.setDate(2,event.getDatedebut());
        ps.setDate(3,event.getDatefin());

        ps.setFloat(4,event.getPrix());
        ps.setString(5,event.getLieux());
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

    public List<Event> geteventsDataFromDatabase() throws SQLException {
        List<Event> events = new ArrayList<>();

        // Connexion à la base de données

        // Requête SQL pour récupérer les données des séances
        String query = "SELECT nom, COUNT(*) AS frequency FROM event GROUP BY Type";

        // Préparation de la requête
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            // Exécution de la requête
            try (ResultSet resultSet = statement.executeQuery()) {
                // Parcours des résultats et ajout des données à la liste
                while (resultSet.next()) {
                    String eventType = resultSet.getString("Type");
                    int frequency = resultSet.getInt("frequency");
                    Event event = new Event(eventType, frequency);
                    events.add(event);
                }
            }
        }

        // Fermeture de la connexion à la base de données
        cnx.close();

        return events;
    }

    public Map<String, Integer> getEventFrequencyMap() {
        Map<String, Integer> frequencyMap = new HashMap<>();

        // Parcourir la liste de toutes les séances
        for (Event event : getAll()) {
            String eventType = event.getType();

            // Mettre à jour la fréquence du nom de séance dans la carte
            frequencyMap.put(eventType, frequencyMap.getOrDefault(eventType, 0) + 1);
        }

        return frequencyMap;
    }

    public List<Event> getAllEvents() {
    }
}

