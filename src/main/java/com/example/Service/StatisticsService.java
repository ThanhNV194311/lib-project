package com.example.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.example.Utils.ExecuteQuery; // Import your database utility class

public class StatisticsService {
    ExecuteQuery executeQuery = ExecuteQuery.getInstance();


    public int getTotalCustomers() throws SQLException {
        String getTotalCustomers = "SELECT COUNT(*) FROM customer";
        ResultSet resultSet = executeQuery.executeQuery(getTotalCustomers);

        if(resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    public int getTotalBorrowedBooks() throws SQLException {
        String getTotalBorrowedBooks = "SELECT COUNT(*) FROM borrow WHERE status = 0";
        ResultSet resultSet = executeQuery.executeQuery(getTotalBorrowedBooks);

        if(resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    public int getTotalBooks() throws SQLException {
        String getTotalBooks = "SELECT COUNT(*) FROM book";
        ResultSet resultSet = executeQuery.executeQuery(getTotalBooks);

        if(resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }
}
