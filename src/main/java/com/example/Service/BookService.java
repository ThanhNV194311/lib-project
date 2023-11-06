package com.example.Service;

import com.example.DTO.BookDTO;
import com.example.Exception.IsExistedException;

import com.example.Helper.AlertHelper;
import com.example.Utils.ExecuteQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BookService {
    ExecuteQuery executeQuery = ExecuteQuery.getInstance();

    public ObservableList<BookDTO> bookData() throws SQLException {
        ObservableList<BookDTO> result = FXCollections.observableArrayList();
        String getAllBookSql = "SELECT\n" +
                "    b.id AS BookID,\n" +
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
                "ORDER BY b.id ASC;";
        ResultSet resultSet = executeQuery.executeQuery(getAllBookSql);

        while (resultSet.next()){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            BookDTO bookDTO = new BookDTO(
              resultSet.getString(1),
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

    public void deleteBook(String bookId){
        String deleteBookSql = "Update book set is_delete = 1 where id = '" + bookId + "'";
        if(AlertHelper.showConfirmation("Bạn có chắc chắn muốn xoá")){
            executeQuery.executeUpdate(deleteBookSql);
            AlertHelper.showAlert(Alert.AlertType.INFORMATION, "Thông báo", null, "Xoá sách thành công");
        }
    }

    public ObservableList<String> listCategory() throws SQLException{
        ObservableList<String> result = FXCollections.observableArrayList();
        String getAllCategorySql = "Select * from category";
        ResultSet resultSet = executeQuery.executeQuery(getAllCategorySql);

        while (resultSet.next()){
            result.add(resultSet.getString(2));
        }

        return result;
    }

    public ObservableList<String> listAuthor() throws SQLException {
        ObservableList<String> result = FXCollections.observableArrayList();
        String getAllAuthorSql = "Select * from author";
        ResultSet resultSet = executeQuery.executeQuery(getAllAuthorSql);

        while (resultSet.next()){
            result.add(resultSet.getString(2));
        }

        return result;
    }


    public void toggleVisibilityAndButton(Button button, boolean flag, ComboBox cb, TextField txt, String buttonText1, String buttonText2, String comboBoxPromptText) {
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


    public void addNewBook(String bookName, String authorNameTxt, String authorNameCb, String categoryTxt, String categoryCb, int quantityText, LocalDate publishDate) throws SQLException, IsExistedException {
        String selectedAuthorName = authorNameCb != null ? authorNameCb : authorNameTxt;
        String selectedCategoryName = categoryCb != null ? categoryCb : categoryTxt;
        if (isBookAlreadyExists(bookName, selectedAuthorName, selectedCategoryName)) {
            throw new IsExistedException("Sách đã tồn tại, vui lòng nhập đúng thông tin");
        }
        BookDTO bookDTO = new BookDTO(bookName, selectedAuthorName, selectedCategoryName, quantityText, publishDate);

        if (!isExisted("name", bookDTO.getAuthorName(), "author")) {
            // Author doesn't exist; insert a new one
            String insertAuthorSql = "INSERT INTO author(name) VALUES('" + bookDTO.getAuthorName() + "')";
            executeQuery.executeUpdate(insertAuthorSql);
        }

        if (!isExisted("name", bookDTO.getCategoryName(), "category")) {
            // Category doesn't exist; insert a new one
            String insertCategorySql = "INSERT INTO category(name) VALUES('" + bookDTO.getCategoryName() + "')";
            executeQuery.executeUpdate(insertCategorySql);
        }

        // Insert the new book; the auto-increment `book_id` will be generated automatically
        String insertNewBookSql = "INSERT INTO book(name, category_id, author_id, amount, create_day) VALUES('" + bookName + "', " +
                "(SELECT author_id FROM author WHERE name = '" + bookDTO.getAuthorName() + "'), " +
                "(SELECT category_id FROM category WHERE name = '" + bookDTO.getCategoryName() + "'), " +
                quantityText + ", '" + publishDate + "')";
        executeQuery.executeUpdate(insertNewBookSql);

        // Show a success message with the formatted book ID
        AlertHelper.showAlert(Alert.AlertType.INFORMATION, "Thông báo", null, "Thêm sách mới thành công.");
    }



    private boolean isBookAlreadyExists(String bookName, String authorName, String categoryName) throws SQLException {
        String checkExistSql = "SELECT COUNT(*) FROM book b " +
                "JOIN author a ON b.author_id = a.author_id " +
                "JOIN category c ON b.category_id = c.category_id " +
                "WHERE b.name = '" + bookName + "' " +
                "AND a.name = '" + authorName + "' " +
                "AND c.name = '" + categoryName + "'";
        ResultSet resultSet = executeQuery.executeQuery(checkExistSql);
        if (resultSet.next()) {
            int count = resultSet.getInt(1);
            return count > 0;
        }
        return false;
    }


    private boolean isExisted(String key, String value, String table) throws SQLException {
        String checkExistSql = "SELECT COUNT(*) FROM " + table +" WHERE " + key + " = '" + value +"'";
        ResultSet resultSet = executeQuery.executeQuery(checkExistSql);
        if (resultSet.next()) {
            int count = resultSet.getInt(1);
            return count > 0;
        }
        return false;
    }

    private int findIdByName(String column, String value, String table, String clause) throws SQLException {
        String findIdByNameSql = "Select " + column + " from " + table + " where " + clause + " = '" + value +"'";
        ResultSet resultSet = executeQuery.executeQuery(findIdByNameSql);

        if(resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;

    }
    public void updateBook(String bookId, String bookName, String authorNameTxt, String authorNameCb, String categoryTxt, String categoryCb, int quantityText, LocalDate publishDate) throws SQLException {
        try {
            // Determine the author name and category name to use based on user input
            String selectedAuthorName = authorNameCb != null ? authorNameCb : authorNameTxt;
            String selectedCategoryName = categoryCb != null ? categoryCb : categoryTxt;

            // Check if the selected author and category already exist; if not, insert them
            int authorId = findIdByName("author_id", selectedAuthorName, "author", "name");
            if (authorId == 0) {
                // Author doesn't exist; insert a new one
                String insertAuthorSql = "INSERT INTO author(name) VALUES('" + selectedAuthorName + "')";
                executeQuery.executeUpdate(insertAuthorSql);
                authorId = findIdByName("author_id", selectedAuthorName, "author", "name");
            }

            int categoryId = findIdByName("category_id", selectedCategoryName, "category", "name");
            if (categoryId == 0) {
                // Category doesn't exist; insert a new one
                String insertCategorySql = "INSERT INTO category(name) VALUES('" + selectedCategoryName + "')";
                executeQuery.executeUpdate(insertCategorySql);
                categoryId = findIdByName("category_id", selectedCategoryName, "category", "name");
            }

            // Update the book's information in the database
            String updateBookSql = "UPDATE book SET " +
                    "name = '" + bookName + "', " +
                    "author_id = " + authorId + ", " +
                    "category_id = " + categoryId + ", " +
                    "amount = " + quantityText + ", " +
                    "create_day = '" + publishDate + "' " +
                    "WHERE id = '" + bookId + "'";
            executeQuery.executeUpdate(updateBookSql);

            // Show a success message
            AlertHelper.showAlert(Alert.AlertType.INFORMATION, "Thông báo", null, "Cập nhật sách thành công");
        } catch (SQLException e) {
            throw new SQLException("Lỗi khi cập nhật sách: " + e.getMessage());
        }
    }



}
