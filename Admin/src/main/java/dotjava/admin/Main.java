package dotjava.admin;

import dotjava.admin.config.db_config;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static Scene scene;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("admin.fxml"));
        scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Admin Page");
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(AnchorPane pane, Parent parent) {
        pane.getChildren().clear();
        pane.getScene().setRoot(parent);
    }

    public static void main(String[] args) {
        db_config.initDBConnection();
        launch();
    }

}