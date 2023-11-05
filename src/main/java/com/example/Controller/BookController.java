package com.example.Controller;

import com.example.App;
import com.example.DTO.BookDTO;
import com.example.Service.BookService;
import com.example.Helper.TableHelper;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;

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
    private TableView<BookDTO> tbBook;
    @FXML
    private TableColumn<BookDTO,Integer> colId;
    @FXML
    private TableColumn<BookDTO, String> colBookName;
    @FXML
    private TableColumn<BookDTO, String> colAuthorName;
    @FXML
    private TableColumn<BookDTO, String> colCategoryName;
    @FXML
    private TableColumn<BookDTO, LocalDate> colPublishDate;
    @FXML
    private TableColumn<BookDTO, Integer> colQuantity;
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
        App.setRootPop("/com/example/popupAddBook", "Danh Thêm sách mới", false, Optional.of(event -> {
            try {
                tbBook.setItems(bookService.bookData());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }));

    }




}
