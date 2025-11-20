package com.cookpedia.dao;

import com.cookpedia.model.RecipeVote;
import com.cookpedia.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecipeVoteDAO {
    private Connection conn;

    public RecipeVoteDAO() {
        try {
            conn = DBConnection.getConnection();
        } catch (SQLException e) { e.printStackTrace();  conn = null; }
    }

    public List<RecipeVote> getVotesByRecipeId(int recipeId) {
        List<RecipeVote> list = new ArrayList<>();
        String sql = "SELECT * FROM RecipeVote WHERE Recipe_RecipeID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, recipeId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RecipeVote v = new RecipeVote();
                    v.setId(rs.getInt("VoteID"));
                    v.setType(rs.getString("VoteType"));
                    v.setUserId(rs.getInt("User_UserID"));
                    v.setRecipeId(rs.getInt("Recipe_RecipeID"));
                    list.add(v);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public RecipeVote getVoteByUserAndRecipe(int userId, int recipeId) {
        String sql = "SELECT * FROM RecipeVote WHERE User_UserID=? AND Recipe_RecipeID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, recipeId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    RecipeVote v = new RecipeVote();
                    v.setId(rs.getInt("VoteID"));
                    v.setType(rs.getString("VoteType"));
                    v.setUserId(userId);
                    v.setRecipeId(recipeId);
                    return v;
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean insertVote(RecipeVote v) {
        String sql = "INSERT INTO RecipeVote (VoteType, User_UserID, Recipe_RecipeID) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, v.getType());
            ps.setInt(2, v.getUserId());
            ps.setInt(3, v.getRecipeId());
            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) v.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean updateVote(RecipeVote v) {
        String sql = "UPDATE RecipeVote SET VoteType=? WHERE VoteID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, v.getType());
            ps.setInt(2, v.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean deleteVote(int voteId) {
        String sql = "DELETE FROM RecipeVote WHERE VoteID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, voteId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public int countUpvotes(int recipeId) {
        if (conn == null) return 0;
        String sql = "SELECT COUNT(*) AS total FROM RecipeVote WHERE Recipe_RecipeID=? AND VoteType='Upvote'";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, recipeId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("total");
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public int countDownvotes(int recipeId) {
        if (conn == null) return 0;
        String sql = "SELECT COUNT(*) AS total FROM RecipeVote WHERE Recipe_RecipeID=? AND VoteType='Downvote'";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, recipeId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("total");
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }
}
