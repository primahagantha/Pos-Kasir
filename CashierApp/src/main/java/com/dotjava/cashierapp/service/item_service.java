package com.dotjava.cashierapp.service;

import com.dotjava.cashierapp.Item;
import com.dotjava.cashierapp.models.item_db;

public class item_service {
    Item barang = new Item();

    public void setBarang(Item barang) {
        this.barang = barang;
        barang.setName("Milku");
        barang.setPrice(5000);
    }


}
