package application.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;

public class HistoryController {

    @FXML
    private Button Button_X;

    @FXML
    private ScrollPane Scrollpane_history;

    @FXML
    void Button_X_action(ActionEvent event) {
        try {
            javafx.stage.Stage stage = (javafx.stage.Stage) Button_X.getScene().getWindow();
            javafx.scene.Scene scene = new javafx.fxml.FXMLLoader(getClass().getResource("/application/fxml/PlayView.fxml")).load();
            stage.setScene(scene);
            stage.setTitle("Memory Puzzle Game - Play");
            stage.getIcons().add(new javafx.scene.image.Image(getClass().getResource("/application/assets/images/logo.png").toExternalForm()));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

}
