package com.example.laborganiser.frontend.shelf;

import com.example.laborganiser.app.AppContext;
import com.example.laborganiser.backend.collections.Collection;
import com.example.laborganiser.backend.shelf.Shelf;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ShelfViewController {
    public Label nameLabel;
    public Label collectionLabel;
    AppContext appContext;
    Stage stage;
    Shelf shelf;
    Collection collection;


    public void onBackButtonClick(ActionEvent actionEvent) {
        stage.close();
    }

    public void init(Stage stage, AppContext appContext, Shelf shelf, Collection collection) {
        this.appContext = appContext;
        this.stage = stage;
        this.shelf = shelf;
        this.collection = collection;

        displayShelfDetails();
    }

    private void displayShelfDetails() {
        if(shelf!=null){
            nameLabel.setText(shelf.getName() != null ? shelf.getName() : "N/A");
            collectionLabel.setText(collection.getName() != null ? collection.getName() : "N/A");
        }

    }
}
