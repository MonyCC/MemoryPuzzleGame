package application.model;

import application.util.GameSettings;
import application.util.SoundUtil;
import javafx.animation.RotateTransition;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

    public class Card extends StackPane {
    private final ImageView front;
    private final ImageView back;
    private boolean flipped = false;
    private boolean matched = false;
    

    public Card(Image frontImage) {
        front = new ImageView(frontImage);
        back = new ImageView(new Image(GameSettings.getResourcePath("src/resources/assets/images/back-card.png")));
        setCardSize(front);
        setCardSize(back);

        getChildren().addAll(back, front);
        front.setVisible(false);

        setOnMouseClicked(e -> {
            if(!matched && !flipped){
                flip();
            }
            });
        getStyleClass().add("card");
    }

    private void setCardSize(ImageView view) {
        view.setFitWidth((200)/(GameSettings.rows - 1));
        System.out.println((200)/(GameSettings.rows - 1));
        view.setFitHeight((300) / (GameSettings.cols -2));
        view.setPreserveRatio(true);
    }
    public void unflip() {
        flipped = false;
        front.setVisible(false);
        back.setVisible(true);
    }
    public boolean matches(Card other) {
        return this.getFrontImage().getUrl().equals(other.getFrontImage().getUrl());
    }


    public boolean isFlipped() {
        return flipped;
    }

    public Image getFrontImage() {
        return front.getImage();
    }

     public Image getImage() {
        return front.getImage();
    }

    public void setImage(Image image) {
        front.setImage(image);
    }


     public void setMatched(boolean matched) {
        this.matched = matched;
        if (matched) {
            setDisable(true); // prevent further interaction
        }
    }
    public boolean isMatched() {
        return matched;
    }


    public void flip() {
        if (flipped) return;
        flipped = true;
        SoundUtil.play("flipcard.mp3");

        RotateTransition rotateOut = new RotateTransition(Duration.millis(150), this);
        rotateOut.setAxis(Rotate.Y_AXIS);
        rotateOut.setFromAngle(0);
        rotateOut.setToAngle(90);

        RotateTransition rotateIn = new RotateTransition(Duration.millis(150), this);
        rotateIn.setAxis(Rotate.Y_AXIS);
        rotateIn.setFromAngle(90);
        rotateIn.setToAngle(0);

        rotateOut.setOnFinished(e -> {
            front.setVisible(true);
            back.setVisible(false);
            rotateIn.play();
        });

        rotateOut.play();
    }

     // New toggleImage method - flips card visibility instantly (no animation)
    public void toggleImage() {
        if (flipped) {
            flipped = false;
            front.setVisible(false);
            back.setVisible(true);
        } else {
            flipped = true;
            front.setVisible(true);
            back.setVisible(false);
        }
    }
    public Node getView() {
        return this;
    }
}
