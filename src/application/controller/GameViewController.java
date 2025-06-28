package application.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import application.model.Card;
import application.model.LevelConfig;
import application.util.GameSettings;
import application.util.JSONUtil;
import application.util.SoundUtil;
import application.dao.GameDatabaseManager;
import application.engine.GameEngine;
import application.engine.ScoreManager;

public class GameViewController {

    @FXML private Button bttn_setting;
    @FXML private GridPane container;
    @FXML private Label label_time;
    @FXML private Label label_score;
    @FXML private Label lbl_number_hints;
    @FXML private Button bttn_use_hint;
    @FXML private Label label_bonus_popup;
    @FXML private AnchorPane pauseOverlay;
    @FXML private StackPane root_pane;
    @FXML private Button toggleMusicButton;
    @FXML private Slider volumeSlider;

  
    private Timeline timer;
    private int timeLeft = 0;

    private final ScoreManager scoreManager = new ScoreManager();

    int prevousBonus = scoreManager.getBonus();


    private GameEngine gameEngine;

    private Map<Card, javafx.scene.Node> cardNodeMap = new HashMap<>();

    public void initialize() {
        LevelConfig config = JSONUtil.getLevelConfig(GameSettings.selectedLevel);
        GameSettings.rows = config.rows;
        GameSettings.cols = config.cols;
        GameSettings.score_add = config.score_add;
        GameSettings.score_punish = config.score_punish;
        setupGrid(config.rows, config.cols);
        timeLeft = config.timeLimit;

        System.out.println(GameSettings.selectedCategory);

        gameEngine = new GameEngine(config);
        gameEngine.setCardNodeMap(cardNodeMap);
        List<Card> cards = gameEngine.getCardList();

        volumeSlider.setValue(SoundUtil.getVolume());
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
                SoundUtil.setVolume(newVal.doubleValue());
        });

        toggleMusicButton.setText(SoundUtil.isMuted() ? "Music: OFF" : "Music: ON");

        int index = 0;
        for (int row = 0; row < config.rows; row++) {
            for (int col = 0; col < config.cols; col++) {
                if (index >= cards.size()) break;
                Card card = cards.get(index++);
                card.setOnMouseClicked(e -> {
                    gameEngine.flipCard(card);
                });
                container.add(card, col, row);
                cardNodeMap.put(card, card);
            }
        }
        gameEngine.setOnScoreChanged(newScore -> {
            label_score.setText("Score: " + newScore);
        });
        gameEngine.setOnBonusGained(bonus ->{
            showBonusPopup(bonus);
        });
        

        gameEngine.setOnGameWon(() -> {
            timer.stop();
            ScoreManager scoreManager = gameEngine.getScoreManager();
            scoreManager.calculateFinalScoreWithTimeBonus(timeLeft, config.timeLimit);
            GameSettings.lastScore = scoreManager;
            GameSettings.lastWin = true;
            updateDatabase(scoreManager);
            loadWinScreen();
        });

        gameEngine.setOnGameLost(() -> {
            timer.stop();
            ScoreManager scoreManager = gameEngine.getScoreManager();
            scoreManager.calculateFinalScoreWithTimeBonus(timeLeft, config.timeLimit);
            GameSettings.lastWin = false;
            GameSettings.lastScore = scoreManager;
            updateDatabase(scoreManager);
            loadLoseScreen();
        });

        gameEngine.peekAllCards();
        startTimer();

    }

    private void setupGrid(int rows, int cols) {
        container.getChildren().clear();
        container.getRowConstraints().clear();
        container.getColumnConstraints().clear();

        for (int i = 0; i < cols; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(100.0 / cols);
            container.getColumnConstraints().add(col);
        }

        for (int i = 0; i < rows; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(100.0 / rows);
            container.getRowConstraints().add(row);
        }
    }

    private void startTimer() {
        
        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeLeft--;
            label_time.setText(timeLeft + "s");
            if (timeLeft <= 0) {
                timer.stop();
                gameEngine.endGame(false);
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }
    private void showBonusPopup(int bonusAmount) {
        label_bonus_popup.setText("+ " + bonusAmount + " Bonus!");
        label_bonus_popup.setVisible(true);
        
        FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), label_bonus_popup);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        
        TranslateTransition slideUp = new TranslateTransition(Duration.millis(1500), label_bonus_popup);
        slideUp.setByY(-40);
        FadeTransition fadeOut = new FadeTransition(Duration.millis(1000), label_bonus_popup);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setDelay(Duration.seconds(0.6));

        ParallelTransition animation = new ParallelTransition(fadeIn, slideUp, fadeOut);
        animation.setOnFinished(e -> {
            label_bonus_popup.setVisible(false);
            label_bonus_popup.setTranslateY(0); // Reset position
        });
        
        animation.play();
    }


      @FXML
    void bttn_use_hint_action(ActionEvent event) {
        boolean success = gameEngine.useHint();
        if (success) {
            lbl_number_hints.setText("Hints: " + GameSettings.loggedInUser.getHints());
        } else {
            lbl_number_hints.setText("No hints left!");
        }
    }

    
    @FXML
    void bttn_setting_action(ActionEvent event) throws IOException {
       if (timer != null) timer.pause();
        gameEngine.setCanFlip(false);
        pauseOverlay.setVisible(true);
    }

    private void loadWinScreen() {
        try {
            Stage stage = (Stage) bttn_setting.getScene().getWindow();
            javafx.scene.Scene scene = new javafx.fxml.FXMLLoader(getClass().getResource(GameSettings.WIN)).load();
            stage.setScene(scene);
            stage.setTitle("Memory Puzzle Game - Win ");
            stage.getIcons().add(new javafx.scene.image.Image(GameSettings.getLogoPath()));
            stage.show();
        } catch (Exception e) {
             e.printStackTrace();
        }
    }

    private void loadLoseScreen() {
        try {
                Stage stage = (Stage) bttn_setting.getScene().getWindow();
                javafx.scene.Scene scene = new javafx.fxml.FXMLLoader(getClass().getResource(GameSettings.LOSE)).load();
                stage.setScene(scene);
                stage.setTitle("Memory Puzzle Game - Lose ");
                stage.getIcons().add(new javafx.scene.image.Image(GameSettings.getLogoPath()));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
        }
    }

    @FXML
    void resumeGame(ActionEvent e) {
        pauseOverlay.setVisible(false);
        gameEngine.setCanFlip(true);
        if (timer != null) timer.play();
    }

    @FXML
    void restart_level(ActionEvent e) {
        pauseOverlay.setVisible(false);
        if (timer != null) timer.stop();
        // reload the same level scene
        try {
            Stage stage = (Stage) label_bonus_popup.getScene().getWindow();
            javafx.scene.Scene scene = new javafx.fxml.FXMLLoader(getClass().getResource(GameSettings.LEVEL_SCENES.get(GameSettings.selectedLevel))).load();
            stage.setScene(scene);
            stage.setTitle("Memory Puzzle Game - Level "+ GameSettings.selectedLevel);
            stage.getIcons().add(new javafx.scene.image.Image(GameSettings.getLogoPath()));
            stage.show();
        } catch (Exception ex) {
             ex.printStackTrace();
        }
    }
    
    @FXML
    void back_to_home(ActionEvent e) {
        try {
                Stage stage = (Stage) label_bonus_popup.getScene().getWindow();
                javafx.scene.Scene scene = new javafx.fxml.FXMLLoader(getClass().getResource(GameSettings.HOME)).load();
                stage.setScene(scene);
                stage.setTitle("Memory Puzzle Game - Home");
                  stage.getIcons().add(new javafx.scene.image.Image(GameSettings.getLogoPath()));
                stage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @FXML
    void toggleMusic(ActionEvent event) {
        boolean isMuted = SoundUtil.isMuted();
        SoundUtil.setMuted(!isMuted);
        toggleMusicButton.setText(isMuted ? "Music: ON" : "Music: OFF");
    }

    void updateDatabase(ScoreManager scoreManager){
        GameDatabaseManager.saveGameResult(
            GameSettings.loggedInUser.getId(),
            GameSettings.selectedLevel,
            scoreManager.getRawScore(),
            scoreManager.getBonus(),
            scoreManager.getTimeUsed(),
            scoreManager.getFinalScore(),
            scoreManager.getStars(),
            GameSettings.lastWin,
            scoreManager.getBestFlipStreak()
        );

        // Then update summary stats:
            GameDatabaseManager.updateUserStats(
            GameSettings.loggedInUser.getId(),
            scoreManager.getFinalScore(),
            GameSettings.selectedLevel,
            scoreManager.getBestFlipStreak()
        );
    }
}
