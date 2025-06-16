package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileWriter;
import java.net.URL;

public class APODFetcher {

    private static final String API_KEY = "MksqFow96LdSKQkfQq3pnMbByg7MbGlvihVTpjMt";
    private static final String APOD_API_URL = "https://api.nasa.gov/planetary/apod";

    public static String fetchAPODData(String date) throws IOException {
        String fullUrl = APOD_API_URL + "?api_key=" + API_KEY + "&date=" + date;
        StringBuilder sb = new StringBuilder();

        try (InputStream in = new URL(fullUrl).openStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        String jsonData = sb.toString();

        // Save JSON data to file
        try (FileWriter writer = new FileWriter("apod_data.json")) {
            writer.write(jsonData);
        }

        return jsonData;
    }
}
