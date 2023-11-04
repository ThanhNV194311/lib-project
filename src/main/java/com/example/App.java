package com.example;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * JavaFX App
 */

public class App extends Application {

    private static Scene scene;
    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;

        Application.setUserAgentStylesheet("primer-light.css");
        Parent root = loadFXML("LoginFrm");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(StageStyle.DECORATED);
        stage.setResizable(false);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        Parent root = loadFXML(fxml);
        scene.setRoot(root);
        // Đặt lại kích thước cửa sổ chính theo kích thước của giao diện người dùng trong FXML
        stage.setWidth(root.prefWidth(-1));
        stage.setHeight(root.prefHeight(-1));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void setRootPop(String fxml, String title, boolean resizable) throws IOException {
        Stage stage = new Stage();
        Scene newScene = new Scene(loadFXML(fxml), 668, 467);
        stage.setResizable(resizable);
        stage.setScene(newScene);
        stage.setTitle(title);

        // khi stage moi duoc hien thi thi stage cha se bi tat
        Stage parentStage = (Stage) scene.getWindow();
        parentStage.setOpacity(0.95);

        // Re-enable the parent stage when the new stage is hidden
        stage.setOnHidden(e -> parentStage.setOpacity(1.0));

        // Show the new stage as a dialog and wait for it to close
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}