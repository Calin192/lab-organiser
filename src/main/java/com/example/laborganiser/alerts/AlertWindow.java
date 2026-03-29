package com.example.laborganiser.alerts;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertWindow {

    public static void showAlert(String title, String message) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    AlertWindow.class.getResource("/com/example/laborganiser/alert/alertWindow.fxml")

            );

            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(title);

            AlertController controller = loader.getController();
            controller.setData(title, message, stage);

            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}