package application.model;

public class GameHistoryEntry {
    private int level;
    private int score;
    private int bonus;
    private int finalScore;
    private int stars;
    private boolean win;
    private String datePlayed;
    private int flipStreak;
    private int time_completed;

    public GameHistoryEntry(int level, int score, int bonus, int finalScore, int stars, boolean win, String datePlayed, int flipStreak,int time_completed) {
        this.level = level;
        this.score = score;
        this.bonus = bonus;
        this.finalScore = finalScore;
        this.stars = stars;
        this.win = win;
        this.datePlayed = datePlayed;
        this.flipStreak = flipStreak;
        this.time_completed = time_completed;
    }

    // ✅ Getters
    public int getLevel() { return level; }
    public int getScore() { return score; }
    public int getBonus() { return bonus; }
    public int getFinalScore() { return finalScore; }
    public int getStars() { return stars; }
    public boolean isWin() { return win; }
    public String getDatePlayed() { return datePlayed; }
    public int getFlipStreak() { return flipStreak; }
    public int getTimeCompleted(){return time_completed;}
}
