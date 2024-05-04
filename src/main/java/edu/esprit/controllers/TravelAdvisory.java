package edu.esprit.controllers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TravelAdvisory {

    private static final String API_KEY = "6h2kkfqgkh3r2urwz6pkbgrz";

    public static String fetchTravelAdvisory(String countryCode) throws IOException, InterruptedException {
        String baseUrl = "https://api.tugo.com/v1/travelsafe/countries/" + countryCode;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .header("Accept", "application/json")
                .header("X-Auth-API-Key", API_KEY)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Status Code: " + response.statusCode());
        return response.body(); // Return the body to be used in the UI
    }
}
