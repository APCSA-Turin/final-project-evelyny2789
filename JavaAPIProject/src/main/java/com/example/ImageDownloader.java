package com.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.FileWriter;
import java.net.URL;
import java.nio.file.*;
import java.util.Scanner;

public class ImageDownloader {

    public static void downloadImageAndExplanation(String imageUrl, String baseFilename, String explanation) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Path imagePath = Paths.get(baseFilename + ".jpg");
        Path textPath = Paths.get(baseFilename + ".txt");

        // image
        imagePath = handleFileConflict(scanner, imagePath, ".jpg");
        if (imagePath == null) {
            System.out.println("Image download skipped.");
            return;
        }

        try (InputStream in = new URL(imageUrl).openStream()) {
            Files.copy(in, imagePath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("✅ Image saved as: " + imagePath.getFileName());
        }

        // explanation
        textPath = Paths.get(imagePath.toString().replace(".jpg", ".txt")); // sync .txt name
        textPath = handleFileConflict(scanner, textPath, ".txt");
        if (textPath == null) {
            System.out.println("Explanation saving skipped.");
            return;
        }

        try (FileWriter writer = new FileWriter(textPath.toFile())) {
            // Normalize actual line breaks
            writer.write(explanation);
            System.out.println("✅ Explanation saved as: " + textPath.getFileName());
        }
    }

    // prompt logic for overwrite 
    private static Path handleFileConflict(Scanner scanner, Path path, String extension) {
        if (Files.exists(path)) {
            System.out.print("File '" + path.getFileName() + "' exists. Overwrite? (yes/no/rename): ");
            String response = scanner.nextLine().trim().toLowerCase();
            switch (response) {
                case "yes":
                case "y":
                    return path;
                case "rename":
                    return findAvailableFilename(path, extension);
                default:
                    return null; // cancel
            }
        }
        return path;
    }

    // Generate new filename with suffix
    private static Path findAvailableFilename(Path path, String extension) {
        String filename = path.getFileName().toString();
        String baseName = filename.replace(extension, "");
        Path parent = path.getParent() != null ? path.getParent() : Paths.get(".");
        int count = 1;
        Path newPath;
        do {
            newPath = parent.resolve(baseName + "_" + count + extension);
            count++;
        } while (Files.exists(newPath));
        return newPath;
    }
}