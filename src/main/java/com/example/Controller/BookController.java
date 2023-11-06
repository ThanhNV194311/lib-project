package com.example.Controller;

import com.example.DTO.BookDTO;
import com.example.Helper.AlertHelper;
import com.example.Service.BookService;
import com.example.Helper.TableHelper;
import com.example.Utils.ExportToExcel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class BookController implements Initializable {
    public ComboBox<String> cbAuthor;
    public ComboBox<String> cbCategory;
    public TextField txtAuthor;
    public TextField txtCategory;
    public Button btnAddCategory;
    public Button btnAddAuthor;
    public RadioButton rAuthor;
    public RadioButton rCategory;
    public ComboBox<String> cbFilter;
    public RadioButton rNone;
    @FXML
    private TextField txtIdBook;

    @FXML
    private TextField txtNameBook;

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
    private boolean flag = false;
    private boolean flag1 = false;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCell();
        bookService = new BookService();

        try {
            cbAuthor.setItems(bookService.listAuthor());
            cbCategory.setItems(bookService.listCategory());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            TableHelper.showOnTable(
                    tbBook,
                    bookService.getBookData(),
                    colId,
                    colBookName,
                    colAuthorName,
                    colCategoryName,
                    colPublishDate,
                    colQuantity
            );
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
            cbCategory.setValue(selectedBook.getCategoryName());
            cbAuthor.setValue(selectedBook.getAuthorName());
            dpPublishDate.setValue(selectedBook.getPublishDate());
        }
    }

    public void onClickAdd(ActionEvent actionEvent) throws SQLException {
//        App.setRootPop("/com/example/popupAddBook", "Danh Thêm sách mới", false, Optional.of(event -> {
//            try {
//                tbBook.setItems(bookService.bookData());
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }));
        int quantityInt = Integer.parseInt(txtQuantity.getText());
        bookService.addNewBook(
                txtIdBook.getText(),
                txtNameBook.getText(),
                txtAuthor.getText(),
                cbAuthor.getValue(),
                txtCategory.getText(),
                cbCategory.getValue(),
                quantityInt,
                dpPublishDate.getValue()
        );

        tbBook.setItems(bookService.getBookData());

    }

    public void onClickDelete(ActionEvent actionEvent) throws SQLException {
        BookDTO bookDTOSelected = tbBook.getSelectionModel().getSelectedItem();
        if(bookDTOSelected == null){
            return;
        } else {

            bookService.deleteBook(bookDTOSelected.getBookId());
            tbBook.setItems(bookService.getBookData());
        }

        // them clear text field
    }

    public void onClickExport(ActionEvent actionEvent) {
        if (AlertHelper.showConfirmation("Bạn có muốn xuất dữ liệu ra excel không?")) {
            ExportToExcel.exportToExcel(tbBook, new Stage());
            AlertHelper.showAlert(Alert.AlertType.INFORMATION, "Thông báo", null, "Xuất dữ liệu thành công!");
        }
    }

    public void onClickUpdate(ActionEvent actionEvent){
//        bookService.updateBook(txtIdBook.getText(), txtNameBook.getText(), txtNameAuthor.getText(), txtCategoryName.getText(), dpPublishDate.getValue(), txtQuantity.getText());
    }


    public void onClickAddAuthor(ActionEvent actionEvent) {
        bookService.toggleVisibilityAndButton(btnAddAuthor, flag, cbAuthor, txtAuthor, "Thêm tác giả", "Huỷ", "Chọn tác giả");
        flag = !flag;
    }

    public void onClickAddCategory(ActionEvent actionEvent) {
        bookService.toggleVisibilityAndButton(btnAddCategory, flag1, cbCategory, txtCategory, "Thêm thể loại", "Huỷ", "Chọn thể loại");
        flag1 = !flag1;
    }



    public void search(KeyEvent keyEvent) throws SQLException {
        String keyword = keyEvent.getText();
        tbBook.setItems(bookService.search(keyword, bookService.getBookData()));
    }

    public void onFilterSelected(ActionEvent actionEvent) throws SQLException {
        String filter = cbFilter.getValue();
        if (filter == null) {
            return;
        }

        String filterType = rAuthor.isSelected() ? "author" : rCategory.isSelected() ? "category" : "";

        if (!filterType.isEmpty()) {
            tbBook.setItems(bookService.filter(filter, filterType));
        } else {
            tbBook.setItems(bookService.getBookData());
        }
    }

    public void getListForFilter(ActionEvent actionEvent) throws SQLException {
        if(rAuthor.isSelected()){
            cbFilter.setItems(bookService.listAuthor());
        } else if(rCategory.isSelected()) {
            cbFilter.setItems(bookService.listCategory());
        } else {
            cbFilter.setItems(null);
            cbFilter.setPromptText("<None>");
            tbBook.setItems(bookService.getBookData());
        }

    }
}
