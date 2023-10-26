package com.example.Service;

import com.example.Models.Customer;
import com.example.Utils.ExecuteQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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

}
