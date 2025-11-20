package com.cookpedia.dao;

import com.cookpedia.model.Recipe;
import com.cookpedia.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecipeDAO {
    private Connection conn;

    public RecipeDAO() throws SQLException {
        conn = DBConnection.getConnection();
    }

    public int insertRecipe(Recipe recipe) {
        int generatedId = 0;
        String sql = "INSERT INTO Recipe (RecipeName, Description, CookingTime, DifficultyLevel, ImageURL, User_UserID, Category_CategoryID) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, recipe.getName());
            stmt.setString(2, recipe.getDescription());
            stmt.setInt(3, recipe.getCookingTime());
            stmt.setString(4, recipe.getDifficulty());
            stmt.setString(5, recipe.getImageUrl());
            stmt.setInt(6, recipe.getUserId());
            stmt.setInt(7, recipe.getCategoryId());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating recipe failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating recipe failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }

    public List<Recipe> getAllRecipes() {
        List<Recipe> list = new ArrayList<>();
        String sql = "SELECT * FROM Recipe";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Recipe r = new Recipe();
                r.setId(rs.getInt("RecipeID"));
                r.setName(rs.getString("RecipeName"));
                r.setDescription(rs.getString("Description"));
                r.setCookingTime(rs.getInt("CookingTime"));
                r.setDifficulty(rs.getString("DifficultyLevel"));
                r.setImageUrl(rs.getString("ImageURL"));
                r.setUserId(rs.getInt("User_UserID"));
                r.setCategoryId(rs.getInt("Category_CategoryID"));
                list.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Recipe getRecipeById(int id) {
        String sql = "SELECT * FROM Recipe WHERE RecipeID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Recipe r = new Recipe();
                    r.setId(rs.getInt("RecipeID"));
                    r.setName(rs.getString("RecipeName"));
                    r.setDescription(rs.getString("Description"));
                    r.setCookingTime(rs.getInt("CookingTime"));
                    r.setDifficulty(rs.getString("DifficultyLevel"));
                    r.setImageUrl(rs.getString("ImageURL"));
                    r.setUserId(rs.getInt("User_UserID"));
                    r.setCategoryId(rs.getInt("Category_CategoryID"));
                    return r;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<Recipe> getRecipesByUserId(int userId) {
        List<Recipe> list = new ArrayList<>();
        String sql = "SELECT * FROM Recipe WHERE User_UserID=? ORDER BY RecipeID DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    Recipe r = new Recipe();
                    r.setId(rs.getInt("RecipeID"));
                    r.setName(rs.getString("RecipeName"));
                    r.setDescription(rs.getString("Description"));
                    r.setCookingTime(rs.getInt("CookingTime"));
                    r.setDifficulty(rs.getString("DifficultyLevel"));
                    r.setImageUrl(rs.getString("ImageURL"));
                    r.setCategoryId(rs.getInt("Category_CategoryID"));
                    r.setUserId(rs.getInt("User_UserID"));
                    list.add(r);
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public boolean updateRecipe(Recipe r) {
        String sql = "UPDATE Recipe SET RecipeName=?, Description=?, CookingTime=?, DifficultyLevel=?, ImageURL=?, User_UserID=?, Category_CategoryID=? WHERE RecipeID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, r.getName());
            ps.setString(2, r.getDescription());
            ps.setInt(3, r.getCookingTime());
            ps.setString(4, r.getDifficulty());
            ps.setString(5, r.getImageUrl());
            ps.setInt(6, r.getUserId());
            ps.setInt(7, r.getCategoryId());
            ps.setInt(8, r.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteRecipe(int id) {
        String sql = "DELETE FROM Recipe WHERE RecipeID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public int getTotalUpvotes(int recipeId) {
        RecipeVoteDAO vdao = new RecipeVoteDAO();
        return vdao.countUpvotes(recipeId);
    }

    public int getTotalDownvotes(int recipeId) {
        RecipeVoteDAO vdao = new RecipeVoteDAO();
        return vdao.countDownvotes(recipeId);
    }

    public List<Recipe> searchRecipesByTitle(String keyword) {
        List<Recipe> list = new ArrayList<>();
        String sql = "SELECT * FROM Recipe WHERE RecipeName LIKE ? ORDER BY RecipeID DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    Recipe r = new Recipe();
                    r.setId(rs.getInt("RecipeID"));
                    r.setName(rs.getString("RecipeName"));
                    r.setDescription(rs.getString("Description"));
                    r.setCookingTime(rs.getInt("CookingTime"));
                    r.setDifficulty(rs.getString("DifficultyLevel"));
                    r.setImageUrl(rs.getString("ImageURL"));
                    r.setCategoryId(rs.getInt("Category_CategoryID"));
                    r.setUserId(rs.getInt("User_UserID"));
                    list.add(r);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
