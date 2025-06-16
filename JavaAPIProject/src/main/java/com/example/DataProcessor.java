package com.example;

public class DataProcessor {
    public static double calculateAverage(double[] nums) {
            double avg = 0;
            for(double num:nums){
            avg+=num;
    }
    return avg/nums.length;
    }
}