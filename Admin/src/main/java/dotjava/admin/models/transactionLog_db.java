package dotjava.admin.models;

import dotjava.admin.transactionLog;
import dotjava.admin.config.db_config;
import javafx.beans.property.SimpleStringProperty;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class transactionLog_db {


    public static ArrayList<transactionLog> fetchTransactionHistory() {
        ArrayList<transactionLog> transactions = new ArrayList<>();
        try (Statement statement = db_config.conn.createStatement()) {
            System.out.println("Fetching transaction history...");
            String sqlQuery = "SELECT th.id_transaction, th.id_act, al.username, al.date, al.tipe_act " +
                    "FROM transaction_history th " +
                    "JOIN activity_log al ON th.id_act = al.id_act " +
                    "WHERE al.tipe_act = 'TR'";
            System.out.println("SQL Query: " + sqlQuery); // Print SQL query
            ResultSet rs = statement.executeQuery(sqlQuery);
            while (rs.next()) {
                transactionLog transaction = new transactionLog();
                transaction.setIdTransaction(rs.getInt("id_transaction"));
                transaction.setIdAct(rs.getInt("id_act"));
                transaction.setUser(rs.getString("username"));

                String dateTimeString = rs.getString("date");

                SimpleStringProperty dateProperty = new SimpleStringProperty();
                SimpleStringProperty timeProperty = new SimpleStringProperty();
                separateDateTime(dateTimeString, dateProperty, timeProperty);

                transaction.setDate(dateProperty.get());
                transaction.setTime(timeProperty.get());
                transaction.setInfoTransaction(rs.getString("tipe_act"));
                transactions.add(transaction);

                // Print transaction details for debugging
                System.out.println("Transaction ID: " + transaction.getIdTransaction());
                System.out.println("Activity ID: " + transaction.getIdAct());
                System.out.println("Username: " + transaction.getUser());
                System.out.println("Date: " + transaction.getDate());
                System.out.println("Time: " + transaction.getTime());
                System.out.println("Transaction Type: " + transaction.getInfoTransaction());
            }
        } catch (SQLException e) {
            // Menangani SQLException
            e.printStackTrace();
        }
        return transactions;
    }




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

//                transaction.setIdUser(rs.getInt("id_user")); // From activity_log
                transaction.setUser(rs.getString("username")); // From activity_log
                String dateTimeString = rs.getString("date");
                SimpleStringProperty dateProperty = new SimpleStringProperty();
                SimpleStringProperty timeProperty = new SimpleStringProperty();
                separateDateTime(dateTimeString, dateProperty, timeProperty);
                String dateString = dateProperty.get();
                String timeString = timeProperty.get();

                transaction.setDate(dateString);
                transaction.setTime(timeString);

                transactions.add(transaction);

                // Print hasil setiap entri
                System.out.println("Transaction ID: " + transaction.getIdTransaction());
                System.out.println("Activity ID: " + transaction.getIdAct());
                System.out.println("Item Sold ID: " + transaction.getIdItemSold());
                System.out.println("Cash: " + transaction.getCash());
                System.out.println("Total: " + transaction.getTotal());
                System.out.println("Item Name: " + transaction.getItem());
                System.out.println("Username: " + transaction.getUser());
                System.out.println("User ID: " + transaction.getIdUser());
                System.out.println("Date: " + transaction.getDate());
                System.out.println("Time: " + transaction.getTime());
            }

            return transactions;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void separateDateTime(String dateTimeString, SimpleStringProperty dateProperty, SimpleStringProperty timeProperty) {
        System.out.println("Separating date and time...");
        // Pisahkan tanggal dan waktu
        String[] parts = dateTimeString.split(" ");
        String dateString = parts[0];
        String timeString = parts[1];

        // Set properti tanggal dan waktu
        dateProperty.set(dateString);
        timeProperty.set(timeString);
    }


    public static void cleartransactionLog() {
        try (Statement statement = db_config.conn.createStatement()) {
            System.out.println("Clearing transaction log...");
            String clearQuery = "DELETE FROM activity_log";
            statement.executeUpdate(clearQuery);
            System.out.println("Activity log table cleared successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertTransactionLog(transactionLog transaction) {
        try (PreparedStatement insertStatement = db_config.conn.prepareStatement(
                "INSERT INTO transaction_history (id_act, id_item_sold, cash, total) VALUES (?, ?, ?, ?)")) {
            System.out.println("Inserting transaction log...");
            // Assuming transaction object has getters for these attributes
            insertStatement.setInt(1, transaction.getIdAct());
            insertStatement.setInt(2, transaction.getIdItemSold());
            insertStatement.setDouble(3, transaction.getCash());
            insertStatement.setDouble(4, transaction.getTotal());

            insertStatement.executeUpdate();
            System.out.println("Transaction log inserted successfully.");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
        )) { // Focus on date from activity_log
            statement.setString(1, String.valueOf(selectedDate));
            statement.setString(2, "TR"); // Filter by type_act "TR"

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                transactionLog transaction = new transactionLog();
                transaction.setIdTransaction(rs.getInt("id_transaction"));
                transaction.setIdAct(rs.getInt("id_act"));
                transaction.setUser(rs.getString("username"));

                String dateTimeString = rs.getString("date");

                SimpleStringProperty dateProperty = new SimpleStringProperty();
                SimpleStringProperty timeProperty = new SimpleStringProperty();
                separateDateTime(dateTimeString, dateProperty, timeProperty);

                transaction.setDate(dateProperty.get());
                transaction.setTime(timeProperty.get());
                transaction.setInfoTransaction(rs.getString("tipe_act"));
                logs.add(transaction);

                // Print transaction details for debugging
                System.out.println("Transaction ID: " + transaction.getIdTransaction());
                System.out.println("Activity ID: " + transaction.getIdAct());
                System.out.println("Username: " + transaction.getUser());
                System.out.println("Date: " + transaction.getDate());
                System.out.println("Time: " + transaction.getTime());
                System.out.println("Transaction Type: " + transaction.getInfoTransaction());

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return logs;
    }

    }


