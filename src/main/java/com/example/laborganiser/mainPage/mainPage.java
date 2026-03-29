package com.example.laborganiser.mainPage;

import com.example.laborganiser.users.service.UserService;
import javafx.stage.Stage;

public class mainPage {

    private Stage stage;
    private UserService userService;

    public void setStage(Stage stage, UserService userService) {
        this.stage = stage;
        this.userService = userService;
    }
}
