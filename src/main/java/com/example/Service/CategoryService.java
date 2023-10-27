package com.example.Service;

import com.example.Models.Category;
import com.example.Utils.ExecuteQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryService {
    private ExecuteQuery executeQuery;

    public CategoryService() {
        this.executeQuery = ExecuteQuery.getInstance();
    }

    public List<Category> getAllCategories() throws SQLException {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM category";

        ResultSet resultSet = executeQuery.executeQuery(sql);

        while (resultSet.next()) {
            Category category = new Category(
                resultSet.getInt("category_id"),
                resultSet.getString("name")
            );
            categories.add(category);
        }

        return categories;
    }

    public void addCategory(Category category) throws SQLException {
        String sql = "INSERT INTO category (name) VALUES ('" + category.getName() + "')";
        executeQuery.executeUpdate(sql);
    }
}
