package application.controller;

import java.io.IOException;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.util.Duration;
import application.model.Card;
import application.util.GameSettings;
import application.engine.GameEngine;

public class GameMechanicController {

    @FXML private Button bttn_setting;
    @FXML private GridPane container;
    @FXML private Label label_time;
    @FXML private Label label_score;
    @FXML private Label lbl_number_hints;
    @FXML private Button bttn_use_hint;
  
    private Timeline timer;
    private int timeLeft = 120;

    private GameEngine gameEngine;



    public void initialize() {
        int level = GameSettings.selectedLevel;
        int rows = 2 + level;
        int cols = 2 + level;

        setupGrid(rows, cols);

        gameEngine = new GameEngine(rows, cols, GameSettings.selectedCategory);
        List<Card> cards = gameEngine.getCardList();

        int index = 0;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (index >= cards.size()) break;
                Card card = cards.get(index++);
                card.setOnMouseClicked(e -> {
                    gameEngine.flipCard(card);
                });
                container.add(card, col, row);
            }
        }
        gameEngine.setOnScoreChanged(newScore -> {
            label_score.setText("Score: " + newScore);
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

      @FXML
    void bttn_use_hint_action(ActionEvent event) {

    }
    // @FXML
    // void button_restart_action(ActionEvent event) {
    //     if(timer != null) { timer.stop();}

    //     // Reset score and time
    //     timeLeft = 120;
    //     label_score.setText("Score:");
    //     label_time.setText("120s");

    //     initialize();
    // }

    @FXML
    void bttn_setting_action(ActionEvent event) throws IOException {
        // show pause/settings menu
        Stage stage = (Stage) bttn_setting.getScene().getWindow();
        javafx.scene.Scene scene = new javafx.fxml.FXMLLoader(getClass().getResource("/application/fxml/Setting.fxml")).load();
        stage.setScene(scene);
        stage.setTitle("Memory Puzzle Game - Play");
        stage.getIcons().add(new Image(getClass().getResource("/application/assets/images/logo.png").toExternalForm()));
        stage.show();
    }
}
