module com.example.laborganiser {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires javafx.media;

    opens com.example.laborganiser to javafx.fxml;
    opens com.example.laborganiser.frontend.auth to javafx.fxml;
    opens com.example.laborganiser.backend.users to com.google.gson;
    opens com.example.laborganiser.frontend.alerts to javafx.fxml;
    opens com.example.laborganiser.frontend.mainPage to javafx.fxml;
    opens com.example.laborganiser.frontend.vialAdd to javafx.fxml;
    opens com.example.laborganiser.backend.vials to com.google.gson;
    opens com.example.laborganiser.app to javafx.fxml;
    opens com.example.laborganiser.backend.collections to javafx.fxml;

    exports com.example.laborganiser;
}
