package application;

import application.dao.DatabaseInitializer;
import application.util.GameSettings;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource(GameSettings.LOGIN));
    Scene scene = loader.load(); 
    stage.setTitle("Memory Puzzle Game - Login");
    stage.setScene(scene);


    stage.getIcons().add(new javafx.scene.image.Image(GameSettings.getLogoPath()));

    stage.show();
    
    }
    public static void main(String[] args) {
        DatabaseInitializer.initialize();
        launch(args);
    }
}
