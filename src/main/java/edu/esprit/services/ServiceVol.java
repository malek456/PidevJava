package edu.esprit.services;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import edu.esprit.entities.Vol;
import edu.esprit.entities.Vol;
import edu.esprit.utils.DataSource;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.sql.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ServiceVol implements IServiceVol<Vol> {

    private Vol currentVol;
   private int i =0;

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Vol vol) throws SQLException {
        String req = "INSERT INTO `vol`(`aeroport_depart`, `aeroport_arrive`,`date_depart`,`date_arrive`,`prix`,`code`,`nombre_personnes`,`image`) VALUES (?,?,?,?,?,?,?,?)";
        PreparedStatement ps = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1,vol.getAeroport_depart());
        ps.setString(2,vol.getGetAeroport_arrive());
        ps.setTimestamp(3,vol.getDate_depart());
        ps.setTimestamp(4,vol.getGetDate_arrive());
        ps.setFloat(5,vol.getPrix());
        ps.setInt(6,vol.getCode());
        ps.setInt(7,vol.getNombre_personnes());
        ps.setString(8,vol.getImage());

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            int id = rs.getInt(1);
            vol.setId(id);  // assuming you have a setId method in your Vol class
        }

        System.out.println("Vol added with ID: " + vol.getId());
    }




    @Override
    public Vol getOneById(int id) {
        return null;
    }

    @Override
    public Set<Vol> getAll() {
        Set<Vol> vols = new HashSet<>();

        String req = "Select * from vol";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                int id = res.getInt(1);
                String aeroport_depart = res.getString(2);
                String aeroport_arrive = res.getString(3);
                Timestamp date_depart = res.getTimestamp(4);
                Timestamp date_arrive = res.getTimestamp(5);
                Float prix = res.getFloat(6);
                int code = res.getInt(7);
                int nombre_personnes = res.getInt(8);
                String image = res.getString(9);


                Vol p = new Vol(id, aeroport_depart, aeroport_arrive, date_depart, date_arrive, prix, code, nombre_personnes, image);
                vols.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return vols;
    }
    @Override
    public void modifier(Vol vol) {
        String req = "UPDATE vol SET `aeroport_depart` = ?, `aeroport_arrive` = ?, `date_depart` = ? ,`date_arrive` = ?, `prix` = ?, `code` = ? ,`nombre_personnes` = ?, `image` = ?  WHERE id = ?";
        try {
            PreparedStatement pmnt = cnx.prepareStatement(req);

            pmnt.setString(1,vol.getAeroport_depart());
            pmnt.setString(2,vol.getGetAeroport_arrive());
            pmnt.setTimestamp(3,vol.getDate_depart());
            pmnt.setTimestamp(4, vol.getGetDate_arrive());
            pmnt.setFloat(5,vol.getPrix());
            pmnt.setInt(6,vol.getCode());
            pmnt.setInt(7,vol.getNombre_personnes());
            pmnt.setString(8,vol.getImage());
            pmnt.setInt(9,vol.getId());
            pmnt.executeUpdate();
            System.out.println("flight modified!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM vol WHERE id = ?";
        try {
            PreparedStatement pmnt = cnx.prepareStatement(req);
            pmnt.setInt(1, id);
            pmnt.executeUpdate();
            System.out.println("flight with ID " + id + " deleted!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setCurrentVol(Vol vol) {
        this.currentVol = vol;
    }

    public Vol getCurrentVol() {
        return this.currentVol;
    }
}

