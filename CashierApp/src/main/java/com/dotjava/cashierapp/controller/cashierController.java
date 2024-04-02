package com.dotjava.cashierapp.controller;

import com.dotjava.cashierapp.ItemBought;
import com.dotjava.cashierapp.models.user_db;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import com.dotjava.cashierapp.Item;
import com.dotjava.cashierapp.models.item_db;
import com.dotjava.cashierapp.service.userSession_service;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.ResourceBundle;


public class cashierController implements Initializable {
    ArrayList <Item> dataBarang = item_db.getAllItems();
    ObservableList <ItemBought>  keranjang = FXCollections.observableArrayList();

    @FXML private TextField input_user;

    @FXML private Label welcomeText;

    @FXML private TextField inputPembayaranCell;

    @FXML private Label totalBelanja;

    @FXML private Label nama_barang;

    @FXML private Label harga_barang;

    @FXML private Label message;

    @FXML private TableView<ItemBought> table_data;

    @FXML private TableColumn<ItemBought, String> table_code;
    @FXML private TableColumn<ItemBought, String> table_name;
    @FXML private TableColumn<ItemBought, String> table_single_price;
    @FXML private TableColumn<ItemBought, Integer> table_amount;
    @FXML private TableColumn<ItemBought, Integer> table_total;
    @FXML private TableColumn<ItemBought, Void> table_action;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        welcomeText.setText("Selamat Datang, " + userSession_service.getUserFullName() + " !");
        table_amount.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        loadData();
    }

    @FXML
    protected void getAllItemsButton() {
        if(dataBarang != null){
            System.out.println(dataBarang.get(1).getName());
            System.out.println(dataBarang.get(1).getPrice());
            System.out.println(dataBarang.get(1).getCode());

        }else
        {
            System.out.println("No Data in DB");
        }
    }

    @FXML
    protected void getItemByCode(javafx.event.ActionEvent actionEvent) {

        try{
            int code = Integer.parseInt(input_user.getText());

            Item selectedItem =  item_db.getItemByCode(code);

            if(selectedItem != null){
                for (Item i: keranjang) {
                    if(i.getCode().equals(selectedItem.getCode())){
                        throw new Exception("Data Sudah Masuk, Silahkan Edit ! ");
                    }
                }

                ItemBought soldItem = new ItemBought();
                soldItem.setName(selectedItem.getName());
                soldItem.setPrice(selectedItem.getPrice());
                soldItem.setCode(selectedItem.getCode());
                soldItem.setJumlah(1);

                keranjang.add(soldItem);
                table_data.setItems(keranjang);

                nama_barang.setText("Nama : " +  selectedItem.getName());
                harga_barang.setText("Harga : " +  Integer.toString(selectedItem.getPrice()));
                totalBelanja.setText("Total Belanja :   " + Integer.toString(countTotalBelanja()));

                message.setTextFill(Color.GREEN);
                message.setText("=> Data berhasil diinput");
                System.out.println("Data berhasil diinput");

            }else {
                message.setTextFill(Color.RED);
                message.setText("=> Data Tidak Ditemukan");
                System.out.println("Data Tidak Ditemukan ");
            }

        }catch (NumberFormatException e) {
            message.setTextFill(Color.RED);
            message.setText("=> Invalid Code Format");
            System.out.println("Invalid Code Format");
        }catch (Exception e){
            message.setTextFill(Color.RED);
            message.setText("=> " + e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    public Integer countTotalBelanja(){
        Integer totalBelanjaKeranjang = 0;
        for (ItemBought i: keranjang) {
            totalBelanjaKeranjang += i.getTotal();
        }
        return  totalBelanjaKeranjang;
    }

    @FXML
    public void loadData  () {

        table_amount.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        table_code.setCellValueFactory(new PropertyValueFactory<ItemBought, String>("code"));
        table_name.setCellValueFactory(new PropertyValueFactory<ItemBought, String>("name"));
        table_single_price.setCellValueFactory(new PropertyValueFactory<ItemBought, String>("price"));
        table_amount.setCellValueFactory(new PropertyValueFactory<ItemBought, Integer>("jumlah"));
        table_total.setCellValueFactory(new PropertyValueFactory<ItemBought, Integer>("total"));

        Callback<TableColumn<ItemBought, Void>, TableCell<ItemBought, Void>> deleteButton = new Callback<>() {
            @Override
            public TableCell<ItemBought, Void> call(final TableColumn<ItemBought, Void> param) {
                return new TableCell<>() {
                    private final Button btn = new Button("Delete");

                    {
                        btn.setStyle("-fx-background-color: red;");
                        btn.setOnAction((ActionEvent event) -> {
                            ItemBought data = getTableView().getItems().get(getIndex());
                            System.out.println("Button clicked: " + data.getName() + " Deleted");
                            keranjang.remove(getIndex());

                            message.setTextFill(Color.GREEN);
                            message.setText(data.getName() + " Deleted");
                            totalBelanja.setText("Total Belanja :   " + Integer.toString(countTotalBelanja()));
                            table_data.refresh();
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
            }
        };

        table_action.setCellFactory(deleteButton);

    }

    public void setJumlahCell(TableColumn.CellEditEvent<ItemBought, Integer> itemBoughtIntegerCellEditEvent) {

        ItemBought editedItemBought = table_data.getSelectionModel().getSelectedItem();
        editedItemBought.setJumlah(itemBoughtIntegerCellEditEvent.getNewValue());
        totalBelanja.setText("Total Belanja :   " + Integer.toString(countTotalBelanja()));
        table_data.refresh();

    }


    public void itemTransaction(ActionEvent actionEvent) {
        try{
            int uangDiberi = Integer.parseInt(inputPembayaranCell.getText());
            int tagihan = countTotalBelanja();

            if(uangDiberi < tagihan){
                throw new Exception("Uang yang diinput tidak cukup untuk melakukan transaksi");
            }

            int insertedIdActivity = user_db.setUserActivity("TR");

            for (ItemBought i: keranjang) {
                if(item_db.setTransactionHistory(insertedIdActivity,uangDiberi, i) > 0){
                    System.out.println("data masuk " + i.getName());
                }else{
                    throw new SQLException();
                }
            }

            keranjang.clear();
            table_data.refresh();
            message.setTextFill(Color.GREEN);
            message.setText("=> Pembayaran Berhasil");
            System.out.println("Pembayaran Berhasil");
        }catch (NumberFormatException e) {
            message.setTextFill(Color.RED);
            message.setText("=> Invalid Code Format");
            System.out.println("Invalid Code Format");
        } catch (Exception e){
            message.setTextFill(Color.RED);
            message.setText("=> " + e.getMessage());
            System.out.println("=> " + e.getMessage());
        }
    }

    public void logOutUser(ActionEvent actionEvent) throws IOException {
        userSession_service.cleanSession();
        sceneController.switchToLogin(actionEvent);
    }

}