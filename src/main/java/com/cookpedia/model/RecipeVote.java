package com.cookpedia.model;

public class RecipeVote {
    private int id;
    private String type;
    private int userId;
    private int recipeId;

    public RecipeVote() {}

    public RecipeVote(int id, String type, int userId, int recipeId) {
        this.id = id;
        this.type = type;
        this.userId = userId;
        this.recipeId = recipeId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getRecipeId() { return recipeId; }
    public void setRecipeId(int recipeId) { this.recipeId = recipeId; }
}
