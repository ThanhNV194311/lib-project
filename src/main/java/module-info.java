module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires atlantafx.base;
    requires java.desktop;


    exports com.example.Controller;
    exports com.example;

    opens com.example to javafx.fxml;
    opens com.example.Controller to javafx.fxml;
}
