package com.example.laborganiser.frontend.mainPage;

import com.example.laborganiser.app.AppContext;
import com.example.laborganiser.frontend.vialAdd.VialAddController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPage {

    private Stage stage;


    private AppContext appContext;

    public void init(Stage stage, AppContext appContext) {
        this.stage = stage;
        this.appContext = appContext;


        stage.setWidth(1200);
        stage.setHeight(800);
        stage.centerOnScreen();
    }

    public void onAddVialClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/laborganiser/frontend/vial/vialAdd.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            VialAddController controller = fxmlLoader.getController();


            controller.init(stage,appContext);


            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
