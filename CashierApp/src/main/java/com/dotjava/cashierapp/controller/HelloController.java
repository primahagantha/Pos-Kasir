package com.dotjava.cashierapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import com.dotjava.cashierapp.Item;
import com.dotjava.cashierapp.models.item_db;

import java.util.ArrayList;

public class HelloController {
    ArrayList <Item> dataBarang = item_db.getAllItems();

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        if(dataBarang != null){
            System.out.println(dataBarang.get(1).getName());
            System.out.println(dataBarang.get(1).getPrice());
            System.out.println(dataBarang.get(1).getCode());

        }else
        {
            System.out.println("No Data in DB");
        }
        welcomeText.setText("Welcome to JavaFX Application!");
    }

//    public void main(String[] args) {
//        if(dataBarang != null){
//            System.out.println(dataBarang);
//        }else
//        {
//            System.out.println("No Data in DB");
//        }
//    }
}