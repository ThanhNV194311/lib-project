package com.example.Service;

import com.example.Common.Regex;
import com.example.Exception.EmailNotValidException;
import com.example.Exception.NullException;
import com.example.Exception.PhoneNumberNotValidException;
import com.example.Models.Customer;
import com.example.Utils.AlertUtil;
import com.example.Utils.ExecuteQuery;
import com.example.Utils.ExportToExcel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CustomerService {
    ExecuteQuery executeQuery = ExecuteQuery.getInstance();
    public ObservableList<Customer> customersData() throws SQLException {
        ObservableList<Customer> result = FXCollections.observableArrayList();

        String getCustomerSql="Select * from customer where is_delete = '" + "0'";
        ResultSet resultSet = executeQuery.executeQuery(getCustomerSql);

        while (resultSet.next()){
            Customer customer = new Customer(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );
            result.add(customer);
        }

        return result;
    }

    public void clear(TextField ...textFields){
        for (TextField textField : textFields) {
            textField.clear();
        }
    }

    public void addCustomer(int id, String name, String email, String phoneNumber) throws EmailNotValidException, PhoneNumberNotValidException, NullException {
        validateInformation(name, email, phoneNumber);

        String addCustomerSql = "INSERT INTO customer (customer_id,name, email, phone_number, identity_card) VALUES (" +
                id + ", '" +
                name + "', '" +
                email + "', '" +
                phoneNumber + "', '2')";

        executeQuery.executeUpdate(addCustomerSql);
    }

    private void validateInformation(String name, String email, String phoneNumber) throws NullException, EmailNotValidException, PhoneNumberNotValidException {
        if(name.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()){
            throw new NullException("Không được để trống các thông tin");
        }

        if(!isValidEmail(email)){
            throw new EmailNotValidException("Email không hợp lệ");
        }

        if(!isValidPhoneNumber(phoneNumber)){
            throw new PhoneNumberNotValidException("Số đện thoại không hợp lệ");
        }
    }

    public void deleteCustomer(String customerId){
        String deleteCustomerSql = "UPDATE customer "
                + "SET is_delete = 1 "
                + "WHERE identity_card = '" + customerId + "'";

        if(AlertUtil.showConfirmation("Việc xoá này sẽ xoá toàn bộ lịch sử mượn của khách!")){
            executeQuery.executeUpdate(deleteCustomerSql);
        }

    }

    private boolean isValidEmail(String email){
        Pattern pattern = Pattern.compile(Regex.rgEmail);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidPhoneNumber(String phoneNumber){
        Pattern pattern = Pattern.compile(Regex.rgPhoneNumber);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }


    public void updateCustomer(String customerIdOld, String name, String email, String phoneNumber, String customerIdNew) throws PhoneNumberNotValidException, EmailNotValidException, NullException {
        validateInformation(name, email, phoneNumber);

        String updateCustomerSql = "UPDATE Customer "
                + "SET name = '" + name + "', "
                + "identity_card = '" + customerIdNew + "', "
                + "email = '" + email + "', "
                + "phone_number = '" + phoneNumber + "' "
                + "WHERE identity_card = '" + customerIdOld + "'";

        executeQuery.executeUpdate(updateCustomerSql);
    }

    public void exportToExcel(TableView tableView, String fileName){
        if (AlertUtil.showConfirmation("Bạn có muốn xuất dữ liệu ra excel không?")) {
            ExportToExcel.exportToExcel(tableView, "Courses.xlsx");
            AlertUtil.showAlert(Alert.AlertType.INFORMATION, "Thông báo", null, "Xuất dữ liệu thành công!");
        }
    }
}
