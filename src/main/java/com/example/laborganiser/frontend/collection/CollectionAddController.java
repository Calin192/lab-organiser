package com.example.laborganiser.frontend.collection;

import com.example.laborganiser.app.AppContext;
import com.example.laborganiser.backend.collections.Collection;
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
    private Runnable onSave;
    private Collection collection = null;


    public void init(Stage stage, AppContext appContext) {
        this.appContext = appContext;

        Platform.runLater(() -> {
            this.stage = (Stage) collectionName.getScene().getWindow();
            this.stage.setWidth(500);
            this.stage.setHeight(500);
        });
    }


    public void onAddClicked(ActionEvent actionEvent) {
        if (collection == null) {
            appContext.getCollectionService().addCollection(collectionName.getText().trim());

        }else {
            collection.setName(collectionName.getText().trim());
            appContext.getCollectionService().updateCollection(collection);
        }
            if (onSave != null) {
                onSave.run();
            }

        stage.close();

    }

    public void addEditingCollection(Collection collection) {
        this.collection = collection;
        collectionName.setText(collection.getName());

    }

    public void setOnSave(Runnable onSave) {
        this.onSave = onSave;
    }
}
