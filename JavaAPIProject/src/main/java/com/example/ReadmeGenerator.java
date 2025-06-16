package com.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadmeGenerator {
    public static void generateReadme(String title, String explanation, String imagePath) throws IOException {
        String mdContent = "# NASA Astronomy Picture of the Day\n\n" +
                "## " + title + "\n\n" +
                "![NASA APOD Image](" + imagePath + ")\n\n" +
                "### Explanation\n\n" +
                explanation + "\n";

        Files.writeString(Paths.get("README.md"), mdContent);
    }
}