package application.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import application.dao.GameDatabaseManager;
import application.dao.UserDAO;
import application.model.GameHistoryEntry;
import application.util.GameSettings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class UserInfoController {

    @FXML
    private BarChart<String,Number> barchart_score;

    @FXML
    private Button bttn_back;

    @FXML
    private Button bttn_edit_profile;

    @FXML
    private Button bttn_back_to_login;

    @FXML
    private Button bttn_edit_username;

    @FXML
    private ImageView default_profile;

    @FXML
    private Label label_best_score;

    @FXML
    private Label label_best_sequence;

    @FXML
    private Label label_last_active;

    @FXML
    private Label label_last_completed_lvl;

    @FXML
    private Label label_username;

    @FXML
    private Label label_num_hints;

    @FXML 
    private Label label_num_coins;

    @FXML
    private Label label_winrate;

    @FXML
    private ScrollPane scrollpane_history;


    @FXML
    public void initialize() {
        var user = GameSettings.loggedInUser;

        if (user == null) return;
         default_profile.setImage(new Image(GameSettings.getResourcePath(user.getPhotoPath())));

        label_username.setText(user.getUsername());
        label_num_hints.setText("Hints: " + user.getHints());
        label_num_coins.setText("Coins: " + user.getCoins());

        label_last_completed_lvl.setText("Completed Level: " + user.getLastLevelCompleted());
        label_best_score.setText("Best Score: " + user.getHighestScore());
        label_best_sequence.setText("Best Flip Streak: " + user.getBestFlipSequence());

        double winRate = GameDatabaseManager.getWinRate(user.getId());
        label_winrate.setText(String.format("Win Rate: %.2f%%", winRate));

        populateBarChart();
        populateGameHistory();

    }

    @FXML
    void bttn_back_action(ActionEvent event) {
        try {
                Stage stage = (Stage) bttn_back.getScene().getWindow();
                javafx.scene.Scene scene = new javafx.fxml.FXMLLoader(getClass().getResource(GameSettings.HOME)).load();
                stage.setScene(scene);
                stage.setTitle("Memory Puzzle Game - Home");
                  stage.getIcons().add(new javafx.scene.image.Image(GameSettings.getLogoPath()));
                stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   @FXML
    void bttn_edit_profile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Profile Picture");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(bttn_edit_profile.getScene().getWindow());
        if (selectedFile != null) {
            try {
                String username = GameSettings.loggedInUser.getUsername();
                String extension = getFileExtension(selectedFile.getName());
                if (extension == null) {
                    showAlert("Invalid image file.");
                    return;
                }

                String newFilename = "src/resources/assets/images/user_photos/" + username + "_photo." + extension;
                
                File destFile = new File(newFilename);
                
                // Copy selected file to project directory
                Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Update image path (relative for GameSettings)
                String resourcePath = GameSettings.getResourcePath(newFilename);

                // Set user info
                GameSettings.loggedInUser.setPhotoPath(newFilename);
                default_profile.setImage(new Image(resourcePath));

                // Save to DB
                UserDAO.updatePhotoPath(GameSettings.loggedInUser.getId(), newFilename);
            } catch (IOException e) {
                showAlert("Failed to upload image: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }


    @FXML
     void bttn_edit_username_action(ActionEvent event) {
            TextInputDialog dialog = new TextInputDialog(GameSettings.loggedInUser.getUsername());
            dialog.setTitle("Edit Username");
            dialog.setHeaderText("Enter your new username:");
            dialog.setContentText("Username:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(newUsername -> {
                if (newUsername.length() < 6) {
                    showAlert("Username must be at least 6 characters.");
                    return;
                }

                if (UserDAO.isUsernameTaken(newUsername)) {
                    showAlert("Username already taken. Please choose another.");
                    return;
                }

                // Update in DB
                UserDAO.updateUsername(GameSettings.loggedInUser.getId(), newUsername);

                // Update local
                GameSettings.loggedInUser.setUsername(newUsername);
                label_username.setText(newUsername);
            });
     }

    private String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < filename.length() - 1) {
            return filename.substring(dotIndex + 1).toLowerCase();
        }
        return null;
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Input");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @FXML
    void bttn_back_to_login_action(ActionEvent event){
        try {
                Stage stage = (Stage) bttn_back.getScene().getWindow();
                javafx.scene.Scene scene = new javafx.fxml.FXMLLoader(getClass().getResource(GameSettings.LOGIN)).load();
                stage.setScene(scene);
                stage.setTitle("Memory Puzzle Game - LOGIN");
                  stage.getIcons().add(new javafx.scene.image.Image(GameSettings.getLogoPath()));
                stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void populateBarChart() {
        var data = GameDatabaseManager.getScoreByLevel(GameSettings.loggedInUser.getId());
        javafx.scene.chart.XYChart.Series<String, Number> series = new javafx.scene.chart.XYChart.Series<>();
        series.setName("Scores");

        for (var entry : data.entrySet()) {
            String level = "Level " + entry.getKey();
            int score = entry.getValue();
            series.getData().add(new javafx.scene.chart.XYChart.Data<>(level, score));
        }

        barchart_score.getData().clear();
        barchart_score.getData().add(series);
    }

    private void populateGameHistory() {
        var historyList = GameDatabaseManager.getGameHistory(GameSettings.loggedInUser.getId());

        VBox content = new VBox(5);
        content.setPadding(new Insets(10));

        for (GameHistoryEntry entry : historyList) {
            Label label = new Label(String.format(
                "Level %d | Score: %d | Bonus: %d | Final: %d | Time: %ds | Stars: %d | Flip: %d | %s",
                entry.getLevel(), entry.getScore(), entry.getBonus(), entry.getFinalScore(),
                entry.getTimeCompleted(), entry.getStars(), entry.getFlipStreak(),
                entry.isWin() ? "✅ Win" : "❌ Lose"
            ));
            content.getChildren().add(label);
        }

        scrollpane_history.setContent(content);
    }

}
