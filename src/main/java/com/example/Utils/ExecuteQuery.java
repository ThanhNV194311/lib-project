package com.example.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ExecuteQuery {
    private static ExecuteQuery instance;

    private final static String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/lib";
    private final static String username = "root";
    private final static String password = "Thinh@123";

    private ResultSet resultSet; // Member variable for storing the ResultSet
    private Connection connection;
    private Statement statement;

    private ExecuteQuery() {
        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize database connection.", e);
        }
    }

    public static ExecuteQuery getInstance() {
        if (instance == null) {
            synchronized (ExecuteQuery.class) {
                if (instance == null) {
                    instance = new ExecuteQuery();
                }
            }
        }
        return instance;
    }

    public ResultSet executeQuery(String sql) {
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sql); // Update the member variable
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void executeUpdate(String sql) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // them method de lay ket qua
    public ResultSet getResultSet() {
        return resultSet;
    }
    public void delete(String table, String condition) {
        String sql = "DELETE FROM " + table + " WHERE " + condition;
        executeUpdate(sql);
    }
    

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

    // method de dong ket noi
    public void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void openConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(jdbcUrl, username, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
