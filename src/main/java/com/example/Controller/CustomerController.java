package com.example.Controller;

import com.example.Models.Customer;
import com.example.Service.CustomerService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;


import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.example.Utils.TableUtil.showOnTable;

public class CustomerController implements Initializable {
    @FXML
    private TableView<Customer> tbCustomer;
    @FXML
    private TableColumn<Customer,Integer> colId;
    @FXML
    private TableColumn<Customer, String> colName;
    @FXML
    private TableColumn<Customer,String> colEmail;
    @FXML
    private TableColumn<Customer,String> colPhoneNumber;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPhoneNumber;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private TextField txtSearch;
    @FXML
    private Button btnHistory;
    private CustomerService customerService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCell();
        customerService = new CustomerService();
        try {
            showOnTable(tbCustomer,customerService.customersData(),colId,colName,colPhoneNumber,colEmail);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCell() {
        colId.setCellValueFactory(new PropertyValueFactory<Customer,Integer>("customerId"));
        colName.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Customer, String>("email"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<Customer, String>("phoneNumber"));
    }

    public void onSelected(MouseEvent mouseEvent) {
        Customer customer = tbCustomer.getSelectionModel().getSelectedItem();
        if(customer != null){
            String customerIdString = String.valueOf(customer.getCustomerId());
            txtId.setText(customerIdString);
            txtName.setText(customer.getName());
            txtEmail.setText(customer.getEmail());
            txtPhoneNumber.setText(customer.getPhoneNumber());
        }
    }

    public void onClickAdd(ActionEvent actionEvent) throws SQLException {
        customerService.addCustomer(txtName.getText(), txtEmail.getText(), txtPhoneNumber.getText());
        tbCustomer.setItems(customerService.customersData());
    }
}
