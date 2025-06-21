package application.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import application.util.GameSettings;
public class LevelScreenController {

    @FXML
    private Button button_back;

    @FXML
    private Button button_level_1;

    @FXML
    private Button button_level_2;

    @FXML
    private Button button_level_3;
       
    @FXML
    private Button button_level_4;

    @FXML
    private Button button_level_5;

    @FXML
    private Button button_level_6;

    @FXML
    private Button button_level_7;

    @FXML
    private Button button_level_8;

    @FXML
    private Button button_level_9;


    @FXML
    void button_level_1_action(ActionEvent event) {
        System.out.println("Level 1 button clicked");
        GameSettings.selectedLevel = 1;
        loadLevelScene("/application/fxml/Level1.fxml");
    }

    @FXML
    void button_level_2_action(ActionEvent event) {
        System.out.println("Level 2 button clicked");
        GameSettings.selectedLevel = 2;
        loadLevelScene("/application/fxml/Level2.fxml");
    }

    @FXML
    void button_level_3_action(ActionEvent event) {
        System.out.println("Level 3 button clicked");
        GameSettings.selectedLevel = 3;
        loadLevelScene("/application/fxml/Level3.fxml");
    }
    @FXML
    void button_level_4_action(ActionEvent event) {

    }

    @FXML
    void button_level_5_action(ActionEvent event) {

    }

    @FXML
    void button_level_6_action(ActionEvent event) {

    }

    @FXML
    void button_level_7_action(ActionEvent event) {

    }

    @FXML
    void button_level_8_action(ActionEvent event) {

    }

    @FXML
    void button_level_9_action(ActionEvent event) {

    }


    @FXML
    void button_back_action(ActionEvent event) {
        try {
            Stage stage = (Stage) button_back.getScene().getWindow();
            javafx.scene.Scene scene = new javafx.fxml.FXMLLoader(getClass().getResource("/application/fxml/OptionView.fxml")).load();
            stage.setScene(scene);
            stage.setTitle("Memory Puzzle Game - Options");
            stage.getIcons().add(new javafx.scene.image.Image(getClass().getResource("/application/assets/images/logo.png").toExternalForm()));
            stage.show();
        } catch (Exception e) {
             e.printStackTrace();
        }
    }
    public void loadLevelScene(String fxmlPath) {
        try {
            Stage stage = (Stage) button_back.getScene().getWindow();
            javafx.scene.Scene scene = new javafx.fxml.FXMLLoader(getClass().getResource(fxmlPath)).load();
            stage.setScene(scene);
            stage.setTitle("Memory Puzzle Game - Level "+ GameSettings.selectedLevel);
            stage.getIcons().add(new javafx.scene.image.Image(getClass().getResource("/application/assets/images/logo.png").toExternalForm()));
            stage.show();
        } catch (Exception e) {
             e.printStackTrace();
        }
    }
}


