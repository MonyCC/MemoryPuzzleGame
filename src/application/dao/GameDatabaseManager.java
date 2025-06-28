// --- In GameResultDAO.java ---
package application.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.model.GameHistoryEntry;

public class GameDatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:db/game.db";

    public static void saveGameResult(int userId, int level, int score, int bonus, 
                                      int timeTaken,int finalScore, int stars, boolean win, int flipStreak) {
        String sql = """
            INSERT INTO game_history 
            (user_id, level, score, bonus, time_completed, final_score, stars, win, date_played, flip_streak)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, datetime('now', 'localtime'), ?)
        """;

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setInt(2, level);
            pstmt.setInt(3, score);
            pstmt.setInt(4, bonus);
            pstmt.setInt(5, timeTaken);
            pstmt.setInt(6, finalScore);
            pstmt.setInt(7, stars);
            pstmt.setBoolean(8, win);
            pstmt.setInt(9, flipStreak);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static double getWinRate(int userId) {
        String sql = """
            SELECT 
                COUNT(*) AS total_games,
                SUM(CASE WHEN win THEN 1 ELSE 0 END) AS total_wins
            FROM game_history
            WHERE user_id = ?
        """;

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            int totalGames = rs.getInt("total_games");
            int totalWins = rs.getInt("total_wins");

            if (totalGames == 0) return 0.0;
            return (totalWins * 100.0) / totalGames;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public static void updateUserStats(int userId, int finalScore, int level, int flipStreak) {
        String sql = """
            UPDATE users SET
                highest_score = CASE WHEN ? > highest_score THEN ? ELSE highest_score END,
                best_flip_sequence = CASE WHEN ? > best_flip_sequence THEN ? ELSE best_flip_sequence END,
                last_level_completed = CASE WHEN ? > last_level_completed THEN ? ELSE last_level_completed END
            WHERE id = ?
        """;

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, finalScore);
            pstmt.setInt(2, finalScore);
            pstmt.setInt(3, flipStreak);
            pstmt.setInt(4, flipStreak);
            pstmt.setInt(5, level);
            pstmt.setInt(6, level);
            pstmt.setInt(7, userId);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateCoinsAndHints(int userId, int coins, int hints) {
    String sql = "UPDATE users SET coins = ?, hints = ? WHERE id = ?";
    try (Connection conn = DriverManager.getConnection(DB_URL);
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setInt(1, coins);
        pstmt.setInt(2, hints);
        pstmt.setInt(3, userId);
        pstmt.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    public static Map<Integer, Integer> getScoreByLevel(int userId) {
        Map<Integer, Integer> map = new HashMap<>();
        String sql = "SELECT level, MAX(final_score) as max_score FROM game_history WHERE user_id = ? GROUP BY level";

        try (Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                map.put(rs.getInt("level"), rs.getInt("max_score"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static List<GameHistoryEntry> getGameHistory(int userId) {
        List<GameHistoryEntry> history = new ArrayList<>();

        String sql = """
            SELECT level, score, bonus, final_score, stars, win, date_played, flip_streak, time_completed
            FROM game_history
            WHERE user_id = ?
            ORDER BY date_played DESC
        """;

        try (Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                GameHistoryEntry entry = new GameHistoryEntry(
                    rs.getInt("level"),
                    rs.getInt("score"),
                    rs.getInt("bonus"),
                    rs.getInt("final_score"),
                    rs.getInt("stars"),
                    rs.getBoolean("win"),
                    rs.getString("date_played"),
                    rs.getInt("flip_streak"),
                    rs.getInt("time_completed") // ✅ ADD THIS
                );
                history.add(entry);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return history;
    }



}
