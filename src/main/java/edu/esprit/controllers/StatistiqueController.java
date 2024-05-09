package edu.esprit.controllers;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import edu.esprit.services.ServiceDonnees;


public class StatistiqueController implements Initializable {



    @FXML
    private PieChart destinationLaPlusConsulteeChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Charger les statistiques dynamiques lors de l'initialisation de la fenêtre
        chargerStatistiquesDynamiques();
    }

    private void chargerStatistiquesDynamiques() {
        // Interroger la base de données pour obtenir les destinations les plus consultées
        Map<String, Integer> statistiques = obtenirStatistiquesDestinations();

        // Ajouter chaque destination avec son nombre de consultations au PieChart
        for (Map.Entry<String, Integer> entry : statistiques.entrySet()) {
            destinationLaPlusConsulteeChart.getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
    }


    private Map<String, Integer> obtenirStatistiquesDestinations() {
        // Code pour interroger la base de données et obtenir les statistiques des destinations
        // Vous pouvez utiliser votre service de données pour récupérer ces informations
        // Retournez les statistiques sous forme de Map où la clé est la destination et la valeur est le nombre de consultations
        return ServiceDonnees.obtenirStatistiquesDestinations();
    }
}
