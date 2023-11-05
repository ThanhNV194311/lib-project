package com.example.Controller;

import com.example.App;
import com.example.DTO.BookDTO;
import com.example.Service.BookService;
import com.example.Helper.TableHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class BookController implements Initializable {
    @FXML
    private TextField txtIdBook;
    @FXML
    private TextField txtNameAuthor;
    @FXML
    private TextField txtNameBook;
    @FXML
    private TextField txtCategoryName;
    @FXML
    private DatePicker dpPublishDate;
    @FXML
    private TextField txtQuantity;
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
            TableHelper.showOnTable(tbBook, bookService.bookData(),colId,colBookName,colAuthorName,colCategoryName,colPublishDate,colQuantity);
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

    public void onSelected(MouseEvent mouseEvent) {
        BookDTO selectedBook = (BookDTO) tbBook.getSelectionModel().getSelectedItem();

        if(selectedBook != null){
            String bookIdStr = String.valueOf(selectedBook.getBookId());
            String quantityStr = String.valueOf(selectedBook.getQuantity());
            txtIdBook.setText(bookIdStr);
            txtNameBook.setText(selectedBook.getBookName());
            txtQuantity.setText(quantityStr);
            txtCategoryName.setText(selectedBook.getCategoryName());
            txtNameAuthor.setText(selectedBook.getAuthorName());
            dpPublishDate.setValue(selectedBook.getPublishDate());
        }
    }

    public void onClickAdd(ActionEvent actionEvent) throws IOException {
        App.setRootPop("/com/example/popupAddBook","Danh Thêm sách mới", false);
    }
}
