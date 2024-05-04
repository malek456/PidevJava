package edu.esprit.services;

import edu.esprit.entities.Notification;
import edu.esprit.entities.Reponse;
import edu.esprit.entities.User;
import edu.esprit.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceNotification implements CRUD<Notification> {
    private Connection cnx ;


    public ServiceNotification() {
        this.cnx = this.cnx = DBConnection.getInstance().getCnx();
    }
    @Override
    public void insertOne(Notification notification) throws SQLException {
        String req="";
        notification.setDate(new Timestamp(System.currentTimeMillis()));
        if(notification.getType().equals("reclamation")) {
            req = "INSERT INTO `notification`(`id_client`, `id_reclamation`, `active`, `date`,`type`) VALUES"
                    + "('" + notification.getClient().getId() + "','" + notification.getReclamation().getId() + "','" + 1 + "','" + notification.getDate() + "','" + notification.getType() + "')";
        } else if (notification.getType().equals("reponse")) {
            req = "INSERT INTO `notification`(`id_client`, `id_reponse`, `active`, `date`,`type`) VALUES"
                    + "('" + notification.getClient().getId() + "','" + notification.getReponse().getId() + "','" + 1 + "','" + notification.getDate() + "','" + notification.getType() + "')";
        }
        Statement st = cnx.createStatement();
        st.executeUpdate(req);
    }

    @Override
    public void updateOne(Notification notification) throws SQLException {
        String req = "UPDATE `notification`SET active=0 WHERE id="+notification.getId();
        Statement st = cnx.createStatement();
        st.executeUpdate(req);
    }

    @Override
    public void deleteOne(Notification notification) throws SQLException {

    }

    @Override
    public List<Notification> selectAll() throws SQLException {
        return null;
    }

    public List<Notification> selectAllBy_id(User user) throws SQLException {
        List<Notification> notificationList = new ArrayList<>();
        String req="";
        String type="";
        if(user.getRoles().equals("[\"ROLE_USER\"]")) {
            req = "SELECT * FROM `notification` WHERE id_client=" + user.getId() + " AND active=1 ORDER BY date DESC";
            type="reponse";
        }
        else if (user.getRoles().equals("[\"ROLE_ADMIN\"]")) {
            req = "SELECT * FROM `notification` WHERE active=1 AND type='reclamation' ORDER BY date DESC";
            type="reclamation";
        }
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        ServiceReponse srep = new ServiceReponse();
        ServiceReclamation srecl = new ServiceReclamation();
        while (rs.next()){
            Notification n = new Notification();
            n.setId(rs.getInt("id"));
            n.setDate(rs.getTimestamp("date"));
            if(type.equals("reponse")) {
                n.setReponse(srep.selectOne_by_id(rs.getInt("id_reponse")));
            }
            else if (type.equals("reclamation")){
                n.setReclamation(srecl.selectOne_by_id(rs.getInt("id_reclamation")));
            }

            notificationList.add(n);
        }

        return notificationList;
    }


    @Override
    public Notification selectOne_by_id(int id) throws SQLException {
        return null;
    }

}
