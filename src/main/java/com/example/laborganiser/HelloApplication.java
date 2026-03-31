package com.example.laborganiser;


import com.example.laborganiser.app.AppContext;
import com.example.laborganiser.backend.vials.VialRepo;
import com.example.laborganiser.backend.vials.VialService;
import com.example.laborganiser.frontend.auth.AuthController;
import com.example.laborganiser.backend.users.UserRepo;
import com.example.laborganiser.backend.users.UserService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    

    private static UserRepo userRepo;
    private static UserService userService;
    private static VialRepo vialRepo;
    private static VialService vialService;

    @Override



    public void start(Stage stage) throws IOException {
        //bigest change
        
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/laborganiser/frontend/auth/authentificator.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        AuthController controller = fxmlLoader.getController();


        AppContext appContext = new AppContext();



        controller.init(stage,appContext);

        stage.setTitle("Flasky");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }
}
