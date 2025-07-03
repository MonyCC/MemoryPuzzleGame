package application.controller;

import application.util.GameSettings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    public void initialize() {
        //update profile
        System.out.println(GameSettings.getResourcePath(GameSettings.loggedInUser.getPhotoPath()));
        default_profile_picture.setImage(new Image(GameSettings.getResourcePath(GameSettings.loggedInUser.getPhotoPath())));
    }

    @FXML
    void Button_play_action(ActionEvent event) {
        GameSettings.PvPOrLevelChoice = GameSettings.LEVELSCREEN;
        try {
            Stage stage = (Stage) Button_play.getScene().getWindow();
            javafx.scene.Scene scene = new javafx.fxml.FXMLLoader(getClass().getResource(GameSettings.OPTION)).load();
            stage.setScene(scene);
            stage.setTitle("Memory Puzzle Game");
            stage.getIcons().add(new javafx.scene.image.Image(GameSettings.getLogoPath()));
            stage.show();
        } catch (Exception e) {
             e.printStackTrace();
        }

    }

    @FXML
    void bttn_leaderboard_action(ActionEvent event) {
        try {
            Stage stage = (Stage) Button_play.getScene().getWindow();
            javafx.scene.Scene scene = new javafx.fxml.FXMLLoader(getClass().getResource(GameSettings.LEADERBOARD)).load();
            stage.setScene(scene);
            stage.setTitle("Memory Puzzle Game - LEADERBOARD");
            stage.getIcons().add(new javafx.scene.image.Image(GameSettings.getLogoPath()));
            stage.show();
        } catch (Exception e) {
             e.printStackTrace();
        }
    }

    @FXML
    void bttn_pvp_action(ActionEvent event) {
        GameSettings.PvPOrLevelChoice = GameSettings.PVPOPTION;
        try {
            Stage stage = (Stage) button_exit.getScene().getWindow();
            javafx.scene.Scene scene = new javafx.fxml.FXMLLoader(getClass().getResource(GameSettings.OPTION)).load();
            stage.setScene(scene);
            stage.setTitle("Memory Puzzle Game - USER INFORMATION");
            stage.getIcons().add(new javafx.scene.image.Image(GameSettings.getLogoPath()));
            stage.show();
        } catch (Exception e) {
             e.printStackTrace();
        }
    }

    @FXML
    void bttn_help_action(ActionEvent event){
        
    }


    @FXML
    void bttn_user_info_action(MouseEvent event) {
         try {
            Stage stage = (Stage) button_exit.getScene().getWindow();
            javafx.scene.Scene scene = new javafx.fxml.FXMLLoader(getClass().getResource(GameSettings.USERINFO)).load();
            stage.setScene(scene);
            stage.setTitle("Memory Puzzle Game - USER INFORMATION");
            stage.getIcons().add(new javafx.scene.image.Image(GameSettings.getLogoPath()));
            stage.show();
        } catch (Exception e) {
             e.printStackTrace();
        }
    }

    @FXML
    void button_exit_action(ActionEvent event) {
        try {
            Stage stage = (Stage) button_exit.getScene().getWindow();
            javafx.scene.Scene scene = new javafx.fxml.FXMLLoader(getClass().getResource(GameSettings.LOGIN)).load();
            stage.setScene(scene);
            stage.setTitle("Memory Puzzle Game - Login");
            stage.getIcons().add(new javafx.scene.image.Image(GameSettings.getLogoPath()));
            stage.show();
        } catch (Exception e) {
             e.printStackTrace();
        }

    }

    @FXML
    void bttn_go_to_shop(ActionEvent event){
        try {
            Stage stage = (Stage) button_exit.getScene().getWindow();
            javafx.scene.Scene scene = new javafx.fxml.FXMLLoader(getClass().getResource(GameSettings.SHOP)).load();
            stage.setScene(scene);
            stage.setTitle("Memory Puzzle Game - Login");
            stage.getIcons().add(new javafx.scene.image.Image(GameSettings.getLogoPath()));
            stage.show();
        } catch (Exception e) {
             e.printStackTrace();
        }
    }

    @FXML
    void bttn_go_to_online(ActionEvent event){
        try {
            Stage stage = (Stage) button_exit.getScene().getWindow();
            javafx.scene.Scene scene = new javafx.fxml.FXMLLoader(getClass().getResource(GameSettings.LOGIN)).load();
            stage.setScene(scene);
            stage.setTitle("Memory Puzzle Game - Login");
            stage.getIcons().add(new javafx.scene.image.Image(GameSettings.getLogoPath()));
            stage.show();
        } catch (Exception e) {
             e.printStackTrace();
        }
    }
       

}

