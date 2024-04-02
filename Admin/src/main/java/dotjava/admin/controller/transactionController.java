package dotjava.admin.controller;

import dotjava.admin.models.transactionLog_db;
import dotjava.admin.transactionLog;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class transactionController implements Initializable {

    @FXML
    private DatePicker tanggalPicker;

    @FXML
    private TableColumn<transactionLog, String> tanggalTransaksiColumn;

    @FXML
    private TableView<transactionLog> informasiTable;



    @FXML
    private TableView<transactionLog> transaksiTable;

    @FXML
    private TableColumn<transactionLog, Integer> idTransaksiColumn;

    @FXML
    private TableColumn<transactionLog, Integer> idActColumn;

    @FXML
    private TableColumn<transactionLog, String> userColumn;

    @FXML
    private TableColumn<transactionLog, String> dateColumn;

    @FXML
    private TextField totalField;

    @FXML
    private TextField userInfo;

    @FXML
    private TextField timeInfo;

    @FXML
    private Button refreshButton;



    @FXML
    TableColumn<transactionLog, Integer> idTransaksiColumnInfo;

    @FXML
    TableColumn<transactionLog, Integer> idItemTerjualColumnInfo;

    @FXML
    TableColumn<transactionLog, String> itemInfo;
    @FXML
    TableColumn<transactionLog, Integer> cashColumnInfo;


    private ObservableList<transactionLog> transactions;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        transactions = FXCollections.observableArrayList();
        transaksiTable.setItems(transactions);

        populateTable();

        tanggalPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            handleDatePicker(newValue);
        });

        refreshButton.setOnAction(event -> {
            tanggalPicker.setValue(null);
            transaksiTable.getItems().clear();
            informasiTable.getItems().clear();
            populateTable(); //
        });

        transaksiTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                try {
                    populateSelectTable(newSelection);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(newSelection);
            } else {
                clearTextFields();
            }

        });
    }

    @FXML
    private void populateTable() {
        transactions.clear();
        List<transactionLog> allLogs = transactionLog_db.fetchAllTransHistory();
        transactions.addAll(allLogs);
        updateTotalField();

        idTransaksiColumn.setCellValueFactory(new PropertyValueFactory<>("idTransaction"));
        idActColumn.setCellValueFactory(new PropertyValueFactory<>("idAct"));
        tanggalTransaksiColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private void updateTotalField() {
        double total = transactions.stream().mapToDouble(transactionLog::getTotal).sum();
        totalField.setText(String.valueOf(total));
    }

    @FXML
    private void handleDatePicker(LocalDate selectedDate) {
        try {
            List<transactionLog> filteredLogs = transactionLog_db.getFilteredTransactionLogLogs(selectedDate);
            transactions.clear();
            transactions.addAll(filteredLogs);
            updateTotalField();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearTextFields() {
        totalField.setText("");
        timeInfo.setText("");
        userInfo.setText("");
    }

    private void populateSelectTable(transactionLog newSelection) throws SQLException {
        totalField.setText(String.valueOf(newSelection.getTotal()));
        timeInfo.setText(newSelection.getTime());
        userInfo.setText(newSelection.getUser());

        idTransaksiColumnInfo.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdTransaction()).asObject());
        idItemTerjualColumnInfo.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdItemSold()).asObject());
        itemInfo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItem()));
        cashColumnInfo.setCellValueFactory(cellData -> new SimpleIntegerProperty((int) cellData.getValue().getCash()).asObject());

        // Misalnya, ambil informasi tambahan dari objek transactionLog yang dipilih
        String additionalInfo = "Additional Info: " + newSelection.getIdItemSold() + ", Cash: " + newSelection.getCash();

        // Tambahkan informasi tambahan ke dalam list
        List<String> additionalInfoList = new ArrayList<>();
        additionalInfoList.add(additionalInfo);

        // Set sumber data untuk informasiTable
        ObservableList<String> observableAdditionalInfoList = FXCollections.observableArrayList(additionalInfoList);

        // Membuat ObservableList baru yang berisi detail transaksi yang dipilih
        ObservableList<transactionLog> selectedTransaction = FXCollections.observableArrayList();
        selectedTransaction.add(newSelection);

        informasiTable.setItems(selectedTransaction);

        System.out.println("Data yang dipilih dari transaksi:");
        System.out.println(newSelection.getIdTransaction() + " - " + newSelection.getIdItemSold() + " - " + newSelection.getItem() + " - " + newSelection.getCash());

    }




}
