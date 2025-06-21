package application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;

public class UserInfoController {

    @FXML
    private BarChart<?, ?> barchart_score;

    @FXML
    private Button bttn_back;

    @FXML
    private Button bttn_edit_profile;

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
    private Label label_winrate;

    @FXML
    private ScrollPane scrollpane_history;

    @FXML
    void bttn_back_action(ActionEvent event) {

    }

    @FXML
    void bttn_edit_profile(ActionEvent event) {

    }

    @FXML
    void bttn_edit_username_action(ActionEvent event) {

    }

}
