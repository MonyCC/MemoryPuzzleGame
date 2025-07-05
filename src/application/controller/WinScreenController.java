package application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import application.util.GameSettings;
import application.util.SoundUtil;

import java.io.IOException;

import application.dao.GameDatabaseManager;
import application.engine.ScoreManager;

public class WinScreenController {

    @FXML private Label labelScore;
    @FXML private Label labelBonus;
    @FXML private Label labelMultiplier;
    @FXML private Label labelFinalScore;
    @FXML private Label base_coins;
    @FXML private Label final_coins;

    @FXML private ImageView star1;
    @FXML private ImageView star2;
    @FXML private ImageView star3;

    private final Image filledStar = new Image(GameSettings.getResourcePath("src/resources/assets/images/star-filled.png"));
    private final Image emptyStar = new Image(GameSettings.getResourcePath("src/resources/assets/images/star-empty.png"));

    @FXML
    public void initialize() {
        ScoreManager scoreManager = GameSettings.lastScore;

        int score = scoreManager.getRawScore();
        int bonus = scoreManager.getBonus();
        double multiplier = scoreManager.getTimeMultiplier();
        int finalScore = scoreManager.getFinalScore();

        System.out.println("score :" + score  +"bonus: " + bonus +" x"+ multiplier + "=> "+ finalScore);
        labelScore.setText("Total Score: " + score);
        labelBonus.setText("Total Bonus: " + bonus);

        System.out.println(GameSettings.coins_gains);
        labelMultiplier.setText(String.format("Time Multiplier: x%.2f", multiplier));
        base_coins.setText("Coins : " + GameSettings.coins_gains + "💰");
        labelFinalScore.setText("Final Score: " + finalScore);
        int final_coin = GameSettings.coins_gains + (int)( GameSettings.coins_gains * multiplier);
        final_coins.setText("Total Coins: " + final_coin + "💰");

        
        updateStars(finalScore);
        coinEarn(final_coin);
    }

    private void updateStars(int finalScore) {
        int starCount;
        if (finalScore >= 1000) starCount = 3;
        else if (finalScore >= 800) starCount = 2;
        else if (finalScore >= 500) starCount = 1;
        else starCount = 0;

        star1.setImage(starCount >= 1 ? filledStar : emptyStar);
        star2.setImage(starCount >= 2 ? filledStar : emptyStar);
        star3.setImage(starCount >= 3 ? filledStar : emptyStar);
    }

    private void coinEarn(int numCoin){
        int newCoinTotal = GameSettings.loggedInUser.getCoins() + numCoin;
        GameSettings.loggedInUser.setCoins(newCoinTotal);

    // Save to DB
        GameDatabaseManager.updateCoinsAndHints(
        GameSettings.loggedInUser.getId(),
        newCoinTotal,
        GameSettings.loggedInUser.getHints()
    );
    }
    @FXML
    void handlePlayAgain(ActionEvent event) {
          try {
            Stage stage = (Stage) labelBonus.getScene().getWindow();
            String path = "/application/fxml/Level"+GameSettings.selectedLevel+".fxml";
            javafx.scene.Scene scene = new javafx.fxml.FXMLLoader(getClass().getResource(path)).load();
            stage.setScene(scene);
            stage.setTitle("Memory Puzzle Game - Level "+ GameSettings.selectedLevel);
            stage.getIcons().add(new javafx.scene.image.Image(GameSettings.getLogoPath()));
            stage.show();
        } catch (Exception e) {
             e.printStackTrace();
        }
    }

    @FXML
    void handleBackToMenu(ActionEvent event) {
        try {
                Stage stage = (Stage) labelBonus.getScene().getWindow();
                javafx.scene.Scene scene = new javafx.fxml.FXMLLoader(getClass().getResource(GameSettings.HOME)).load();
                stage.setScene(scene);
                stage.setTitle("Memory Puzzle Game - Home");
                  stage.getIcons().add(new javafx.scene.image.Image(GameSettings.getLogoPath()));
                stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
void nextLevel(ActionEvent e) {
    int next = GameSettings.selectedLevel + 1;
    SoundUtil.play("level-up.mp3");
    if (GameSettings.LEVEL_SCENES.containsKey(next)) {
        GameSettings.selectedLevel = next;
        try {
                Stage stage = (Stage) labelBonus.getScene().getWindow();
                javafx.scene.Scene scene = new javafx.fxml.FXMLLoader(getClass().getResource(GameSettings.LEVEL_SCENES.get(next))).load();
                stage.setScene(scene);
                stage.setTitle("Memory Puzzle Game - Level " + next);
                  stage.getIcons().add(new javafx.scene.image.Image(GameSettings.getLogoPath()));
                stage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    } else {
        // optional: go back to level selection if no more levels
        handlePlayAgain(e);
    }
}

}
