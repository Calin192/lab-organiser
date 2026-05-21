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
    opens com.example.laborganiser.frontend.vial to javafx.fxml;
    opens com.example.laborganiser.backend.vials to com.google.gson,javafx.fxml,javafx.base;
    opens com.example.laborganiser.app to javafx.fxml;
    opens com.example.laborganiser.backend.collections to com.google.gson,javafx.base;
    opens com.example.laborganiser.backend.shelf to com.google.gson,javafx.base;
    opens com.example.laborganiser.frontend.shelf to javafx.fxml;
    opens com.example.laborganiser.frontend.collection to javafx.fxml;

            //com.example.laborganiser does not open com.example.laborganiser.frontend.mainPage to javafx.base


    exports com.example.laborganiser;
}
