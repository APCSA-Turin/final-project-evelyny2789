package com.example;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class TestGUI {
    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless()) {
            System.out.println("Headless detected");
            System.exit(1);
        }
        JFrame frame = new JFrame("Test GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new JLabel("Hello, GUI!"));
        frame.setSize(300, 100);
        frame.setVisible(true);
    }
}
