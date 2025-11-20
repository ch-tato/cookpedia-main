package com.cookpedia.model;

public class Step {
    private int id;
    private int stepNumber;
    private String instruction;
    private int recipeId;

    public Step() {}

    public Step(int id, int stepNumber, String instruction, int recipeId) {
        this.id = id;
        this.stepNumber = stepNumber;
        this.instruction = instruction;
        this.recipeId = recipeId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getStepNumber() { return stepNumber; }
    public void setStepNumber(int stepNumber) { this.stepNumber = stepNumber; }

    public String getInstruction() { return instruction; }
    public void setInstruction(String instruction) { this.instruction = instruction; }

    public int getRecipeId() { return recipeId; }
    public void setRecipeId(int recipeId) { this.recipeId = recipeId; }
}
