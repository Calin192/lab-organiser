module com.example.laborganiser {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires javafx.media;
    requires java.desktop;

    opens com.example.laborganiser to javafx.fxml;
    opens com.example.laborganiser.frontend.auth to javafx.fxml;
    opens com.example.laborganiser.backend.users to com.google.gson;
    opens com.example.laborganiser.frontend.alerts to javafx.fxml;
    opens com.example.laborganiser.frontend.mainPage to javafx.fxml;
    opens com.example.laborganiser.frontend.vialAdd to javafx.fxml;
    opens com.example.laborganiser.backend.vials to com.google.gson;
    opens com.example.laborganiser.app to javafx.fxml;
    opens com.example.laborganiser.backend.collections to com.google.gson;
    opens com.example.laborganiser.backend.shelf to com.google.gson;
    opens com.example.laborganiser.frontend.shelfAdd to javafx.fxml;
    opens com.example.laborganiser.frontend.collectionAdd to javafx.fxml;

    exports com.example.laborganiser;
}
