package com.example.Controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class DashBoardController implements Initializable {
    @FXML
    private ListView<String> listMenu;

    private ObservableList<String> items = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        items.add("Quản lý sách");
        items.add("Quản lý người mượn");
        listMenu.setItems(items);
    }

    public void onClicked(MouseEvent mouseEvent) {
        Platform.exit();
    }
}
