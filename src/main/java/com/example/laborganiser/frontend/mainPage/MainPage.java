package com.example.laborganiser.frontend.mainPage;

import com.example.laborganiser.app.AppContext;
import com.example.laborganiser.backend.Observer;
import com.example.laborganiser.backend.collections.Collection;
import com.example.laborganiser.backend.vials.Vial;
import com.example.laborganiser.frontend.collectionAdd.CollectionAddController;
import com.example.laborganiser.frontend.shelfAdd.ShelfAddController;
import com.example.laborganiser.frontend.vialAdd.VialAddController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPage implements Observer {

    public TableView tableView;
    public TableColumn name;
    public TableColumn material;
    public TableColumn size;
    public TableColumn color;
    public TableColumn owner;
    public Label paginationLabel;
    public Label paginationButtonLabel;
    private Stage stage;

    private AppContext appContext;

    public void init(Stage stage, AppContext appContext) {
        this.stage = stage;
        this.appContext = appContext;

        appContext.getCollectionService().addObserver(this);
        appContext.getVialService().addObserver(this);

        stage.setWidth(appContext.getWidth());
        stage.setHeight(appContext.getHeight());
        stage.centerOnScreen();
        
        initializeTableColumns();
        load();
    }

    private void initializeTableColumns() {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        material.setCellValueFactory(new PropertyValueFactory<>("material"));
        size.setCellValueFactory(new PropertyValueFactory<>("size"));
        color.setCellValueFactory(new PropertyValueFactory<>("color"));
        owner.setCellValueFactory(new PropertyValueFactory<>("owner"));
    }

    // private int id;
    //    private String name;
    //    private String material;
    //    private String shape;
    //    private String size;
    //    private String unit;
    //    private String color;
    //    private String cap;
    //    private String capColor;
    //    private String description;
    //    private String ownder;
    private void load() {
        paginationLabel.setText("Loading...");

        tableView.getItems().clear();
        for (Vial vial : appContext.getVialService().getVials()) {
            tableView.getItems().add(vial);
        }

        setPaginationLabel();
    }

    private void setPaginationLabel(){
        int vialCount =  appContext.getVialService().getVials().size();
        if(vialCount == 0) {
            paginationLabel.setText("No vials found");
            paginationButtonLabel.setText("Page 1");
        }
        else{
            if(vialCount < 8){
                paginationLabel.setText("Viewing " + vialCount   + " vials");
                paginationButtonLabel.setText("Page 1");
            }
            else{
                paginationLabel.setText("Viewing 8 out of "  + vialCount + " vials");
                paginationButtonLabel.setText("Page 1 of " + vialCount/8);
            }
        }
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
        load();
    }


    public void onImageClick(ActionEvent actionEvent) {
        Stage stage = new Stage();

        var resource = getClass().getResource("/Images/Cub calin.png");
        if (resource == null) {

            return;
        }

        String imagePath = resource.toExternalForm();

        ImageView imageView = new ImageView(new Image(imagePath));
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(400);

        StackPane root = new StackPane(imageView);
        root.setStyle("-fx-background-color: red;");

        Scene scene = new Scene(root, 400, 400);

        stage.setTitle("Image");
        stage.setScene(scene);
        stage.show();
    }
}
