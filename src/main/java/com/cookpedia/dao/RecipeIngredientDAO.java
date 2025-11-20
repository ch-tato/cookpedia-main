package com.cookpedia.dao;

import com.cookpedia.model.RecipeIngredient;
import com.cookpedia.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecipeIngredientDAO {
    private Connection conn;

    public RecipeIngredientDAO() {
        try {
            conn = DBConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<RecipeIngredient> getIngredientsByRecipeId(int recipeId) {
        List<RecipeIngredient> list = new ArrayList<>();
        String sql = "SELECT * FROM Recipe_Ingredient WHERE Recipe_RecipeID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, recipeId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RecipeIngredient ri = new RecipeIngredient();
                    ri.setRecipeId(rs.getInt("Recipe_RecipeID"));
                    ri.setIngredientId(rs.getInt("Ingredient_IngredientID"));
                    ri.setQuantity(rs.getString("Quantity"));
                    list.add(ri);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insertRecipeIngredient(int recipeId, int ingredientId, String qty) {
        String sql = "INSERT INTO Recipe_Ingredient (Recipe_RecipeID, Ingredient_IngredientID, Quantity) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, recipeId);
            ps.setInt(2, ingredientId);
            ps.setString(3, qty);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteIngredientsByRecipeId(int recipeId) {
        String sql = "DELETE FROM Recipe_Ingredient WHERE Recipe_RecipeID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, recipeId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
