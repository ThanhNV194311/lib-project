package com.example.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import com.example.Service.StatisticsService;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StatisticsController implements Initializable {

    @FXML
    private Text txtSoLuongKhach;

    @FXML
    private Text txtSoSachDangMuon;

    @FXML
    private Text txtSoLuongSach;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            int totalCustomers = statisticsService.getTotalCustomers();
            int totalBorrowedBooks = statisticsService.getTotalBorrowedBooks();
            int totalBooks = statisticsService.getTotalBooks();

            txtSoLuongKhach.setText(Integer.toString(totalCustomers));
            txtSoSachDangMuon.setText(Integer.toString(totalBorrowedBooks));
            txtSoLuongSach.setText(Integer.toString(totalBooks));
        } catch (SQLException e) {
            // Handle the exception, e.g., show an error message or log the exception
            e.printStackTrace();
        }
    }
    private StatisticsService statisticsService;

    public StatisticsController() {
        this.statisticsService = new StatisticsService();
    }



}
