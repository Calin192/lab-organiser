package com.example.laborganiser.frontend.vial;

import com.example.laborganiser.app.AppContext;
import com.example.laborganiser.backend.shelf.Shelf;
import com.example.laborganiser.backend.vials.Vial;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

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
            colorLabel.setText(vial.getColor() != null ? vial.getColor() : "N/A");
//            capLabel.setText(vial.getCap() != null ? vial.getCap() : "N/A");
//            capColorLabel.setText(vial.getCapColor() != null ? vial.getCapColor() : "N/A");
            ownerLabel.setText(vial.getOwner() != null ? vial.getOwner() : "N/A");
            descriptionArea.setText(vial.getDescription() != null ? vial.getDescription() : "No description");
            descriptionArea.setEditable(false);
        }
    }

    @FXML
    public void onBackButtonClick() {
        stage.close();
    }
}
