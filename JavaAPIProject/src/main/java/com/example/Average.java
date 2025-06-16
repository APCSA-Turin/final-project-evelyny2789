package com.example;

import javax.swing.*;

public class Average {
    public static double getAverageRating(DefaultListModel<String> model) {
        if (model.size() == 0) return 0.0;
        int sum = 0;
        int count = 0;
        for (int i = 0; i < model.size(); i++) {
            int val = extractRatingFromEntry(model.getElementAt(i));
            sum += val;
            count++;
        }
        return count == 0 ? 0.0 : (double) sum / count;
    }

    private static int extractRatingFromEntry(String entry) {
        int idx = entry.lastIndexOf(":");
        if (idx != -1 && idx + 2 < entry.length()) {
            try {
                return Integer.parseInt(entry.substring(idx + 2).trim());
            } catch (NumberFormatException e) {
            }
        }
        return 0;
    }
}