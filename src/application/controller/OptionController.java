package application.controller;
import javafx.fxml.FXML;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class OptionController {

    @FXML
    private ImageView Imageview_animal;

    @FXML
    private ImageView Imageview_fruit;

    @FXML
    private ImageView Imgaeview_car;



    @FXML
    void Imageview_animal_click(MouseEvent event) {
        try {
            Stage stage = (Stage) Imageview_animal.getScene().getWindow();
            javafx.scene.Scene scene = new javafx.fxml.FXMLLoader(getClass().getResource("/application/fxml/level.fxml")).load();
            stage.setScene(scene);
            stage.setTitle("Memory Puzzle Game - Level");
            stage.getIcons().add(new javafx.scene.image.Image(getClass().getResourceAsStream("/application/assets/images/logo.png")));
            stage.show();
        } catch (Exception e) {
             e.printStackTrace();
        }
    }

    @FXML
    void Imageview_car_click(MouseEvent event) {
        try {
            Stage stage = (Stage) Imageview_animal.getScene().getWindow();
            javafx.scene.Scene scene = new javafx.fxml.FXMLLoader(getClass().getResource("/application/fxml/level.fxml")).load();
            stage.setScene(scene);
            stage.setTitle("Memory Puzzle Game - Level");
            stage.getIcons().add(new javafx.scene.image.Image(getClass().getResource("/application/assets/images/logo.png").toExternalForm()));
            stage.show();
        } catch (Exception e) {
             e.printStackTrace();
        }
    }

    @FXML
    void Imageview_fruit_click(MouseEvent event) {
        try {
            Stage stage = (Stage) Imageview_animal.getScene().getWindow();
            javafx.scene.Scene scene = new javafx.fxml.FXMLLoader(getClass().getResource("/application/fxml/level.fxml")).load();
            stage.setScene(scene);
            stage.setTitle("Memory Puzzle Game - Level");
            stage.getIcons().add(new javafx.scene.image.Image(getClass().getResource("/application/assets/images/logo.png").toExternalForm()));
            stage.show();
        } catch (Exception e) {
             e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        addHoverEffect(Imageview_animal);
        addHoverEffect(Imageview_fruit);
        addHoverEffect(Imgaeview_car); // again, fix spelling if necessary
    }

    private void addHoverEffect(ImageView imageView) {
        ColorAdjust brighter = new ColorAdjust();
        brighter.setBrightness(0.3); // Brighter effect

        imageView.setOnMouseEntered(e -> imageView.setEffect(brighter));
        imageView.setOnMouseExited(e -> imageView.setEffect(null));
    }
       
    @FXML
    void Imageview_fruit_action(MouseEvent event) {

    }

    @FXML
    void Imageview_animal_action(MouseEvent event) {

    }

    @FXML
    void Imgaeview_car_action(MouseEvent event) {

    }

}
