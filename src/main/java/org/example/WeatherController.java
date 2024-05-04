package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
//import edu.esprit.controllers.APIConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.net.MalformedURLException;


public class WeatherController {

    // Votre clé API
    private final String apiKey = "6ee820e1687ec34c5ee7802faee628de";
           // nom de API : OpenWeatherMap

    @FXML
    private TextField cityInput;

    @FXML
    private Text weatherText;

    // URL de l'API OpenWeatherMap pour obtenir l'ID de la ville
    private final String cityAPI = "http://api.openweathermap.org/data/2.5/weather?q=";

    // URL de l'API OpenWeatherMap pour obtenir les données météo
    private final String weatherAPI = "http://api.openweathermap.org/data/2.5/weather";


    @FXML
    void getWeatherData(ActionEvent event) throws MalformedURLException {
        JSONObject todaysWeather = GetTodaysWeatherInformation(getWoeid());

        System.out.println(todaysWeather);
        JSONObject mainData = (JSONObject) todaysWeather.get("main");


        weatherText.setText(
                "Min temperature: " + mainData.get("temp_min") +
                        "\nCurrent temperature: " + mainData.get("temp") +
                        "\nMax temperature: " + mainData.get("temp_max")
        );
    }


    public String getWoeid() throws MalformedURLException {
        APIConnector apiConnectorCity = new APIConnector(cityAPI + cityInput.getText() + "&appid=" + apiKey);
        JSONObject jsonData = apiConnectorCity.getJSONObject("");
        return jsonData.get("id").toString();
    }


    public JSONObject GetTodaysWeatherInformation(String cityId) throws MalformedURLException {
        // Utilisation de l'URL weatherAPI pour obtenir les données météo
        APIConnector apiConnectorWeather = new APIConnector(weatherAPI + "?id=" + cityId + "&appid=" + apiKey);
        return apiConnectorWeather.getJSONObject("");
    }
}