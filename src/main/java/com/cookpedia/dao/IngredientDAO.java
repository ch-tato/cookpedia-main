package com.cookpedia.dao;

import com.cookpedia.model.DetailIngredient;
import com.cookpedia.model.Ingredient;
import com.cookpedia.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngredientDAO {
    private Connection conn;

    public IngredientDAO() {
        try {
            conn = DBConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Ingredient> getAllIngredients() {
        List<Ingredient> list = new ArrayList<>();
        String sql = "SELECT * FROM Ingredient";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Ingredient i = new Ingredient(
                        rs.getInt("IngredientID"),
                        rs.getInt("RecipeID"),
                        rs.getString("IngredientName"),
                        rs.getString("Unit")
                );
                i.setId(rs.getInt("IngredientID"));
                i.setRecipeId(rs.getInt("RecipeID"));
                i.setName(rs.getString("IngredientName"));
                i.setUnit(rs.getString("Unit"));
                list.add(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Ingredient getIngredientById(int id) {
        String sql = "SELECT * FROM Ingredient WHERE IngredientID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Ingredient i = new Ingredient(
                            rs.getInt("IngredientID"),
                            rs.getInt("RecipeID"),
                            rs.getString("IngredientName"),
                            rs.getString("Unit")
                    );
                    return i;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Ingredient getIngredientByName(String name) {
        String sql = "SELECT * FROM Ingredient WHERE IngredientName=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Ingredient i = new Ingredient(
                            rs.getInt("IngredientID"),
                            rs.getInt("RecipeID"),
                            rs.getString("IngredientName"),
                            rs.getString("Unit")
                    );
                    i.setId(rs.getInt("IngredientID"));
                    i.setName(rs.getString("IngredientName"));
                    i.setUnit(rs.getString("Unit"));
                    return i;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<DetailIngredient> getIngredientDetailsByRecipeId(int recipeId) {
        List<DetailIngredient> list = new ArrayList<>();

        String sql =
                "SELECT i.IngredientName, i.Unit, ri.Quantity " +
                        "FROM Recipe_Ingredient ri " +
                        "JOIN Ingredient i ON ri.Ingredient_IngredientID = i.IngredientID " +
                        "WHERE ri.Recipe_RecipeID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, recipeId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                DetailIngredient d = new DetailIngredient();
                d.setName(rs.getString("IngredientName"));
                d.setUnit(rs.getString("Unit"));
                d.setQuantity(rs.getString("Quantity"));
                list.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


    public int insertIngredient(String name, String unit) {
        Ingredient existing = getIngredientByName(name);
        if (existing != null) return existing.getId();


        String sql = "INSERT INTO Ingredient (IngredientName, Unit) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setString(2, unit);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) { e.printStackTrace();}

        return 0;
    }

    public boolean updateIngredient(Ingredient i) {
        String sql = "UPDATE Ingredient SET IngredientName=?, Unit=? WHERE IngredientID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, i.getName());
            ps.setString(2, i.getUnit());
            ps.setInt(3, i.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace();
        }
        return false;
    }

    public boolean deleteIngredient(int id) {
        String sql = "DELETE FROM Ingredient WHERE IngredientID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
}
