package com.dotjava.cashierapp.models;

import com.dotjava.cashierapp.User;
import com.dotjava.cashierapp.config.db_config;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
}
