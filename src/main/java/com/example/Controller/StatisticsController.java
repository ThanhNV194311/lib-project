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
        pieChart.getData().add(new PieChart.Data("Sách đang mượn", 10));
        pieChart.getData().add(new PieChart.Data("Sách chưa mượn", 20));
        pieChart.getData().add(new PieChart.Data("Sách đã mượn", 30));

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Sách đang mượn", 10));
        series.getData().add(new XYChart.Data<>("Sách chưa mượn", 20));
        series.getData().add(new XYChart.Data<>("Sách đã mượn", 30));
        barChart.getData().add(series);

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


}
