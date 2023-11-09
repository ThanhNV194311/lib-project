package com.example.Controller;

import com.example.Models.Borrow;
import com.example.Service.BorrowService;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static com.example.Helper.TableHelper.showOnTable;

public class CustomerBorrowController extends TableRow<Borrow> implements Initializable {
    public TableView<Borrow> tbDetail;
    public TableColumn<Borrow, String> colCustomerId;
    public TableColumn<Borrow, String> colBookId;
    public TableColumn<Borrow, String> colBookName;
    public TableColumn<Borrow, String> colStartDate;
    public TableColumn<Borrow, String> colEndDate;
    public TextField txtSearch;
    public TableColumn<Borrow, String> colReturnDate;
    public TableColumn<Borrow, String> colStatus;

    private BorrowService borrowService;
    private static String customerId;


    public static void setCustomerId(String identityCard) {
        customerId = identityCard;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        borrowService = new BorrowService();

        hightlightOutOfDate();

        try {
            showOnTable(tbDetail, borrowService.getBorrowByCustomerId(customerId), colCustomerId, colBookId, colBookName, colStartDate, colEndDate, colReturnDate, colStatus);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setCell();
    }

    private void hightlightOutOfDate() {
        try {
            List<String> outOfDateBookIds = borrowService.getOutOfDateBookIds(customerId);
            // Tạo một Map để lưu trữ các bookId cần tô màu
            Map<String, Boolean> bookIdToColor = new HashMap<>();
            for (String bookId : outOfDateBookIds) {
                bookIdToColor.put(bookId, true);
            }

            tbDetail.setRowFactory(tableView -> new TableRow<Borrow>() {
                @Override
                protected void updateItem(Borrow item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item != null) {
                        if (bookIdToColor.containsKey(item.getBookId())) {
                            setStyle("-fx-background-color: #CB6918;");
                        } else {
                            setStyle("");
                        }
                    } else {
                        setStyle("");
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void onClickReturnBook(ActionEvent actionEvent) throws SQLException {
        String bookIdSelected = tbDetail.getSelectionModel().getSelectedItem().getBookId();
        borrowService.returnBookByBookId(bookIdSelected, customerId);
        tbDetail.setItems(borrowService.getBorrowByCustomerId(customerId));
    }
}
