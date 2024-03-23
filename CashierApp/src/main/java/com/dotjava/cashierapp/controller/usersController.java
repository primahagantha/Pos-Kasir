package com.dotjava.cashierapp.controller;

import com.dotjava.cashierapp.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import com.dotjava.cashierapp.service.password_service;
import com.dotjava.cashierapp.models.user_db;

//import com.dotjava.cashierapp.controller.sceneController;
import com.dotjava.cashierapp.Main;
import javafx.scene.layout.AnchorPane;

import java.awt.event.ActionEvent;
import java.io.IOException;

import static com.dotjava.cashierapp.Main.setRoot;
import static com.dotjava.cashierapp.controller.sceneController.switchToMainApp;

public class usersController {

    @FXML TextField usernameData;
    @FXML TextField passwordData;
    @FXML TextField nameData;
    @FXML TextField confirmPasswordData;
    @FXML
    AnchorPane rootPane;


    public void loginUser(javafx.event.ActionEvent actionEven) {
        String username = usernameData.getText();
        String candidatePassword = passwordData.getText();

        try{
            User UserData = user_db.getUserByUsername(username);

            if(UserData == null){
                throw new Exception("Username / Password Incorrect !");
            }

            if(password_service.validatePassword(candidatePassword, UserData.getPassword())){
                switchToMainApp(actionEven);
                System.out.println("Login Success");
            }else{
                throw new Exception("Username / Password Incorrect !");
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }



    }

    public void registerUser(){
        String username = usernameData.getText();
        String password = passwordData.getText();
        String confirmPassword = confirmPasswordData.getText();
        String fullName = nameData.getText();

        try{
            if(!password.equals(confirmPassword)){
                throw new Exception("Password doesn't match");
            }

            User candidateUserData = user_db.getUserByUsername(username);

            if(candidateUserData != null){
                throw new Exception("Username already taken");
            }

            String hashedPassword = password_service.hashPassword(password);

            if(user_db.Register(fullName, username, hashedPassword) == 1){
                System.out.println("Registered Successfully");
            }else {
                System.out.println("Register Failed");
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }


    }
}
