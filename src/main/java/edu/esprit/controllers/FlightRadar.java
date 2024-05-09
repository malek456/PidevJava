package edu.esprit.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.net.URI;




public class FlightRadar {

    @FXML
    private VBox vbox;



    public void initialize() throws IOException, InterruptedException {

        fetchData();
    }

    private void fetchData() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://sky-scanner3.p.rapidapi.com/get-config"))
                .header("X-RapidAPI-Key", "2b6a97ba0cmsh5e2c89ff9862957p15863ajsnf2cb41817cd4")
                .header("X-RapidAPI-Host", "sky-scanner3.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        new Thread(() -> {
            try {
                HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                String responseBody = response.body();
                System.out.println(responseBody); // Debug print to inspect the response structure

                // Assuming the response has a top-level object that contains an array under "data"
                JSONObject responseObject = new JSONObject(responseBody);
                JSONArray jsonArray = responseObject.getJSONArray("data"); // Adjust this key according to your response structure

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/countryDetail.fxml"));
                    Node pane = loader.load();
                    CountryDetail controller = loader.getController();
                    controller.setData(
                            jsonObject.getString("country"),
                            jsonObject.getString("market"),
                            jsonObject.getString("locale"),
                            jsonObject.optJSONObject("currencyTitle") != null ? jsonObject.getJSONObject("currencyTitle").toString() : jsonObject.optString("currencyTitle", "N/A"),
                            jsonObject.getString("currency"),
                            jsonObject.getString("currencySymbol"),
                            jsonObject.getString("site")
                    );

                    // Use Platform.runLater to update the UI on the JavaFX Application Thread
                    Platform.runLater(() -> vbox.getChildren().add(pane));
                }
            } catch (IOException | InterruptedException | JSONException e) {
                e.printStackTrace();
            }
        }).start();

    }


}
