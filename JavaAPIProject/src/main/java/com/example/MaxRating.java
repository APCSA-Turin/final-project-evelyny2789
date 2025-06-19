package com.example;

import javax.swing.*;

public class MaxRating {
  
    public static int getMaxRating(DefaultListModel<String> list) { //returns the highest rating
        int max = 0; //initializes the max to 0
        for (int i = 0; i < list.size(); i++) { //iterate through the list of ratings 
            int val = extractRatingFromEntry(list.getElementAt(i)); //gets the integer rating 
            if (val > max) max = val; //sets the max to be the current integer if greater than current max
        }
        return max; //returns the max value 
    }


    private static int extractRatingFromEntry(String entry) { ////extracts the integer rating 
        int idx = entry.lastIndexOf(":"); //finds the index of the the colon of the string
        if (idx != -1 && idx + 2 < entry.length()) { //checks that there is a colon in the string and that there is a number value
            try {
                return Integer.parseInt(entry.substring(idx + 2).trim()); //returns integer parsed from substring 
            } catch (NumberFormatException e) { //does nothing if integer cannot be parsed
            }
        }
        return 0; //returns 0 if no number was found
    }
}