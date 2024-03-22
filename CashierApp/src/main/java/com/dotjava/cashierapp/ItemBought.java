package com.dotjava.cashierapp;

import com.dotjava.cashierapp.Item;

public class ItemBought extends Item{
//    super();
    private Integer jumlah;

    private static Integer total;


    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

    public Integer getTotal() {
        return jumlah * this.getPrice();
    }

}
