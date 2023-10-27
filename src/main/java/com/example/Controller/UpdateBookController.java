package com.example.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

import com.example.Models.Author;
import com.example.Models.Book;
import com.example.Models.Category;
import com.example.Service.AuthorService;
import com.example.Service.BookService;
import com.example.Service.CategoryService;
import com.example.Utils.ExecuteQuery;

public class UpdateBookController {


    @FXML
    private TextField txt_tensach;

    @FXML
    private ComboBox<Author> cb_tacgia;

    @FXML
    private TextField txt_soluong;

    @FXML
    private ComboBox<Category> cb_theloai;

    @FXML
    private Button btn_themtacgia;

    @FXML
    private Button btn_themtheloai;

    @FXML
    private Button btn_capnhat;

    @FXML
    private Button btn_huy;

    private BookService bookService = new BookService();

    // You can set a book object to this controller to pre-fill data
    private Book bookToUpdate;
    private AuthorService authorService = new AuthorService(); // You need to create AuthorService
    private CategoryService categoryService = new CategoryService(); // You need to create CategoryService
    private ObservableList<Author> authors; // Declare an ObservableList for authors
    private ObservableList<Category> categories; // Declare an ObservableList for categories
    ExecuteQuery executeQuery = ExecuteQuery.getInstance();
    private String id_sach;
    public void initialize() {
        executeQuery.openConnection();
        loadAuthors();
        loadCategories();
    }
    private void loadAuthors() {
        try {
            // Populate the authors collection with data from the AuthorService
            authors = FXCollections.observableArrayList(authorService.getAllAuthors());
            cb_tacgia.setItems(authors);
            System.out.println("Get authors for combobox success");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadCategories() {
        try {
            // Populate the categories collection with data from the CategoryService
            categories = FXCollections.observableArrayList(categoryService.getAllCategories());

            cb_theloai.setItems(categories);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void setBookToUpdate(int id_sach, String tensach, String tentacgia, String tentheloai, int soluong) {
        this.id_sach = String.valueOf(id_sach);
        txt_tensach.setText(tensach);
        txt_soluong.setText(String.valueOf(soluong));
            // Find the Author and Category objects that match the provided names
        Author author = findAuthorByName(tentacgia);
        Category category = findCategoryByName(tentheloai);

        // Check if the Author and Category objects were found
        if (author != null && category != null) {
            // Set the values in the ComboBoxes
            cb_tacgia.setValue(author);
            cb_theloai.setValue(category);
        } else {
            // Handle the case where Author or Category is not found
            // You might want to show an error message to the user or take appropriate action
        }
    }
    
   // Add a method to find Author by name
    private Author findAuthorByName(String name) {
        for (Author author : authors) {
            if (author.getName().equals(name)) {
                return author;
            }
        }
        return null; // Return null if Author is not found
    }

    // Add a method to find Category by name
    private Category findCategoryByName(String name) {
        for (Category category : categories) {
            if (category.getName().equals(name)) {
                return category;
            }
        }
        return null; // Return null if Category is not found
    }

    @FXML
    public void onClickCapNhat() {
        // Get the updated data from the fields
        String tensach = txt_tensach.getText();
        String soluong = txt_soluong.getText();
        Author selectedAuthor = cb_tacgia.getValue();
        Category selectedCategory = cb_theloai.getValue();
        String id_tacgia = String.valueOf(selectedAuthor.getAuthorId());
        String id_theloai = String.valueOf(selectedCategory.getCategoryId());
        
        bookService.updateBook(id_sach, tensach, id_theloai, id_tacgia, soluong);
        // Close the popup
        Stage stage = (Stage) btn_capnhat.getScene().getWindow();
        stage.close();

    }

    @FXML
    public void onClickHuy() {
        // Close the popup
        Stage stage = (Stage) btn_huy.getScene().getWindow();
        stage.close();
    }
}
