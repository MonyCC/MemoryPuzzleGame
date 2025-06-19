package application.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class GameProgressDAO {
    private static final String DB_URL = "jdbc:sqlite:game.db";

    public void saveProgress(String username, int level, int stars, int bonus) {
    
    try (Connection conn = DriverManager.getConnection(DB_URL)) {
        String sql = "INSERT OR REPLACE INTO progress (username, level, stars, bonus) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, username);
        ps.setInt(2, level);
        ps.setInt(3, stars);
        ps.setInt(4, bonus);
        ps.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}
