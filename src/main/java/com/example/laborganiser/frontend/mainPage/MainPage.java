package com.example.laborganiser.frontend.mainPage;

import com.example.laborganiser.backend.users.UserService;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class MainPage {

    private Stage stage;
    private UserService userService;

    public void setStage(Stage stage, UserService userService) {
        this.stage = stage;
        this.userService = userService;

        stage.setWidth(1200);
        stage.setHeight(800);
        stage.centerOnScreen();
    }

    public void onAddVialClick(ActionEvent actionEvent) {

    }
}
