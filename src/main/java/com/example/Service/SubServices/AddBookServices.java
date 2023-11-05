package com.example.Service.SubServices;

import com.example.DTO.BookDTO;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddBookServices {
    private ExecuteQuery executeQuery = ExecuteQuery.getInstance();

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

    public ObservableList<String> listAuthor() throws SQLException {
        ObservableList<String> result = FXCollections.observableArrayList();
        String getAllAuthorSql = "Select * from author";
        ResultSet resultSet = executeQuery.executeQuery(getAllAuthorSql);

        while (resultSet.next()){
            result.add(resultSet.getString(2));
        }

        return result;
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

    public void addNewBook(String bookName, String authorNameTxt, String authorNameCb, String categoryTxt, String categoryCb, int quantityText, LocalDate publishDate) throws SQLException {
//        System.out.println("cb "+authorNameCb);
//        System.out.println("txt " + authorNameTxt);
        String selectedAuthorName = authorNameCb != null ? authorNameCb : authorNameTxt;
        String selectedCategoryName = categoryCb != null ? categoryCb : categoryTxt;

        BookDTO bookDTO = new BookDTO(bookName, selectedAuthorName, selectedCategoryName, quantityText, publishDate);
//        System.out.println(bookDTO.toString());
        if(!isExisted("name", bookDTO.getAuthorName(),"author")){ // neu khong co thi them
            System.out.println("add author");
            String insertAuthorSql = "Insert into author(name) values('"+ bookDTO.getAuthorName() + "')";
//            System.out.println(insertAuthorSql);
            executeQuery.executeUpdate(insertAuthorSql);
        }

        if(!isExisted("name",bookDTO.getCategoryName(),"category")){ // neu khong co thi them
            System.out.println("add category");
            String insertCategorySql = "Insert into category(name) values('" + bookDTO.getCategoryName() + "')";
//            System.out.println(insertCategorySql);
            executeQuery.executeUpdate(insertCategorySql);
        }

//        System.out.println(findIdByName("author_id",bookDTO.getAuthorName(), "author", "name"));

        int authorId = findIdByName("author_id",bookDTO.getAuthorName(), "author", "name");

        int categoryId = findIdByName("category_id", bookDTO.getCategoryName(),"category","name");


        String insertNewBookSql = "insert into book(name, category_id, author_id, amount, create_day) values('" + bookDTO.getBookName() + "', " + categoryId + "," + authorId +"," + bookDTO.getQuantity() + ",'" + bookDTO.getPublishDate() + "')";
        executeQuery.executeUpdate(insertNewBookSql);
//        System.out.println(insertNewBookSql);
        AlertHelper.showAlert(Alert.AlertType.INFORMATION, "Thông báo", null, "Thêm sách mới thành công");



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


}
