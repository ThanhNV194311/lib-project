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

    public Button btnUpdate, btnAdd;

    public Button btnSaveUpdate, btnSaveAdd, btnCancelUpdate, btnCancelAdd, btnDelete, btnExport;

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
    private TableColumn<BookDTO, Integer> colId;
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
        btnSaveAdd.setVisible(false);
        btnCancelAdd.setVisible(false);
        try {
            cbAuthor.setItems(bookService.listAuthor());
            cbCategory.setItems(bookService.listCategory());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            TableHelper.showOnTable(
                    tbBook,
                    bookService.bookData(),
                    colId,
                    colBookName,
                    colAuthorName,
                    colCategoryName,
                    colPublishDate,
                    colQuantity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void setDefaultStateButton() {
        btnUpdate.setVisible(true);
        btnAdd.setVisible(true);
        btnDelete.setVisible(true);
        btnExport.setVisible(true);
        btnUpdate.setDisable(false);
        btnAdd.setDisable(false);
        btnSaveUpdate.setVisible(false);
        btnCancelUpdate.setVisible(false);
        btnSaveAdd.setVisible(false);
        btnCancelAdd.setVisible(false);
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

        if (selectedBook != null) {
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
        System.out.println(txtAuthor.getText());
        if (txtIdBook.getText().isEmpty() || txtNameBook.getText().isEmpty() || txtQuantity.getText().isEmpty()
                || (cbAuthor.getValue() == null && txtAuthor.getText() == null)
                || (cbCategory.getValue() == null && txtCategory.getText() == null)
                || dpPublishDate.getValue() == null) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Lỗi", null, "Vui lòng điền đầy đủ thông tin.");
            return;
        }
        int quantityInt = Integer.parseInt(txtQuantity.getText());
        bookService.addNewBook(
                txtIdBook.getText(),
                txtNameBook.getText(),
                txtAuthor.getText(),
                cbAuthor.getValue(),
                txtCategory.getText(),
                cbCategory.getValue(),
                quantityInt,
                dpPublishDate.getValue());

        tbBook.setItems(bookService.bookData());

        setDefaultStateButton();

    }

    public void onClickDelete(ActionEvent actionEvent) throws SQLException {
        BookDTO bookDTOSelected = tbBook.getSelectionModel().getSelectedItem();
        if (bookDTOSelected == null) {
            return;
        } else {

            bookService.deleteBook(bookDTOSelected.getBookId());
            tbBook.setItems(bookService.bookData());
        }

    }

    public void onClickCancelAdd(ActionEvent actionEvent) {
        setDefaultStateButton();
    }

    public void onClickExport(ActionEvent actionEvent) {
        if (AlertHelper.showConfirmation("Bạn có muốn xuất dữ liệu ra excel không?")) {
            ExportToExcel.exportToExcel(tbBook, new Stage());
            AlertHelper.showAlert(Alert.AlertType.INFORMATION, "Thông báo", null, "Xuất dữ liệu thành công!");
        }
    }

    public void onClickUpdate(ActionEvent actionEvent) {
        // disable and delete id txt va btnUpdate
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

            BookDTO selectedBook = tbBook.getSelectionModel().getSelectedItem();
            if (selectedBook == null) {
                AlertHelper.showAlert(Alert.AlertType.ERROR, "Lỗi", null, "Chọn một cuốn sách để cập nhật.");
                return;
            }

            String selectedBook_id = selectedBook.getBookId();
            System.out.println("book_id: " + selectedBook_id);

            int Id = bookService.findIdByName("id", selectedBook_id, "book", "book_id");
            System.out.println("id: " + Id);

            String newBookId = txtIdBook.getText();
            System.out.println(newBookId);
            String newBookName = txtNameBook.getText(); // Get the new book name from your input fields
            System.out.println(newBookName);
            String newAuthorName = cbAuthor.getValue(); // Get the new author name from your input fields
            System.out.println(newAuthorName);
            String newCategoryName = cbCategory.getValue(); // Get the new category name from your input fields
            System.out.println(newCategoryName);
            int newQuantity = Integer.parseInt(txtQuantity.getText()); // Get the new quantity from your input fields
            System.out.println(newQuantity);
            LocalDate newPublishDate = dpPublishDate.getValue(); // Get the new publish date from your input fields
            System.out.println(newPublishDate);
            // Call the updateBook method to update the book in the database
            bookService.updateBook(Id, newBookId, newBookName, newAuthorName, newCategoryName, newQuantity,
                    newPublishDate);

            // Refresh the table view with updated data
            tbBook.setItems(bookService.bookData());

            // Show a success message
            AlertHelper.showAlert(Alert.AlertType.INFORMATION, "Thông báo", null, "Cập nhật sách thành công");
            setDefaultStateButton();

        }

    }

    public void onClickCancelUpdate(ActionEvent actionEvent) {
        setDefaultStateButton();
    }

    public void onClickAddAuthor(ActionEvent actionEvent) {
        bookService.toggleVisibilityAndButton(btnAddAuthor, flag, cbAuthor, txtAuthor, "Thêm tác giả", "Huỷ",
                "Chọn tác giả");
        flag = !flag;
    }

    public void onClickAddCategory(ActionEvent actionEvent) {
        bookService.toggleVisibilityAndButton(btnAddCategory, flag1, cbCategory, txtCategory, "Thêm thể loại", "Huỷ",
                "Chọn thể loại");
        flag1 = !flag1;
    }

    private boolean validateInput() {
        if (txtIdBook.getText().isEmpty() || txtNameBook.getText().isEmpty() || txtQuantity.getText().isEmpty() || cbAuthor.getValue() == null || cbCategory.getValue() == null || dpPublishDate.getValue() == null) {
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
    public void search(KeyEvent keyEvent) throws SQLException {
        String keyword = keyEvent.getText();
        tbBook.setItems(bookService.search(keyword, bookService.bookData()));
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
            tbBook.setItems(bookService.bookData());
        }
    }

    public void getListForFilter(ActionEvent actionEvent) throws SQLException {
        if (rAuthor.isSelected()) {
            cbFilter.setItems(bookService.listAuthor());
        } else if (rCategory.isSelected()) {
            cbFilter.setItems(bookService.listCategory());
        } else {
            cbFilter.setItems(null);
            cbFilter.setPromptText("<None>");
            tbBook.setItems(bookService.bookData());
        }

    }
}
