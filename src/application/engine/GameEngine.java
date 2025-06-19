package application.engine;

import application.model.Card;
import application.util.SoundUtil;
import javafx.animation.PauseTransition;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.io.File;
import java.util.*;
import java.util.function.Consumer;

public class GameEngine {

    private int rows, cols;
    private String category;
    private List<Card> cards = new ArrayList<>();
    private Card firstCard = null;
    private boolean canFlip = true;

    private int score = 0;
    private Consumer<Integer> onScoreChanged;

    public GameEngine(int rows, int cols, String category) {
        this.rows = rows;
        this.cols = cols;
        this.category = category;
        initCards();
    }

    public int getScore(){
        return score;
    }

    public void setOnScoreChanged(Consumer<Integer> callback){
        this.onScoreChanged = callback;
    }

    public List<Card> getCardList() {
        return cards;
    }

    private void initCards() {
        int totalCards = rows * cols;
        if (totalCards % 2 != 0) totalCards--; // must be even

        List<Image> images = loadImages(totalCards / 2);
        for (Image img : images) {
            cards.add(new Card(img));
            cards.add(new Card(img));
        }

        Collections.shuffle(cards);
    }

    private List<Image> loadImages(int pairCount) {
        List<Image> list = new ArrayList<>();
        File folder = new File("src/application/assets/images/" + category);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".png") || name.endsWith(".jpg"));

        if (files != null) {
            List<File> fileList = Arrays.asList(files);
            Collections.shuffle(fileList);

            for (int i = 0; i < pairCount && i < fileList.size(); i++) {
                list.add(new Image(fileList.get(i).toURI().toString()));
            }
        }

        return list;
    }

    public void flipCard(Card card) {
        if (!canFlip || card.isFlipped() || card.isMatched()) return;

        card.flip();

        if (firstCard == null) {
            firstCard = card;
        } else {
            canFlip = false;
            Card secondCard = card;

            if (firstCard.matches(secondCard)) {
                firstCard.setMatched(true);
                secondCard.setMatched(true);
                score += 10;
                if (onScoreChanged != null) onScoreChanged.accept(score);
                SoundUtil.play("flip-right.mp3");
                reset();
            } else {
                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(e -> {
                    firstCard.unflip();
                    secondCard.unflip();
                    SoundUtil.play("flip-wrong.mp3");
                    reset();
                });
                pause.play();
            }
        }
    }

    private void reset() {
        firstCard = null;
        canFlip = true;
    }

    public void peekAllCards() {
        for (Card c : cards) {
            c.flip();
        }

        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(e -> {
            for (Card c : cards) {
                c.unflip();
            }
        });
        delay.play();
    }

    public void endGame(boolean success) {
        //TODO
        // You can play sounds or trigger UI updates here.
        System.out.println("Game ended. Success = " + success);
    }
}
