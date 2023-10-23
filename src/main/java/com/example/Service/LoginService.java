package com.example.Service;


import com.example.Utils.ExecuteQuery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginService {
    ExecuteQuery executeQuery = ExecuteQuery.getInstance();
    public boolean doLogin(String username, String password) throws SQLException {
        String sql = "SELECT COUNT(*) FROM user WHERE username = "+ "\"" + username + "\"" +" AND password = " + "\"" +password +"\"" ;
        ResultSet resultSet = executeQuery.executeQuery(sql);
        return resultSet.next();
    }
}
