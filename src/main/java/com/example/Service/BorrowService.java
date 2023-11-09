package com.example.Service;

import com.example.Helper.AlertHelper;
import com.example.Models.Borrow;
import com.example.Utils.ExecuteQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BorrowService {
    private final ExecuteQuery executeQuery = ExecuteQuery.getInstance();

    public ObservableList<Borrow> getBorrowByCustomerId(String customerId) throws SQLException {
        ObservableList<Borrow> borrows = FXCollections.observableArrayList();
        String getBorrowByCustomerIdSql = "SELECT c.identity_card, b.book_id, b.name AS book_name, br.start_day, br.end_day, br.return_date,\n" +
                "       CASE\n" +
                "           WHEN br.status = 0 THEN 'Chưa trả'\n" +
                "           WHEN br.status = 1 THEN 'Đã trả'\n" +
                "           ELSE 'Quá hạn'\n" +
                "       END AS status\n" +
                "FROM customer c\n" +
                "JOIN borrow br ON c.customer_id = br.customer_id\n" +
                "JOIN book b ON br.id = b.id\n" +
                "WHERE c.identity_card = '" + customerId + "'\n" +
                "and b.is_delete = 0;";
        ResultSet resultSet = executeQuery.executeQuery(getBorrowByCustomerIdSql);
        while (resultSet.next()) {
            Borrow borrow = new Borrow(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDate(4).toLocalDate(),
                    resultSet.getDate(5).toLocalDate(),
                    resultSet.getDate(6) != null ? resultSet.getDate(6).toLocalDate() : null,
                    resultSet.getString(7)
            );
            borrows.add(borrow);
        }
        return borrows;
    }

    public List<String> getOutOfDateBookIds(String customerId) throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Định dạng ngày tháng
        LocalDate currentDate = LocalDate.now();

        List<String> outOfDateBookIds = new ArrayList<>();

        for (Borrow borrow : getBorrowByCustomerId(customerId)) {
            LocalDate endDay = borrow.getEndDate();
            if (endDay != null && endDay.isBefore(currentDate)) {
                outOfDateBookIds.add(borrow.getBookId());
            }
        }

        return outOfDateBookIds;
    }

    public void returnBookByBookId(String bookId, String customerId) throws SQLException {
        String returnBookSql = "update borrow\n" +
                "set status = 1\n" + ", return_date = NOW()" +
                "where id = (\n" +
                "        select id\n" +
                "        from book\n" +
                "        where book_id = '" + bookId +"'\n" +
                "    ) and customer_id = (\n" +
                "        select customer_id\n" +
                "        from customer\n" +
                "        where identity_card = '"+ customerId+"'\n" +
                "    );";
        executeQuery.executeUpdate(returnBookSql);
//        System.out.println(returnBookSql);
        AlertHelper.showAlert(Alert.AlertType.INFORMATION, "Trả sách thành công", null, "Trả sách thành công");
    }

    public ObservableList<Borrow> getAllBorrow() throws SQLException {
        ObservableList<Borrow> result = FXCollections.observableArrayList();
        String getAllBorrowSql = "SELECT\n" +
                "    c.identity_card,\n" +
                "    b.book_id,\n" +
                "    b.name AS book_name,\n" +
                "    br.start_day,\n" +
                "    br.end_day,\n" +
                "    br.return_date,\n" +
                "    CASE\n" +
                "        WHEN br.status = 0 THEN 'Chưa trả'\n" +
                "        WHEN br.status = 1 THEN 'Đã trả'\n" +
                "        ELSE 'Quá hạn'\n" +
                "    END AS status\n" +
                "FROM customer c\n" +
                "    JOIN borrow br ON c.customer_id = br.customer_id\n" +
                "    JOIN book b ON br.id = b.id\n" +
                "WHERE\n" +
                "    b.is_delete = 0\n" +
                "    and c.is_delete = 0;\n";

        ResultSet resultSet = executeQuery.executeQuery(getAllBorrowSql);
        while (resultSet.next()){
            Borrow borrow = new Borrow(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    null,
                    resultSet.getDate(4).toLocalDate(),
                    resultSet.getDate(5).toLocalDate(),
                    resultSet.getDate(6) != null ? resultSet.getDate(6).toLocalDate() : null,
                    resultSet.getString(7)

            );
            result.add(borrow);
        }

        return result;
    }

    public ObservableList<String> getAllCustomerId() throws SQLException {
        String getAllCustomerIdSql = "Select identity_card from customer";
        ObservableList<String> result = FXCollections.observableArrayList();
        ResultSet resultSet = executeQuery.executeQuery(getAllCustomerIdSql);
        while (resultSet.next()){
            result.add(resultSet.getString(1));
        }
        return result;
    }

    public ObservableList<String> getAllBookId() throws SQLException {
        String getAllBookIdSql = "Select book_id from book";
        ObservableList<String> result = FXCollections.observableArrayList();
        ResultSet resultSet = executeQuery.executeQuery(getAllBookIdSql);
        while (resultSet.next()){
            result.add(resultSet.getString(1));
        }
        return result;
    }

    public boolean checkDate(LocalDate date){
        return date.isAfter(LocalDate.now());
    }
}
