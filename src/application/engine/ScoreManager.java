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
        if (correctFlipStreak > bestFlipStreak) {
            bestFlipStreak = correctFlipStreak;
        }
        // Bonus logic
        if (correctFlipStreak == 2) {
            bonus += 200;
        } else if (correctFlipStreak > 2) {
            int lastBonus = 200 * (int)Math.pow(2, correctFlipStreak - 2);
            bonus += lastBonus;
        }
    }

    public void addWrongFlip() {
        rawScore = Math.max(0, rawScore - score_punish);
        correctFlipStreak = 0;

    }

    public void reset() {
        rawScore = 0;
        bonus = 0;
        finalScore =0;
        correctFlipStreak = 0;
        bestFlipStreak = 0;
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

    public void calculateFinalScoreWithTimeBonus(int timeLeft, int timeLimit) {
        timeUsed = timeLimit - timeLeft;
        timeMultiplier = (double) timeLeft / timeLimit;

        int base = getTotalScore();
        finalScore = base + (int)(base * timeMultiplier);

        // Stars
        if (finalScore >= 1000) stars = 3;
        else if (finalScore >= 800) stars = 2;
        else if (finalScore >= 500) stars = 1;
        else stars = 0;
    }
}
