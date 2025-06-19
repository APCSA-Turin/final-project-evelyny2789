package com.example;

import javax.swing.*;

public class Average {
    public static double getAverageRating(DefaultListModel<String> list) { //returns average of ratings
        if (list.size() == 0) return 0.0; //if the list of ratings is empty, returns the average of 0.0
        int sum = 0; //sum of all ratings
        int count = 0; //number of ratings 
        for (int i = 0; i < list.size(); i++) { //iterate through the list of ratings 
            int val = extractRatingFromEntry(list.getElementAt(i)); //gets the integer rating
            sum += val; //adds number to the total sum 
            count++; //increments the number of total ratings 
        }
        return count == 0 ? 0.0 : (double) sum / count; //returns the average
    }

    private static int extractRatingFromEntry(String entry) { //extracts the integer rating 
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