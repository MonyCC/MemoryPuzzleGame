package application.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

import application.util.GameSettings;

public class OptionController {

    @FXML private StackPane stackRoot;
    @FXML private AnchorPane mainContent;
    @FXML private ImageView backgroundImage;
    @FXML private ImageView mainImage;
    @FXML private Label labelOptionName;

    private String[] categories = {"Artifacts", "KhmerArtifacts", "fruits"};
    private int currentIndex = 0;

    @FXML
    public void initialize() {
        updateCategoryDisplay();
    }

    @FXML
    void arrowLeftAction(ActionEvent event) {
        currentIndex = (currentIndex - 1 + categories.length) % categories.length;
        updateCategoryDisplay();
    }

    @FXML
    void arrowRightAction(ActionEvent event) {
        currentIndex = (currentIndex + 1) % categories.length;
        updateCategoryDisplay();
    }

    @FXML
    void bttn_confirm_action(ActionEvent event) {
        GameSettings.selectedCategory = categories[currentIndex];
        loadLevelScreen();
    }

    private void updateCategoryDisplay() {
        String current = categories[currentIndex];

        switch (current) {
            case "Artifacts":
                mainImage.setImage(new Image(GameSettings.getResourcePath("src/resources/assets/images/poster_options/artifact_theme.png")));
                backgroundImage.setImage(new Image(GameSettings.getResourcePath("src/resources/assets/images/background/artifact_bg.png")));
                break;
            case "KhmerArtifacts":
                mainImage.setImage(new Image(GameSettings.getResourcePath("src/resources/assets/images/poster_options/khmer_artifact_theme.png")));
                backgroundImage.setImage(new Image(GameSettings.getResourcePath("src/resources/assets/images/background/khmer_artifact_bg.png")));
                break;
            case "fruits":
                mainImage.setImage(new Image(GameSettings.getResourcePath("src/resources/assets/images/poster_options/fruits_poster.png")));
                backgroundImage.setImage(new Image(GameSettings.getResourcePath("src/resources/assets/images/background/fruit_bg.png")));
                break;
        }
        labelOptionName.setText(capitalize(categories[currentIndex]));


    }

    private String capitalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }


      @FXML
        void bttn_back_home(ActionEvent event) {
            try {
                    Stage stage = (Stage) mainImage.getScene().getWindow();
                    javafx.scene.Scene scene = new javafx.fxml.FXMLLoader(getClass().getResource(GameSettings.HOME)).load();
                    stage.setScene(scene);
                    stage.setTitle("Memory Puzzle Game - Home");
                    stage.getIcons().add(new javafx.scene.image.Image(GameSettings.getLogoPath()));
                    stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void loadLevelScreen() {
            try {
                    Stage stage = (Stage) mainImage.getScene().getWindow();
                    javafx.scene.Scene scene = new javafx.fxml.FXMLLoader(getClass().getResource(GameSettings.LEVELSCREEN)).load();
                    stage.setScene(scene);
                    stage.setTitle("Memory Puzzle Game - Level Screen");
                    stage.getIcons().add(new javafx.scene.image.Image(GameSettings.getLogoPath()));
                    stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    @FXML
    public void categoryImageClick(MouseEvent event) {
        bttn_confirm_action(null); // Allow image click to select
    }
        

}
