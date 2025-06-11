package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RecipeFinderGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Recipe Finder");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);

        JButton searchButton = new JButton("Find Recipes");

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Prompt user for ingredient
                String ingredient = JOptionPane.showInputDialog(
                        frame,
                        "Enter an ingredient:",
                        "Ingredient Input",
                        JOptionPane.PLAIN_MESSAGE
                ); // [3][4][5]

                if (ingredient != null && !ingredient.trim().isEmpty()) {
                    resultArea.setText("Searching for recipes with: " + ingredient + "...");
                    // API call in a new thread
                    new Thread(() -> {
                        try {
                            // Replace with your actual API endpoint and key
                            String endpoint = "https://api.example.com/recipes?ingredient=" + ingredient;
                            String apiKey = "YOUR_API_KEY_HERE";
                            String response = API.getData(endpoint, apiKey);

                            // Here you should parse the response to extract 3 recipes.
                            // For demonstration, we just show the raw response.
                            // In practice, use a JSON library to parse and format nicely.

                            SwingUtilities.invokeLater(() -> resultArea.setText("Recipes:\n" + response));
                        } catch (Exception ex) {
                            SwingUtilities.invokeLater(() -> resultArea.setText("Error: " + ex.getMessage()));
                        }
                    }).start();
                } else {
                    resultArea.setText("No ingredient entered.");
                }
            }
        });

        frame.setLayout(new BorderLayout());
        frame.add(searchButton, BorderLayout.NORTH);
        frame.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
