package com.example.Service;

import com.example.Models.Author;
import com.example.Utils.ExecuteQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorService {
    private ExecuteQuery executeQuery;

    public AuthorService() {
        this.executeQuery = ExecuteQuery.getInstance();
    }

    public List<Author> getAllAuthors() throws SQLException {
        List<Author> authors = new ArrayList<>();
        String sql = "SELECT * FROM author";

        ResultSet resultSet = executeQuery.executeQuery(sql);

        while (resultSet.next()) {
            Author author = new Author(
                resultSet.getInt("author_id"),
                resultSet.getString("name")
            );
            authors.add(author);
        }

        return authors;
    }

    public void addAuthor(Author author) throws SQLException {
        String sql = "INSERT INTO author (name) VALUES ('" + author.getName() + "')";
        executeQuery.executeUpdate(sql);
    }
}
