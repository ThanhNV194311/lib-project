package com.example.Controller.SubController;

import com.example.Service.SubServices.AddBookServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddBookController implements Initializable {
    public TextField txtBookName;
    public TextField txtQuantity;
    public DatePicker dpPublishDate;
    public Button btnAddBook;
    @FXML
    private TextField txtAuthorName;
    @FXML
    private TextField txtCategory;
    @FXML
    private ComboBox<String> cbAuthorName;
    @FXML
    private ComboBox<String> cbCategory;
    @FXML
    private Button btnAddAuthor;
    @FXML
    private Button btnAddCategory;
    private boolean flag = false;
    private boolean flag1 = false;
    private AddBookServices addBookServices;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addBookServices = new AddBookServices();

        try {
            cbAuthorName.setItems(addBookServices.listAuthor());
            cbCategory.setItems(addBookServices.listCategory());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void onClickAddAuthor(ActionEvent actionEvent) {
        addBookServices.toggleVisibilityAndButton(btnAddAuthor, flag, cbAuthorName, txtAuthorName, "Thêm tác giả", "Huỷ", "Chọn tác giả");
        flag = !flag;
    }

    public void onClickAddCategory(ActionEvent actionEvent) {
        addBookServices.toggleVisibilityAndButton(btnAddCategory, flag1, cbCategory, txtCategory, "Thêm thể loại", "Huỷ", "Chọn thể loại");
        flag1 = !flag1;
    }


    public void onClickAddBook(ActionEvent actionEvent) throws SQLException {
        addBookServices.addNewBook(
                txtBookName.getText(),
                txtAuthorName.getText(),
                cbAuthorName.getValue(),
                txtCategory.getText(),
                cbCategory.getValue(),
                Integer.parseInt(txtQuantity.getText()),
                dpPublishDate.getValue()
        );

    }
}
