package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/fxml/login.fxml"));
    Scene scene = loader.load(); 

    // Set stage properties
    stage.setTitle("Memory Puzzle Game - Login");
    stage.setScene(scene);

    stage.getIcons().add(new javafx.scene.image.Image(getClass().getResource("/application/assets/images/logo.png").toExternalForm()));

    stage.show();
    
    }
    public static void main(String[] args) {
        DatabaseInitializer.initialize();
        launch(args);
    }
}
