package edu.esprit.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class FlightData {

    @FXML
    private ListView<String> listView;

    @FXML
    private TextArea textArea;

    @FXML
    private ImageView imageView;

    @FXML
    private GridPane gridPane;

    @FXML
    private VBox vBox;

    private ObservableList<String> flightDetailsList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        runPythonScript();

    }



    private void runPythonScript() {
        String country = "France";
        String year = "2020";
        String month = "10";
        String day = "30";

        ProcessBuilder processBuilder = new ProcessBuilder(
                "python",
                "C:\\Users\\BOUZIDI MALEK\\IdeaProjects\\gestionVol\\src\\main\\resources\\predictions.py",
                country, year, month, day
        );
        processBuilder.redirectErrorStream(true);

        new Thread(() -> {
            try {
                Process process = processBuilder.start();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                String line;
                while ((line = reader.readLine()) != null) {
                    final String finalLine = line.trim();
                    javafx.application.Platform.runLater(() -> {
                        if (finalLine.startsWith("Image URL for")) {
                            // Extract description and URL
                            String description = finalLine.substring(finalLine.indexOf(" for ") + 5, finalLine.indexOf(":"));
                            String imageUrl = finalLine.substring(finalLine.indexOf("http"));
                            addImageAndLabel(imageUrl, description);
                        } else {
                            System.out.println("Non-URL output: " + finalLine);
                        }
                    });
                }

                int exitCode = process.waitFor();
                javafx.application.Platform.runLater(() -> System.out.println("Exited with code " + exitCode));
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }



    private AtomicInteger rowIndex = new AtomicInteger(0);  // Class member to keep track of the current row

    private void addImageAndLabel(String imageUrl, String description) {
        try {
            // Load the image-label FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/recomendation.fxml"));
            Node imageLabel = loader.load();
            Recomendation controller = loader.getController();

            // Set the image and description
            controller.setImageAndText(imageUrl, description);

            // Add to HBox
            javafx.application.Platform.runLater(() -> vBox.getChildren().add(imageLabel));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}






