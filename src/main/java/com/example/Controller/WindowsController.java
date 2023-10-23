package com.example.Controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class WindowsController {
    @FXML
    private Label lbExit;

    public void onClickExit(MouseEvent mouseEvent) {
        Platform.exit();
    }
}
