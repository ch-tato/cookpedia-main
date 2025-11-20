package com.cookpedia.dao;

import  com.cookpedia.model.User;
import com.cookpedia.util.DBConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class UserDAO {
    public boolean registerUser(User user){
        String sql = "INSERT INTO User (Username, Email, Password) VALUES (?, ?, ?)";
        try(Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            return ps.executeUpdate()>0;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public User validateLogin(String email, String password){
        String sql = "SELECT * FROM User WHERE Email=?";
        try(Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String hashed = rs.getString("Password");
                if(BCrypt.checkpw(password, hashed)){
                    return new User(rs.getInt("UserID"),rs.getString("Username"),rs.getString("Email"),hashed);
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public User getUserByEmail(String email){
        User user = null;
        String sql = "SELECT * FROM User WHERE Email=?";
        try(Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                user = new User();
                user.setId(rs.getInt("UserID"));
                user.setUsername(rs.getString("Username"));
                user.setEmail(rs.getString("Email"));
                user.setPassword(rs.getString("Password"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return user;
    }
    public User getUserById(int id) {
        String sql = "SELECT * FROM User WHERE UserID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("UserID"),
                        rs.getString("Username"),
                        rs.getString("Email"),
                        rs.getString("Password")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isEmailUsed(String email){
        String sql = "SELECT UserID FROM User WHERE Email = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean isUsernameUsed(String username){
        String sql = "SELECT UserID FROM User WHERE Username = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean isEmailUsedByOthers(String email, int userId) {
        String sql = "SELECT UserID FROM User WHERE Email = ? AND UserID <> ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setInt(2, userId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isUsernameUsedByOthers(String username, int userId) {
        String sql = "SELECT UserID FROM User WHERE Username = ? AND UserID <> ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setInt(2, userId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateUserProfile(int userID, String newUsername, String newEmail){
        String sql = "UPDATE User SET Username=?, Email=? WHERE UserID=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, newUsername);
            ps.setString(2, newEmail);
            ps.setInt(3, userID);
            return ps.executeUpdate()>0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean changePassword(int userID, String currentPlain, String newPlain){
        String sqlGet = "SELECT Password FROM User WHERE UserID=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement psGet = conn.prepareStatement(sqlGet)){
            psGet.setInt(1, userID);
            ResultSet rs = psGet.executeQuery();
            if(rs.next()){
                String currentHash = rs.getString("Password");
                if(!BCrypt.checkpw(currentPlain, currentHash)){
                    return false;
                }
                String newHash = BCrypt.hashpw(newPlain, BCrypt.gensalt());
                String sqlUpdate = "UPDATE User SET Password=? WHERE UserID=?";
                try (PreparedStatement psUpd = conn.prepareStatement(sqlUpdate)){
                    psUpd.setString(1, newHash);
                    psUpd.setInt(2, userID);
                    return psUpd.executeUpdate()>0;
                }
            }
            return psGet.executeUpdate()>0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}