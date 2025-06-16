package application.model;

public class GameHistory {
    private int userId;
    private int level;
    private int score;
    private int stars;
    private String status;
    private String finishedTime;

    public GameHistory(int userId, int level, int score, int stars, String status, String finishedTime) {
        this.userId = userId;
        this.level = level;
        this.score = score;
        this.stars = stars;
        this.status = status;
        this.finishedTime = finishedTime;
    }

    public int getUserId() {
        return userId;
    }

    public int getLevel() {
        return level;
    }

    public int getScore() {
        return score;
    }

    public int getStars() {
        return stars;
    }

    public String getStatus() {
        return status;
    }

    public String getFinishedTime() {
        return finishedTime;
    }
}
