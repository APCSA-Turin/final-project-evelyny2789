package com.example;

import javax.swing.*;

public class MaxRating {
  
    public static int getMaxRating(DefaultListModel<String> model) {
        int max = 0;
        for (int i = 0; i < model.size(); i++) {
            int val = extractRatingFromEntry(model.getElementAt(i));
            if (val > max) max = val;
        }
        return max;
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