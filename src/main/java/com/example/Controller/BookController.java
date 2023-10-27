package com.example.Controller;


import com.example.Models.Author;
import com.example.Models.Book;
import com.example.Models.Category;
import com.example.Service.BookService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import static com.example.Utils.TableUtil.showOnTable;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

public class BookController {
    @FXML
    private TableView<Book> bookTableView;

    @FXML
    private TableColumn<Book, Integer> idColumn;

    @FXML
    private TableColumn<Book, String> nameColumn;

    @FXML
    private TableColumn<Book, Author> authorColumn;

    @FXML
    private TableColumn<Book, Category> categoryColumn;

    @FXML
    private TableColumn<Book, Timestamp> createDayColumn;

    @FXML
    private TableColumn<Book, Integer> amountColumn;
    @FXML
    private TextField txt_idsach, txt_tensach, txt_tentacgia, txt_theloai, txt_ngayxb, txt_soluong;
    
    @FXML
    private Button btn_delete,btn_add,btn_reload,btn_update;
 

    private BookService bookService = new BookService();

    public void initialize() {
        setCell();
        bookService = new BookService();
        try {
            showOnTable(bookTableView, bookService.booksData(),idColumn,nameColumn,authorColumn,categoryColumn,createDayColumn,amountColumn);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    public void setCell(){
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        createDayColumn.setCellValueFactory(new PropertyValueFactory<>("createDay"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
    }
    public void onClickDelete(ActionEvent actionEventvent)throws SQLException {
        Book book = bookTableView.getSelectionModel().getSelectedItem();
        bookService.deleteBook(book.getId());
        initialize();
    }
    public void onSelected(MouseEvent mouseEvent) {
        Book book = bookTableView.getSelectionModel().getSelectedItem();
        txt_idsach.setText(String.valueOf(book.getId()));
        txt_tensach.setText(book.getName());
        txt_tentacgia.setText(book.getAuthor());
        txt_theloai.setText(book.getCategory());
        txt_ngayxb.setText(String.valueOf(book.getCreateDay()));
        txt_soluong.setText(String.valueOf(book.getAmount()));
    }

    public void onclickPopupAdd(ActionEvent actionEvent) {
        try {
            // Load the FXML file for the popup
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/popupAddBook.fxml"));
            Parent root = loader.load();

            // Create a new Stage for the popup
            Stage popupStage = new Stage();
            popupStage.setTitle("Add Popup"); // Set the title of the popup window

            // Create a new Scene and set it in the Stage
            Scene scene = new Scene(root);
            popupStage.setScene(scene);

            // Show the popup
            popupStage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle any potential FXML loading exceptions
        }
    }
    public void onclickPopupUpdate(ActionEvent actionEvent) {
        try {
            // Load the FXML file for the popup
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/popupUpdateBook.fxml"));
            Parent root = loader.load();
    
            // Get the controller for the popupUpdateBook
            UpdateBookController updateBookController = loader.getController();
    
            int id = Integer.parseInt(txt_idsach.getText());
            String name = txt_tensach.getText();
            String author = txt_tentacgia.getText();
            System.out.println(author);
            String category = txt_theloai.getText();
            System.out.println(category);
            int amount = Integer.parseInt(txt_soluong.getText());
            // Set the selected book to the controller
            updateBookController.setBookToUpdate(id,name,author,category,amount);
    
            // Create a new Stage for the popup
            Stage popupStage = new Stage();
            popupStage.setTitle("Update Popup"); // Set the title of the popup window
    
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
    public void onClickReload(ActionEvent event) {
        initialize();
    }
}
