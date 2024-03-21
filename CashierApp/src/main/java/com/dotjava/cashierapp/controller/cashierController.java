package com.dotjava.cashierapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import com.dotjava.cashierapp.Item;
import com.dotjava.cashierapp.models.item_db;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class cashierController {
    ArrayList <Item> dataBarang = item_db.getAllItems();
    ArrayList <Item> keranjang = new ArrayList<Item>();

    @FXML
    private TextField input_user;

    @FXML
    private Label nama_barang;

    @FXML
    private Label harga_barang;

    @FXML
    private Label message;

    @FXML
    private TableView<Item> table_data;

    @FXML private TableColumn<Item, Integer> table_no;
    @FXML private TableColumn<Item, String> table_code;
    @FXML private TableColumn<Item, String> table_name;
    @FXML private TableColumn<Item, String> table_single_price;
    @FXML private TableColumn<Item, Integer> table_amount;
    @FXML private TableColumn<Item, Integer> table_total;
    @FXML private TableColumn<Item, String> table_action;

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
            getKerangjang();
            int code = Integer.parseInt(input_user.getText());

            Item selectedItem =  item_db.getItemByCode(code);

            if(selectedItem != null){
                for (Item i: keranjang) {
                    if(i.getCode().equals(selectedItem.getCode())){
                        throw new Exception("Data Sudah Masuk, Silahkan Edit ! ");
                    }
                }

                keranjang.add(selectedItem);
                nama_barang.setText("Nama : " +  selectedItem.getName());
                harga_barang.setText("Harga : " +  Integer.toString(selectedItem.getPrice()));

                message.setText("=> Data berhasil diinput");
                System.out.println("Data berhasil diinput");

            }else {
                message.setText("=> Data Tidak Ditemukan");
                System.out.println("Data Tidak Ditemukan ");
            }

        }catch (NumberFormatException e) {
            message.setText("=> Invalid Code Format");
            System.out.println("Invalid Code Format");
        }catch (Exception e){
            message.setText("=> " + e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    @FXML
    protected void getKerangjang(){
        for (Item i: keranjang) {
            table_data.getItems().add(i);
        }
    }

    @FXML
    public void getKeranjang() {
        table_no.setCellValueFactory(new PropertyValueFactory<Item, Integer>("table_no"));
        table_code.setCellValueFactory(new PropertyValueFactory<Item, String>("table_code"));
        table_name.setCellValueFactory(new PropertyValueFactory<Item, String>("table_name"));
        table_single_price.setCellValueFactory(new PropertyValueFactory<Item, String>("table_single_price"));
        table_amount.setCellValueFactory(new PropertyValueFactory<Item, Integer>("table_amount"));
        table_total.setCellValueFactory(new PropertyValueFactory<Item, Integer>("table_total"));
        table_action.setCellValueFactory(new PropertyValueFactory<Item, String>("table_action"));

        for (Item i: keranjang) {
            table_data.getItems().add(i);
        }
    }

}