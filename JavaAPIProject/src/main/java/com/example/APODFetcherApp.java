package com.example;

import org.json.JSONObject;

import java.time.LocalDate;
import java.time.Period;
import java.util.Scanner;

public class APODFetcherApp {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("--- Today's Astronomy Picture ---");
            System.out.println("Enter a date (YYYY-MM-DD): ");
            String date = scanner.nextLine();

            String jsonData = APODFetcher.fetchAPODData(date);
            JSONObject json = new JSONObject(jsonData);

            String title = json.getString("title");
            String explanation = json.getString("explanation");
            String mediaType = json.getString("media_type");
            String url = json.getString("url");

            System.out.println("--- Picture Info ---");
            System.out.println("Title: " + title);
            System.out.println("Media Type: " + mediaType);
            System.out.println("URL: " + url);
            System.out.println("\nExplanation:\n" + explanation);

            LocalDate startDate = LocalDate.parse(date);
            LocalDate endDate = LocalDate.now();
            Period period = Period.between(startDate, endDate);
            System.out.println("Years: " + period.getYears() + ", Months: " + period.getMonths() + ", Days: " + period.getDays());

            if ("image".equalsIgnoreCase(mediaType)) {
                String imageFilename = "apod_image.jpg";
                ImageDownloader.downloadImageAndExplanation(url, "apod_image", explanation);
                System.out.println("Image downloaded as: " + imageFilename);

                ReadmeGenerator.generateReadme(title, explanation, imageFilename);
                System.out.println("README.md file generated with image and explanation.");
            } else {
                System.out.println("Media is not an image.");
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}