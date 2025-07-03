package application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;

import application.dao.UserDAO;
import application.util.GameSettings;

public class ShopController {

    @FXML private Label labelCoins;
    @FXML private Label categoryTitle;
    @FXML private VBox itemContainer;
    @FXML private AnchorPane popupOverlay;
    @FXML private Label popupMessage;
    @FXML private TextField couponField;

    private String currenUserName = GameSettings.loggedInUser.getUsername();
    private int currentCategoryIndex = 0;
    private final String[] categories = {"Hints", "Coins"};

    public void initialize() {
        updateCoinsDisplay();
        displayItemsForCurrentCategory();
    }

    private void updateCoinsDisplay() {
        int coins = UserDAO.getUserCoins(currenUserName);
        labelCoins.setText(String.valueOf(coins));
    }

    private void displayItemsForCurrentCategory() {
        itemContainer.getChildren().clear();
        categoryTitle.setText(categories[currentCategoryIndex]);

        if (categories[currentCategoryIndex].equals("Hints")) {
            if (categories[currentCategoryIndex].equals("Hints")) {
                addHintItem("1 Hint", 50, 1);
                addHintItem("3 Hints", 130, 3);
                addHintItem("5 Hints", 200, 5);
            }
        } else if (categories[currentCategoryIndex].equals("Coins")) {
            addCoinItem("Buy 100 Coins", 0.99);
            addCoinItem("Buy 500 Coins", 3.99);
            addCoinItem("Buy 1000 Coins", 6.99);
        }
    }

    private void addHintItem(String label, int cost, int hintAmount) {
    Button btn = new Button(label + " - " + cost + "💰");
    btn.setOnAction(e -> {
        int currentCoins = UserDAO.getUserCoins(currenUserName);
        if (currentCoins >= cost) {
            // Deduct coins
            boolean coinUpdated = UserDAO.updateCoins(currenUserName, -cost);
            if (coinUpdated) {
                // Add hints
                UserDAO.updateHints(currenUserName, hintAmount);
                showAlert("Success", "You bought: " + label);
                updateCoinsDisplay();
            } else {
                showAlert("Error", "Failed to update coins.");
            }
        } else {
            showAlert("Error", "Not enough coins!");
        }
    });
    itemContainer.getChildren().add(btn);
}


    private void addCoinItem(String label, double price) {
        Button btn = new Button(label + " - $" + price);
        btn.setOnAction(e -> {
            showAlert("Info", "This is a demo. Payment not implemented.");
        });
        itemContainer.getChildren().add(btn);
    }

    @FXML
    void prevCategory() {
        currentCategoryIndex = (currentCategoryIndex - 1 + categories.length) % categories.length;
        displayItemsForCurrentCategory();
    }

    @FXML
    void nextCategory() {
        currentCategoryIndex = (currentCategoryIndex + 1) % categories.length;
        displayItemsForCurrentCategory();
    }

    @FXML
    void showCouponPopup() {
        popupOverlay.setVisible(true);
        popupMessage.setText("Enter a coupon code to get free coins or hints!");
        couponField.setText("");
    }

    @FXML
    void hidePopup() {
        popupOverlay.setVisible(false);
    }

    @FXML
    void applyCoupon() {
        String code = couponField.getText().trim().toLowerCase();
        if (code.equals("free100")) {
            UserDAO.updateCoins(currenUserName, 100);
            showAlert("Coupon Applied", "You received 100 coins!");
            updateCoinsDisplay();
        } else if (code.equals("freehint")) {
            UserDAO.updateHints(currenUserName, 1);
            showAlert("Coupon Applied", "You received 1 hint!");
        } else {
            showAlert("Invalid Coupon", "This code is not valid.");
        }
        hidePopup();
    }

    @FXML
    void handleBack() {
        GameSettings.loggedInUser = UserDAO.UpdateStatus(GameSettings.loggedInUser.getUsername());
        try {
                Stage stage = (Stage) labelCoins.getScene().getWindow();
                javafx.scene.Scene scene = new javafx.fxml.FXMLLoader(getClass().getResource(GameSettings.HOME)).load();
                stage.setScene(scene);
                stage.setTitle("Memory Puzzle Game - Home");
                  stage.getIcons().add(new javafx.scene.image.Image(GameSettings.getLogoPath()));
                stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
