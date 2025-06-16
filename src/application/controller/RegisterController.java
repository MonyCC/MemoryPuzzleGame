package application.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import application.model.User;

import application.dao.UserDAO;

public class RegisterController {

    @FXML
    private CheckBox Checkbox_show_password;

    @FXML
    private Label Label_status_usernameRegister;

    @FXML
    private PasswordField Passwordfield_password_register;

    @FXML
    private PasswordField Passwordfield_verify_register;

    @FXML
    private TextField Textfield_password_register;

    @FXML
    private TextField Textfield_username_register;

    @FXML
    private TextField Textfield_verify_register;

    @FXML
    private Button button_back_to_login;

    @FXML
    private Button button_register;

    @FXML
    private Label label_statusResult;

    @FXML
    private Label label_status_passwordRegister;

    @FXML
    private Label label_status_verifyRegister;


    @FXML
    public void initialize() {
        // Listen to password field changes
        Passwordfield_password_register.textProperty().addListener((obs, oldText, newText) -> checkPasswordsMatch());
        Textfield_password_register.textProperty().addListener((obs, oldText, newText) -> checkPasswordsMatch());

        // Listen to verify field changes
        Passwordfield_verify_register.textProperty().addListener((obs, oldText, newText) -> checkPasswordsMatch());
        Textfield_verify_register.textProperty().addListener((obs, oldText, newText) -> checkPasswordsMatch());
    }
    
    private void checkPasswordsMatch() {
        String password = Checkbox_show_password.isSelected()
            ? Textfield_password_register.getText()
            : Passwordfield_password_register.getText();

        String verifyPassword = Checkbox_show_password.isSelected()
            ? Textfield_verify_register.getText()
            : Passwordfield_verify_register.getText();

        if (verifyPassword.isEmpty()) {
            label_status_verifyRegister.setText("");
        } else if (password.equals(verifyPassword)) {
            label_status_verifyRegister.setText("Passwords match");
            label_status_verifyRegister.setStyle("-fx-text-fill: green;");
        } else {
            label_status_verifyRegister.setText("Passwords do not match");
            label_status_verifyRegister.setStyle("-fx-text-fill: red;");
        }
    }


    @FXML
    void Checkbox_show_password_action(ActionEvent event) {
        if (Checkbox_show_password.isSelected()) {
            Textfield_password_register.setText(Passwordfield_password_register.getText());
            Textfield_password_register.setVisible(true);
            Passwordfield_password_register.setVisible(false);

            Textfield_verify_register.setText(Passwordfield_verify_register.getText());
            Textfield_verify_register.setVisible(true);
            Passwordfield_verify_register.setVisible(false);
        } else {
            Passwordfield_password_register.setText(Passwordfield_password_register.getText());
            Passwordfield_password_register.setVisible(true);
            Textfield_password_register.setVisible(false);

            Passwordfield_verify_register.setText(Textfield_verify_register.getText());
            Passwordfield_verify_register.setVisible(true);
            Textfield_verify_register.setVisible(false);
        }
    }

    @FXML
    void button_back_to_login_action(ActionEvent event) {
        try {
            Stage stage = (Stage) button_back_to_login.getScene().getWindow();
            javafx.scene.Scene scene = new javafx.fxml.FXMLLoader(getClass().getResource("/application/fxml/login.fxml")).load();
            stage.setScene(scene);
            stage.setTitle("Memory Puzzle Game - Login");
            stage.getIcons().add(new javafx.scene.image.Image(getClass().getResource("/application/assets/images/logo.png").toExternalForm()));
            stage.show();
        } catch (Exception e) {
             e.printStackTrace();
        }
    }

    @FXML
    void register_login(ActionEvent event) {
        System.out.println("button register is pressed;");

        String username = Textfield_username_register.getText();
        String password = Passwordfield_password_register.getText();
        String confirmPassword = Passwordfield_verify_register.getText();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            label_statusResult.setText("Please fill in all fields.");
            label_statusResult.setStyle("-fx-text-fill: red;");
            return;
        }

        // Temporarily use plain password for validation
        User tempUser = new User(username, password, "/application/assets/images/user_photos/default.png");

        String validationError = tempUser.validateRegistration(confirmPassword);
        System.out.println(validationError);
        if (validationError != null) {
            label_statusResult.setText(validationError);
            label_statusResult.setStyle("-fx-text-fill: red;");
            return;
        }

        // ✅ Only hash AFTER validation
        String hashedPassword = User.hashPassword(password);
        User finalUser = new User(username, hashedPassword, tempUser.getPhotoPath());

        try {
            if (UserDAO.register(finalUser)) {
                label_statusResult.setText("Registered successfully.");
                label_statusResult.setStyle("-fx-text-fill: green;");
            } else {
                label_statusResult.setText("Username already exists.");
                label_statusResult.setStyle("-fx-text-fill: red;");
            }
        } catch (Exception e) {
            label_statusResult.setText("Error: " + e.getMessage());
            label_statusResult.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }   

}
