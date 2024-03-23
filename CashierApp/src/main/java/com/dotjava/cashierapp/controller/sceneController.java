package com.dotjava.cashierapp.controller;

import com.dotjava.cashierapp.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Objects;

public class sceneController {

    public static void switchToMainApp(javafx.event.ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(sceneController.class.getResource("/com/dotjava/cashierapp/index.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 754, 639);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }
}
