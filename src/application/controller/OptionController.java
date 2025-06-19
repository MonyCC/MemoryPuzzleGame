package application.controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

import application.util.GameSettings;

public class OptionController {

    @FXML
    private ImageView Imageview_animal;

    @FXML
    private ImageView Imageview_fruit;

    @FXML
    private ImageView Imageview_car;



   @FXML
    void Imageview_animal_click(MouseEvent event) {
        GameSettings.selectedCategory = "animals";
        loadLevelScreen();
    }

    @FXML
    void Imageview_car_click(MouseEvent event) {
        GameSettings.selectedCategory = "cars";
        loadLevelScreen();
    }

    @FXML
    void Imageview_fruit_click(MouseEvent event) {
        GameSettings.selectedCategory = "fruits";
        loadLevelScreen();
    }

    public void loadLevelScreen() {
        try {
                Stage stage = (Stage) Imageview_animal.getScene().getWindow();
                javafx.scene.Scene scene = new javafx.fxml.FXMLLoader(getClass().getResource("/application/fxml/LevelScreen.fxml")).load();
                stage.setScene(scene);
                stage.setTitle("Memory Puzzle Game - Play");
                stage.getIcons().add(new Image(getClass().getResource("/application/assets/images/logo.png").toExternalForm()));
                stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
}



    @FXML
    public void initialize() {
    addHoverEffect(Imageview_animal);
    addHoverEffect(Imageview_fruit);
    addHoverEffect(Imageview_car);
    }


    private void addHoverEffect(ImageView imageView) {
    if (imageView == null) {
        System.err.println("Hover effect skipped: ImageView is null");
        return;
    }

    ColorAdjust brighter = new ColorAdjust();
    brighter.setBrightness(0.3);

    imageView.setOnMouseEntered(e -> imageView.setEffect(brighter));
    imageView.setOnMouseExited(e -> imageView.setEffect(null));
}

       

}
