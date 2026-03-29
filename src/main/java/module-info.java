module com.example.laborganiser {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.laborganiser to javafx.fxml;
    exports com.example.laborganiser;
}