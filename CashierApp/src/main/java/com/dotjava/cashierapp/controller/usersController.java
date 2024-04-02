package com.dotjava.cashierapp.controller;

import com.dotjava.cashierapp.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.dotjava.cashierapp.service.password_service;
import com.dotjava.cashierapp.models.user_db;
import javafx.scene.paint.Color;

import java.io.IOException;
import com.dotjava.cashierapp.service.userSession_service;

public class usersController {

    @FXML TextField usernameData;
    @FXML TextField passwordData;
    @FXML TextField nameData;
    @FXML TextField confirmPasswordData;

    @FXML Label errorMessage;

    public void loginUser(javafx.event.ActionEvent actionEven) {
//        mengambil username dan password dari text field
        String username = usernameData.getText();
        String candidatePassword = passwordData.getText();

        try{
            User UserData = user_db.getUserByUsername(username);

            if(UserData == null){
                throw new Exception("Username / Password Incorrect !");
            }

            if(password_service.validatePassword(candidatePassword, UserData.getPassword())){
//                set User Session
                userSession_service.setUserId(UserData.getIdUser());
                userSession_service.setUserFullName(UserData.getName());
                userSession_service.setUserName(UserData.getUsername());

//                Set User Log Activity
                user_db.setUserActivity("LI");

//                pindah ke scene main
                sceneController.switchToMainApp(actionEven);
                System.out.println("Login Success");
            }else{
                throw new Exception("Username / Password Incorrect !");
            }

        }catch (Exception e){
            errorMessage.setText(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    public void registerUser(){
//        mengambil data dari text field
        String username = usernameData.getText();
        String password = passwordData.getText();
        String confirmPassword = confirmPasswordData.getText();
        String fullName = nameData.getText();

        try{
//            cek apakah password sesuai dengan confirmPassword jika tidak throw error
            if(!password.equals(confirmPassword)){
                throw new Exception("Password doesn't match");
            }

//            cek apakah username ada pada database
            User candidateUserData = user_db.getUserByUsername(username);

//              Jika ada maka tidak dapat melanjutkan proses registrasi
            if(candidateUserData != null){
                throw new Exception("Username already taken");
            }

//            hash password
            String hashedPassword = password_service.hashPassword(password);

//            query ke db table users
            if(user_db.Register(fullName, username, hashedPassword) == 1){
                errorMessage.setTextFill(Color.GREEN);
                errorMessage.setText("Registered Successfully");
                System.out.println("Registered Successfully");
            }else {
                System.out.println("Register Failed");
            }

        }catch (Exception e){
            errorMessage.setText(e.getMessage());
            System.out.println(e.getMessage());
        }


    }

//    pindah ke scene register
    public void gotoRegister(javafx.event.ActionEvent actionEvent) throws IOException {
        sceneController.switchToRegister(actionEvent);
    }

//    pindah ke scene login
    public void gotoLogin(javafx.event.ActionEvent actionEvent) throws IOException {
        sceneController.switchToLogin(actionEvent);
    }
}
