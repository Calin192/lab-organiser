package com.example.laborganiser.frontend.vialAdd;

import com.example.laborganiser.app.AppContext;
import com.example.laborganiser.backend.users.UserService;
import com.example.laborganiser.backend.vials.VialService;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

public class VialAddController {

    @FXML
    public TextField vialNameField;
    @FXML
    public TextField vialMaterialField;
    @FXML
    public TextField vialShapeField;
    @FXML
    public TextField vialVolumeField;
    @FXML
    public TextField vialUnitField;
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

    public void onAddVialClicked(ActionEvent actionEvent) {
        appContext.getVialService().addVial(
                vialNameField.getText(),
                vialMaterialField.getText(),
                vialShapeField.getText(),
                vialVolumeField.getText(),
                vialUnitField.getText(),
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
        vialMaterialField.setVisible(false);
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

        vialNameField.setOnAction(e -> showNext(vialMaterialField));
        vialMaterialField.setOnAction(e -> showNext(vialShapeField));
        vialShapeField.setOnAction(e -> showNext(vialVolumeField));
        vialVolumeField.setOnAction(e -> showNext(vialUnitField));
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
}
