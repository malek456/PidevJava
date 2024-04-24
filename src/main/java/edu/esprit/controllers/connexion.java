package edu.esprit.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author ASUS
 */
public class connexion {
    public String url="jdbc:mysql://localhost:3306/wetravel";
    public String login="root";
    public String pwd="";
    Connection cnx;
    public static connexion instance;
    private connexion() {
        try {
            cnx= DriverManager.getConnection(url, login, pwd);
            System.out.println("connexion établie !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public Connection getCnx() {
        return cnx;
    }

    public static connexion getInstance()
    {
        if(instance==null)
        {
            instance=new connexion();
        }
        return instance;
    }
}
