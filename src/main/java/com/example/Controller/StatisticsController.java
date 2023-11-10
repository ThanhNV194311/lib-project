package com.example.Controller;

import com.example.Service.StatisticsService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StatisticsController implements Initializable {

    public PieChart pieChart;
    public BarChart barChart;
    @FXML
    private Text txtSoLuongKhach;

    @FXML
    private Text txtSoSachDangMuon;

    @FXML
    private Text txtSoLuongSach;
    private final StatisticsService statisticsService;


    public StatisticsController() {
        this.statisticsService = new StatisticsService();
    }

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

            e.printStackTrace();
        }
    }


}
