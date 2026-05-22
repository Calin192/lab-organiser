package com.example.laborganiser.frontend.vial;

import com.example.laborganiser.app.AppContext;
import com.example.laborganiser.backend.shelf.Shelf;
import com.example.laborganiser.backend.vials.Vial;
import com.example.laborganiser.frontend.alerts.AlertWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
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
    public ColorPicker vialColorField;
    @FXML
    public TextField vialCapField;
    @FXML
    public ColorPicker vialCapColorField;
    @FXML
    public TextArea vialDescriptionField;
    public Button addButton;
    private Stage stage;
    private AppContext appContext;

    private AlertWindow alert;

    private Vial editingVial = null;
    private Runnable onSave;

    private Shelf previousShelf = null;


    public void addEditingVial(Vial vial) {

        previousShelf = appContext.getShelfService().getShelf(vial);

        this.editingVial = vial;
        vialNameField.setText(vial.getName());
        vialShapeField.setText(vial.getShape());
        shelfComboBox.setValue(appContext.getShelfService().getShelf(vial).getName());
        if(vial.getMaterial().equals("PLASTIC")){
            materialGroup.selectToggle(plasticBtn);
        }else{
            materialGroup.selectToggle(glassBtn);
        }
        vialShapeField.setText(vial.getShape());
        vialVolumeField.setText(vial.getSize());
        vialUnitField.setValue(vial.getUnit());


        vialCapField.setText(vial.getCap());

        vialCapColorField.setValue(Color.web(normalizeHex(vial.getCapColor())));
        vialColorField.setValue(Color.web(normalizeHex(vial.getColor())));

        vialDescriptionField.setText(vial.getDescription());
    }

    private String normalizeHex(String hex) {
        if (hex == null || hex.isBlank()) {
            return "#FFFFFF";
        }

        hex = hex.trim();

        if (!hex.startsWith("#")) {
            hex = "#" + hex;
        }

        return hex;
    }
    public void onAddVialClicked(ActionEvent actionEvent) {

        alert = new AlertWindow();




        if(editingVial == null) {

            Toggle selected = materialGroup.getSelectedToggle();
            String material = null;

            if (selected != null) {
                material = ((ToggleButton) selected).getText();
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


            String colorHex = colorToHex(vialColorField.getValue());
            String capColorHex = colorToHex(vialCapColorField.getValue());

            int vialId = appContext.getVialService().addVial(
                    vialNameField.getText(),
                    material,
                    vialShapeField.getText(),
                    vialVolumeField.getText(),
                    vialUnitField.getValue(),
                    colorHex,
                    vialCapField.getText(),
                    capColorHex,
                    vialDescriptionField.getText(),
                    appContext.getCurrentUser().getUsername()
            );

            appContext.getShelfService().addVial(selectedShelf, vialId);
        }
        else{
            Vial vial = new Vial();
            vial.setId(editingVial.getId());
            vial.setName(vialNameField.getText());
            Toggle selected = materialGroup.getSelectedToggle();
            String material = null;

            if (selected != null) {
                material = ((ToggleButton) selected).getText();
            }
            vial.setMaterial(material);
            vial.setShape(vialShapeField.getText());
            vial.setSize(vialVolumeField.getText());
            vial.setUnit(vialUnitField.getValue());
            vial.setCap(vialCapField.getText());

            vial.setColor(colorToHex(vialColorField.getValue()));
            vial.setCapColor(colorToHex(vialCapColorField.getValue()));

            vial.setDescription(vialDescriptionField.getText());

            String selectedShelfName = shelfComboBox.getValue().toString();

            Shelf selectedShelf = null;
            if (selectedShelfName != null && appContext.getShelfService() != null) {
                selectedShelf = appContext.getShelfService().getAllShelves().stream()
                        .filter(shelf -> shelf.getName().equals(selectedShelfName))
                        .findFirst()
                        .orElse(null);
            }


            appContext.getShelfService().removeVial(vial.getId(), previousShelf);

            appContext.getShelfService().addVial(selectedShelf, vial.getId());

            appContext.getVialService().updateVial(vial);

        }
        Stage stage = (Stage) vialNameField.getScene().getWindow();

        if (onSave != null) {
            onSave.run();
        }

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
        vialColorField.valueProperty().addListener((obs, oldVal, newVal) -> validate());
        vialCapField.textProperty().addListener((obs, oldVal, newVal) -> validate());
        vialCapColorField.valueProperty().addListener((obs, oldVal, newVal) -> validate());

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
        Color color = vialColorField.getValue();
        String cap = vialCapField.getText().trim();
        Color capColor = vialCapColorField.getValue();


        boolean allFieldsFilled = !name.isEmpty() && !shape.isEmpty() && !volume.isEmpty()
                && unit != null && color != null && !cap.isEmpty() && capColor != null;

        boolean materialSelected = materialGroup.getSelectedToggle() != null;

        addButton.setDisable(!(allFieldsFilled && materialSelected));
    }

    private String colorToHex(Color color) {
        if (color == null) return "#000000";
        int r = (int) Math.round(color.getRed() * 255);
        int g = (int) Math.round(color.getGreen() * 255);
        int b = (int) Math.round(color.getBlue() * 255);
        return String.format("#%02X%02X%02X", r, g, b);
    }

    public void setOnSave(Runnable onSave) {
        this.onSave = onSave;
    }

}
