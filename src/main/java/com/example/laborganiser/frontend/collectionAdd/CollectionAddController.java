package com.example.laborganiser.frontend.collectionAdd;

import com.example.laborganiser.app.AppContext;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CollectionAddController {
    @FXML
    public TextField collectionName;

    Stage stage = new Stage();
    AppContext appContext = new AppContext();


    public void init(Stage stage, AppContext appContext) {
        this.appContext = appContext;

        Platform.runLater(() -> {
            this.stage = (Stage) collectionName.getScene().getWindow();
            this.stage.setWidth(500);
            this.stage.setHeight(500);
        });
    }


    public void onAddClicked(ActionEvent actionEvent) {

    }
}
