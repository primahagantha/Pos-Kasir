package com.dotjava.cashierapp.models;

import com.dotjava.cashierapp.Item;
import com.dotjava.cashierapp.ItemBought;
import com.dotjava.cashierapp.config.db_config;
import com.dotjava.cashierapp.service.userSession_service;

import java.sql.PreparedStatement;
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

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Item getItemByCode(int code){
        Item tempBarang = new Item();

        try {
            boolean isDataFound = false;

            for (Item i : tempArrItem) {
                if (i.getCode().equals(Integer.toString(code))){
                    isDataFound = true;
                    return i;
                }
            }
            if (!isDataFound){
                return null;
            }
        }catch (Error e){
            System.out.println(e);
            return null;
        }
        return null;
    }

    public static Integer setTransactionHistory(int id_act, int cash ,ItemBought item){
        try{
            String query = "INSERT INTO `transaction_history`(`id_transaction`, `id_act`, `id_item_sold`,`cash`, `total`) VALUES (?,?,?,?,?)";

            PreparedStatement statement = db_config.conn.prepareStatement(query);

            statement.setNull(1, java.sql.Types.NULL);
            statement.setString(2,Integer.toString(id_act));
            statement.setString(3, item.getCode());
            statement.setInt(4, cash);
            statement.setInt(5, item.getTotal());

            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }






}
