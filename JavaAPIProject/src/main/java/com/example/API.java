package com.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class API {
    public static String getData(String endpoint, String apiKey) throws Exception {
        URL url = new URL(endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
       
        // Add headers
        connection.setRequestProperty("Authorization", "Bearer " + apiKey);
        connection.setRequestProperty("Content-Type", "application/json");

        BufferedReader buff = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
       
        while ((inputLine = buff.readLine()) != null) {
            content.append(inputLine);
        }

        buff.close();
        connection.disconnect();
        return content.toString();
    }
}