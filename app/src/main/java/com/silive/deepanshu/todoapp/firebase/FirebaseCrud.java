package com.silive.deepanshu.todoapp.firebase;


import android.app.Activity;
import android.app.Service;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.silive.deepanshu.todoapp.models.TodoModel;
import com.silive.deepanshu.todoapp.models.UserModel;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class FirebaseCrud {
    private Activity activity;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference mUserReference;
    private DatabaseReference mUsersReference;
    private DatabaseReference mToDoListReference;
    private DatabaseReference mDataReference;

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
        mDataReference = mDatabaseReference.child("Data");
        mToDoListReference = mDataReference.child(user_id).child("data");

    }

    public FirebaseCrud(Service activity){
//        this.activity = activity;
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        SharedPreferences sharedPref = activity.getSharedPreferences("MyPref", 0);
        user_id = sharedPref.getString("user_id",null);
        user_name = sharedPref.getString("user_name", null);
        user_email = sharedPref.getString("user_email",null);
        user_imgUrl = sharedPref.getString("user_image", null);
        mUserReference = mDatabaseReference.child("User").child(user_id);
        mUsersReference = mDatabaseReference.child("User");
        mDataReference = mDatabaseReference.child("Data");
        mToDoListReference = mDataReference.child(user_id).child("data");

    }
    //Get Refrences


    public DatabaseReference getmToDoListReference() {
        return mToDoListReference;
    }

    public DatabaseReference getmDataReference() {
        return mDataReference;
    }

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

    public void addTodoListModel(TodoModel todoModel){
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", todoModel.getId());
        result.put("title", todoModel.getTitle());
        result.put("keyword", todoModel.getKeyword());
        result.put("created_at", todoModel.getCreated_at());
        result.put("notification", todoModel.getNotification());
        // Log.v("result",result.toString());
        mToDoListReference.child(todoModel.getId()+"").updateChildren(result);
    }

    public ArrayList<TodoModel> getTodoList(){
        final ArrayList<TodoModel> arrayList = new ArrayList<>();
        mToDoListReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    TodoModel wishListModel = postSnapshot.getValue(TodoModel.class);
                    arrayList.add(wishListModel);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        return arrayList;
    }
}

