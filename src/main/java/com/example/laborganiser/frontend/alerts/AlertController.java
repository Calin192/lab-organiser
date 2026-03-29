package com.example.laborganiser.frontend.alerts;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AlertController {

    @FXML
    private Label titleLabel;

    @FXML
    private Label messageLabel;

    private Stage stage;

    public void setData(String title, String message, Stage stage) {
        titleLabel.setText(title);
        messageLabel.setText(message);
        this.stage = stage;
    }

    @FXML
    private void onOkClicked() {
        if (stage != null) {
            stage.close();
        }
    }
}