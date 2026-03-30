package com.example.laborganiser.frontend.auth;

import com.example.laborganiser.app.AppContext;
import com.example.laborganiser.frontend.alerts.AlertWindow;
import com.example.laborganiser.backend.users.User;
import com.example.laborganiser.backend.users.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {

    @FXML
    public PasswordField passwordConfirm;
    @FXML
    public PasswordField password;
    @FXML
    public TextField username;
    private Stage stage;
    private final AlertWindow alert = new AlertWindow();

    private UserService userService;
    private AppContext appContext;


    public void init(Stage stage, AppContext  appContext) {
        this.stage = stage;
        this.appContext = appContext;
        this.userService = appContext.getUserService();
    }

    @FXML
    private void onBackToLoginClicked() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/laborganiser/frontend/auth/authentificator.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            AuthController controller = fxmlLoader.getController();
            controller.init(stage,appContext);
            stage.setScene(scene);
        } catch (IOException e) {
            alert.showAlert("Error", "Could not load login page.");
        }
    }

    public void onRegisterClick(ActionEvent actionEvent) {
        String usernameText = username.getText().trim();
        String passwordText = password.getText();
        String passwordConfirmText = passwordConfirm.getText();

        if (usernameText.isEmpty() || passwordText.isEmpty() || passwordConfirmText.isEmpty()) {
            alert.showAlert( "Validation Error",
                    "Please fill in all fields.");
            return;
        }

        if (usernameText.length() < 3) {
            alert.showAlert( "Validation Error",
                    "Username must be at least 3 characters long.");
            return;
        }

        if (passwordText.length() < 6) {
            alert.showAlert( "Validation Error",
                    "Password must be at least 6 characters long.");
            return;
        }

        if (!passwordText.equals(passwordConfirmText)) {
            alert.showAlert("Validation Error",
                    "The password and confirm password fields do not match.");
            return;
        }

        if (userExists(usernameText)) {
            alert.showAlert( "User Exists",
                    "This username is already registered. Please choose another.");
            return;
        }

        try {
            User newUser = new User(usernameText, passwordText);
            userService.addUser(newUser);

            alert.showAlert( "Success",
                    "Your account has been created successfully! You will be redirected to login.");

            clearFields();

            onBackToLoginClicked();
        } catch (Exception e) {
            alert.showAlert("Error",
                    "An error occurred during registration: " + e.getMessage());
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



}
