package application.controller;

import application.dao.LeaderboardDAO;
import application.model.LeaderboardEntry;
import application.util.GameSettings;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LeaderboardController {

    @FXML private TableView<LeaderboardEntry> tableTopScores;
    @FXML private TableView<LeaderboardEntry> tableFlipSequence;
    @FXML private TableView<LeaderboardEntry> tableWinRate;

    @FXML private TableColumn<LeaderboardEntry, String> colRank1, colUsername1, colScore;
    @FXML private TableColumn<LeaderboardEntry, String> colRank2, colUsername2, colSequence;
    @FXML private TableColumn<LeaderboardEntry, String> colRank3, colUsername3, colWinRate;

    @FXML
    public void initialize() {
        loadTopScores();
        loadFlipSequence();
        loadWinRates();
    }

    private void loadTopScores() {
        var entries = LeaderboardDAO.getTopScores();
        for (int i = 0; i < entries.size(); i++) entries.get(i).setRank(i + 1);
        tableTopScores.setItems(FXCollections.observableArrayList(entries));
        colRank1.setCellValueFactory(e -> e.getValue().rankProperty());
        colUsername1.setCellValueFactory(e -> e.getValue().usernameProperty());
        colScore.setCellValueFactory(e -> e.getValue().valueProperty());
    }

    private void loadFlipSequence() {
        var entries = LeaderboardDAO.getBestFlipSequences();
        for (int i = 0; i < entries.size(); i++) entries.get(i).setRank(i + 1);
        tableFlipSequence.setItems(FXCollections.observableArrayList(entries));
        colRank2.setCellValueFactory(e -> e.getValue().rankProperty());
        colUsername2.setCellValueFactory(e -> e.getValue().usernameProperty());
        colSequence.setCellValueFactory(e -> e.getValue().valueProperty());
    }

    private void loadWinRates() {
        var entries = LeaderboardDAO.getTopWinRates();
        for (int i = 0; i < entries.size(); i++) entries.get(i).setRank(i + 1);
        tableWinRate.setItems(FXCollections.observableArrayList(entries));
        colRank3.setCellValueFactory(e -> e.getValue().rankProperty());
        colUsername3.setCellValueFactory(e -> e.getValue().usernameProperty());
        colWinRate.setCellValueFactory(e -> e.getValue().valueProperty());
    }

    @FXML
    void handleBack() {
        try {
            Stage stage = (Stage) tableTopScores.getScene().getWindow();
            javafx.scene.Scene scene = new javafx.fxml.FXMLLoader(getClass().getResource(GameSettings.HOME)).load();
            stage.setScene(scene);
            stage.setTitle("Memory Puzzle Game - Home");
            stage.getIcons().add(new javafx.scene.image.Image(GameSettings.getLogoPath()));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

