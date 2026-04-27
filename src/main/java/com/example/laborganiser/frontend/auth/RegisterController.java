package com.example.laborganiser.frontend.auth;

import com.example.laborganiser.app.AppContext;
import com.example.laborganiser.frontend.alerts.AlertWindow;
import com.example.laborganiser.backend.users.User;
import com.example.laborganiser.backend.users.UserService;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class RegisterController {

    @FXML public PasswordField passwordConfirm;
    @FXML public PasswordField password;
    @FXML public TextField username;

    @FXML public TextField visiblePasswordConfirm;
    @FXML public TextField visiblePasswordField;

    @FXML public Button registerButton;
    public Label onRegisterFailed;

    private Stage stage;
    private final AlertWindow alert = new AlertWindow();

    private UserService userService;
    private AppContext appContext;

    private boolean passwordsVisible = false;

    public void init(Stage stage, AppContext appContext) {
        this.stage = stage;
        this.appContext = appContext;
        this.userService = appContext.getUserService();
    }

    @FXML
    private void initialize() {

        registerButton.setDisable(true);

        onRegisterFailed.setVisible(false);
        onRegisterFailed.setManaged(false);

        visiblePasswordField.textProperty().bindBidirectional(password.textProperty());
        visiblePasswordConfirm.textProperty().bindBidirectional(passwordConfirm.textProperty());

        username.textProperty().addListener((obs, oldVal, newVal) -> validate());
        password.textProperty().addListener((obs, oldVal, newVal) -> validate());
        passwordConfirm.textProperty().addListener((obs, oldVal, newVal) -> validate());
    }

    @FXML
    private void onBackToLoginClicked() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/com/example/laborganiser/frontend/auth/authentificator.fxml")
            );

            Scene scene = new Scene(
                    fxmlLoader.load(),
                    appContext.getWidth(),
                    appContext.getHeight()
            );

            AuthController controller = fxmlLoader.getController();
            controller.init(stage, appContext);

            stage.setScene(scene);

        } catch (IOException e) {
            alert.showAlert("Error", "Could not load login page.");
        }
    }

    @FXML
    public void onRegisterClick(ActionEvent actionEvent) {

        String usernameText = username.getText().trim();
        String passwordText = password.getText();
        String passwordConfirmText = passwordConfirm.getText();

        if (usernameText.isEmpty() || passwordText.isEmpty() || passwordConfirmText.isEmpty()) {
            //alert.showAlert("Validation Error", "Please fill in all fields.");
            showRegisterFailedPopup(true,"Please fill in all fields.");
            return;
        }

        if (passwordText.length() < 6) {
            //alert.showAlert("Validation Error", "Password must be at least 6 characters long.");
            showRegisterFailedPopup(true,"Password must be at least 6 characters long.");
            return;
        }

        if (!passwordText.equals(passwordConfirmText)) {
            //alert.showAlert("Validation Error", "Passwords do not match.");
            showRegisterFailedPopup(true,"Passwords do not match.");
            return;
        }

        if (userExists(usernameText)) {
            //alert.showAlert("User Exists", "This email is already registered.");
            showRegisterFailedPopup(true,"User already exists.");
            return;
        }

        try {
            User newUser = new User(usernameText, passwordText);
            userService.addUser(newUser);

//            alert.showAlert("Success",
//                    "Account created successfully!");
            showRegisterFailedPopup(false,"Account created successfully!");

            clearFields();

            PauseTransition pause = new PauseTransition(Duration.millis(1500));
            pause.setOnFinished(e -> onBackToLoginClicked());
            pause.play();


        } catch (Exception e) {
//            alert.showAlert("Error",
//                    "Registration failed: " + e.getMessage());
            showRegisterFailedPopup(true,e.getMessage());
        }
    }

    private boolean userExists(String username) {
        return userService.getAllUsers().stream()
                .anyMatch(user -> user.getUsername().equalsIgnoreCase(username));
    }

    private void clearFields() {
        username.clear();
        password.clear();
        passwordConfirm.clear();
    }

    @FXML
    public void togglePassword(ActionEvent actionEvent) {

        passwordsVisible = !passwordsVisible;

        visiblePasswordField.setVisible(passwordsVisible);
        visiblePasswordField.setManaged(passwordsVisible);

        visiblePasswordConfirm.setVisible(passwordsVisible);
        visiblePasswordConfirm.setManaged(passwordsVisible);

        password.setVisible(!passwordsVisible);
        password.setManaged(!passwordsVisible);

        passwordConfirm.setVisible(!passwordsVisible);
        passwordConfirm.setManaged(!passwordsVisible);
    }

    private void validate() {

        boolean validEmail =
                username.getText().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

        boolean validPassword =
                password.getText().length() >= 6;

        boolean samePasswords =
                password.getText().equals(passwordConfirm.getText());

        registerButton.setDisable(!(validEmail && validPassword && samePasswords));
    }

    private void showRegisterFailedPopup(Boolean error,String message) {


        if (error) {
            onRegisterFailed.getStyleClass().clear();
            onRegisterFailed.getStyleClass().add("error-label");

        }else {
            onRegisterFailed.getStyleClass().clear();
            onRegisterFailed.getStyleClass().add("success-label");

        }

        onRegisterFailed.setManaged(true);
        onRegisterFailed.setVisible(true);

        onRegisterFailed.setText(message);


        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(e -> onRegisterFailed.setVisible(false));
        pause.play();
    }
}