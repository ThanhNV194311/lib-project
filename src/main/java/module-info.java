module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;

    exports com.example.Controller;
    exports com.example.Models;
    exports com.example.DTO;
    exports com.example;
    exports com.example.Controller.SubController;
    exports com.example.Service.SubServices;
    exports com.example.Helper;

    opens com.example to javafx.fxml;
    opens com.example.Controller to javafx.fxml;
    opens com.example.Controller.SubController to javafx.fxml;
    opens com.example.Service.SubServices to javafx.fxml;
    opens com.example.DTO to javafx.fxml;
    opens com.example.Helper to javafx.fxml;
}
