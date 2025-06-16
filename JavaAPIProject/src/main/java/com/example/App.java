package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONObject;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        try {
            // 1. Send HTTP request
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://cataas.com/cat?json=true"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // 2. Parse JSON to get image URL
            JSONObject json = new JSONObject(response.body());
            System.out.println(json);
            
            String imageUrl = json.getString("url");

            if (!imageUrl.startsWith("http")) {
                imageUrl = "https://cataas.com" + imageUrl;
            }

            // 3. Load Image
            Image image = new Image(imageUrl, true);

            // 4. Setup ImageView
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(600);

            // 5. Setup Scene
            StackPane root = new StackPane(imageView);
            Scene scene = new Scene(root, 640, 480);

            stage.setTitle("Random Cat Image");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}