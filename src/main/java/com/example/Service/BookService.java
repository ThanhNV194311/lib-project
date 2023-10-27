package com.example.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


import com.example.Models.Book;
import com.example.Utils.ExecuteQuery;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class BookService {
    private ExecuteQuery executeQuery = ExecuteQuery.getInstance();

    public ObservableList<Book> booksData() throws SQLException {
        ObservableList<Book> result = FXCollections.observableArrayList();
        String sql = "SELECT b.book_id, b.name, a.name AS author, c.name AS category, b.create_day, b.amount " +
                     "FROM book b " +
                     "LEFT JOIN author a ON b.author_id = a.author_id " +
                     "INNER JOIN category c ON b.category_id = c.category_id";
        
        ResultSet resultSet = executeQuery.executeQuery(sql);
        while (resultSet.next()) {
            Book book = new Book(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getInt(6)
            );
            result.add(book);
        }

        return result;
    }

    public void deleteBook(int bookId) {
        executeQuery.openConnection();
        String sql = "DELETE FROM book WHERE book_id =  " + bookId;
        System.out.println(sql);
        executeQuery.executeUpdate(sql);
    }

    public void addBook( String tensach,String id_theloai, String id_tacgia,  String soluong){
        executeQuery.openConnection();
        String sql = "INSERT INTO book(name, category_id,author_id,  amount) VALUES ('"+tensach+"', '"+id_theloai+"', '"+id_tacgia+"', '"+soluong+"')";
        executeQuery.executeUpdate(sql);
        System.out.println("Thêm sach thành công");
    }
    //update book
    public void updateBook(String id_sach, String tensach,String id_theloai, String id_tacgia,  String soluong){
        executeQuery.openConnection();
        String sql = "UPDATE book SET name = '"+tensach+"', category_id = '"+id_theloai+"', author_id = '"+id_tacgia+"', amount = '"+soluong+"' WHERE book_id = '"+id_sach+"'";
        executeQuery.executeUpdate(sql);
        System.out.println("Cập nhật sách thành công");
    }
}
