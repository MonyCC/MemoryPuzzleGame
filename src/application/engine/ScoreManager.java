package application.engine;

import application.util.GameSettings;

public class ScoreManager {
    private int rawScore = 0;
    private int bonus = 0;
    private int timeUsed = 0;
    private int finalScore = 0;
    private int stars = 0;
    private double timeMultiplier =0.0;
    private int score_add = GameSettings.score_add;
    private int score_punish = GameSettings.score_punish;

    private int correctFlipStreak = 0;
    private int bestFlipStreak = 0;

    public void addCorrectFlip() {
        rawScore += score_add;
        correctFlipStreak++;

        correctFlipStreak++;
        if (correctFlipStreak > bestFlipStreak) {
            bestFlipStreak = correctFlipStreak;
        }
        // Bonus system: on 2nd right flip, +200 bonus, 3rd+ multiplies last bonus
        if (correctFlipStreak  == 2) {
            bonus += 200;
        } else if (correctFlipStreak  > 2) {
            bonus *= 2;
        }
    }

    public void addWrongFlip() {
        rawScore = Math.max(0, rawScore - score_punish);
        correctFlipStreak = 0;
        bonus = 0;
    }

    public void reset() {
        rawScore = 0;
        bonus = 0;
        correctFlipStreak = 0;
        finalScore = 0;
        timeMultiplier = 1.0;
    }

    //getter

    public int getTotalScore() { return rawScore + bonus;}
    public int getRawScore()         { return rawScore; }
    public int getBonus()            { return bonus; }
    public int getFinalScore()       { return finalScore; }
    public double getTimeMultiplier(){ return timeMultiplier; }
    public int getStars()            { return stars; }
    public int getTimeUsed()         { return timeUsed; }
    public int getBestFlipStreak()   { return bestFlipStreak; }

    // Used when game is won and timer still has time
    public void calculateFinalScoreWithTimeBonus(int timeLeft, int timeLimit) {
        timeUsed = timeLimit - timeLeft;
        timeMultiplier = (timeLeft * 1.0) / timeLimit;
        finalScore = (int) (rawScore + bonus + (rawScore + bonus) * timeMultiplier);
        // Stars logic based on final score
        if (finalScore >= 1000) stars = 3;
        else if (finalScore >= 800) stars = 2;
        else if (finalScore >= 500) stars = 1;
        else stars = 0;
    }
    public void finalizeScore(int timeLeft, int timeLimit) {
        int base = getTotalScore();
        this.timeMultiplier = (double) timeLeft / timeLimit;
        this.finalScore = base + (int)(base * timeMultiplier);
    }

}
