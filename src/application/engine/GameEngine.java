package application.engine;

import application.dao.GameDatabaseManager;
import application.model.Card;
import application.model.LevelConfig;
import application.util.GameSettings;
import application.util.SoundUtil;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.io.File;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;

public class GameEngine {

    private int rows, cols  ;
    private String category;
    private List<Card> cards = new ArrayList<>();
    private Map<Card, Node> cardNodeMap;
    private Card firstCard = null;
    private boolean canFlip = true;

    private final ScoreManager scoreManager = new ScoreManager();
    private Consumer<Integer> onScoreChanged;
    private Consumer<Integer> onBonusGained;

    private List<String> mechanics ;

    private Runnable onGameWon;
    private Runnable onGameLost;


    private int pairsFlippedCount = 0;

    public GameEngine(LevelConfig config) {
        this.rows = config.rows;
        this.cols = config.cols;
        this.category = GameSettings.selectedCategory;
        this.mechanics = config.mechanics;
        GameSettings.score_add = config.score_add;
        GameSettings.score_punish = config.score_punish;
        GameSettings.coins_gains = config.coins_gain;
        System.out.println(config.coins_gain);
        initCards();
    }

    public GameEngine(int rows, int cols, String category, List<String> mechanics) {
        this.rows = rows;
        this.cols = cols;
        this.category = category;
        this.mechanics = mechanics;
        initCards(); // already exists in your class
    }


    public void setOnScoreChanged(Consumer<Integer> callback) {
        this.onScoreChanged = callback;
    }

    public void setOnBonusGained(Consumer<Integer> callback) {
        this.onBonusGained = callback;
    }
    
    public void setOnGameWon(Runnable callback) {
        this.onGameWon = callback;
    }

    public void setOnGameLost(Runnable callback) {
        this.onGameLost = callback;
    }

    public void setCanFlip(boolean value){
        this.canFlip = value;
    }
    public void setCardNodeMap(Map<Card, Node> map) {
        this.cardNodeMap = map;
    }


     public ScoreManager getScoreManager() {
        return scoreManager;
    }

    public boolean isCanFlip(){
        return canFlip;
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
        System.out.println(category);
        if(category == null){
            category = "animals";
        }
        String path = Paths.get(System.getProperty("user.dir"), "src/resources/assets/images", category).toString();

        System.err.println(path);
        File folder = new File(path);
        System.out.println("Folder: " + folder.getAbsolutePath());

        File[] files = folder.listFiles((dir, name) -> name.endsWith(".png") || name.endsWith(".jpg"));

        if (files == null || files.length == 0) {
            System.err.println("⚠️ No image files found in: " + path);
            return Collections.emptyList();
        }

        if (files != null) {
            List<File> fileList = Arrays.asList(files);
            Collections.shuffle(fileList);
            for (int i = 0; i < pairCount && i < fileList.size(); i++) {
                list.add(new Image(fileList.get(i).toURI().toString()));
            }
        }

        return list;
    }

    public void checkWinCondition() {
        boolean allMatched = cards.stream().allMatch(Card::isMatched);
        if (allMatched) {
            if (onGameWon != null) onGameWon.run();
        }
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

                 scoreManager.addCorrectFlip();
                if (onScoreChanged != null) onScoreChanged.accept(scoreManager.getTotalScore());
                if (onBonusGained != null) onBonusGained.accept(scoreManager.getBonus());

                // Delay and then check win
                PauseTransition pause = new PauseTransition(Duration.seconds(0.3));
                pause.setOnFinished(e -> {
                    canFlip = true;
                    firstCard = null;
                    checkWinCondition();  // ✅ Check if game is won here
                });
                pause.play();

                SoundUtil.play("flip-right.mp3");
                resetAfterPairFlip(true);

            } else {
                scoreManager.addWrongFlip();
                if (onScoreChanged != null)
                    onScoreChanged.accept(scoreManager.getTotalScore());

                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(e -> {
                    firstCard.unflip();
                    secondCard.unflip();
                    SoundUtil.play("flip-wrong.mp3");
                    resetAfterPairFlip(false);
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

        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(e -> {
            for (Card c : cards) {
                c.unflip();
            }
        });
        delay.play();
    }

    public void endGame(boolean won) {
        if (won && onGameWon != null) onGameWon.run();
        if (!won && onGameLost != null) onGameLost.run();
    }

    public boolean hasMechanic(String mechanicName){
        return mechanics != null && mechanics.contains(mechanicName);
    }

    private void resetAfterPairFlip(boolean matched) {
        pairsFlippedCount++;
        if (hasMechanic("swap") && pairsFlippedCount % 2 == 0) {
            swapRandomCards();
        }
        reset();
    }

    private void swapRandomCards() {
        List<Card> unmatchedCards = new ArrayList<>();
        for (Card c : cards) {
            if (!c.isMatched()) {
                unmatchedCards.add(c);
            }
        }

        if (unmatchedCards.size() < 2 || cardNodeMap == null) return;

        Collections.shuffle(unmatchedCards);

        Card card1 = unmatchedCards.get(0);
        Card card2 = unmatchedCards.get(1);

        Node node1 = cardNodeMap.get(card1);
        Node node2 = cardNodeMap.get(card2);

        if (node1 == null || node2 == null) return;

        // Store original positions
        double x1 = node1.getLayoutX();
        double y1 = node1.getLayoutY();
        double x2 = node2.getLayoutX();
        double y2 = node2.getLayoutY();

        // Animate the two cards swapping positions
        TranslateTransition anim1 = new TranslateTransition(Duration.seconds(0.4), node1);
        anim1.setToX(x2 - x1);
        anim1.setToY(y2 - y1);

        TranslateTransition anim2 = new TranslateTransition(Duration.seconds(0.4), node2);
        anim2.setToX(x1 - x2);
        anim2.setToY(y1 - y2);

        ParallelTransition parallel = new ParallelTransition(anim1, anim2);

        parallel.setOnFinished(e -> {
            // After animation: swap actual layout positions
            node1.setLayoutX(x2);
            node1.setLayoutY(y2);
            node2.setLayoutX(x1);
            node2.setLayoutY(y1);

            node1.setTranslateX(0);
            node1.setTranslateY(0);
            node2.setTranslateX(0);
            node2.setTranslateY(0);

            // Swap card images
            Image temp = card1.getImage();
            card1.setImage(card2.getImage());
            card2.setImage(temp);

            SoundUtil.play("swap-sound.mp3");
        });

        parallel.play();
    }
    public boolean useHint() {
        if (GameSettings.loggedInUser.getHints() <= 0) return false;

        for (int i = 0; i < cards.size(); i++) {
            Card a = cards.get(i);
            if (a.isMatched()) continue;
            for (int j = i + 1; j < cards.size(); j++) {
                Card b = cards.get(j);
                if (!b.isMatched() && a.matches(b)) {
                    a.flip(); b.flip();
                    PauseTransition delay = new PauseTransition(Duration.seconds(2));
                    delay.setOnFinished(e -> {
                        a.unflip(); b.unflip();
                    });
                    delay.play();

                    int newHintCount = GameSettings.loggedInUser.getHints() - 1;
                    GameSettings.loggedInUser.setHints(newHintCount);
                    GameDatabaseManager.updateCoinsAndHints(GameSettings.loggedInUser.getId(), GameSettings.loggedInUser.getCoins(), newHintCount);
                    return true;
                }
            }
        }
        return false;
    }


}
