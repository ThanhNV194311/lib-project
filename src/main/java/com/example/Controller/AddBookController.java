package com.example.Controller;


import com.example.Models.Author;
import com.example.Models.Category;
import com.example.Service.AuthorService;
import com.example.Service.BookService;
import com.example.Service.CategoryService;
import com.example.Utils.ExecuteQuery;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

import java.util.ResourceBundle;

public class AddBookController  implements Initializable{


    @FXML
    private TextField txt_tensach;

    @FXML
    private ComboBox<Author> cb_tacgia;

    @FXML
    private Button btn_themtacgia;


    @FXML
    private TextField txt_soluong;

    @FXML
    private ComboBox<Category> cb_theloai;

    @FXML
    private Button btn_themtheloai,btn_themsach,btn_huy;


    private AuthorService authorService = new AuthorService(); // You need to create AuthorService
    private CategoryService categoryService = new CategoryService(); // You need to create CategoryService
    ExecuteQuery executeQuery = ExecuteQuery.getInstance();
    @Override
    public void initialize(URL location, ResourceBundle resources)  {
        executeQuery.openConnection();
        loadAuthors();
        loadCategories();
    }

    private void loadAuthors() {
        try {
            ObservableList<Author> authors = FXCollections.observableArrayList(authorService.getAllAuthors());
            cb_tacgia.setItems(authors);
            System.out.println("Get authors for combobox success");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadCategories() {
        try {
            System.out.println("Get categories for combobox success");

            ObservableList<Category> categories = FXCollections.observableArrayList(categoryService.getAllCategories());
            cb_theloai.setItems(categories);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onClickThemTacGia(ActionEvent event) {
        try {
            // Load the FXML file for the popup
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/addAuthor.fxml"));
            Parent root = loader.load();

            // Create a new Stage for the popup
            Stage popupStage = new Stage();
            popupStage.setTitle("Add Author"); // Set the title of the popup window

            // Create a new Scene and set it in the Stage
            Scene scene = new Scene(root);
            popupStage.setScene(scene);

            // Show the popup
            popupStage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle any potential FXML loading exceptions
        }
    }

    @FXML
    public void onClickThemTheLoai(ActionEvent event) {
        try {
            // Load the FXML file for the popup
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/addCategory.fxml"));
            Parent root = loader.load();

            // Create a new Stage for the popup
            Stage popupStage = new Stage();
            popupStage.setTitle("Add Category"); // Set the title of the popup window

            // Create a new Scene and set it in the Stage
            Scene scene = new Scene(root);
            popupStage.setScene(scene);

            // Show the popup
            popupStage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle any potential FXML loading exceptions
        }
    }

    @FXML
    public void onClickThemSach(ActionEvent event) {
        BookService bookService = new BookService(); // You need to create BookService
        // Get the selected Author and Category from the ComboBoxes
        Author selectedAuthor = cb_tacgia.getValue();
        Category selectedCategory = cb_theloai.getValue();
    
        // Check if an author and category are selected
        if (selectedAuthor == null || selectedCategory == null) {
            // Handle the case where an author or category is not selected
            // You might want to show an error message to the user
        } else {
            // Gather information from text fields and combo boxes
            String tensach = txt_tensach.getText();
            String id_tacgia = String.valueOf(selectedAuthor.getAuthorId());
            String id_theloai = String.valueOf(selectedCategory.getCategoryId());
            String soluong = txt_soluong.getText();
    
            // Call your addBook function
            bookService.addBook(tensach, id_theloai, id_tacgia, soluong);
        }
        //close this window
        Button cancelButton = (Button) event.getSource();
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();

    }

    

    @FXML
    public void onClickHuy(ActionEvent event) {
        // Get the source of the event (in this case, the "Hủy" button)
        Button cancelButton = (Button) event.getSource();

        // Get the Stage associated with the button's scene
        Stage stage = (Stage) cancelButton.getScene().getWindow();

        // Close the stage (window)
        stage.close();

    }

    
}
