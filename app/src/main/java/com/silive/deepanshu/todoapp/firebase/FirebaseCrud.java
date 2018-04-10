package com.silive.deepanshu.todoapp.firebase;


import android.app.Activity;
import android.content.SharedPreferences;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.silive.deepanshu.todoapp.models.UserModel;

import java.util.HashMap;

public class FirebaseCrud {
    private Activity activity;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference mUserReference;
    private DatabaseReference mUsersReference;

    private String user_id,user_name,user_email,user_imgUrl;


    public FirebaseCrud(Activity activity){
        this.activity = activity;
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        SharedPreferences sharedPref = activity.getSharedPreferences("MyPref", 0);
        user_id = sharedPref.getString("user_id",null);
        user_name = sharedPref.getString("user_name", null);
        user_email = sharedPref.getString("user_email",null);
        user_imgUrl = sharedPref.getString("user_image", null);
        mUserReference = mDatabaseReference.child("User").child(user_id);
        mUsersReference = mDatabaseReference.child("User");

    }
    //Get Refrences

    public DatabaseReference getmDatabaseReference() {
        return mDatabaseReference;
    }

    public DatabaseReference getmUserReference() {
        return mUserReference;
    }

    public DatabaseReference getmUsersReference() {
        return mUsersReference;
    }

    public void addUserModel(UserModel userModel){
        HashMap<String, Object> result = new HashMap<>();
        result.put("userId",userModel.getUserId());
        result.put("userName", userModel.getUserName());
        result.put("userEmail", userModel.getUserEmail());
        result.put("userImgUrl", userModel.getUserImgUrl());
        mUsersReference.child(userModel.getUserId()).updateChildren(result);
    }
}

