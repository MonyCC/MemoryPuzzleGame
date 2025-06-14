package application.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    private Label textfield_username;

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

}
