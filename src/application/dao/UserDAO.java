package application.dao;
import application.model.User;
import java.sql.*;

public class UserDAO {
    private static final String DB_URL = "jdbc:sqlite:db/game.db";

    public static boolean register(User user) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "INSERT INTO users (username, password, photo_path) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPasswordHash());
            stmt.setString(3, user.getPhotoPath());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static User login(String username, String passwordHash) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, User.hashPassword(passwordHash)); // secure input password
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("photo_path"),
                    rs.getInt("coins"),
                    rs.getInt("hints"),
                    rs.getInt("last_level_completed"),
                    rs.getInt("highest_score"),
                    rs.getInt("best_flip_sequence")
                    
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static User UpdateStatus(String username) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT * FROM users WHERE username = ? ";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("photo_path"),
                    rs.getInt("coins"),
                    rs.getInt("hints"),
                    rs.getInt("last_level_completed"),
                    rs.getInt("highest_score"),
                    rs.getInt("best_flip_sequence")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void updatePhotoPath(int userId, String path) {
        String sql = "UPDATE users SET photo_path = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, path);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isUsernameTaken(String username) {
        String sql = "SELECT id FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // if found, taken
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void updateUsername(int userId, String newUsername) {
        String sql = "UPDATE users SET username = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newUsername);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get user's current coin balance
    public static int getUserCoins(String username) {
        String sql = "SELECT coins FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("coins");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static boolean updateCoins(String username, int deltaCoins) {
        String getSql = "SELECT coins FROM users WHERE username = ?";
        String updateSql = "UPDATE users SET coins = ? WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement getStmt = conn.prepareStatement(getSql);
            PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {

            getStmt.setString(1, username);
            ResultSet rs = getStmt.executeQuery();

            if (rs.next()) {
                int currentCoins = rs.getInt("coins");
                int updatedCoins = currentCoins + deltaCoins;

                // Prevent negative coins
                if (updatedCoins < 0) return false;

                updateStmt.setInt(1, updatedCoins);
                updateStmt.setString(2, username);
                return updateStmt.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean updateHints(String username, int deltaHints) {
        String getSql = "SELECT hints FROM users WHERE username = ?";
        String updateSql = "UPDATE users SET hints = ? WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement getStmt = conn.prepareStatement(getSql);
            PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {

            getStmt.setString(1, username);
            ResultSet rs = getStmt.executeQuery();

            if (rs.next()) {
                int currentHints = rs.getInt("hints");
                int updatedHints = currentHints + deltaHints;

                if (updatedHints < 0) return false;

                updateStmt.setInt(1, updatedHints);
                updateStmt.setString(2, username);
                return updateStmt.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


}
