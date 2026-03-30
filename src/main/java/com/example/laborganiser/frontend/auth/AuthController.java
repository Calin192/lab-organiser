package com.example.laborganiser.frontend.auth;

import com.example.laborganiser.frontend.alerts.AlertWindow;
import com.example.laborganiser.frontend.mainPage.MainPage;
import com.example.laborganiser.backend.users.UserService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class AuthController {

    public TextField usernameField;
    public PasswordField passwordField;
    private Stage stage;
    private AlertWindow alert;

    private UserService userService;


    @FXML
    private VBox vbox;

    public void setStage(Stage stage, UserService userService) {
        this.stage = stage;
        this.userService = userService;
        this.alert = new AlertWindow();
    }

    @FXML
    private void initialize() {
        Platform.runLater(() -> vbox.requestFocus());
    }

    @FXML
    private void onRegisterClicked() {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/laborganiser/frontend/auth/register.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            RegisterController controller = fxmlLoader.getController();


            controller.setStage(stage,userService);


            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onLoginClicked() {
        usernameField.setText("admin");
        passwordField.setText("admin123");
        if(userService.loginUser(usernameField.getText(), passwordField.getText())) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/laborganiser/frontend/mainPage/mainPage.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                MainPage controller = fxmlLoader.getController();


                controller.setStage(stage,userService);


                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else
        {
            alert.showAlert( "Error processing the username and/or password",
                    "Please check your credentials and try again.");
            return;
        }

    }

}
