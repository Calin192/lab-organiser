package com.example.laborganiser.frontend.collection;

import com.example.laborganiser.app.AppContext;
import com.example.laborganiser.backend.collections.Collection;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CollectionViewController {
    public Label nameLabel;
    private AppContext appContext;
    private Stage stage;
    private Collection collection;

    public void onBackButtonClick(ActionEvent actionEvent) {
        if (stage != null) {
            stage.close();
        }
    }

    public void init(Stage stage, AppContext appContext, Collection collection) {
        this.appContext = appContext;
        this.stage = stage;
        this.collection = collection;


        displayCollectionDetails();
    }

    private void displayCollectionDetails() {
        if(collection!=null){
            nameLabel.setText(collection.getName() != null ? collection.getName() : "N/A");

        }
    }
}
