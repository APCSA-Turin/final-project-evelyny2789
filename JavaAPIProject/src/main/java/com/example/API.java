// package com.example;

// import org.json.JSONObject;

// import java.io.FileWriter;
// import java.time.LocalDate;
// import java.time.Period;
// import java.util.Scanner;

// public class API {
//     public static void main(String[] args) {
//         try {
//             Scanner scanner = new Scanner(System.in);
//             String apiKey = "MksqFow96LdSKQkfQq3pnMbByg7MbGlvihVTpjMt";
//             System.out.println("--- NASA Astronomy Picture of the Day ---");
//             System.out.println("When is your birthday? YYYY-MM-DD");

//             String date = scanner.nextLine();
//             String fullApiKey = apiKey + "&date=" + date;

//             String jsonData = APODFetcher.fetchAPODData(fullApiKey);
//             JSONObject json = new JSONObject(jsonData);

//             String title = json.getString("title");
//             String explanation = json.getString("explanation");
//             String mediaType = json.getString("media_type");
//             String url = json.getString("url");

//             // Parse date and calculate time since
//             LocalDate inputDate = LocalDate.parse(date);
//             LocalDate today = LocalDate.now();
//             Period period = Period.between(inputDate, today);

//             String output = String.format(
//                 "--- NASA Astronomy Picture of the Day ---\n" +
//                 "Date: %s\nTitle: %s\nMedia Type: %s\nURL: %s\n\n" +
//                 "Explanation:\n%s\n\n" +
//                 "Time since %s: %d years, %d months, %d days\n",
//                 date, title, mediaType, url, explanation, date,
//                 period.getYears(), period.getMonths(), period.getDays()
//             );

//             System.out.println(output);

//             // Ask to save to file
//             System.out.print("Would you like to save this to a file? (yes/no): ");
//             String saveResponse = scanner.nextLine().trim().toLowerCase();

//             if (saveResponse.equals("yes") || saveResponse.equals("y")) {
//                 System.out.print("Enter filename to save (e.g., apod.txt): ");
//                 String filename = scanner.nextLine();

//                 try (FileWriter writer = new FileWriter(filename)) {
//                     writer.write(output);
//                     System.out.println("✅ APOD info saved to " + filename);
//                 } catch (Exception e) {
//                     System.err.println("❌ Failed to save file: " + e.getMessage());
//                 }
//             }

//         } catch (Exception e) {
//             System.err.println("Failed to fetch APOD: " + e.getMessage());
//         }
//     }
// }