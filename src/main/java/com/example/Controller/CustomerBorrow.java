package com.example.Controller;

import com.example.Models.Borrow;
import com.example.Service.BorrowService;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerBorrow implements Initializable {
    public TableView<Borrow> tbDetail;
    public TableColumn colCustomerId;
    public TableColumn colBookId;
    public TableColumn colBookName;
    public TableColumn colStartDate;
    public TableColumn colEndDate;
    public TextField txtSearch;

    private BorrowService borrowService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        borrowService = new BorrowService();

        setCell();
    }

    private void setCell() {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
    }
}
