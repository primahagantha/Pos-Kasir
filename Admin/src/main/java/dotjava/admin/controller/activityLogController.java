package dotjava.admin.controller;

import dotjava.admin.activityLog;
import dotjava.admin.models.activityLog_db; // Use the correct class name
import javafx.application.Platform; // Import for potential navigation (optional)
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader; // Import for potential navigation (optional)
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.Pane; // Import for potential navigation (optional)


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.EventObject;
import java.util.List; // Import List
import java.util.ResourceBundle;

public class activityLogController implements Initializable {

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableView<activityLog> tableView;

    @FXML
    private TableColumn<activityLog, Integer> idActColumn;

    @FXML
    private TableColumn<activityLog, String> tanggalColumn;

    @FXML
    private TableColumn<activityLog, Integer> userIdColumn;

    @FXML
    private TableColumn<activityLog, String> userColumn;

    @FXML
    private TableColumn<activityLog, String> typeColumn;

    @FXML
    private TableColumn<activityLog, String> timeColumn;

    @FXML
    private Button clearButton;

    @FXML
    private Label switchToMenu;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateTable();
        // Added for clarity and separation of concerns
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            filterLogsByDate(newValue);
        });

        // Added for Clear button functionality
        clearButton.setOnAction(event -> {
            datePicker.setValue(null);
            tableView.getItems().clear();
            populateTable(); //
        });
    }

    @FXML
    private void populateTable() {
        try {
            System.out.println("Hello User, Activity Log Here");
            activityLog_db db = new activityLog_db();
            List<activityLog> logs = db.getActivityLogs();

            idActColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdAct()).asObject());
            userIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdUser()).asObject());
            tanggalColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate()));
            userColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
            typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTypeAct()));
            timeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTime()));
            // Populate table with data
            tableView.getItems().addAll(logs);

        } catch (SQLException e) {
            // Handle database errors (e.g., display an error message)
            e.printStackTrace();
        }
    }

    @FXML
    private void filterLogsByDate(LocalDate selectedDate) {
        try {
            activityLog_db db = new activityLog_db();
            List<activityLog> filteredLogs = db.getActivityLogs(selectedDate); // Pass the selectedDate argument
            tableView.getItems().clear();
            tableView.getItems().addAll(filteredLogs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Optional method for navigation back to main menu (replace with your logic)
    @FXML
    private void handleBackToMenu(javafx.event.ActionEvent event) {
        // Load the FXML for the main menu scene
        System.out.println("Back to Menu clicked!");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(mainController.class.getResource("/dotjava/admin/index.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 400, 600);
            System.out.println("Admin clicked Admin Menu button");
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Admin: Main Menu");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            // Handle the IOException here (e.g., display error message)
            e.printStackTrace();  // For debugging purposes (remove in production)
            System.out.println("Error loading itemEntry.fxml: " + e.getMessage());
        }
    }

}
