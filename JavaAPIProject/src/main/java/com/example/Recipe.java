package com.example;

public class Recipe {
    private String name;
    private String ingredients;
    private int calories;

    public Recipe(String name, String ingredients, int calories) {
        this.name = name;
        this.ingredients = ingredients;
        this.calories = calories;
    }

    public void getInfo() {
        StringBuilder info = new StringBuilder();
        info.append("Recipe: ").append(this.name).append("\n");
        info.append("Ingredients: ").append(this.ingredients).append("\n");
        info.append("Calories: ").append(this.calories).append("\n");
        System.out.println(info.toString());
    }
}

