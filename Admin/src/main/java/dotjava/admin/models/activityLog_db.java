package dotjava.admin.models;

import dotjava.admin.activityLog;
import dotjava.admin.config.db_config;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class activityLog_db {

    public List<activityLog> getActivityLogs() throws SQLException {
        activityLog_db db = new activityLog_db();
        List<activityLog> logs = new ArrayList<>();

        // Check if connection is already established
        if (db_config.conn == null) {
            db_config.initDBConnection(); // Initialize connection if not established
        }

        try (Connection conn = db_config.conn; // Use the established connection
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM activity_log")) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                activityLog log = new activityLog();
                log.setIdAct(rs.getInt("id_act"));
                log.setTypeAct(rs.getString("tipe_act"));
                log.setIdUser(rs.getInt("id_user"));
                log.setUsername(rs.getString("username"));


                int idAct = rs.getInt("id_act");
                String typeAct = rs.getString("tipe_act");
                int userId = rs.getInt("id_user");
                String username = rs.getString("username");
                String date = rs.getString("date");

                // Print retrieved data
                System.out.printf("ID: %d, Type: %s, User ID: %d, Username: %s, Date: %s\n", idAct, typeAct, userId, username, date);
                // Extract date and time from the "date" attribute
                String[] dateAndTime = rs.getString("date").split(" ");
                log.setDate(dateAndTime[0]); // Set the date part

                // Extract time components (hour, minute, second)
                if (dateAndTime.length > 1) {
                    String[] timeComponents = dateAndTime[1].split(":");
                    log.setTime(timeComponents[0] + ":" + timeComponents[1] + ":" + timeComponents[2]);
                } else {
                    log.setTime(""); // Set empty time if unavailable
                }

                logs.add(log);
            }

        } catch (SQLException e) {
            // Handle database errors appropriately
            e.printStackTrace();
        }

        return logs;
    }

    public List<activityLog> getActivityLogs(LocalDate date) throws SQLException {
        List<activityLog> logs = new ArrayList<>();

        // Check if connection is already established
        if (db_config.conn == null) {
            db_config.initDBConnection(); // Initialize connection if not established
        }

        try (Connection conn = db_config.conn; // Use the established connection
             PreparedStatement stmt = conn.prepareStatement("SELECT id_act, tipe_act, id_user, username, date FROM activity_log WHERE date = ?")) {

            stmt.setObject(1, date); // Set the date parameter

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                activityLog log = new activityLog();
                log.setIdAct(rs.getInt("id_act"));
                log.setTypeAct(rs.getString("tipe_act"));
                log.setIdUser(rs.getInt("id_user"));
                log.setUsername(rs.getString("username"));

                // Extract date and time from the "date" attribute
                String[] dateAndTime = rs.getString("date").split(" ");
                log.setDate(dateAndTime[0]); // Set the date part

                // Extract time components (hour, minute, second)
                if (dateAndTime.length > 1) {
                    String[] timeComponents = dateAndTime[1].split(":");
                    log.setTime(timeComponents[0] + ":" + timeComponents[1] + ":" + timeComponents[2]);
                } else {
                    log.setTime(""); // Set empty time if unavailable
                }

                logs.add(log);
            }

        } catch (SQLException e) {
            // Handle database errors appropriately
            e.printStackTrace();
        }

        return logs;
    }
}
