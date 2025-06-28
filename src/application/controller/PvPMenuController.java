package application.controller;

import application.util.GameSettings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class PvPMenuController {
    @FXML private Button mode_4x4;

    private void startGame(int rows, int cols, boolean swapMode) {
        try {
            GameSettings.selectedLevel = -1; // For PvP mode
            GameSettings.rows = rows;
            GameSettings.cols = cols;
            GameSettings.pvpSwap = swapMode;

            Stage stage = (Stage) mode_4x4.getScene().getWindow();
            javafx.scene.Scene scene = new javafx.fxml.FXMLLoader(getClass().getResource(GameSettings.PVPGAME)).load();
            stage.setScene(scene);
            stage.setTitle("PvP Memory Puzzle: " + rows + " x " + cols + (swapMode ? " (Swap)" : ""));
            stage.getIcons().add(new javafx.scene.image.Image(GameSettings.getLogoPath()));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML void handle4x4(ActionEvent e)       { startGame(4, 4, false); }
    @FXML void handle4x5(ActionEvent e)       { startGame(4, 5, false); }
    @FXML void handle5x6(ActionEvent e)       { startGame(5, 6, false); }
    @FXML void handle6x7(ActionEvent e)       { startGame(6, 7, false); }
    @FXML void handle6x7Swap(ActionEvent e)   { startGame(6, 7, true); }

    @FXML void handleBack(ActionEvent e) {
        try {
            Stage stage = (Stage) mode_4x4.getScene().getWindow();
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
