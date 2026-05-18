package com.example.laborganiser.frontend.vial;

import com.example.laborganiser.app.AppContext;
import com.example.laborganiser.backend.shelf.Shelf;
import com.example.laborganiser.frontend.alerts.AlertWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class VialAddController {

    @FXML
    public TextField vialNameField;
    public ComboBox shelfComboBox;
    @FXML
    private ToggleButton plasticBtn;

    @FXML
    private ToggleButton glassBtn;

    private ToggleGroup materialGroup;
    @FXML
    public TextField vialShapeField;
    @FXML
    public TextField vialVolumeField;
    @FXML
    public ComboBox<String> vialUnitField;
    @FXML
    public TextField vialColorField;
    @FXML
    public TextField vialCapField;
    @FXML
    public TextField vialCapColorField;
    @FXML
    public TextArea vialDescriptionField;
    public Button addButton;
    private Stage stage;
    private AppContext appContext;

    private AlertWindow alert;

    public void onAddVialClicked(ActionEvent actionEvent) {

        alert = new AlertWindow();

        Toggle selected = materialGroup.getSelectedToggle();
        String material = null;

        if (selected != null) {
            material = ((ToggleButton) selected).getText(); // "PLASTIC" sau "GLASS"
        }




        String selectedShelfName = shelfComboBox.getValue().toString();
        Shelf selectedShelf = null;
        if (selectedShelfName != null && appContext.getShelfService() != null) {
            selectedShelf = appContext.getShelfService().getAllShelves().stream()
                    .filter(shelf -> shelf.getName().equals(selectedShelfName))
                    .findFirst()
                    .orElse(null);
        }


        if (selectedShelf == null) {
            alert.showAlert("Error", "Please select a shelf.");
            return;
        }

        int vialId = appContext.getVialService().addVial(
                vialNameField.getText(),
                material,
                vialShapeField.getText(),
                vialVolumeField.getText(),
                vialUnitField.getValue(),
                vialColorField.getText(),
                vialCapField.getText(),
                vialCapColorField.getText(),
                vialDescriptionField.getText(),
                appContext.getCurrentUser().getUsername()
        );

        appContext.getShelfService().addVial(selectedShelf, vialId);

        Stage stage = (Stage) vialNameField.getScene().getWindow();
        stage.close();
    }




    public void init(Stage stage, AppContext appContext) {
        this.stage = stage;
        this.appContext = appContext;

        addButton.setDisable(true);

        materialGroup = new ToggleGroup();
        plasticBtn.setToggleGroup(materialGroup);
        glassBtn.setToggleGroup(materialGroup);


        vialUnitField.getItems().addAll("μl","ml","cl","dl","l");


        if (appContext != null && appContext.getShelfService() != null && appContext.getShelfService().getAllShelves() != null) {
            for (Shelf shelf : appContext.getShelfService().getAllShelves()) {
                shelfComboBox.getItems().add(shelf.getName());
            }
        } else {
            shelfComboBox.setPromptText("No shelves available");
            shelfComboBox.setDisable(true);
        }
        vialNameField.textProperty().addListener((obs, oldVal, newVal) -> validate());
        vialShapeField.textProperty().addListener((obs, oldVal, newVal) -> validate());
        vialVolumeField.textProperty().addListener((obs, oldVal, newVal) -> validate());
        vialUnitField.valueProperty().addListener((obs, oldVal, newVal) -> validate());
        vialColorField.textProperty().addListener((obs, oldVal, newVal) -> validate());
        vialCapField.textProperty().addListener((obs, oldVal, newVal) -> validate());
        vialCapColorField.textProperty().addListener((obs, oldVal, newVal) -> validate());

        materialGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> validate());

    }

    public void onBackButtonClick(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    private void validate() {
        String name = vialNameField.getText().trim();
        String shape = vialShapeField.getText().trim();
        String volume = vialVolumeField.getText().trim();
        String unit = vialUnitField.getValue();
        String color = vialColorField.getText().trim();
        String cap = vialCapField.getText().trim();
        String capColor = vialCapColorField.getText().trim();

        // Verifică dacă toate câmpurile sunt completate și materialul e selectat
        boolean allFieldsFilled = !name.isEmpty() && !shape.isEmpty() && !volume.isEmpty()
                && unit != null && !color.isEmpty() && !cap.isEmpty() && !capColor.isEmpty();

        boolean materialSelected = materialGroup.getSelectedToggle() != null;

        addButton.setDisable(!(allFieldsFilled && materialSelected));
    }
}
