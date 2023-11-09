package com.example.Controller;

import com.example.Helper.TableHelper;
import com.example.Models.Borrow;
import com.example.Service.BorrowService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class BorrowController implements Initializable {
    @FXML
    private TableView<Borrow> tbBorrow;
    @FXML
    private TableColumn<Borrow, String> colCustomerId;
    @FXML
    private TableColumn<Borrow, String> colBookId;
    @FXML
    private TableColumn<Borrow, String> colStartDate;
    @FXML
    private TableColumn<Borrow, String> colEndDate;
    @FXML
    private TableColumn<Borrow, String> colReturnDate;
    @FXML
    private TableColumn<Borrow, String> colStatus;
    @FXML
    private TextField txtSearch;
    @FXML
    private ComboBox<String> cbBookId;
    @FXML
    private ComboBox<String> cbCustomerId;
    @FXML
    private DatePicker dpEndDate;
    @FXML
    private Label lbErrDate;

    private final BorrowService borrowService;

    public BorrowController(){
        this.borrowService = new BorrowService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCell();
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
