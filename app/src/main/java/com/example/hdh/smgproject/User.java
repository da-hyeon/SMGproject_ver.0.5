package com.example.hdh.smgproject;

public class User {
    String userID , userPassword , userName, userEmail ,userGender, userHeight, userWeight, userAge ;
    int userPT;


    public User(String userID, String userPassword, String userName, String userGender , String userAge) {
        this.userID = userID;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userGender = userGender;
        this.userAge = userAge;
    }

    public User(String userID , String userPassword , String userName, String userEmail , String userGender, String userHeight,String userWeight,String userAge, int userPT){
        this.userID = userID;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userGender = userGender;
        this.userHeight = userHeight;
        this.userWeight = userWeight;
        this.userAge = userAge;
        this.userPT = userPT;
    }

    public User(String userID , String userName, String userEmail , String userGender, String userHeight,String userWeight, String userAge){
        this.userID = userID;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userGender = userGender;
        this.userHeight = userHeight;
        this.userWeight = userWeight;
        this.userAge = userAge;
    }



    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserHeight() {
        return userHeight;
    }

    public void setUserHeight(String userHeight) {
        this.userHeight = userHeight;
    }

    public String getUserWeight() {
        return userWeight;
    }

    public void setUserWeight(String userWeight) {
        this.userWeight = userWeight;
    }

    public int getUserPT() {
        return userPT;
    }

    public void setUserPT(int userPT) {
        this.userPT = userPT;
    }
}
