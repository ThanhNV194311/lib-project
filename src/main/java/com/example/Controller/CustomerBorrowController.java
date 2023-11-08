package com.example.Controller;

import com.example.Models.Borrow;
import com.example.Service.BorrowService;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.example.Helper.TableHelper.showOnTable;

public class CustomerBorrowController extends TableRow<Borrow> implements Initializable {
    public TableView<Borrow> tbDetail;
    public TableColumn colCustomerId;
    public TableColumn colBookId;
    public TableColumn colBookName;
    public TableColumn colStartDate;
    public TableColumn colEndDate;
    public TextField txtSearch;
    public TableColumn colReturnDate;
    public TableColumn colStatus;

    private BorrowService borrowService;
    private static String customerId;


    public static void setCustomerId(String identityCard) {
        customerId = identityCard;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        borrowService = new BorrowService();



        try {
            showOnTable(tbDetail, borrowService.getBorrowByCustomerId(customerId), colCustomerId, colBookId, colBookName, colStartDate, colEndDate, colReturnDate,colStatus);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setCell();
    }



    private void setCell() {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
}
