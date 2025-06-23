package application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class HomeViewController {

    @FXML
    private Button Button_play;

    @FXML
    private Button bttn_leaderboard;

    @FXML
    private Button bttn_pvp;

    @FXML
    private Button bttn_help;
    @FXML
    private Button bttn_user_info;

    @FXML
    private Button button_exit;

    @FXML
    private ImageView default_profile_picture;

    @FXML
    void Button_play_action(ActionEvent event) {
        try {
            Stage stage = (Stage) Button_play.getScene().getWindow();
            javafx.scene.Scene scene = new javafx.fxml.FXMLLoader(getClass().getResource("/application/fxml/OptionView.fxml")).load();
            stage.setScene(scene);
            stage.setTitle("Memory Puzzle Game");
            stage.getIcons().add(new javafx.scene.image.Image(getClass().getResource("/application/assets/images/logo.png").toExternalForm()));
            stage.show();
        } catch (Exception e) {
             e.printStackTrace();
        }

    }

    @FXML
    void bttn_leaderboard_action(ActionEvent event) {

    }

    @FXML
    void bttn_pvp_action(ActionEvent event) {

    }

    @FXML
    void bttn_help_action(ActionEvent event){
        
    }


    @FXML
    void bttn_user_info_action(ActionEvent event) {

    }

    @FXML
    void button_exit_action(ActionEvent event) {
        try {
            Stage stage = (Stage) button_exit.getScene().getWindow();
            javafx.scene.Scene scene = new javafx.fxml.FXMLLoader(getClass().getResource("/application/fxml/Login.fxml")).load();
            stage.setScene(scene);
            stage.setTitle("Memory Puzzle Game - Login");
            stage.getIcons().add(new javafx.scene.image.Image(getClass().getResource("/application/assets/images/logo.png").toExternalForm()));
            stage.show();
        } catch (Exception e) {
             e.printStackTrace();
        }

    }

}

