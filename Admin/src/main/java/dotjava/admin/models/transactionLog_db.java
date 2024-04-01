package dotjava.admin.models;

import dotjava.admin.transactionLog;
import dotjava.admin.config.db_config;
import javafx.beans.property.SimpleStringProperty;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class transactionLog_db {

    public static ArrayList<transactionLog> fetchAllTransHistory() {
        ArrayList<transactionLog> transactions = new ArrayList<>();
        try (Statement statement = db_config.conn.createStatement()) {
            System.out.println("Fetching transaction history...");
            String query = "SELECT th.id_transaction, th.id_act, th.id_item_sold, th.cash, th.total, i.name, al.username, al.date, al.tipe_act " +
                    "FROM transaction_history th " +
                    "JOIN activity_log al ON th.id_act = al.id_act " +
                    "JOIN item_db i ON th.id_item_sold = i.id " +
                    "WHERE al.tipe_act = 'TR'";
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                transactionLog transaction = new transactionLog();

                transaction.setIdTransaction(rs.getInt("id_transaction"));
                transaction.setIdAct(rs.getInt("id_act"));
                transaction.setIdItemSold(rs.getInt("id_item_sold"));
                transaction.setCash(rs.getInt("cash"));
                transaction.setTotal(rs.getInt("total"));
                transaction.setItem(rs.getString("name"));
                transaction.setUser(rs.getString("username"));

                transaction.setUser(rs.getString("username"));
                String dateTimeString = rs.getString("date");
                SimpleStringProperty dateProperty = new SimpleStringProperty();
                SimpleStringProperty timeProperty = new SimpleStringProperty();
                separateDateTime(dateTimeString, dateProperty, timeProperty);
                String dateString = dateProperty.get();
                String timeString = timeProperty.get();

                transaction.setDate(dateString);
                transaction.setTime(timeString);

                transactions.add(transaction);
//
//                System.out.println("Transaction ID: " + transaction.getIdTransaction());
//                System.out.println("Activity ID: " + transaction.getIdAct());
//                System.out.println("Item Sold ID: " + transaction.getIdItemSold());
//                System.out.println("Cash: " + transaction.getCash());
//                System.out.println("Total: " + transaction.getTotal());
//                System.out.println("Item Name: " + transaction.getItem());
//                System.out.println("Username: " + transaction.getUser());
//                System.out.println("User ID: " + transaction.getIdUser());
//                System.out.println("Date: " + transaction.getDate());
//                System.out.println("Time: " + transaction.getTime());
            }

            return transactions;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void separateDateTime(String dateTimeString, SimpleStringProperty dateProperty, SimpleStringProperty timeProperty) {
        System.out.println("Separating date and time...");
        String[] parts = dateTimeString.split(" ");
        String dateString = parts[0];
        String timeString = parts[1];

        dateProperty.set(dateString);
        timeProperty.set(timeString);
    }


    public static List<transactionLog> getFilteredTransactionLogLogs(LocalDate selectedDate) throws SQLException {
        System.out.println("Fetching filtered transaction logs...");
        List<transactionLog> logs = new ArrayList<>();
        try (PreparedStatement statement = db_config.conn.prepareStatement(
        "SELECT th.id_transaction, th.id_act, th.id_item_sold, th.cash, th.total, i.name, al.username, al.date, al.tipe_act " +
                        "FROM transaction_history th " +
                        "JOIN activity_log al ON th.id_act = al.id_act " +
                        "JOIN item_db i ON th.id_item_sold = i.id " +
                "WHERE DATE(al.date) = ? AND al.tipe_act = ?"
        )) {
            statement.setString(1, String.valueOf(selectedDate));
            statement.setString(2, "TR"); // Filter by type_act "TR"

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                transactionLog transaction = new transactionLog();
                transaction.setIdTransaction(rs.getInt("id_transaction"));
                transaction.setIdAct(rs.getInt("id_act"));
                transaction.setIdItemSold(rs.getInt("id_item_sold"));
                transaction.setCash(rs.getInt("cash"));
                transaction.setTotal(rs.getInt("total"));
                transaction.setItem(rs.getString("name"));
                transaction.setUser(rs.getString("username"));


                String dateTimeString = rs.getString("date");

                SimpleStringProperty dateProperty = new SimpleStringProperty();
                SimpleStringProperty timeProperty = new SimpleStringProperty();
                separateDateTime(dateTimeString, dateProperty, timeProperty);

                transaction.setDate(dateProperty.get());
                transaction.setTime(timeProperty.get());
                transaction.setInfoTransaction(rs.getString("tipe_act"));
                logs.add(transaction);

//                System.out.println("Transaction ID: " + transaction.getIdTransaction());
//                System.out.println("Activity ID: " + transaction.getIdAct());
//                System.out.println("Username: " + transaction.getUser());
//                System.out.println("Date: " + transaction.getDate());
//                System.out.println("Time: " + transaction.getTime());
//                System.out.println("Transaction Type: " + transaction.getInfoTransaction());

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return logs;
    }

    }


