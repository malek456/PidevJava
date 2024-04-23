package com.example.reclamation.controllers;

import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;

public class TraductionController implements Initializable {

    @FXML
    private TextArea detect;

    @FXML
    private TextArea translate;
    @FXML
    private ComboBox<String> combobox;

    @FXML
    public void traduir(ActionEvent event) {
        String input = detect.getText();
        String apiUrl = "https://api-translate.systran.net/translation/text/translate";
        String authorizationToken = "Key 4b7b3b61-9cc5-4796-a93e-4fefc0e3d40e";
        String target = combobox.getSelectionModel().getSelectedItem().substring(0,2);
        System.out.println(target);

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("input", input);
        requestBody.put("target", target);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(apiUrl))
                    .header("Authorization", authorizationToken)
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(new Gson().toJson(requestBody)))
                    .build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                Type mapType = new TypeToken<Map<String, List<Map<String, String>>>>(){}.getType();
                Map<String, List<Map<String, String>>> responseData = new Gson().fromJson(response.body(), mapType);
                String output = responseData.get("outputs").get(0).get("output");
                translate.setText(output);
            } else {
                System.out.println("Translation Error1234");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.out.println("Translation Error1234");
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String[] languages = Locale.getISOLanguages();
        for (String languageCode : languages) {
            Locale locale = new Locale(languageCode);
            String languageName = locale.getDisplayLanguage();
            String lang = languageCode + " - " + languageName;
            combobox.getItems().add(lang);
        }
    }
}
