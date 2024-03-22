package com.dotjava.cashierapp.controller;

import com.dotjava.cashierapp.ItemBought;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import com.dotjava.cashierapp.Item;
import com.dotjava.cashierapp.models.item_db;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.InputMethodEvent;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.ResourceBundle;

public class cashierController implements Initializable {
    ArrayList <Item> dataBarang = item_db.getAllItems();
    ObservableList <ItemBought>  keranjang = FXCollections.observableArrayList();

    @FXML
    private TextField input_user;

    @FXML
    private Label nama_barang;

    @FXML
    private Label harga_barang;

    @FXML
    private Label message;

    @FXML
    private TableView<ItemBought> table_data;

//    @FXML private TableColumn<ItemBought, Integer> table_no;
    @FXML private TableColumn<ItemBought, String> table_code;
    @FXML private TableColumn<ItemBought, String> table_name;
    @FXML private TableColumn<ItemBought, String> table_single_price;
    @FXML private TableColumn<ItemBought, Integer> table_amount;
    @FXML private TableColumn<ItemBought, Integer> table_total;
    @FXML private TableColumn<ItemBought, String> table_action;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadData();
        table_amount.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
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
    public void loadData  () {

//        table_no.setCellValueFactory(new PropertyValueFactory<ItemBought, Integer>("table_no"));
        table_code.setCellValueFactory(new PropertyValueFactory<ItemBought, String>("code"));
        table_name.setCellValueFactory(new PropertyValueFactory<ItemBought, String>("name"));
        table_single_price.setCellValueFactory(new PropertyValueFactory<ItemBought, String>("price"));
        table_amount.setCellValueFactory(new PropertyValueFactory<ItemBought, Integer>("jumlah"));
        table_total.setCellValueFactory(new PropertyValueFactory<ItemBought, Integer>("total"));
//        table_action.setCellValueFactory(new PropertyValueFactory<ItemBought, String>("table_action"));


    }

    public void setJumlah(TableColumn.CellEditEvent<ItemBought, Integer> itemBoughtIntegerCellEditEvent) {
        ItemBought editedItemBought = table_data.getSelectionModel().getSelectedItem();
        editedItemBought.setJumlah(itemBoughtIntegerCellEditEvent.getNewValue());
//        set total cell to update immediately when there is any change in amount cell
//        table_data.getItems().set(1, editedItemBought);
    }

    public void getTotalCellValue(InputMethodEvent inputMethodEvent) {

        System.out.println("changed detected");

    }
}