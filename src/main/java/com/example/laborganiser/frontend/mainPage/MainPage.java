package com.example.laborganiser.frontend.mainPage;

import com.example.laborganiser.app.AppContext;
import com.example.laborganiser.backend.Observer;
import com.example.laborganiser.backend.collections.Collection;
import com.example.laborganiser.frontend.collectionAdd.CollectionAddController;
import com.example.laborganiser.frontend.shelfAdd.ShelfAddController;
import com.example.laborganiser.frontend.vialAdd.VialAddController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.IOException;

public class MainPage implements Observer {
    @FXML
    public TilePane collectionContainer;
    private Stage stage;

    private AppContext appContext;

    public void init(Stage stage, AppContext appContext) {
        this.stage = stage;
        this.appContext = appContext;

        appContext.getCollectionService().addObserver(this);

        stage.setWidth(1200);
        stage.setHeight(800);
        stage.centerOnScreen();
        
        loadCollections();
    }

    private void loadCollections() {
        collectionContainer.getChildren().clear();

        var collections = appContext.getCollectionService().getCollection();

        for (var collection : collections) {
            VBox card = createCollectionCard(collection);
            collectionContainer.getChildren().add(card);
        }
    }

    private VBox createCollectionCard(Collection collection) {
        VBox box = new VBox();
        box.getStyleClass().add("collection-card");
        box.setPrefSize(150, 150);

        Label name = new Label(collection.getName());
        name.getStyleClass().add("title");

        box.getChildren().add(name);

        // click event (optional)
        box.setOnMouseClicked(e -> {
            System.out.println("Clicked: " + collection.getName());
        });

        return box;
    }

    public void onAddVialClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/laborganiser/frontend/vial/vialAdd.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 800);

            VialAddController controller = fxmlLoader.getController();

            controller.init(null,appContext);

            Stage stage = new Stage();
            stage.setTitle("Add Vial");
            stage.setScene(scene);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onAddShelfClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/laborganiser/frontend/shelf/shelfAdd.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 800);

            ShelfAddController controller = fxmlLoader.getController();

            controller.init(null,appContext);

            Stage stage = new Stage();
            stage.setTitle("Add Shelf");
            stage.setScene(scene);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void onAddCollectionClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/laborganiser/frontend/collection/collectionAdd.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 800);

            CollectionAddController controller = fxmlLoader.getController();

            controller.init(null,appContext);

            Stage stage = new Stage();
            stage.setTitle("Add Collection");
            stage.setScene(scene);

            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        loadCollections();
    }
}
