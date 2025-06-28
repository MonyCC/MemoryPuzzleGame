package application.dao;

import application.model.LeaderboardEntry;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardDAO {
    private static final String DB_URL = "jdbc:sqlite:db/game.db";

    public static List<LeaderboardEntry> getTopScores() {
        String sql = """
            SELECT username, highest_score 
            FROM users 
            ORDER BY highest_score DESC 
            LIMIT 10
        """;

        return fetchLeaderboard(sql, "highest_score");
    }

    public static List<LeaderboardEntry> getBestFlipSequences() {
        String sql = """
            SELECT username, best_flip_sequence 
            FROM users 
            ORDER BY best_flip_sequence DESC 
            LIMIT 10
        """;

        return fetchLeaderboard(sql, "best_flip_sequence");
    }

    public static List<LeaderboardEntry> getTopWinRates() {
        String sql = """
            SELECT u.username,
                   ROUND(
                       (SELECT COUNT(*) FROM game_history g WHERE g.user_id = u.id AND win = 1) * 100.0 /
                       MAX((SELECT COUNT(*) FROM game_history g WHERE g.user_id = u.id), 1), 2
                   ) AS win_rate
            FROM users u
            ORDER BY win_rate DESC
            LIMIT 10
        """;

        return fetchLeaderboard(sql, "win_rate");
    }

    private static List<LeaderboardEntry> fetchLeaderboard(String query, String column) {
        List<LeaderboardEntry> entries = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String username = rs.getString("username");
                String value = rs.getString(column);
                entries.add(new LeaderboardEntry(username, value));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entries;
    }
}
