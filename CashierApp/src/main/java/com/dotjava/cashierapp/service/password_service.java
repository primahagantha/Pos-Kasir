package com.dotjava.cashierapp.service;

import org.mindrot.jbcrypt.BCrypt;

public class password_service {

    public static String hashPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    };

    public static Boolean validatePassword(String candidatePassword, String hashedPassword){
        return BCrypt.checkpw(candidatePassword, hashedPassword);
    }


}
