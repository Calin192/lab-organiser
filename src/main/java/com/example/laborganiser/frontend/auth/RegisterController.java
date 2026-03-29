package com.example.laborganiser.frontend.auth;

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

    public PasswordField passwordConfirm;
    public PasswordField password;
    public TextField username;
    private Stage stage;
    private AlertWindow alert;

    private UserService userService;


    public void setStage(Stage stage,UserService userService) {
        this.stage = stage;
        this.userService = userService;

        this.alert = new AlertWindow();
    }

    @FXML
    private void onBackToLoginClicked() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/laborganiser/frontend/auth/authentificator.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            AuthController controller = fxmlLoader.getController();
            controller.setStage(stage,userService);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
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
