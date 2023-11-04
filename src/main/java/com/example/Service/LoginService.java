package com.example.Service;


import com.example.Utils.ExecuteQuery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginService { 
    ExecuteQuery executeQuery = ExecuteQuery.getInstance();
    public int doLogin(String username, String password) throws SQLException {
        if(isEmpty(username, password)){
            return 0;
        }

        if(!checkAccount(username, password)){
            return 1;
        }

        return 2;

    }

    private Boolean checkAccount(String username, String password) throws SQLException {
        String sql = "SELECT COUNT(*) FROM user WHERE username = "+ "\"" + username + "\"" +" AND password = " + "\"" +password +"\"" ;
        ResultSet resultSet = executeQuery.executeQuery(sql);
        if (resultSet.next()) {
            int count = resultSet.getInt(1);
            return count > 0;
        }
        return false;
    }

    private Boolean isEmpty(String username, String password){
        return username.isEmpty() || password.isEmpty();
    }
}
