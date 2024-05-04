<<<<<<< HEAD
package com.example.reclamation.services;

import com.example.reclamation.models.Notification;
import com.example.reclamation.models.Reponse;
import com.example.reclamation.utils.DBConnection;
=======
package edu.esprit.services;

import edu.esprit.entities.Notification;
import edu.esprit.entities.Reponse;
import edu.esprit.utils.DBConnection;
>>>>>>> ba038a7 (metiers+api)

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ServiceReponse implements CRUD<Reponse>{
    private Connection cnx ;
    ServiceNotification sn = new ServiceNotification();
    ServiceReclamation sr = new ServiceReclamation();

    public ServiceReponse() {this.cnx = DBConnection.getInstance().getCnx();}

    @Override
    public void insertOne(Reponse reponse) throws SQLException {
        String req = "INSERT INTO `reponse`(`reclamation_id`, `date`, `contenu`, `etat`) VALUES "
                +"('"+reponse.getReclamation().getId()+"','"+reponse.getDate()+"','"+reponse.getContenu()+"','"+reponse.getEtat()+"')";

        Statement st = cnx.createStatement();
        st.executeUpdate(req,Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = st.getGeneratedKeys();

        if (rs.next()) {
            reponse.setId(rs.getInt(1));
        }
        Notification notification = new Notification();
        notification.setReponse(reponse);
        notification.setReponse(reponse);
        notification.setClient(reponse.getReclamation().getId_client());
        notification.setType("reponse");
        sn.insertOne(notification);
        System.out.println("Reponse Added !");
    }

    @Override
    public void updateOne(Reponse reponse) throws SQLException {
        String req = "UPDATE `reponse` SET " +
                "`reclamation_id`="+reponse.getReclamation().getId()+
                ",`date`='"+reponse.getDate()+"'"+
                ",`contenu`='"+reponse.getContenu()+"'"+
                ",`etat`='"+reponse.getEtat()+"'"+
                " WHERE id="+reponse.getId();
        System.out.println(req);
        Statement st = cnx.createStatement();
        st.executeUpdate(req);
        System.out.println("Reponse Modified !");
    }

    @Override
    public void deleteOne(Reponse reponse) throws SQLException {
        String req = "DELETE FROM `reponse` WHERE id="+reponse.getId();
        Statement st = cnx.createStatement();
        st.executeUpdate(req);
        System.out.println("Reclamation Deleted !");
    }

    @Override
    public List<Reponse> selectAll() throws SQLException {
        List<Reponse> reponseList = new ArrayList<>();
        String req = "SELECT * FROM `reponse`";
        Statement st = cnx.createStatement();

        ResultSet rs = st.executeQuery(req);
        while (rs.next()){
            Reponse r = new Reponse();

            r.setId(rs.getInt("id"));
            r.setDate(rs.getTimestamp("date"));
            r.setContenu(rs.getString("contenu"));
            r.setEtat(rs.getString("etat"));
            r.setReclamation(sr.selectOne_by_id(rs.getInt("reclamation_id")));

            reponseList.add(r);
        }

        return reponseList;
    }

    @Override
    public Reponse selectOne_by_id(int id) throws SQLException {
        String req = "SELECT * FROM `reponse` WHERE id="+id;
        Statement st = cnx.createStatement();

        ResultSet rs = st.executeQuery(req);
        Reponse r = new Reponse();
//
//
        if (rs.next()) {
            r.setId(rs.getInt(("id")));
            r.setDate(rs.getTimestamp("date"));
            r.setContenu(rs.getString("contenu"));
//            r.setReclamation();
//            r.setType(rs.getString(("type")));
//            r.setDescription(rs.getString(("description")));
//            r.setStatut(rs.getString(("statut")));
//            r.setDate_envoi(rs.getTimestamp("date_envoi"));
//            r.setId_client(su.selectOne_by_id(rs.getInt("client_id")));
//            r.setId_paiement(sp.selectOne_by_id(rs.getInt("paiement_id")));
//
//            r.setId_reservation(sr.selectOne_by_id(rs.getInt("reservation_id")));
        }
//
        return r;
    }

    public List<Reponse> selectAll_by_idReclamation(int id) throws SQLException {
        String req = "SELECT * FROM `reponse` WHERE reclamation_id="+id;
        Statement st = cnx.createStatement();
        List<Reponse> reponseList = new ArrayList<>();
        ResultSet rs = st.executeQuery(req);

        while (rs.next()){
            Reponse r = new Reponse();

            r.setId(rs.getInt("id"));
            r.setDate(rs.getTimestamp("date"));
            r.setContenu(rs.getString("contenu"));
            r.setEtat(rs.getString("etat"));
            r.setReclamation(sr.selectOne_by_id(rs.getInt("reclamation_id")));

            reponseList.add(r);
        }

        return reponseList;
    }
    }
