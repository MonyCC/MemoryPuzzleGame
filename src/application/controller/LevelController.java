package application.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LevelController {

    @FXML
    private Button button_back;

    @FXML
    private Button button_level_1;

    @FXML
    private Button button_level_2;

    @FXML
    private Button button_level_3;

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

    @FXML
    void button_level_1_action(ActionEvent event) {
        System.out.println("Level 1 button clicked");
    }

    @FXML
    void button_level_2_action(ActionEvent event) {
        System.out.println("Level 2 button clicked");
    }

    @FXML
    void button_level_3_action(ActionEvent event) {
        System.out.println("Level 3 button clicked");
    }

}
