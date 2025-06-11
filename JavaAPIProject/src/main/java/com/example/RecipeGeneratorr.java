package com.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;


public class RecipeGeneratorr {
    public static String getCaptionFromTags(String tags) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            String prompt = "generate 3 recipes that contains the ingredient " + tags;
            System.out.println("Calling OpenAI API");
            JSONObject body = new JSONObject() // anthropic/claude-3.5-sonnet , deepseek/deepseek-coder-6.7b-instruct
                    .put("model", "openai/gpt-3.5-turbo")
                    .put("prompt", prompt);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://openrouter.ai/api/v1/chat/completions"))
                    .header("Authorization", "Bearer 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")
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("RESPONSE: " + response);
            JSONObject json = new JSONObject(response.body());
            System.out.println("RESPONSE: " + json);
            return json.getJSONArray("choices")
                    .getJSONObject(0)
                    .getString("text")
                    .strip();
        } catch (Exception e) {
            e.printStackTrace();
            return "no recipe";
        }
    }

}
