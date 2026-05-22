package com.example.laborganiser.frontend.vial;

import com.example.laborganiser.app.AppContext;
import com.example.laborganiser.backend.shelf.Shelf;
import com.example.laborganiser.backend.vials.Vial;
import com.example.laborganiser.frontend.collection.CollectionViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

public class VialViewController {
    public Label shelfLabel;
    @FXML private Label nameLabel;
    @FXML private Label materialLabel;
    //@FXML private Label shapeLabel;
    @FXML private Label sizeLabel;
    @FXML private Label colorLabel;
    @FXML private Label capLabel;
    @FXML private Label capColorLabel;
    @FXML private Label ownerLabel;
    @FXML private TextArea descriptionArea;

    AppContext appContext;
    private Stage stage;
    private Vial vial;
    private Shelf shelf;

    private Runnable onDelete;
    private Runnable onUpdate;

    private VialAddController controller;


    public void init(Stage stage, AppContext appContext, Vial vial, Shelf shelf) {
        this.appContext = appContext;
        this.stage = stage;
        this.vial = vial;
        this.shelf = shelf;

        displayVialDetails();
    }

    private void displayVialDetails() {
        if (vial != null) {
            nameLabel.setText(vial.getName() != null ? vial.getName() : "N/A");
            materialLabel.setText(vial.getMaterial() != null ? vial.getMaterial() : "N/A");
            shelfLabel.setText(shelf.getName() != null ? shelf.getName() : "N/A");
            sizeLabel.setText(vial.getSize() + " " + (vial.getUnit() != null ? vial.getUnit() : ""));

            String colorHex = vial.getColor();
            String norm = normalizeHex(colorHex);
            if (norm != null) {
                colorLabel.setText(norm);
                String textColor = isLight(norm) ? "#000000" : "#FFFFFF";
                colorLabel.setStyle("-fx-background-color: " + norm + "; -fx-text-fill: " + textColor + "; -fx-padding: 6px; -fx-background-radius: 4px;");
            } else {
                colorLabel.setText("N/A");
                colorLabel.setStyle("");
            }


            if (capLabel != null) {
                capLabel.setText(vial.getCap() != null ? vial.getCap() : "N/A");
            }
            if (capColorLabel != null) {
                String capHex = vial.getCapColor();
                String capNorm = normalizeHex(capHex);
                if (capNorm != null) {
                    capColorLabel.setText(capNorm);
                    String capTextColor = isLight(capNorm) ? "#000000" : "#FFFFFF";
                    capColorLabel.setStyle("-fx-background-color: " + capNorm + "; -fx-text-fill: " + capTextColor + "; -fx-padding: 6px; -fx-background-radius: 4px;");
                } else {
                    capColorLabel.setText("N/A");
                    capColorLabel.setStyle("");
                }
            }

            ownerLabel.setText(vial.getOwner() != null ? vial.getOwner() : "N/A");
            descriptionArea.setText(vial.getDescription() != null ? vial.getDescription() : "No description");
            descriptionArea.setEditable(false);
        }
    }

    @FXML
    public void onBackButtonClick() {
        stage.close();
    }

    private String normalizeHex(String hex) {
        if (hex == null) return null;
        String s = hex.trim();
        if (s.isEmpty()) return null;
        if (s.startsWith("#")) {
            s = s.substring(1);
        }
        if (s.length() == 6 || s.length() == 8) {
            return "#" + s.toUpperCase();
        }
        return null;
    }

    private boolean isLight(String hex) {
        try {
            String s = hex.startsWith("#") ? hex.substring(1) : hex;
            int r = Integer.parseInt(s.substring(0,2),16);
            int g = Integer.parseInt(s.substring(2,4),16);
            int b = Integer.parseInt(s.substring(4,6),16);
            double luminance = 0.299 * r + 0.587 * g + 0.114 * b;
            return luminance > 186;
        } catch (Exception e) {
            return true;
        }
    }

    public void deleteVial(ActionEvent actionEvent) {
        appContext.getVialService().removeVial(vial.getId());
        appContext.getShelfService().removeVial(vial.getId(), shelf);
        stage.close();

        if (onDelete != null) {
            onDelete.run();
        }

    }

    public void setOnDelete(Runnable onDelete) {
        this.onDelete = onDelete;
    }
    public void setOnUpdate(Runnable onUpdate) {
        this.onUpdate = onUpdate;
    }


    public void editVial(ActionEvent actionEvent){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/laborganiser/frontend/vial/vialAdd.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 700);

            VialAddController controller = fxmlLoader.getController();
            Stage shelfStage = new Stage();

            controller.init(shelfStage, appContext);
            controller.addEditingVial(vial);

            controller.setOnSave(() -> {


                Stage currentStage = (Stage) nameLabel.getScene().getWindow();
                currentStage.close();

                if (onUpdate != null) {
                    onUpdate.run();
                }
            });

            shelfStage.setTitle("Vial Details");
            shelfStage.setScene(scene);
            shelfStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
