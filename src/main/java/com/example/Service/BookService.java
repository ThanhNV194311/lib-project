package com.example.Service;

import com.example.DTO.BookDTO;
import com.example.Utils.ExecuteQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public class BookService {
    ExecuteQuery executeQuery = ExecuteQuery.getInstance();

    public ObservableList<BookDTO> bookData() throws SQLException {
        ObservableList<BookDTO> result = FXCollections.observableArrayList();
        String getAllBookSql = "SELECT\n" +
                "    b.book_id AS BookID,\n" +
                "    b.name AS BookName,\n" +
                "    a.name AS AuthorName,\n" +
                "    c.name AS CategoryName,\n" +
                "    b.create_day AS PublishDate,\n" +
                "    b.amount AS Quantity\n" +
                "FROM\n" +
                "    book b\n" +
                "JOIN\n" +
                "    author a ON b.author_id = a.author_id\n" +
                "JOIN\n" +
                "    category c ON b.category_id = c.category_id\n" +
                "WHERE b.is_delete = '0'\n" +
                "ORDER BY b.book_id ASC;";
        ResultSet resultSet = executeQuery.executeQuery(getAllBookSql);

        while (resultSet.next()){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            BookDTO bookDTO = new BookDTO(
              resultSet.getInt(1),
              resultSet.getString(2),
              resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getDate(5).toLocalDate(),
                    resultSet.getInt(6)
            );
            result.add(bookDTO);
        }
        return result;
    }
}
