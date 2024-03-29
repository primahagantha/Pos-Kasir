package com.dotjava.cashierapp.service;

public class userSession_service {
    private static String userId;
    private static String userFullName;
    private static String userName;


    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        userSession_service.userId = userId;
    }

    public static String getUserFullName() {
        return userFullName;
    }

    public static void setUserFullName(String userFullName) {
        userSession_service.userFullName = userFullName;
    }

    public static void cleanSession(){
        userSession_service.userId = null;
        userSession_service.userFullName = null;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        userSession_service.userName = userName;
    }

    public String activeSession(){
        return "UserSession{" +
                "userFullName='" + userFullName + '\'' +
                ", UserId=" + userId +
                '}';
    }
}
