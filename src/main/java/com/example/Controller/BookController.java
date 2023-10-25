package com.example.Controller;

import com.example.Service.BookService;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class BookController implements Initializable {
    private BookService bookService;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bookService = new BookService();
    }
}
