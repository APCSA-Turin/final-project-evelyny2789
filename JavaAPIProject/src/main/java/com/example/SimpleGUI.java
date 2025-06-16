package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import javax.imageio.ImageIO;
import org.json.JSONObject; 
public class SimpleGUI {
    private static final String API_KEY = "FTMp1rsSirRG6M29jyfe946jobbywhtZSMFt5CK1";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SimpleGUI::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Cosmos image Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 600);

        // Components
        JLabel dateLabel = new JLabel("Enter date (YYYY-MM-DD):");
        JTextField dateField = new JTextField();
        JButton fetchButton = new JButton("Generate cosmos");
        JLabel imageLabel = new JLabel("", SwingConstants.CENTER);
        JTextArea infoArea = new JTextArea(5, 40);
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);
        infoArea.setEditable(false);
        JScrollPane infoScroll = new JScrollPane(infoArea);

        // Layout
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.add(dateLabel, BorderLayout.WEST);
        inputPanel.add(dateField, BorderLayout.CENTER);
        inputPanel.add(fetchButton, BorderLayout.EAST);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(imageLabel, BorderLayout.CENTER);
        mainPanel.add(infoScroll, BorderLayout.SOUTH);

        frame.setContentPane(mainPanel);

        // Button Action
        fetchButton.addActionListener(e -> {
            String date = dateField.getText().trim();
            if (date.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter a date in YYYY-MM-DD format.", "There is an error with your input", JOptionPane.ERROR_MESSAGE);
                return;
            }
            fetchButton.setEnabled(false);
            infoArea.setText("Generating cosmos...");
            imageLabel.setIcon(null);

            // Fetch in background
            new SwingWorker<Void, Void>() {
                String title = "";
                String explanation = "";
                ImageIcon apodImage = null;
                String errorMsg = null;

                @Override
                protected Void doInBackground() {
                    try {
                        String apiUrl = String.format(
                            "https://api.nasa.gov/planetary/apod?api_key=%s&date=%s",
                            API_KEY, URLEncoder.encode(date, "UTF-8")
                        );
                        HttpURLConnection conn = (HttpURLConnection) new URL(apiUrl).openConnection();
                        conn.setConnectTimeout(8000);
                        conn.setReadTimeout(8000);

                        if (conn.getResponseCode() != 200) {
                            errorMsg = "Error: " + conn.getResponseCode();
                            return null;
                        }

                        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) sb.append(line);
                        reader.close();

                        JSONObject json = new JSONObject(sb.toString());
                        title = json.optString("title", "No Title");
                        explanation = json.optString("explanation", "No Explanation");
                        String mediaType = json.optString("media_type", "image");
                        String url = json.optString("url", "");

                        if (!mediaType.equals("image")) {
                            errorMsg = "APOD for this date is not an image.\nURL: " + url;
                            return null;
                        }

                        // Download image
                        BufferedImage img = ImageIO.read(new URL(url));
                        if (img != null) {
                            // Scale image to fit label
                            int width = Math.min(img.getWidth(), 600);
                            int height = Math.min(img.getHeight(), 350);
                            Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                            apodImage = new ImageIcon(scaled);
                        } else {
                            errorMsg = "Could not load image.";
                        }

                    } catch (Exception ex) {
                        errorMsg = "Error: " + ex.getMessage();
                    }
                    return null;
                }

                @Override
                protected void done() {
                    fetchButton.setEnabled(true);
                    if (errorMsg != null) {
                        infoArea.setText(errorMsg);
                        imageLabel.setIcon(null);
                    } else {
                        imageLabel.setIcon(apodImage);
                        infoArea.setText(title + "\n\n" + explanation);
                    }
                }
            }.execute();
        });

        frame.setVisible(true);
    }
}