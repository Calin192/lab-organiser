package com.example.laborganiser.frontend.collection;

import com.example.laborganiser.app.AppContext;
import com.example.laborganiser.backend.collections.Collection;
import com.example.laborganiser.backend.shelf.Shelf;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class CollectionViewController {
    @FXML
    public Label nameLabel;
    @FXML
    public TableView<Shelf> collectionTable;
    @FXML
    public TableColumn<Shelf, String> nameColumn;
    @FXML
    public TableColumn<Shelf, Integer> shelfCountColumn;
    @FXML
    public javafx.scene.control.Label shelfCountLabel;

    private AppContext appContext;
    private Stage stage;
    private Collection collection;

    private Runnable onDelete;

    public void onBackButtonClick(ActionEvent actionEvent) {
        if (stage != null) {
            stage.close();
        }
    }

    public void init(Stage stage, AppContext appContext, Collection collection) {
        this.appContext = appContext;
        this.stage = stage;
        this.collection = collection;


        // initialize column factories before populating the table
        initializeTableColumns();
        displayCollectionDetails();
    }

    private void displayCollectionDetails() {
        if(collection!=null){
            nameLabel.setText(collection.getName() != null ? collection.getName() : "N/A");

            int shelfCount = (collection.getShelves() == null) ? 0 : collection.getShelves().size();
            if (shelfCountLabel != null) {
                shelfCountLabel.setText(String.valueOf(shelfCount));
            }

            setTable();
        }
    }
    private void initializeTableColumns() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        // show number of vials on each shelf
        shelfCountColumn.setCellValueFactory(cellData -> {
            Object value = cellData.getValue();
            if (value instanceof Shelf) {
                Shelf s = (Shelf) value;
                return new javafx.beans.property.ReadOnlyObjectWrapper<>(s.getVialCount());
            }
            return new javafx.beans.property.ReadOnlyObjectWrapper<>(0);
        });

    }

    private void setTable(){
        collectionTable.getItems().clear();
        List<Integer> shelfIds = collection.getShelves();

        if (shelfIds == null || shelfIds.isEmpty()) {
            return;
        }

        List<Shelf> shelves = shelfIds.stream()
                .map(id -> appContext.getShelfService().getShelfById(id))
                .filter(shelf -> shelf != null)
                .toList();

        collectionTable.getItems().addAll(shelves);

    }

    public void deleteCollection(ActionEvent actionEvent) {
        appContext.getCollectionService().removeCollection(collection);
        stage.close();

        if (onDelete != null) {
            onDelete.run();
        }

    }

    public void setOnDelete(Runnable onDelete) {
        this.onDelete = onDelete;
    }
}
