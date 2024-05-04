package edu.esprit.services;

import edu.esprit.entities.Reservation;
import edu.esprit.utils.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ServiceReservation implements CRUD<Reservation>{
    private Connection cnx ;
    public ServiceReservation() {
        this.cnx = DBConnection.getInstance().getCnx();
    }


    @Override
    public void insertOne(Reservation reservation) throws SQLException {

    }

    @Override
    public void updateOne(Reservation reservation) throws SQLException {

    }

    @Override
    public void deleteOne(Reservation reservation) throws SQLException {

    }

    @Override
    public List<Reservation> selectAll() throws SQLException {
        List<Reservation> resList = new ArrayList<>();
        String req = "SELECT * FROM `reservation`";
        Statement st = cnx.createStatement();

        ResultSet rs = st.executeQuery(req);
        while (rs.next()){
            Reservation r = new Reservation();

            r.setId(rs.getInt(("id")));
            r.setNumber_of_persons(rs.getInt("number_of_persons"));
            r.setDate_from(rs.getTimestamp("date_from"));

            resList.add(r);
        }

        return resList;
    }

    public Reservation selectOne_by_id(int id) throws SQLException {
        String req = "SELECT * FROM `reservation` WHERE id="+id;
        Statement st = cnx.createStatement();

        ResultSet rs = st.executeQuery(req);
        Reservation r = new Reservation();
        if(rs.next()) {
            r.setId(rs.getInt(("id")));
            r.setNumber_of_persons(rs.getInt("number_of_persons"));
            r.setDate_from(rs.getTimestamp("date_from"));
        }
        return r;
    }
}
