package com.example.Controller;

import com.example.App;
import com.example.Service.LoginService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private AnchorPane pane;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;

    private LoginService loginService;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        loadPane();
        loginService = new LoginService();
    }

//    private void loadPane(){
//        String frm = "/com/example/Windows.fxml";
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource(frm));
//            AnchorPane adminPane = loader.load();
//            pane.getChildren().add(adminPane);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void onClickLogin(ActionEvent actionEvent) throws SQLException, IOException {
        if(loginService.doLogin(txtUsername.getText(), txtPassword.getText())){
            App.setRoot("DashBoard");
        }
    }
}
