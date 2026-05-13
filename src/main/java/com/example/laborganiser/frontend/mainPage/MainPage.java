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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MainPage implements Observer {

    public TableView tableView;
    public TableColumn name;
    public TableColumn material;
    public TableColumn size;
    public TableColumn color;
    public TableColumn owner;
    public Label paginationLabel;
    public Label paginationButtonLabel;
    public TextField searchField;
    private Stage stage;

    private List<Vial> allVials = new ArrayList<>();

    private AppContext appContext;

    private static final int ITEMS_PER_PAGE = 8;
    private int currentPage = 0;

    public void init(Stage stage, AppContext appContext) {
        this.stage = stage;
        this.appContext = appContext;

        appContext.getCollectionService().addObserver(this);
        appContext.getShelfService().addObserver(this);
        appContext.getVialService().addObserver(this);

        stage.setWidth(appContext.getWidth());
        stage.setHeight(appContext.getHeight());
        stage.centerOnScreen();

        searchField.textProperty().addListener((obs, oldValue, newValue) -> {

            currentPage = 0;

            List<Vial> filtered = appContext.getVialService()
                    .getVials()
                    .stream()
                    .filter(vial ->
                            vial.getName()
                                    .toLowerCase()
                                    .contains(newValue.toLowerCase())
                    ).toList();
            loadPage(filtered);});
        
        initializeTableColumns();


        allVials = new ArrayList<>(appContext.getVialService().getVials());


        tableView.setPrefHeight(
                51 * 8 + 30
        );

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
    //    private String owner;
    private void load() {
        paginationLabel.setText("Loading...");
        currentPage = 0;
        loadPage(null);
        setPaginationLabel();
    }

    private void loadPage(List<Vial> vials) {

        tableView.getItems().clear();

        if (vials == null) {

            List<Vial> serviceVials = appContext.getVialService().getVials();

            if (serviceVials == null) {
                serviceVials = new ArrayList<>();
            }

            vials = new ArrayList<>(serviceVials);
        }

        int startIndex = currentPage * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, vials.size());

        if (startIndex > endIndex) {
            return;
        }

        tableView.getItems().addAll(
                vials.subList(startIndex, endIndex)
        );

        setPaginationLabel();
    }

    private void setPaginationLabel(){
        int vialCount = allVials.size();
        int totalPages = (vialCount + ITEMS_PER_PAGE - 1) / ITEMS_PER_PAGE;

        if(vialCount == 0) {
            paginationLabel.setText("No vials found");
            paginationButtonLabel.setText("Page 1");
        } else {
            int startIndex = currentPage * ITEMS_PER_PAGE + 1;
            int endIndex = Math.min((currentPage + 1) * ITEMS_PER_PAGE, vialCount);
            paginationLabel.setText("Viewing " + startIndex + "-" + endIndex + " out of " + vialCount + " vials");
            paginationButtonLabel.setText("Page " + (currentPage + 1) + " of " + totalPages);
        }
    }

    @FXML
    public void nextPage() {
        int totalPages = (appContext.getVialService().getVials().size() + ITEMS_PER_PAGE - 1) / ITEMS_PER_PAGE;
        if (currentPage < totalPages - 1) {
            currentPage++;
            loadPage(null);
        }
    }

    @FXML
    public void previousPage() {
        if (currentPage > 0) {
            currentPage--;
            loadPage(null);
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
