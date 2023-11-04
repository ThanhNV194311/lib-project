package com.example.Controller;

import com.example.Service.BookService;
import com.example.Utils.TableUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class BookController implements Initializable {
    @FXML
    private TableView tbBook;
    @FXML
    private TableColumn colId;
    @FXML
    private TableColumn colBookName;
    @FXML
    private TableColumn colAuthorName;
    @FXML
    private TableColumn colCategoryName;
    @FXML
    private TableColumn colPublishDate;
    @FXML
    private TableColumn colQuantity;
    private BookService bookService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCell();
        bookService = new BookService();

        try {
            TableUtil.showOnTable(tbBook, bookService.bookData(),colId,colBookName,colAuthorName,colCategoryName,colPublishDate,colQuantity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCell() {
        colId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        colAuthorName.setCellValueFactory(new PropertyValueFactory<>("authorName"));
        colCategoryName.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        colPublishDate.setCellValueFactory(new PropertyValueFactory<>("publishDate"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }
}
