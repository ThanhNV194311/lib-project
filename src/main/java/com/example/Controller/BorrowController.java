package com.example.Controller;

import com.example.Service.BorrowService;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class BorrowController implements Initializable {
    private BorrowService borrowService;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        borrowService = new BorrowService();
    }


    public void search(KeyEvent keyEvent) {
    }


}
