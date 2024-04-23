package com.example.reclamation.utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    String url = "jdbc:mysql://127.0.0.1:3306/wetravel";
    private static DBConnection instance;
    private Connection cnx;

    private DBConnection() {
        try {
            cnx = DriverManager.getConnection(url, "root", "");
        } catch (
                SQLException e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }

    public static DBConnection getInstance() {
        if (instance == null) instance = new DBConnection();
        return instance;
    }

    public Connection getCnx() {
        return cnx;
    }

}
