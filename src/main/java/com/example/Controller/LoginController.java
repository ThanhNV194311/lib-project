package com.example.Controller;

import com.example.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class LoginController {

    @FXML
    private Button btn;

    public void btnAction() throws IOException {
        App.setRoot("Node");
    }
}
