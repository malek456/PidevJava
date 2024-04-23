package com.example.reclamation.services;
import com.example.reclamation.models.Notification;
import com.example.reclamation.models.Reclamation;
import com.example.reclamation.models.Reponse;
import com.example.reclamation.models.Reservation;
import com.example.reclamation.utils.DBConnection;

import java.net.Authenticator;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class ServiceReclamation implements CRUD<Reclamation>{
    private Connection cnx ;
    ServiceReservation sr = new ServiceReservation();
    ServicePaiement sp = new ServicePaiement();
    ServiceUser su = new ServiceUser();
    ServiceNotification sn = new ServiceNotification();

    public ServiceReclamation() {
        this.cnx = DBConnection.getInstance().getCnx();
    }

    public void insertOne(Reclamation reclamation) throws SQLException{
        String req="";
        if(reclamation.getType().equals("paiement"))
            req = "INSERT INTO `reclamation`(`client_id`,`paiement_id`,`type`, `date`, `description`, `statut`, `date_envoi`) VALUES"
        +"('"+reclamation.getId_client().getId()+"','"+reclamation.getId_paiement().getId()+"','"+reclamation.getType()+"','"+reclamation.getDate()+"','"+reclamation.getDescription()+"','"+reclamation.getStatut()+"','"+reclamation.getDate_envoi()+"')";
        else
        if(reclamation.getType().equals("reservation"))
            req = "INSERT INTO `reclamation`(`client_id`,`reservation_id`,`type`, `date`, `description`, `statut`, `date_envoi`) VALUES"
                    +"('"+reclamation.getId_client().getId()+"','"+reclamation.getId_reservation().getId()+"','"+reclamation.getType()+"','"+reclamation.getDate()+"','"+reclamation.getDescription()+"','"+reclamation.getStatut()+"','"+reclamation.getDate_envoi()+"')";

//           PreparedStatement statement = cnx.prepareStatement(req);
//            statement.setString(1, reclamation.getType());
//            statement.setDate(2, reclamation.getDate());
//            statement.setString(3, reclamation.getDescription());
//            statement.setString(4, reclamation.getStatut());
//            statement.setDate(5, reclamation.getDate_envoi());
              Statement st = cnx.createStatement();
              st.executeUpdate(req,Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = st.getGeneratedKeys();

        if (rs.next()) {
             reclamation.setId(rs.getInt(1));
        }
        Notification notification = new Notification();
        notification.setReclamation(reclamation);
        notification.setClient(reclamation.getId_client());
        notification.setType("reclamation");
        sn.insertOne(notification);
        System.out.println("Reclamation Added !");
    }

    public void updateOne(Reclamation reclamation) throws SQLException{
        String req = "";
if(reclamation.getType().equals("paiement")){
    req = "UPDATE `reclamation` SET " +
            "`client_id`="+reclamation.getId_client().getId()+
            ",`reservation_id`="+null+
            ",`paiement_id`="+reclamation.getId_paiement().getId()+
            ",`type`='"+reclamation.getType()+"'"+
            ",`date`='"+reclamation.getDate()+"'"+
            ",`description`='"+reclamation.getDescription()+"'"+
            ",`statut`='"+reclamation.getStatut()+"'"+
            ",`date_envoi`='"+reclamation.getDate_envoi()+"'"+
            " WHERE id="+reclamation.getId();
} else if (reclamation.getType().equals("reservation")) {
    req = "UPDATE `reclamation` SET " +
            "`client_id`="+reclamation.getId_client().getId()+
            ",`reservation_id`="+reclamation.getId_reservation().getId()+
            ",`paiement_id`="+null+
            ",`type`='"+reclamation.getType()+"'"+
            ",`date`='"+reclamation.getDate()+"'"+
            ",`description`='"+reclamation.getDescription()+"'"+
            ",`statut`='"+reclamation.getStatut()+"'"+
            ",`date_envoi`='"+reclamation.getDate_envoi()+"'"+
            " WHERE id="+reclamation.getId();
}
        Statement st = cnx.createStatement();
        st.executeUpdate(req);
        System.out.println("Reclamation Modified !");
    }

    public void deleteOne(Reclamation reclamation) throws SQLException{
        String req = "DELETE FROM `reclamation` WHERE id="+reclamation.getId();
        Statement st = cnx.createStatement();
        st.executeUpdate(req);
        System.out.println("Reclamation Deleted !");
    }

    public List<Reclamation> selectAll() throws SQLException{
        List<Reclamation> reclamList = new ArrayList<>();
        String req = "SELECT * FROM `reclamation`";
        Statement st = cnx.createStatement();

        ResultSet rs = st.executeQuery(req);
        while (rs.next()){
            Reclamation r = new Reclamation();
            r.setId(rs.getInt("id"));
            r.setDate(rs.getDate("date"));
            r.setType(rs.getString("type"));
            r.setDescription(rs.getString("description"));
            r.setStatut(rs.getString("statut"));
            r.setDate_envoi(rs.getTimestamp("date_envoi"));
            r.setId_client(su.selectOne_by_id(rs.getInt("client_id")));
            if(r.getType().equals("paiement"))
                r.setId_paiement(sp.selectOne_by_id(rs.getInt("paiement_id")));
            if(r.getType().equals("reservation"))
            r.setId_reservation(sr.selectOne_by_id(rs.getInt("reservation_id")));

            reclamList.add(r);
        }

        return reclamList;
    }

    public List<Reclamation> selectAll_ByIdUser(int id) throws SQLException{
        List<Reclamation> reclamList = new ArrayList<>();
        String req = "SELECT * FROM `reclamation` WHERE client_id="+id;
        Statement st = cnx.createStatement();

        ResultSet rs = st.executeQuery(req);
        while (rs.next()){
            Reclamation r = new Reclamation();
            r.setId(rs.getInt("id"));
            r.setDate(rs.getDate("date"));
            r.setType(rs.getString("type"));
            r.setDescription(rs.getString("description"));
            r.setStatut(rs.getString("statut"));
            r.setDate_envoi(rs.getTimestamp("date_envoi"));
            r.setId_client(su.selectOne_by_id(rs.getInt("client_id")));
            if(r.getType().equals("paiement"))
                r.setId_paiement(sp.selectOne_by_id(rs.getInt("paiement_id")));
            if(r.getType().equals("reservation"))
                r.setId_reservation(sr.selectOne_by_id(rs.getInt("reservation_id")));

            reclamList.add(r);
        }

        return reclamList;
    }

    public Reclamation selectOne_by_id(int id)throws SQLException{
        String req = "SELECT * FROM `reclamation` WHERE id="+id;
        Statement st = cnx.createStatement();

        ResultSet rs = st.executeQuery(req);
        Reclamation r = new Reclamation();
//
//
        if (rs.next()) {
            r.setId(rs.getInt(("id")));
            r.setDate(rs.getDate(("date")));
            r.setType(rs.getString(("type")));
            r.setDescription(rs.getString(("description")));
            r.setStatut(rs.getString(("statut")));
            r.setDate_envoi(rs.getTimestamp("date_envoi"));
            r.setId_client(su.selectOne_by_id(rs.getInt("client_id")));
            if(r.getType().equals("paiement"))
                r.setId_paiement(sp.selectOne_by_id(rs.getInt("paiement_id")));
            if(r.getType().equals("reservation"))
                r.setId_reservation(sr.selectOne_by_id(rs.getInt("reservation_id")));
        }
//
        return r;
    }

//    public List <Reclamation> selectAll2() throws SQLException {
//        List<Reclamation> reclamList = new ArrayList<>();
//        String req = "SELECT * FROM `reclamation` WHERE statut='en attente' ORDER BY date_envoi DESC";
//        Statement st = cnx.createStatement();
//        ResultSet rs = st.executeQuery(req);
//        while (rs.next()){
//            Reclamation r = new Reclamation();
//
//            r.setId(rs.getInt(("id")));
//            r.setDate(rs.getDate(("date")));
//            r.setType(rs.getString(("type")));
//            r.setDescription(rs.getString(("description")));
//            r.setStatut(rs.getString(("statut")));
//            r.setDate_envoi(rs.getTimestamp("date_envoi"));
//            r.setId_client(su.selectOne_by_id(rs.getInt("client_id")));
//
//            reclamList.add(r);
//        }
//
//        return reclamList;
//    }
}
