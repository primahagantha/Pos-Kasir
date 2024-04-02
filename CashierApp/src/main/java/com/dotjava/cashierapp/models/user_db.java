package com.dotjava.cashierapp.models;

import com.dotjava.cashierapp.User;
import com.dotjava.cashierapp.config.db_config;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

import com.dotjava.cashierapp.service.userSession_service;

public class user_db {
    public static Integer Register(String name, String username, String password){

        try{
            String query = "INSERT INTO `users`(`id`, `name`, `username`, `password`) VALUES (?,?,?,?)";
            PreparedStatement statement = db_config.conn.prepareStatement(query);

            statement.setNull(1,java.sql.Types.NULL);
            statement.setString(2, name);
            statement.setString(3, username);
            statement.setString(4, password);

            int result = statement.executeUpdate();

            return result;

//          returning something when query success or not
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static User getUserByUsername(String username){
        try{
            String query = "SELECT * FROM `users` WHERE `username` = ?";
            PreparedStatement statement = db_config.conn.prepareStatement(query);

            statement.setString(1, username);

            ResultSet result = statement.executeQuery();

            if (result.next()){
                User userData = new User();

                String userId = result.getString("id");
                String userFullName = result.getString("name");
                String userHashedPassword = result.getString("password");

                userData.setIdUser(userId);
                userData.setUsername(username);
                userData.setName(userFullName);
                userData.setPassword(userHashedPassword);
                return userData;
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Integer setUserActivity(String actType){
        try{
            String query = "INSERT INTO `activity_log`(`id_act`, `tipe_act`, `id_user`, `username`) VALUES (?,?,?,?)";

            PreparedStatement statement = db_config.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statement.setNull(1,java.sql.Types.NULL);
            statement.setString(2, actType);
            statement.setString(3, userSession_service.getUserId());
            statement.setString(4, userSession_service.getUserName());


            int affectedRows = statement.executeUpdate();
            int insertedIdActivity = 0;

            if(affectedRows > 0){
                try (ResultSet rs = statement.getGeneratedKeys()) {
                    if (rs.next()) {
                        System.out.println("Inserted record's ID: " + rs.getInt(1));
                        insertedIdActivity =  rs.getInt(1);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return insertedIdActivity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
