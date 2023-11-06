package com.example.Controller;

import com.example.App;
import com.example.DTO.BookDTO;
import com.example.Helper.AlertHelper;
import com.example.Service.BookService;
import com.example.Helper.TableHelper;
import com.example.Utils.ExportToExcel;
import com.example.Exception.IsExistedException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;


import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class BookController implements Initializable {
    public ComboBox<String> cbAuthor;
    public ComboBox<String> cbCategory;
    public TextField txtAuthor;
    public TextField txtCategory;
    public Button btnAddCategory;
    public Button btnAddAuthor;
    public Button btnUpdate,btnAdd;

    public Button btnSaveUpdate,btnSaveAdd,btnCancelUpdate,btnCancelAdd,btnDelete,btnExport;

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
        btnSaveUpdate.setVisible(false);
        btnCancelUpdate.setVisible(false);
        txtIdBook.setDisable(true);
        btnSaveAdd.setVisible(false);
        btnCancelAdd.setVisible(false);
        try {
            cbAuthor.setItems(bookService.listAuthor());
            cbCategory.setItems(bookService.listCategory());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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
            cbCategory.setValue(selectedBook.getCategoryName());
            cbAuthor.setValue(selectedBook.getAuthorName());
            dpPublishDate.setValue(selectedBook.getPublishDate());
        }
    }

    public void onClickAdd(ActionEvent actionEvent){
        //disable and delete id txt va disable  addBtn
        txtIdBook.setText("");
        btnAdd.setDisable(true);
        // show luu va huy
        btnSaveAdd.setVisible(true);
        btnCancelAdd.setVisible(true);
        // show deleteButton , updateButton, xuat
        btnDelete.setVisible(false);
        btnUpdate.setVisible(false);
        btnExport.setVisible(false);
    }
    public void onClickSaveAdd(ActionEvent actionEvent) throws SQLException {

        if (validateInput()) {

            try {
                int quantityInt = Integer.parseInt(txtQuantity.getText());
                bookService.addNewBook(
                        txtNameBook.getText(),
                        txtAuthor.getText(),
                        cbAuthor.getValue(),
                        txtCategory.getText(),
                        cbCategory.getValue(),
                        quantityInt,
                        dpPublishDate.getValue()
                );

                tbBook.setItems(bookService.bookData());
                btnSaveAdd.setVisible(false);
                btnCancelAdd.setVisible(false);
                btnAdd.setVisible(true);
                btnAdd.setDisable(false);
                // show deleteButton , updateButton, xuat
                btnDelete.setVisible(true);
                btnUpdate.setVisible(true);
                btnExport.setVisible(true);
            } catch (IsExistedException e) {
                AlertHelper.showAlert(Alert.AlertType.ERROR, "Lỗi", null, e.getMessage());
            }
        }
    }

    public void onClickCancelAdd(ActionEvent actionEvent){
        btnSaveAdd.setVisible(false);
        btnCancelAdd.setVisible(false);
        btnAdd.setVisible(true);
        btnAdd.setDisable(false);
        // show deleteButton , updateButton, xuat
        btnDelete.setVisible(true);
        btnUpdate.setVisible(true);
        btnExport.setVisible(true);
    }


    public void onClickDelete(ActionEvent actionEvent) throws SQLException {
        BookDTO bookDTOSelected = tbBook.getSelectionModel().getSelectedItem();
        if(bookDTOSelected == null){
            return;
        } else {

            bookService.deleteBook(bookDTOSelected.getBookId());
            tbBook.setItems(bookService.bookData());
        }

        // them clear text field
    }

    public void onClickExport(ActionEvent actionEvent) {
        if (AlertHelper.showConfirmation("Bạn có muốn xuất dữ liệu ra excel không?")) {
            ExportToExcel.exportToExcel(tbBook, new Stage());
            AlertHelper.showAlert(Alert.AlertType.INFORMATION, "Thông báo", null, "Xuất dữ liệu thành công!");
        }
    }


    public void onClickAddAuthor(ActionEvent actionEvent) {
        bookService.toggleVisibilityAndButton(btnAddAuthor, flag, cbAuthor, txtAuthor, "Thêm tác giả", "Huỷ", "Chọn tác giả");
        flag = !flag;
    }

    public void onClickAddCategory(ActionEvent actionEvent) {
        bookService.toggleVisibilityAndButton(btnAddCategory, flag1, cbCategory, txtCategory, "Thêm thể loại", "Huỷ", "Chọn thể loại");
        flag1 = !flag1;
    }

    public void onClickUpdate(ActionEvent actionEvent) {
        //disable and delete id txt va btnUpdate
        txtIdBook.setDisable(true);
        btnUpdate.setDisable(true);
        // show luu va huy ,
        btnSaveUpdate.setVisible(true);
        btnCancelUpdate.setVisible(true);
        // hide deleteButton , addButton, xuat
        btnDelete.setVisible(false);
        btnAdd.setVisible(false);
        btnExport.setVisible(false);

    }

    public void onClickSaveUpdate(ActionEvent actionEvent) throws SQLException {
        if (validateInput()) {
            // Get the selected book from the table view
            BookDTO selectedBook = tbBook.getSelectionModel().getSelectedItem();

            if (selectedBook == null) {
                AlertHelper.showAlert(Alert.AlertType.ERROR, "Lỗi", null, "Chọn một cuốn sách để cập nhật.");
                return;
            }

            // Retrieve updated values from the input fields
            String bookId = txtIdBook.getText();
            String bookName = txtNameBook.getText();
            String authorNameTxt = txtAuthor.getText();
            String authorNameCb = cbAuthor.getValue();
            String categoryTxt = txtCategory.getText();
            String categoryCb = cbCategory.getValue();
            int quantityText = Integer.parseInt(txtQuantity.getText());
            LocalDate publishDate = dpPublishDate.getValue();

            try {
                bookService.updateBook(
                        bookId, bookName, authorNameTxt, authorNameCb, categoryTxt, categoryCb, quantityText, publishDate
                );

                // Refresh the table view with updated data
                tbBook.setItems(bookService.bookData());

                btnSaveUpdate.setVisible(false);
                btnCancelUpdate.setVisible(false);
                btnUpdate.setVisible(true);
                btnUpdate.setDisable(false);
                // show deleteButton , addButton, xuat
                btnDelete.setVisible(true);
                btnAdd.setVisible(true);
                btnExport.setVisible(true);
                // Show a success message
                AlertHelper.showAlert(Alert.AlertType.INFORMATION, "Thông báo", null, "Cập nhật sách thành công");
            } catch (SQLException e) {
                AlertHelper.showAlert(Alert.AlertType.ERROR, "Lỗi", null, "Lỗi khi cập nhật sách: " + e.getMessage());
            }
        }

    }
    public void onClickCancelUpdate(ActionEvent actionEvent){
        btnSaveUpdate.setVisible(false);
        btnCancelUpdate.setVisible(false);
        btnUpdate.setVisible(true);
        btnUpdate.setDisable(false);

        // show deleteButton , addButton, xuat
        btnDelete.setVisible(true);
        btnAdd.setVisible(true);
        btnExport.setVisible(true);
    }
    private boolean validateInput() {
        if (txtNameBook.getText().isEmpty() || txtQuantity.getText().isEmpty() || cbAuthor.getValue() == null || cbCategory.getValue() == null || dpPublishDate.getValue() == null) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Lỗi", null, "Vui lòng điền đầy đủ thông tin.");
            return false;
        }

        try {
            int quantityInt = Integer.parseInt(txtQuantity.getText());
            if (quantityInt <= 0) {
                AlertHelper.showAlert(Alert.AlertType.ERROR, "Lỗi", null, "Số lượng phải lớn hơn 0.");
                return false;
            }
        } catch (NumberFormatException e) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Lỗi", null, "Số lượng phải là một số nguyên dương.");
            return false;
        }


        LocalDate currentDate = LocalDate.now();
        LocalDate selectedDate = dpPublishDate.getValue();

        if (selectedDate.isAfter(currentDate)) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Lỗi", null, "Ngày xuất bản không thể ở tương lai.");
            return false;
        }

        if (selectedDate.isBefore(LocalDate.of(1900, 1, 1))) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Lỗi", null, "Ngày xuất bản không hợp lệ.");
            return false;
        }

        return true;
    }



}


