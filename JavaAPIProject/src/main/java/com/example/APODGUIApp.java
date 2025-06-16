// package com.example;

// import org.json.JSONObject;

// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.time.LocalDate;
// import java.time.Period;

// public class APODGuiApp {

//     public static void main(String[] args) {
//         SwingUtilities.invokeLater(APODGuiApp::createAndShowGUI);
//     }

//     private static void createAndShowGUI() {
//         JFrame frame = new JFrame("NASA APOD Viewer");
//         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         frame.setSize(600, 600);
//         frame.setLayout(new BorderLayout());

//         // Input Panel
//         JPanel inputPanel = new JPanel();
//         inputPanel.setLayout(new FlowLayout());

//         JTextField dateField = new JTextField(10);
//         dateField.setToolTipText("YYYY-MM-DD");

//         JButton fetchButton = new JButton("Fetch APOD");

//         inputPanel.add(new JLabel("Enter your birthday (YYYY-MM-DD):"));
//         inputPanel.add(dateField);
//         inputPanel.add(fetchButton);

//         // Output Panel
//         JTextArea outputArea = new JTextArea();
//         outputArea.setLineWrap(true);
//         outputArea.setWrapStyleWord(true);
//         outputArea.setEditable(false);
//         JScrollPane scrollPane = new JScrollPane(outputArea);

//         frame.add(inputPanel, BorderLayout.NORTH);
//         frame.add(scrollPane, BorderLayout.CENTER);

//         // Button Action
//         fetchButton.addActionListener((ActionEvent e) -> {
//             String date = dateField.getText().trim();
//             if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {
//                 fetchAndDisplayAPOD(date, outputArea);
//             } else {
//                 JOptionPane.showMessageDialog(frame, "Please enter a valid date in YYYY-MM-DD format.");
//             }
//         });

//         frame.setVisible(true);
//     }

//     private static void fetchAndDisplayAPOD(String date, JTextArea outputArea) {
//         try {
//             String apiKey = "MksqFow96LdSKQkfQq3pnMbByg7MbGlvihVTpjMt";
//             String fullApiKey = apiKey + "&date=" + date;

//             String jsonData = APODFetcher.fetchAPODData(fullApiKey);
//             JSONObject json = new JSONObject(jsonData);

//             String title = json.getString("title");
//             String explanation = json.getString("explanation");
//             String mediaType = json.getString("media_type");
//             String url = json.getString("url");

//             // Age calculation
//             LocalDate inputDate = LocalDate.parse(date);
//             LocalDate today = LocalDate.now();
//             Period period = Period.between(inputDate, today);

//             String result = String.format(
//                 "--- NASA Astronomy Picture of the Day ---\n" +
//                 "Title: %s\n" +
//                 "Media Type: %s\n" +
//                 "URL: %s\n\n" +
//                 "Explanation:\n%s\n\n" +
//                 "Time since %s:\n%d years, %d months, %d days",
//                 title, mediaType, url, explanation, date,
//                 period.getYears(), period.getMonths(), period.getDays()
//             );

//             outputArea.setText(result);

//         } catch (Exception ex) {
//             outputArea.setText("Failed to fetch APOD: " + ex.getMessage());
//         }
//     }
// }
