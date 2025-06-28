package application.controller;

import application.engine.GameEngine;
import application.model.Card;
import application.util.GameSettings;
import application.util.SoundUtil;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PvPGameController {

    @FXML private GridPane cardGrid;
    @FXML private Label statusLabel;
    @FXML private Label turnLabel;
    @FXML private ProgressBar progressBar;
    @FXML private Button btnBack;

    private GameEngine engine;
    private boolean isPlayerOneTurn = true;
    private int scoreP1 = 0;
    private int scoreP2 = 0;

    private Card selected1 = null;
    private Card selected2 = null;
    private boolean canClick = true;

    @FXML
    public void initialize() {
        System.out.println("error");
        int rows = GameSettings.rows;
        int cols = GameSettings.cols;
        boolean swap = GameSettings.pvpSwap;

        engine = new GameEngine(rows, cols, "animals", swap ? List.of("swap") : List.of());
        List<Card> cards = engine.getCardList();

        setupGrid(rows, cols, cards);

        updateTurnLabel();

        engine.setOnGameWon(this::onGameEnd);

        btnBack.setOnAction(e -> goBack());
    }

    private void setupGrid(int rows, int cols, List<Card> cards) {
        cardGrid.getChildren().clear();
        Map<Card, javafx.scene.Node> map = new HashMap<>();

        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Card card = cards.get(index++);
                card.setOnMouseClicked(e -> handleCardClick(card));
                cardGrid.add(card.getView(), j, i);
                map.put(card, card.getView());
            }
        }

        engine.setCardNodeMap(map);
    }

    private void handleCardClick(Card card) {
        if (!canClick || card.isFlipped() || card.isMatched()) return;

        card.flip();
        SoundUtil.play("flipcard.mp3");;

        if (selected1 == null) {
            selected1 = card;
        } else {
            selected2 = card;
            canClick = false;

            PauseTransition pause = new PauseTransition(Duration.seconds(0.8));
            pause.setOnFinished(e -> checkMatch());
            pause.play();
        }
    }

    private void checkMatch() {
        if (selected1.matches(selected2)) {
            selected1.setMatched(true);
            selected2.setMatched(true);
            SoundUtil.play("flip-right.mp3");

            if (isPlayerOneTurn) scoreP1 += 100;
            else scoreP2 += 100;

            engine.getScoreManager().addCorrectFlip(); // internal tracking
            updateTurnLabel();

            PauseTransition delay = new PauseTransition(Duration.seconds(0.3));
            delay.setOnFinished(e -> {
                canClick = true;
                selected1 = null;
                selected2 = null;
                engine.checkWinCondition(); // reused
            });
            delay.play();
        } else {
            engine.getScoreManager().addWrongFlip(); // for accuracy if needed
            updateTurnLabel();

            PauseTransition delay = new PauseTransition(Duration.seconds(1));
            delay.setOnFinished(e -> {
                selected1.unflip();
                selected2.unflip();
                SoundUtil.play("flip-wrong.mp3");;
                selected1 = null;
                selected2 = null;
                canClick = true;
                switchTurn();
            });
            delay.play();
        }
    }

    private void switchTurn() {
        isPlayerOneTurn = !isPlayerOneTurn;
        updateTurnLabel();
    }

    private void updateTurnLabel() {
        turnLabel.setText("Turn: Player " + (isPlayerOneTurn ? "1" : "2") +
                " | P1: " + scoreP1 + " pts  -  P2: " + scoreP2 + " pts");
    }

    private void onGameEnd() {
        String winner;
        if (scoreP1 > scoreP2) winner = "🎉 Player 1 Wins!";
        else if (scoreP2 > scoreP1) winner = "🎉 Player 2 Wins!";
        else winner = "🤝 It's a Tie!";
        statusLabel.setText(winner);
        canClick = false;
    }

    private void goBack() {
        try {
            Stage stage = (Stage) turnLabel.getScene().getWindow();
            javafx.scene.Scene scene = new javafx.fxml.FXMLLoader(getClass().getResource(GameSettings.HOME)).load();
            stage.setScene(scene);
            stage.setTitle("Memory Puzzle Game - HOME");
            stage.getIcons().add(new javafx.scene.image.Image(GameSettings.getLogoPath()));
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
