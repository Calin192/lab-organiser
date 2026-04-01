package com.example.laborganiser.frontend.vialAdd;

import com.example.laborganiser.app.AppContext;
import com.example.laborganiser.backend.users.UserService;
import com.example.laborganiser.backend.vials.VialService;
import com.example.laborganiser.frontend.alerts.AlertWindow;
import com.example.laborganiser.frontend.auth.AuthController;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class VialAddController {

    @FXML
    public TextField vialNameField;
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


        appContext.getVialService().addVial(
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
        Stage stage = (Stage) vialNameField.getScene().getWindow();
        stage.close();
    }


    public void hideAll() {
        plasticBtn.setVisible(false);
        glassBtn.setVisible(false);
        vialShapeField.setVisible(false);
        vialVolumeField.setVisible(false);
        vialUnitField.setVisible(false);
        vialColorField.setVisible(false);
        vialCapField.setVisible(false);
        vialCapColorField.setVisible(false);
        vialDescriptionField.setVisible(false);
    }

    @FXML
    public void initialize() {
        hideAll();
        //material choosing stuff
        materialGroup = new ToggleGroup();
        plasticBtn.setToggleGroup(materialGroup);
        glassBtn.setToggleGroup(materialGroup);

        //volume choosing stuff
        vialUnitField.getItems().addAll("μl","ml","cl","dl","l");


        vialNameField.setOnAction(e -> {showNext(plasticBtn);showNext(glassBtn);});


        plasticBtn.setOnAction(e -> showNext(vialShapeField));
        glassBtn.setOnAction(e -> showNext(vialShapeField));

        vialShapeField.setOnAction(e -> {showNext(vialVolumeField);showNext(vialUnitField);});

        //vialVolumeField.setOnAction(e -> showNext(vialUnitField));
        vialUnitField.setOnAction(e -> showNext(vialColorField));
        vialColorField.setOnAction(e -> showNext(vialCapField));
        vialCapField.setOnAction(e -> showNext(vialCapColorField));
        vialCapColorField.setOnAction(e -> showNext(vialDescriptionField));
        vialDescriptionField.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case ENTER -> {
                    addButton.setVisible(true);
                    addButton.requestFocus();
                }
            }
        });
    }

    public void showNext(javafx.scene.Node node){
        node.setOpacity(0);
        node.setVisible(true);

        FadeTransition ft = new FadeTransition(Duration.millis(300), node);
        ft.setToValue(1);
        ft.play();

        node.requestFocus();
    }

    public void init(Stage stage, AppContext appContext) {
        this.stage = stage;
        this.appContext = appContext;
    }

    public void onBackButtonClick(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
