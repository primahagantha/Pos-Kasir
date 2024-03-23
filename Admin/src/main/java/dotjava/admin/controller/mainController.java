package dotjava.admin.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class mainController implements Initializable {
    @FXML
    private AnchorPane mainContainer;

    @FXML
    private Button itemEntryButton;

    @FXML
    private Button activityLogButton;

    @FXML
    private Button transactionLogButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        itemEntryButton.setOnAction(event -> handleButtonClick("Item Entry", "itemEntry.fxml"));
        activityLogButton.setOnAction(event -> handleButtonClick("Activity Log", "activityLog.fxml"));
        transactionLogButton.setOnAction(event -> handleButtonClick("Transaction Log", "transactionLog.fxml"));
    }

    private void handleButtonClick(String buttonName, String fxmlFileName) {
        System.out.println("User clicked " + buttonName + " button");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Popup");
        alert.setHeaderText(null);
        alert.setContentText("Menampilkan " + buttonName);
        alert.showAndWait();

        try {
            System.out.println("Trying to load " + fxmlFileName);
            AnchorPane pane = FXMLLoader.load(getClass().getResource(fxmlFileName));
            mainContainer.getChildren().setAll(pane);
            System.out.println("Loaded " + fxmlFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
