package com.silive.deepanshu.todoapp.models;

/**
 * Created by deepanshu on 11/4/18.
 */

public class UserModel {
    private String userId;
    private String userName;
    private String userEmail;
    private String userImgUrl;

    public UserModel(){}

    public UserModel(String userId,String userName,String userEmail,String userImgUrl){
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userImgUrl = userImgUrl;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserImgUrl() {
        return userImgUrl;
    }

    public String getUserName() {
        return userName;
    }
}
