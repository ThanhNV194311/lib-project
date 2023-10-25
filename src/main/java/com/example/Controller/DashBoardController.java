package com.example.Controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashBoardController implements Initializable {
    @FXML
    private AnchorPane pane;
    @FXML
    private ListView<String> listMenu;

    private ObservableList<String> items = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        items.add("Quản lý sách");
        items.add("Quản lý người mượn");
        listMenu.setItems(items);
    }

    public void selectedItem(MouseEvent mouseEvent) {
        String selectedItem = listMenu.getSelectionModel().getSelectedItem();
        if(selectedItem != null && selectedItem.equals("Quản lý sách")){
            loadPane();
        }
    }

    private void loadPane(){
        String frm = "/com/example/QuanLySachFrm.fxml";
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(frm));
            AnchorPane adminPane = loader.load();
            pane.getChildren().add(adminPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
