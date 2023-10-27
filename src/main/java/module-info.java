module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;

    exports com.example.Controller;
    exports com.example.Models;
    exports com.example;

    opens com.example to javafx.fxml;
    opens com.example.Controller to javafx.fxml;
}
