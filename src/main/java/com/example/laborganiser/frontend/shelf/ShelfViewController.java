package com.example.laborganiser.frontend.shelf;

import com.example.laborganiser.app.AppContext;
import com.example.laborganiser.backend.collections.Collection;
import com.example.laborganiser.backend.shelf.Shelf;
import com.example.laborganiser.backend.vials.Vial;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ShelfViewController {
    public Label nameLabel;
    public Label collectionLabel;
    public TableView vialsTable;
    public TableColumn nameColumn;
    public TableColumn materialColumn;
    public TableColumn sizeColumn;

    AppContext appContext;
    Stage stage;
    Shelf shelf;
    Collection collection;

    private Runnable onDelete;


    public void onBackButtonClick(ActionEvent actionEvent) {
        stage.close();
    }

    public void init(Stage stage, AppContext appContext, Shelf shelf, Collection collection) {
        this.appContext = appContext;
        this.stage = stage;
        this.shelf = shelf;
        this.collection = collection;

        displayShelfDetails();
        initializeTableColumns();
    }

    private void initializeTableColumns() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        materialColumn.setCellValueFactory(new PropertyValueFactory<>("material"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));
    }

    private void displayShelfDetails() {
        if(shelf!=null){
            nameLabel.setText(shelf.getName() != null ? shelf.getName() : "N/A");
            collectionLabel.setText(collection.getName() != null ? collection.getName() : "N/A");


            setTable();
        }

    }

    private void setTable(){
        vialsTable.getItems().clear();
        List<Integer> vials = shelf.getVials();


        if (vials == null || vials.isEmpty()) {
            vials = new ArrayList<>();
        }




        vialsTable.getItems().addAll(vials.stream()
                .map(vialId -> appContext.getVialService().getVialById(vialId))
                .filter(vial -> vial != null)
                .toList());

    }

    public void deleteShelf(ActionEvent actionEvent) {
        appContext.getShelfService().removeShelf(shelf);
        stage.close();

        appContext.getCollectionService().removeShelf(shelf, collection);




        if (onDelete != null) {
            onDelete.run();
        }

    }

    public void setOnDelete(Runnable onDelete) {
        this.onDelete = onDelete;
    }

}
