package com.example.Service;

import com.example.DTO.BookDTO;
import com.example.Helper.AlertHelper;
import com.example.Utils.ExecuteQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;

public class BookService {
    static ExecuteQuery executeQuery = ExecuteQuery.getInstance();

    public static ObservableList<BookDTO> getBookData() throws SQLException {
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

        while (resultSet.next()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            BookDTO bookDTO = new BookDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getDate(5).toLocalDate(),
                    resultSet.getInt(6));
            result.add(bookDTO);
        }
        return result;
    }

    public void deleteBook(String bookId) {
        String deleteBookSql = "Update book set is_delete = 1 where book_id = '" + bookId + "'";
        if (AlertHelper.showConfirmation("Bạn có chắc chắn muốn xoá")) {
            executeQuery.executeUpdate(deleteBookSql);
            AlertHelper.showAlert(Alert.AlertType.INFORMATION, "Thông báo", null, "Xoá sách thành công");
        }
    }

    public ObservableList<String> listCategory() throws SQLException {
        ObservableList<String> result = FXCollections.observableArrayList();
        String getAllCategorySql = "Select * from category";
        ResultSet resultSet = executeQuery.executeQuery(getAllCategorySql);

        while (resultSet.next()) {
            result.add(resultSet.getString(2));
        }

        return result;
    }

    public ObservableList<String> listAuthor() throws SQLException {
        ObservableList<String> result = FXCollections.observableArrayList();
        String getAllAuthorSql = "Select * from author";
        ResultSet resultSet = executeQuery.executeQuery(getAllAuthorSql);

        while (resultSet.next()) {
            result.add(resultSet.getString(2));
        }

        return result;
    }

    public void toggleVisibilityAndButton(Button button, boolean flag, ComboBox cb, TextField txt, String buttonText1,
            String buttonText2, String comboBoxPromptText) {
        if (flag) {
            cb.setVisible(true);
            txt.setVisible(false);
            button.setText(buttonText1);
            txt.clear();
        } else {
            cb.setVisible(false);
            txt.setVisible(true);
            button.setText(buttonText2);
            cb.setValue(null);

        }
    }

    public void addNewBook(String bookId, String bookName, String authorNameTxt, String authorNameCb,
            String categoryTxt, String categoryCb, int quantityText, LocalDate publishDate) throws SQLException {
        // System.out.println("cb "+authorNameCb);
        // System.out.println("txt " + authorNameTxt);
        String selectedAuthorName = authorNameCb != null ? authorNameCb : authorNameTxt;
        String selectedCategoryName = categoryCb != null ? categoryCb : categoryTxt;

        BookDTO bookDTO = new BookDTO(bookName, selectedAuthorName, selectedCategoryName, quantityText, publishDate);
        // System.out.println(bookDTO.toString());
        if (!isExisted("name", bookDTO.getAuthorName(), "author")) { // neu khong co thi them
            System.out.println("add author");
            String insertAuthorSql = "Insert into author(name) values('" + bookDTO.getAuthorName() + "')";
            // System.out.println(insertAuthorSql);
            executeQuery.executeUpdate(insertAuthorSql);
        }

        if (!isExisted("name", bookDTO.getCategoryName(), "category")) { // neu khong co thi them
            System.out.println("add category");
            String insertCategorySql = "Insert into category(name) values('" + bookDTO.getCategoryName() + "')";
            // System.out.println(insertCategorySql);
            executeQuery.executeUpdate(insertCategorySql);
        }

        // System.out.println(findIdByName("author_id",bookDTO.getAuthorName(),
        // "author", "name"));

        int authorId = findIdByName("author_id", bookDTO.getAuthorName(), "author", "name");

        int categoryId = findIdByName("category_id", bookDTO.getCategoryName(), "category", "name");

        String insertNewBookSql = "insert into book(book_id ,name, category_id, author_id, amount, create_day) values('"
                + bookId + "','" + bookDTO.getBookName() + "', " + categoryId + "," + authorId + ","
                + bookDTO.getQuantity() + ",'" + bookDTO.getPublishDate() + "')";
        executeQuery.executeUpdate(insertNewBookSql);
        // System.out.println(insertNewBookSql);
        AlertHelper.showAlert(Alert.AlertType.INFORMATION, "Thông báo", null, "Thêm sách mới thành công");

    }

    private boolean isExisted(String key, String value, String table) throws SQLException {
        String checkExistSql = "SELECT COUNT(*) FROM " + table + " WHERE " + key + " = '" + value + "'";
        ResultSet resultSet = executeQuery.executeQuery(checkExistSql);
        if (resultSet.next()) {
            int count = resultSet.getInt(1);
            return count > 0;
        }
        return false;
    }

    public int findIdByName(String name, String value, String table, String clause) throws SQLException {
        String findIdByNameSql = "Select " + name + " from " + table + " where " + clause + " = '" + value + "'";
        ResultSet resultSet = executeQuery.executeQuery(findIdByNameSql);

        if (resultSet.next()) {
            return resultSet.getInt(1);
        }

        return 0;
    }

    public void updateBook(int id, String newBookId, String newBookName, String newAuthorName, String newCategoryName,
            int newQuantity, LocalDate newPublishDate) throws SQLException {
        // Get the author and category IDs based on their names
        int authorId = findIdByName("author_id", newAuthorName, "author", "name");
        int categoryId = findIdByName("category_id", newCategoryName, "category", "name");

        // Create an SQL UPDATE statement
        String updateBookSql = "UPDATE book SET book_id = '" + newBookId + "', name = '" + newBookName
                + "', category_id = " + categoryId + ", author_id = " + authorId + ", amount = " + newQuantity
                + ", create_day = '" + newPublishDate + "' WHERE id = " + id + " ";
        System.out.println("string sql: " + updateBookSql);
        // Execute the SQL statement using a prepared statement
        executeQuery.executeUpdate(updateBookSql);
        AlertHelper.showAlert(Alert.AlertType.INFORMATION, "Thông báo", null, " Cập nhật thành công");
    }

    public FilteredList<BookDTO> search(String keyword, ObservableList<BookDTO> books) {
        FilteredList<BookDTO> filteredData = new FilteredList<>(books, p -> true);
        if (!keyword.isEmpty()) {
            String lowerCaseKeyword = keyword.toLowerCase();

            filteredData.setPredicate(book -> {
                String bookName = book.getBookName().toLowerCase();
                String authorName = book.getAuthorName().toLowerCase();
                String categoryName = book.getCategoryName().toLowerCase();

                return bookName.contains(lowerCaseKeyword)
                        || authorName.contains(lowerCaseKeyword)
                        || categoryName.contains(lowerCaseKeyword);
            });

        }
        return filteredData;
    }

    public ObservableList<BookDTO> filter(String filter, String type) throws SQLException {
        ObservableList<BookDTO> bookData = BookService.getBookData();
        ObservableList<BookDTO> result = FXCollections.observableArrayList();
        if (type.equals("author")) {
            for (BookDTO bookDTO : bookData) {
                if (bookDTO.getAuthorName().equals(filter)) {
                    result.add(bookDTO);
                }
            }
        } else if (type.equals("category")) {
            for (BookDTO bookDTO : bookData) {
                if (bookDTO.getCategoryName().equals(filter)) {
                    result.add(bookDTO);
                }
            }
        }

        return result;

    }
}
