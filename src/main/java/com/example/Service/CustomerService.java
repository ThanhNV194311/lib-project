package com.example.Service;

import com.example.Common.Regex;
import com.example.Exception.EmailNotValidException;
import com.example.Exception.NullException;
import com.example.Exception.PhoneNumberNotValidException;
import com.example.Models.Customer;
import com.example.Utils.AlertUtil;
import com.example.Utils.ExecuteQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CustomerService {
    ExecuteQuery executeQuery = ExecuteQuery.getInstance();
    public ObservableList<Customer> customersData() throws SQLException {
        ObservableList<Customer> result = FXCollections.observableArrayList();

        String getCustomerSql="Select * from customer";
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

    public void addCustomer(String name, String email, String phoneNumber) throws EmailNotValidException, PhoneNumberNotValidException, NullException {
        if(name.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()){
            throw new NullException("Không được để trống các thông tin");
        }

        if(!isValidEmail(email)){
            throw new EmailNotValidException("Email không hợp lệ");
        }

        if(!isValidPhoneNumber(phoneNumber)){
            throw new PhoneNumberNotValidException("Số đện thoại không hợp lệ");
        }

        String addCustomerSql = "INSERT INTO customer (name, email, phone_number, identity_card) VALUES ('" +
                name + "', '" +
                email + "', '" +
                phoneNumber + "', '2')";

        executeQuery.executeUpdate(addCustomerSql);
    }

    public void deleteCustomer(String customerId){
        String deleteCustomerSql = "Delete from Customer Where customer_id = '" + customerId + "'";
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


}
