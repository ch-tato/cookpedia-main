package com.cookpedia.dao;

import com.cookpedia.model.Step;
import com.cookpedia.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StepDAO {
    private Connection conn;
    public StepDAO() {
        try {
            conn = DBConnection.getConnection();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public List<Step> getStepsByRecipeId(int recipeId) {
        List<Step> list = new ArrayList<>();
        String sql = "SELECT * FROM Step WHERE Recipe_RecipeID=? ORDER BY StepNumber ASC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, recipeId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Step s = new Step();
                    s.setId(rs.getInt("StepID"));
                    s.setStepNumber(rs.getInt("StepNumber"));
                    s.setInstruction(rs.getString("Instruction"));
                    s.setRecipeId(rs.getInt("Recipe_RecipeID"));
                    list.add(s);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public void insertStep(int recipeId, int stepNumber, String instruction) {
        String sql = "INSERT INTO Step (Recipe_RecipeID, StepNumber, Instruction) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, recipeId);
            ps.setInt(2, stepNumber);
            ps.setString(3, instruction);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean updateStep(Step s) {
        String sql = "UPDATE Step SET StepNumber=?, Instruction=? WHERE StepID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, s.getStepNumber());
            ps.setString(2, s.getInstruction());
            ps.setInt(3, s.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean deleteStep(int stepId) {
        String sql = "DELETE FROM Step WHERE StepID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, stepId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
}
