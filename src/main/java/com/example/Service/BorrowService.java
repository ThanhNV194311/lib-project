package com.example.Service;

import com.example.Models.Borrow;
import com.example.Utils.ExecuteQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
        String getBorrowByCustomerIdSql = "SELECT c.identity_card, b.book_id, b.name AS book_name, br.start_day, br.end_day,\n" +
                "       CASE\n" +
                "           WHEN br.status = 0 THEN 'Chưa trả'\n" +
                "           WHEN br.status = 1 THEN 'Đã trả'\n" +
                "           ELSE 'Quá hạn'\n" +
                "       END AS status\n" +
                "FROM customer c\n" +
                "JOIN borrow br ON c.customer_id = br.customer_id\n" +
                "JOIN book b ON br.id = b.id\n" +
                "WHERE c.identity_card = '" + customerId + "'";
        ResultSet resultSet = executeQuery.executeQuery(getBorrowByCustomerIdSql);
        while (resultSet.next()) {
            Borrow borrow = new Borrow(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDate(4).toLocalDate(),
                    resultSet.getDate(5).toLocalDate(),
                    null,
                    resultSet.getString(6)
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

}
