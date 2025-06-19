package application.model;

import java.net.URL;

import application.util.SoundUtil;
import javafx.animation.RotateTransition;
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
        URL url = getClass().getResource("/application/assets/images/back-card.png");
        // System.out.println("BACK URL = " + url);
        back = new ImageView(new Image(url.toExternalForm()));
        
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
        view.setFitWidth(100);
        view.setFitHeight(100);
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
}
