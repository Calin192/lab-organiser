package com.example.laborganiser.frontend.shelf;

import com.example.laborganiser.app.AppContext;
import com.example.laborganiser.backend.collections.Collection;
import com.example.laborganiser.backend.shelf.Shelf;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ShelfAddController {
    @FXML
    public TextField shelfName;
    public ComboBox collectionCombobox;
    Stage stage = new Stage();
    AppContext appContext = new AppContext();
    private Runnable onSave;


    public void init(Stage stage, AppContext appContext) {
        this.appContext = appContext;

        Platform.runLater(() -> {
            this.stage = (Stage) shelfName.getScene().getWindow();
            this.stage.setWidth(500);
            this.stage.setHeight(500);
        });



        if(appContext != null && appContext.getCollectionService() != null && appContext.getCollectionService().getCollection() != null) {
            for (Collection collection : appContext.getCollectionService().getCollection()) {
                collectionCombobox.getItems().add(collection.getName());
            }
        } else {
            collectionCombobox.setPromptText("No collections available");
            collectionCombobox.setDisable(true);
        }
    }


    public void onAddClicked(ActionEvent actionEvent) {
        String name = shelfName.getText();
        String collectionName = (String) collectionCombobox.getValue();

        if(name == null || name.trim().isEmpty()) {
            // Show error message
            return;
        }

        if(collectionName == null) {
            // Show error message
            return;
        }

        Collection selectedCollection = appContext.getCollectionService().getCollection().stream()
                .filter(c -> c.getName().equals(collectionName))
                .findFirst()
                .orElse(null);

        if(selectedCollection == null) {
            // Show error message
            return;
        }

        // Adaugă raftul în lista globală și obține ID-ul
        int shelfId = appContext.getShelfService().addShelf(name);
        Shelf shelf = appContext.getShelfService().getShelfById(shelfId);

        // Adaugă ID-ul raftului în colecție
        boolean addedToCollection = appContext.getCollectionService().addShelf(selectedCollection, shelf);

        if(addedToCollection) {
            stage.close();
        } else {
            // Show error message
        }
    }

    public void addEditingShelf(Shelf shelf) {
    }

    public void setOnSave(Runnable onSave) {
        this.onSave = onSave;
    }
}
