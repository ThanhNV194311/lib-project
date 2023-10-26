package com.example.Utils;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.ObservableList;

import java.util.List;

public class TableUtil {
        public static <T> void showOnTable(TableView<T> table, ObservableList<T> data, TableColumn<T, ?>... columns) {
            table.setItems(data);
            table.getColumns().setAll(columns);
        }


}
