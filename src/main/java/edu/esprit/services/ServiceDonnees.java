package edu.esprit.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ServiceDonnees {

    // Méthode pour obtenir les statistiques des destinations depuis la base de données
    public static Map<String, Integer> obtenirStatistiquesDestinations() {
        Map<String, Integer> statistiques = new HashMap<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Obtenez la connexion à la base de données
            connection = obtenirConnexion();

            // Requête SQL pour obtenir les statistiques des destinations
            String query = "SELECT destination, COUNT(*) AS consultations FROM voyage GROUP BY destination";
            preparedStatement = connection.prepareStatement(query);

            // Exécution de la requête
            resultSet = preparedStatement.executeQuery();

            // Parcourir les résultats et ajouter les statistiques au Map
            while (resultSet.next()) {
                String destination = resultSet.getString("destination");
                int consultations = resultSet.getInt("consultations");
                statistiques.put(destination, consultations);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les exceptions
        } finally {
            // Fermer les ressources
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return statistiques;
    }

    // Méthode pour obtenir la connexion à la base de données
    private static Connection obtenirConnexion() throws SQLException {
        // Informations de connexion à la base de données
        String url = "jdbc:mysql://localhost:3306/WeTravel";
        String user = "root";
        String password = "";

        // Obtenez la connexion à la base de données
        return DriverManager.getConnection(url, user, password);
    }
}
