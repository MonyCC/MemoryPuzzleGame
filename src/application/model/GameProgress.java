package application.model;

public class GameProgress {
    private int userId;
    private int currentLevel;
    private int currentScore;
    private int currentStars;
    private int timeRemaining;

    public GameProgress(int userId, int currentLevel, int currentScore, int currentStars, int timeRemaining) {
        this.userId = userId;
        this.currentLevel = currentLevel;
        this.currentScore = currentScore;
        this.currentStars = currentStars;
        this.timeRemaining = timeRemaining;
    }

    public int getUserId() {
        return userId;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public int getCurrentStars() {
        return currentStars;
    }

    public int getTimeRemaining() {
        return timeRemaining;
    }
}
