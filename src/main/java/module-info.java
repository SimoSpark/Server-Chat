module com.example.serverr {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.serverr to javafx.fxml;
    exports com.example.serverr;
}