package com.example.Controller;

import com.example.App;
import com.example.Service.LoginService;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Label lbErr;
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
        if(loginService.doLogin(txtUsername.getText(), txtPassword.getText()) == 0){
            lbErr.setText("*Tài khoản hoặc mật khẩu không được để trống");
            return;
        }

        if(loginService.doLogin(txtUsername.getText(), txtPassword.getText()) == 1){
            lbErr.setText("*Tài khoản hoặc mật khẩu không chính xác");
            return;
        }

        App.setRoot("DashBoardFrm");
    }

}
