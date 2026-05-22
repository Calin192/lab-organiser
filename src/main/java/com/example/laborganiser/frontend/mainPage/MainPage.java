package com.example.laborganiser.frontend.mainPage;

import com.example.laborganiser.app.AppContext;
import com.example.laborganiser.backend.Observer;
import com.example.laborganiser.backend.collections.Collection;
import com.example.laborganiser.backend.shelf.Shelf;
import com.example.laborganiser.backend.vials.Vial;
import com.example.laborganiser.frontend.collection.CollectionAddController;
import com.example.laborganiser.frontend.collection.CollectionViewController;
import com.example.laborganiser.frontend.shelf.ShelfAddController;
import com.example.laborganiser.frontend.shelf.ShelfViewController;
import com.example.laborganiser.frontend.vial.VialAddController;
import com.example.laborganiser.frontend.vial.VialViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    public Button vialFilterBtn;
    public Button shelfFilterBtn;
    public Button collectionFilterBtn;
    @FXML
    public Button addProductBtn;
    private Stage stage;

    private String currentSearchTerm = " vials";

    private List<Vial> allVials = new ArrayList<>();
    private List<Vial> currentDisplay = new ArrayList<>();

    private AppContext appContext;

    private static final int ITEMS_PER_PAGE = 8;
    private int currentPage = 0;


    private enum FilterType { VIALS, SHELVES, COLLECTIONS }
    private FilterType currentFilter = FilterType.VIALS;

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
            String q = (newValue == null) ? "" : newValue.toLowerCase();

            if (currentFilter == FilterType.VIALS) {
                List<Vial> filtered = appContext.getVialService()
                        .getVials()
                        .stream()
                        .filter(v -> v.getName() != null && v.getName().toLowerCase().contains(q))
                        .toList();
                loadPage(filtered);
            } else if (currentFilter == FilterType.SHELVES) {
                List<Vial> placeholders = appContext.getShelfService().getAllShelves().stream()
                        .filter(s -> s.getName() != null && s.getName().toLowerCase().contains(q))
                        .map(shelf -> {
                            Vial p = new Vial();
                            p.setName(shelf.getName());
                            p.setMaterial("Shelf");
                            int count = (shelf.getVials() == null) ? 0 : shelf.getVials().size();
                            p.setOwner(String.valueOf(count));
                            return p;
                        })
                        .toList();
                loadPage(placeholders);
            } else if (currentFilter == FilterType.COLLECTIONS) {
                List<Vial> placeholders = appContext.getCollectionService().getCollection().stream()
                        .filter(c -> c.getName() != null && c.getName().toLowerCase().contains(q))
                        .map(col -> {
                            Vial p = new Vial();
                            p.setName(col.getName());
                            p.setMaterial("Collection");
                            int shelfCount = (col.getShelves() == null) ? 0 : col.getShelves().size();
                            p.setOwner(String.valueOf(shelfCount));
                            return p;
                        })
                        .toList();
                loadPage(placeholders);
            }
        });

        initializeTableColumns();


        updateFilterButtons();


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

    private void configureColumnsForVials() {
        tableView.getColumns().clear();

        TableColumn<Vial, String> nameCol = new TableColumn<>("Name");
        nameCol.setPrefWidth(150);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Vial, String> materialCol = new TableColumn<>("Material");
        materialCol.setPrefWidth(120);
        materialCol.setCellValueFactory(new PropertyValueFactory<>("material"));

        TableColumn<Vial, String> sizeCol = new TableColumn<>("Size");
        sizeCol.setPrefWidth(180);
        sizeCol.setCellValueFactory(cell -> {
            Vial v = cell.getValue();
            String sizeText = (v.getSize() == null ? "" : v.getSize()) + (v.getUnit() != null ? " " + v.getUnit() : "");
            return new javafx.beans.property.ReadOnlyObjectWrapper<>(sizeText);
        });

        TableColumn<Vial, String> colorCol = new TableColumn<>("Color");
        colorCol.setPrefWidth(120);
        colorCol.setCellValueFactory(new PropertyValueFactory<>("color"));

        TableColumn<Vial, String> ownerCol = new TableColumn<>("Owner");
        ownerCol.setPrefWidth(150);
        ownerCol.setCellValueFactory(new PropertyValueFactory<>("owner"));

        tableView.getColumns().addAll(nameCol, materialCol, sizeCol, colorCol, ownerCol);
    }

    private void configureColumnsForShelves() {
        tableView.getColumns().clear();

        TableColumn<Vial, String> nameCol = new TableColumn<>("Name");
        nameCol.setPrefWidth(300);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Vial, String> countCol = new TableColumn<>("Vial Count");
        countCol.setPrefWidth(200);
        // count stored in owner field for placeholders
        countCol.setCellValueFactory(cell -> new javafx.beans.property.ReadOnlyObjectWrapper<>(cell.getValue().getOwner()));

        tableView.getColumns().addAll(nameCol, countCol);
    }

    private void configureColumnsForCollections() {
        tableView.getColumns().clear();

        TableColumn<Vial, String> nameCol = new TableColumn<>("Name");
        nameCol.setPrefWidth(300);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Vial, String> shelfCountCol = new TableColumn<>("Shelf Count");
        shelfCountCol.setPrefWidth(200);
        // shelf count stored in owner field for placeholders
        shelfCountCol.setCellValueFactory(cell -> new javafx.beans.property.ReadOnlyObjectWrapper<>(cell.getValue().getOwner()));

        tableView.getColumns().addAll(nameCol, shelfCountCol);
    }

    private void updateTableColumnsForCurrentFilter() {
        if (currentFilter == FilterType.VIALS) configureColumnsForVials();
        else if (currentFilter == FilterType.SHELVES) configureColumnsForShelves();
        else if (currentFilter == FilterType.COLLECTIONS) configureColumnsForCollections();
    }


    private void load() {
        paginationLabel.setText("Loading...");
        currentPage = 0;
        loadPage(null);
        setPaginationLabel();


        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Object selectedItem = tableView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    if (currentFilter == FilterType.VIALS) {
                        onVialClicked((Vial) selectedItem);
                    } else if (currentFilter == FilterType.SHELVES) {
                        onShelfClicked((Shelf) appContext.getShelfService().getAllShelves().stream()
                                .filter(s -> s.getName().equals(((Vial) selectedItem).getName()))
                                .findFirst()
                                .orElse(null));
                    } else if (currentFilter == FilterType.COLLECTIONS) {
                        onCollectionClicked((Collection) appContext.getCollectionService().getCollection().stream()
                                .filter(c -> c.getName().equals(((Vial) selectedItem).getName()))
                                .findFirst()
                                .orElse(null));
                    }
                }
            }
        });
    }

    private void loadPage(List<Vial> vials) {

        tableView.getItems().clear();


        List<Vial> dataToDisplay = (vials != null) ? vials : allVials;

        currentDisplay = dataToDisplay;

        if (dataToDisplay == null || dataToDisplay.isEmpty()) {
            dataToDisplay = new ArrayList<>();
        }

        int startIndex = currentPage * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, dataToDisplay.size());

        if (startIndex >= dataToDisplay.size()) {
            return;
        }

        tableView.getItems().addAll(
                dataToDisplay.subList(startIndex, endIndex)
        );

        setPaginationLabel();
    }

    private void setPaginationLabel(){
        int vialCount = (currentDisplay == null) ? 0 : currentDisplay.size();
        int totalPages = (vialCount + ITEMS_PER_PAGE - 1) / ITEMS_PER_PAGE;

        if(vialCount == 0) {
            paginationLabel.setText("No vials found");
            paginationButtonLabel.setText("Page 1");
        } else {
            int startIndex = currentPage * ITEMS_PER_PAGE + 1;
            int endIndex = Math.min((currentPage + 1) * ITEMS_PER_PAGE, vialCount);
            paginationLabel.setText("Viewing " + startIndex + "-" + endIndex + " out of " + vialCount + currentSearchTerm);
            paginationButtonLabel.setText("Page " + (currentPage + 1) + " of " + totalPages);
        }
    }

    @FXML
    public void nextPage() {
        int totalPages = (allVials.size() + ITEMS_PER_PAGE - 1) / ITEMS_PER_PAGE;
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
        currentPage = 0;

        switch (currentFilter) {
            case VIALS:
                filterVials();
                break;
            case SHELVES:
                filterShelves();
                break;
            case COLLECTIONS:
                filterCollections();
                break;
        }
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


    @FXML
    public void filterVials() {
        currentSearchTerm=" vials";
        currentFilter = FilterType.VIALS;
        currentPage = 0;
        updateFilterButtons();
        allVials = new ArrayList<>(appContext.getVialService().getVials());
        searchField.setDisable(false);
        searchField.clear();
        loadPage(null);
        setPaginationLabel();
    }

    @FXML
    public void filterShelves() {
        currentSearchTerm=" shelves";
        currentFilter = FilterType.SHELVES;
        currentPage = 0;
        updateFilterButtons();
        tableView.getItems().clear();
        allVials.clear();
        searchField.setDisable(false);
        searchField.clear();

        for (var shelf : appContext.getShelfService().getAllShelves()) {
            Vial vialPlaceholder = new Vial();
            vialPlaceholder.setName(shelf.getName());
            vialPlaceholder.setMaterial("Shelf");
            int count = (shelf.getVials() == null) ? 0 : shelf.getVials().size();
            vialPlaceholder.setOwner(String.valueOf(count));
            allVials.add(vialPlaceholder);
        }

        loadPage(null);
        setPaginationLabel();
    }

    @FXML
    public void filterCollections() {
        currentSearchTerm=" collections";
        currentFilter = FilterType.COLLECTIONS;
        currentPage = 0;
        updateFilterButtons();
        tableView.getItems().clear();
        allVials.clear();
        searchField.setDisable(false);
        searchField.clear();

        for (var collection : appContext.getCollectionService().getCollection()) {
            Vial vialPlaceholder = new Vial();
            vialPlaceholder.setName(collection.getName());
            vialPlaceholder.setMaterial("Collection");
            int shelfCount = (collection.getShelves() == null) ? 0 : collection.getShelves().size();
            vialPlaceholder.setOwner(String.valueOf(shelfCount));
            allVials.add(vialPlaceholder);
        }

        loadPage(null);
        setPaginationLabel();
    }

    private void updateFilterButtons() {

        boolean isVial = currentFilter == FilterType.VIALS;
        boolean isShelf = currentFilter == FilterType.SHELVES;
        boolean isCollection = currentFilter == FilterType.COLLECTIONS;


        if (isVial) {
            vialFilterBtn.getStyleClass().remove("button-gray");
            if (!vialFilterBtn.getStyleClass().contains("button-blue")) {
                vialFilterBtn.getStyleClass().add("button-blue");
            }
        } else {
            vialFilterBtn.getStyleClass().remove("button-blue");
            if (!vialFilterBtn.getStyleClass().contains("button-gray")) {
                vialFilterBtn.getStyleClass().add("button-gray");
            }
        }


        if (isShelf) {
            shelfFilterBtn.getStyleClass().remove("button-gray");
            if (!shelfFilterBtn.getStyleClass().contains("button-blue")) {
                shelfFilterBtn.getStyleClass().add("button-blue");
            }
        } else {
            shelfFilterBtn.getStyleClass().remove("button-blue");
            if (!shelfFilterBtn.getStyleClass().contains("button-gray")) {
                shelfFilterBtn.getStyleClass().add("button-gray");
            }
        }


        if (isCollection) {
            collectionFilterBtn.getStyleClass().remove("button-gray");
            if (!collectionFilterBtn.getStyleClass().contains("button-blue")) {
                collectionFilterBtn.getStyleClass().add("button-blue");
            }
        } else {
            collectionFilterBtn.getStyleClass().remove("button-blue");
            if (!collectionFilterBtn.getStyleClass().contains("button-gray")) {
                collectionFilterBtn.getStyleClass().add("button-gray");
            }
        }

        if (addProductBtn != null) {
            if (isVial) addProductBtn.setText("Add Vial");
            else if (isShelf) addProductBtn.setText("Add Shelf");
            else if (isCollection) addProductBtn.setText("Add Collection");
        }

        updateTableColumnsForCurrentFilter();
    }

    @FXML
    public void onAddProductClick(ActionEvent actionEvent) {
        switch (currentFilter) {
            case VIALS:
                onAddVialClick(actionEvent);
                break;
            case SHELVES:
                onAddShelfClick(actionEvent);
                break;
            case COLLECTIONS:
                onAddCollectionClick(actionEvent);
                break;
        }
    }


    private void onVialClicked(Vial vial) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/laborganiser/frontend/vial/vialView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 700);

            VialViewController controller = fxmlLoader.getController();
            Stage vialStage = new Stage();
            Shelf shelf = appContext.getShelfService().getShelf(vial);

            controller.init(vialStage, appContext, vial, shelf);
            controller.setOnDelete(() -> update());
            controller.setOnUpdate(() ->update());

            vialStage.setTitle("Vial Details");
            vialStage.setScene(scene);
            vialStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onShelfClicked(Shelf shelf) {
        if (shelf != null) {

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/laborganiser/frontend/shelf/shelfView.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 600, 700);

                ShelfViewController controller = fxmlLoader.getController();
                Stage shelfStage = new Stage();
                Collection collection = appContext.getCollectionService().getCollection(shelf);

                controller.init(shelfStage, appContext, shelf,collection);
                controller.setOnDelete(() -> update());
                controller.setOnUpdate(() ->update());

                shelfStage.setTitle("Shelf Details");
                shelfStage.setScene(scene);
                shelfStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void onCollectionClicked(Collection collection) {
        if (collection != null) {


            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/laborganiser/frontend/collection/CollectionView.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 600, 700);

                CollectionViewController controller = fxmlLoader.getController();
                Stage shelfStage = new Stage();
                controller.init(shelfStage, appContext, collection);
                controller.setOnDelete(() -> update());

                shelfStage.setTitle("Collection Details");
                shelfStage.setScene(scene);
                shelfStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
