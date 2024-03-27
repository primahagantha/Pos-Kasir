package com.dotjava.cashierapp.config;

import java.sql.DriverManager;
import java.sql.Connection;

public class db_config {
    public static Connection conn;

    static final String JDBC_URL = "jdbc:mysql://u5573_nCf0U3cvJC:8w%5Ev%5E%3DVkuHo.j0HXaWt3N4ZV@51.79.145.219:3306/s5573_cashier-app";
    static final String username = "u5573_nCf0U3cvJC";
    static final String password = "8w^v^=VkuHo.j0HXaWt3N4ZV";

    public static void initDBConnection()
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(JDBC_URL, username, password);

            if(conn != null)
            {
                System.out.println("Connection is established");
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }
}
