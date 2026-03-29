module com.example.laborganiser {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens com.example.laborganiser to javafx.fxml;
    opens com.example.laborganiser.auth to javafx.fxml;
    opens com.example.laborganiser.users.domain to com.google.gson;
    opens com.example.laborganiser.alerts to javafx.fxml;

    exports com.example.laborganiser;
}
