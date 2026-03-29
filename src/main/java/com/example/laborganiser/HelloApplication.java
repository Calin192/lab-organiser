package com.example.laborganiser;


import com.example.laborganiser.auth.AuthController;
import com.example.laborganiser.users.repository.UserRepo;
import com.example.laborganiser.users.service.UserService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    

    private static UserRepo userRepo;
    private static UserService userService;

    @Override



    public void start(Stage stage) throws IOException {
        
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/laborganiser/auth/authentificator.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        AuthController controller = fxmlLoader.getController();


        UserRepo userRepo = new UserRepo();
        userService = new UserService(userRepo);
        controller.setStage(stage, userService);

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }
}
