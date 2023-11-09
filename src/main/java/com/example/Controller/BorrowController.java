package com.example.Controller;

import com.example.Helper.TableHelper;
import com.example.Models.Borrow;
import com.example.Service.BookService;
import com.example.Service.BorrowService;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class BorrowController implements Initializable {
    public TableView<Borrow> tbBorrow;
    public TableColumn colCustomerId;
    public TableColumn colBookId;
    public TableColumn colStartDate;
    public TableColumn colEndDate;
    public TableColumn colReturnDate;
    public TableColumn colStatus;
    public TextField txtSearch;
    public ComboBox<String> cbBookId;
    public ComboBox<String> cbCustomerId;
    public DatePicker dpEndDate;
    public Label lbErrDate;
    private BorrowService borrowService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCell();
        borrowService = new BorrowService();


        try {
            cbCustomerId.setItems(borrowService.getAllCustomerId());
            cbBookId.setItems(borrowService.getAllBookId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        try {
            TableHelper.showOnTable(tbBorrow, borrowService.getAllBorrow(), colBookId, colCustomerId, colStartDate, colEndDate, colReturnDate, colStatus);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCell() {
        colBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    public void search(KeyEvent keyEvent) {
    }


    public void onSelected(ActionEvent actionEvent) {
        if(!borrowService.checkDate(dpEndDate.getValue())){
            lbErrDate.setText("Không được nhỏ hơn ngày hiện tại");
        } else {
            lbErrDate.setText("");
        }
    }
}
