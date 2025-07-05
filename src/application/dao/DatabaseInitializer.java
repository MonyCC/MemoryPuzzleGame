package application.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseInitializer {
    private static final String DB_URL = "jdbc:sqlite:db/game.db";

    public static void initialize() {
        String createUserTable = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT UNIQUE NOT NULL,
                password TEXT NOT NULL,
                coins INTEGER DEFAULT 0,
                hints INTEGER DEFAULT 3,
                photo_path TEXT,
                last_level_completed INTEGER DEFAULT 0,
                highest_score INTEGER DEFAULT 0,
                best_flip_sequence INTEGER DEFAULT 0 
            );
        """;

        String createGameHistoryTable = """
            CREATE TABLE IF NOT EXISTS game_history (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER,
                level INTEGER,
                score INTEGER,
                bonus INTEGER,
                time_completed INTEGER,
                final_score INTEGER,
                stars INTEGER,
                win BOOLEAN,
                date_played TEXT,
                flip_streak INTEGER,
                FOREIGN KEY (user_id) REFERENCES users(id)
            );
        """;

        try {
            Class.forName("org.sqlite.JDBC"); // load driver manually
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();
            stmt.execute(createUserTable);
            stmt.execute(createGameHistoryTable);
            System.out.println("Database initialized.");
        } catch (Exception e) {
            System.out.println("DB init failed: " + e.getMessage());
        }
    } 
}
