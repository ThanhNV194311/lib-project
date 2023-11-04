package com.example.Utils;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExportToExcel {

    public static <T> void exportToExcel(TableView<T> table, Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Lưu tệp Excel");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
        fileChooser.setInitialFileName("export.xlsx");
        File file = fileChooser.showSaveDialog(primaryStage);

        if (file != null) {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Sheet 1");
            XSSFRow headerRow = sheet.createRow(0);

            // Tạo header
            List<TableColumn<T, ?>> columns = table.getColumns();
            for (int i = 0; i < columns.size(); i++) {
                headerRow.createCell(i).setCellValue(columns.get(i).getText());
            }

            // Thêm dữ liệu
            ObservableList<T> items = table.getItems();
            for (int row = 0; row < items.size(); row++) {
                Row sheetRow = sheet.createRow(row + 1);
                T item = items.get(row);
                for (int col = 0; col < columns.size(); col++) {
                    TableColumn<T, ?> column = columns.get(col);
                    Cell cell = sheetRow.createCell(col);
                    Object value = column.getCellData(item);
                    if (value != null) {
                        switch (value.getClass().getSimpleName()) {
                            case "String":
                                cell.setCellValue((String) value);
                                break;
                            case "Double":
                                cell.setCellValue((Double) value);
                                break;
                            case "Integer":
                                cell.setCellValue((Integer) value);
                                break;
                            case "Boolean":
                                cell.setCellValue((Boolean) value);
                                break;
                            default:
                                cell.setCellValue(value.toString());
                                break;
                        }
                    }
                }
            }

            // Lưu file
            try (FileOutputStream fileOut = new FileOutputStream(file)) {
                workbook.write(fileOut);
            } catch (IOException e) {
                System.out.println("Lỗi khi lưu file");
                e.printStackTrace();
            }

            // Đóng workbook
            try {
                workbook.close();
            } catch (IOException e) {
                System.out.println("Lỗi khi đóng tệp");
                e.printStackTrace();
            }
        }
    }
}
