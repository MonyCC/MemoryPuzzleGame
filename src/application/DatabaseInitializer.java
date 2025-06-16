package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseInitializer {
    private static final String DB_URL = "jdbc:sqlite:game.db";

    public static void initialize() {
        String createUserTable = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT UNIQUE NOT NULL,
                password TEXT NOT NULL,
                photo_path TEXT
            );
        """;

        String createProgressTable = """
            CREATE TABLE IF NOT EXISTS game_progress (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER,
                level INTEGER,
                score INTEGER,
                stars INTEGER,
                time_limit INTEGER,
                status TEXT,
                FOREIGN KEY(user_id) REFERENCES users(id)
            );
        """;

        String createHistoryTable = """
            CREATE TABLE IF NOT EXISTS game_history (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER,
                level INTEGER,
                score INTEGER,
                stars INTEGER,
                status TEXT,
                finished_time TEXT,
                FOREIGN KEY(user_id) REFERENCES users(id)
            );
        """;

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(createUserTable);
            stmt.execute(createProgressTable);
            stmt.execute(createHistoryTable);
            System.out.println("Database initialized.");
        } catch (Exception e) {
            System.out.println("DB init failed: " + e.getMessage());
        }
    } 
}
