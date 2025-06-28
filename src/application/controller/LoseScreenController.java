package application.controller;

import java.io.IOException;

import application.util.GameSettings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class LoseScreenController {
    @FXML private Label label_lost_status;

     @FXML
    void handleRetry(ActionEvent event) {
        try {
            Stage stage = (Stage) label_lost_status.getScene().getWindow();
            String path = "/application/fxml/Level"+GameSettings.selectedLevel+".fxml";
            javafx.scene.Scene scene = new javafx.fxml.FXMLLoader(getClass().getResource(path)).load();
            stage.setScene(scene);
            stage.setTitle("Memory Puzzle Game - Level "+ GameSettings.selectedLevel);
            stage.getIcons().add(new javafx.scene.image.Image(GameSettings.getLogoPath()));
            stage.show();
        } catch (Exception e) {
             e.printStackTrace();
        }
    }

    @FXML
    void handleBackToMenu(ActionEvent event) {
        try {
                Stage stage = (Stage) label_lost_status.getScene().getWindow();
                javafx.scene.Scene scene = new javafx.fxml.FXMLLoader(getClass().getResource(GameSettings.HOME)).load();
                stage.setScene(scene);
                stage.setTitle("Memory Puzzle Game - Home");
                  stage.getIcons().add(new javafx.scene.image.Image(GameSettings.getLogoPath()));
                stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
