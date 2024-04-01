package dotjava.admin.models;

import dotjava.admin.activityLog;
import dotjava.admin.config.db_config;
import javafx.beans.property.SimpleStringProperty;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class activityLog_db {
    static ArrayList<activityLog> tempActLog;

    public static ArrayList<activityLog> fetchAllActivityLog() {
        tempActLog = new ArrayList<>();
        try (Statement statement = db_config.conn.createStatement()) {
            String query = "SELECT * FROM activity_log";
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                activityLog actLog = new activityLog();

                int idAct = result.getInt("id_act");
                int idUser = result.getInt("id_user");
                String dateTimeString = result.getString("date");
                String username = result.getString("username");
                String typeAct = result.getString("tipe_act");

                SimpleStringProperty dateProperty = new SimpleStringProperty();
                SimpleStringProperty timeProperty = new SimpleStringProperty();
                separateDateTime(dateTimeString, dateProperty, timeProperty);

                String dateString = dateProperty.get();
                String timeString = timeProperty.get();

                actLog.setDate(dateString);

                actLog.setIdAct(idAct);
                actLog.setIdUser(idUser);
                actLog.setTime(timeString);
                actLog.setUsername(username);
                actLog.setTypeAct(typeAct);
                actLog.setDesc(convertTypeActToDesc(typeAct));
                tempActLog.add(actLog);
            }

            return tempActLog;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void separateDateTime(String dateTimeString, SimpleStringProperty dateProperty, SimpleStringProperty timeProperty) {
        // Pisahkan tanggal dan waktu
        String[] parts = dateTimeString.split(" ");
        String dateString = parts[0];
        String timeString = parts[1];

        // Set properti tanggal dan waktu
        dateProperty.set(dateString);
        timeProperty.set(timeString);
    }

    public static void clearActivityLog() {
        try (Statement statement = db_config.conn.createStatement()) {
            String clearQuery = "DELETE FROM activity_log";
            statement.executeUpdate(clearQuery);
            System.out.println("Activity log table cleared successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertActivityLog(activityLog actLog) {
        try (PreparedStatement insertStatement = db_config.conn.prepareStatement(
                "INSERT INTO activity_log (id_user, username, typeAct, date) VALUES (?, ?, ?, ?)")) {

            insertStatement.setInt(1, actLog.getIdUser());
            insertStatement.setString(2, actLog.getUsername());
            insertStatement.setString(3, actLog.getTypeAct());
            insertStatement.setString(4, actLog.getDate());
            insertStatement.executeUpdate();
            System.out.println("Activity log inserted successfully.");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String convertTypeActToDesc(String typeAct) {
        String description = "";
        switch (typeAct) {
            case "LI":
                description = "Login / Logout";
                break;
            case "TR":
                description = "Transaction Added";
                break;
            default:
                description = typeAct;
        }
        return description;
    }

    public List<activityLog> getFilteredActivityLogs(LocalDate selectedDate) throws SQLException {
        List<activityLog> logs = new ArrayList<>();
        try (PreparedStatement statement = db_config.conn.prepareStatement(
                "SELECT * FROM activity_log WHERE DATE(date) = ?")) {
            statement.setString(1, String.valueOf(selectedDate));

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                activityLog actLog = new activityLog();

                int idAct = rs.getInt("id_act");
                int idUser = rs.getInt("id_user");
                String dateTimeString = rs.getString("date");
                String username = rs.getString("username");
                String typeAct = rs.getString("tipe_act");

                SimpleStringProperty dateProperty = new SimpleStringProperty();
                SimpleStringProperty timeProperty = new SimpleStringProperty();
                separateDateTime(dateTimeString, dateProperty, timeProperty);

                String dateString = dateProperty.get();
                String timeString = timeProperty.get();

                actLog.setDate(dateString);

                actLog.setIdAct(idAct);
                actLog.setIdUser(idUser);
                actLog.setTime(timeString);
                actLog.setUsername(username);
                actLog.setTypeAct(typeAct);
                actLog.setDesc(convertTypeActToDesc(typeAct));
                logs.add(actLog);
            }

            rs.close();
            return logs;
        }
    }

    }


