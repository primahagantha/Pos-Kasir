package com.dotjava.cashierapp.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class sceneController {

    public static void switchToMainApp(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(sceneController.class.getResource("/com/dotjava/cashierapp/index.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 754, 639);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public static void switchToLogin(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(sceneController.class.getResource("/com/dotjava/cashierapp/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public static void switchToRegister(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(sceneController.class.getResource("/com/dotjava/cashierapp/register.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
