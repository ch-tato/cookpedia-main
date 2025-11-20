package com.cookpedia.model;

public class Ingredient {
    private int id;
    private int recipeId;
    private String name;
    private String unit;

    public Ingredient() {}

    public Ingredient(int id, int recipeId, String name, String unit) {
        this.id = id;
        this.recipeId = recipeId;
        this.name = name;
        this.unit = unit;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getRecipeId() {
        return recipeId;
    }
    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUnit() {
        return unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }
}
