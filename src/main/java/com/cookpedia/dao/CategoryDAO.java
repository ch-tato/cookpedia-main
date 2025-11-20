package com.cookpedia.dao;
import com.cookpedia.model.Category;
import com.cookpedia.util.DBConnection;

import java.sql.*;
import java.util.*;

public class CategoryDAO {
    private Connection conn;

    public CategoryDAO() {
            try {
                conn = DBConnection.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public List<Category> getAllCategories() {
        List<Category> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM Category";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Category c = new Category();
                c.setId(rs.getInt("CategoryID"));
                c.setName(rs.getString("CategoryName"));
                c.setDescription(rs.getString("Description"));
                list.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Category getCategoryById(int id) {
        String sql = "SELECT * FROM Category WHERE CategoryID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Category c = new Category();
                    c.setId(rs.getInt("CategoryID"));
                    c.setName(rs.getString("CategoryName"));
                    c.setDescription(rs.getString("Description"));
                    return c;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertCategory(Category c) {
        String sql = "INSERT INTO Category (CategoryName, Description) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, c.getName());
            ps.setString(2, c.getDescription());
            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) c.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateCategory(Category c) {
        String sql = "UPDATE Category SET CategoryName=?, Description=? WHERE CategoryID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getName());
            ps.setString(2, c.getDescription());
            ps.setInt(3, c.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteCategory(int id) {
        String sql = "DELETE FROM Category WHERE CategoryID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<String> getAllCategoryNames() {
        List<String> categories = new ArrayList<>();
        String sql = "SELECT CategoryName FROM Category ORDER BY CategoryName ASC";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while(rs.next()) {
                categories.add(rs.getString("CategoryName"));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

}