package com.example;

import javax.swing.*;
import java.awt.*;
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
        frame.setSize(900, 600);

        JLabel dateLabel = new JLabel("Enter date (YYYY-MM-DD):");
        JTextField dateField = new JTextField();
        JButton fetchButton = new JButton("Generate cosmos");
        JLabel imageLabel = new JLabel("", SwingConstants.CENTER);
        JTextArea infoArea = new JTextArea(5, 40);
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);
        infoArea.setEditable(false);
        JScrollPane infoScroll = new JScrollPane(infoArea);

        JPanel ratingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ratingPanel.setBorder(BorderFactory.createTitledBorder("Rate this image"));
        JLabel rateLabel = new JLabel("Satisfaction (1-10):");
        JSlider ratingSlider = new JSlider(1, 10, 5);
        ratingSlider.setMajorTickSpacing(1);
        ratingSlider.setPaintTicks(true);
        ratingSlider.setPaintLabels(true);
        ratingSlider.setEnabled(false);
        JButton submitRating = new JButton("Submit Rating");
        submitRating.setEnabled(false);
        JLabel ratingResult = new JLabel("");
        ratingPanel.add(rateLabel);
        ratingPanel.add(ratingSlider);
        ratingPanel.add(submitRating);
        ratingPanel.add(ratingResult);

        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.add(dateLabel, BorderLayout.WEST);
        inputPanel.add(dateField, BorderLayout.CENTER);
        inputPanel.add(fetchButton, BorderLayout.EAST);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(imageLabel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(ratingPanel, BorderLayout.NORTH);
        southPanel.add(infoScroll, BorderLayout.CENTER);
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        DefaultListModel<String> ratingsModel = new DefaultListModel<>();
        JList<String> ratingsList = new JList<>(ratingsModel);
        JScrollPane ratingsScroll = new JScrollPane(ratingsList);
        ratingsScroll.setPreferredSize(new Dimension(200, 0));
        JPanel ratingsPanel = new JPanel(new BorderLayout());
        ratingsPanel.setBorder(BorderFactory.createTitledBorder("Ratings"));
        ratingsPanel.add(ratingsScroll, BorderLayout.CENTER);

        
        JLabel maxRatingLabel = new JLabel("Max Ranking: N/A");
        JLabel avgRatingLabel = new JLabel("Average Rating: N/A");
        JPanel statsPanel = new JPanel(new GridLayout(2, 1));
        statsPanel.add(maxRatingLabel);
        statsPanel.add(avgRatingLabel);
        ratingsPanel.add(statsPanel, BorderLayout.SOUTH);
        

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, ratingsPanel, mainPanel);
        splitPane.setDividerLocation(220);
        frame.setContentPane(splitPane);

        fetchButton.addActionListener(e -> {
            String date = dateField.getText().trim();
            if (date.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter a date in YYYY-MM-DD format.", "There is an error with your input", JOptionPane.ERROR_MESSAGE);
                return;
            }
            fetchButton.setEnabled(false);
            infoArea.setText("Generating cosmos...");
            imageLabel.setIcon(null);
            ratingSlider.setEnabled(false);
            submitRating.setEnabled(false);
            ratingResult.setText("");

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

                        BufferedImage img = ImageIO.read(new URL(url));
                        if (img != null) {
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
                        ratingSlider.setEnabled(false);
                        submitRating.setEnabled(false);
                        ratingResult.setText("");
                    } else {
                        imageLabel.setIcon(apodImage);
                        infoArea.setText(title + "\n\n" + explanation);
                        ratingSlider.setEnabled(true);
                        submitRating.setEnabled(true);
                        ratingResult.setText("");
                    }
                }
            }.execute();
        });

        submitRating.addActionListener(ev -> {
            int rating = ratingSlider.getValue();
            String date = dateField.getText().trim();
            ratingResult.setText("You rated this image: " + rating + "/10");
            ratingsModel.addElement("Date: " + date + " - Rating: " + rating);


            int max = MaxRating.getMaxRating(ratingsModel);
            double avg = Average.getAverageRating(ratingsModel);
            maxRatingLabel.setText("Max Ranking: " + (max > 0 ? max : "N/A"));
            avgRatingLabel.setText("Average Rating: " + (ratingsModel.size() > 0 ? String.format("%.2f", avg) : "N/A"));
        });

        frame.setVisible(true);
    }
}