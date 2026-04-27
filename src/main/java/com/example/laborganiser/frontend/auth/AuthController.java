package com.example.laborganiser.frontend.auth;

import com.example.laborganiser.app.AppContext;
import com.example.laborganiser.app.SoundUtil;
import com.example.laborganiser.backend.security.PasswordUtil;
import com.example.laborganiser.backend.users.User;
import com.example.laborganiser.backend.vials.VialService;
import com.example.laborganiser.frontend.alerts.AlertWindow;
import com.example.laborganiser.frontend.mainPage.MainPage;
import com.example.laborganiser.backend.users.UserService;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class AuthController {

    public TextField usernameField;
    public PasswordField passwordField;
    public TextField visiblePasswordField;
    public boolean passwordVisible = false;
    public Label passwordError;
    public Label emailError;
    public Label onLoginFailed;

    private Stage stage;
    private final AlertWindow alert = new AlertWindow();
    private final PasswordUtil passwordUtil = new PasswordUtil();

    @FXML
    private Button loginButton;

    private SoundUtil soundUtil = new SoundUtil();


    private AppContext appContext;

    @FXML
    private VBox vbox;


    public void init(Stage stage,AppContext appContext) {
        this.stage = stage;
        this.appContext = appContext;

        onLoginFailed.setVisible(false);
        onLoginFailed.setManaged(false);

        Platform.runLater(() -> vbox.requestFocus());

        usernameField.textProperty().addListener((obs, oldVal, newVal) -> validate());
        passwordField.textProperty().addListener((obs, oldVal, newVal) -> validate());


    }


    @FXML
    private void initialize() {
        loginButton.setDisable(true);


        Platform.runLater(() -> vbox.requestFocus());

        usernameField.textProperty().addListener((obs, oldVal, newVal) -> validate());
        passwordField.textProperty().addListener((obs, oldVal, newVal) -> validate());



    }

    @FXML
    private void onRegisterClicked() {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/laborganiser/frontend/auth/register.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), appContext.getWidth(), appContext.getHeight());
            RegisterController controller = fxmlLoader.getController();


            controller.init(stage,appContext);


            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onLoginClicked() {
        String email = usernameField.getText();
        String password = passwordField.getText();

        UserService userService = appContext.getUserService();
        User user = userService.getUser(email);

        if (user == null || !passwordUtil.verifyPassword(password, user.getPassword())) {
            //alert.showAlert("Login Failed", "Invalid email or password.");
            showLoginFailedPopup();
            return;
        }

        appContext.setCurrentUser(user);


        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/laborganiser/frontend/mainPage/mainPage.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1440, 880);
            MainPage controller = fxmlLoader.getController();

            controller.init(stage,appContext);

            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();

    }}

    public void togglePassword(ActionEvent actionEvent) {

        if (!passwordVisible) {
            visiblePasswordField.setText(passwordField.getText());

            visiblePasswordField.setVisible(true);
            visiblePasswordField.setManaged(true);

            passwordField.setVisible(false);
            passwordField.setManaged(false);

            passwordVisible = true;
        } else {
            passwordField.setText(visiblePasswordField.getText());

            passwordField.setVisible(true);
            passwordField.setManaged(true);

            visiblePasswordField.setVisible(false);
            visiblePasswordField.setManaged(false);

            passwordVisible = false;
        }
    }


    private void validate(){
        boolean validEmail = usernameField.getText().matches("[a-zA-Z]*@[a-zA-Z]+\\.[a-zA-Z]+");
        boolean validPassword = passwordField.getText().length() >= 6;

        loginButton.setDisable(!validEmail || !validPassword);
    }


    private void showLoginFailedPopup() {
        onLoginFailed.setManaged(true);
        onLoginFailed.setVisible(true);

        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(e -> onLoginFailed.setVisible(false));
        pause.play();
    }
}
