package com.dotjava.cashierapp.models;

import com.dotjava.cashierapp.Item;
import com.dotjava.cashierapp.config.db_config;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class item_db {
    static ArrayList<Item> tempArrItem;
    public static ArrayList<Item> getAllItems(){
        tempArrItem = new ArrayList<Item>();

        try {
            Statement statement = db_config.conn.createStatement();
            String query = "SELECT * FROM item_db";

            ResultSet result = statement.executeQuery(query);
            System.out.println(result);


            while (result.next()) {
                Item barang = new Item();

                String code = Integer.toString(result.getInt("id"));
                String name = result.getString("name");
                int price = result.getInt("price");

                barang.setName(name);
                barang.setPrice(price);
                barang.setCode(code);

                tempArrItem.add(barang);
            }

            return tempArrItem;
//            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
