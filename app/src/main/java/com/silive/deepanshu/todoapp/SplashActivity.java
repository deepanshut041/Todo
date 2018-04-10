package com.silive.deepanshu.todoapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.silive.deepanshu.todoapp.firebase.FirebaseCrud;
import com.silive.deepanshu.todoapp.models.UserModel;

import java.util.Arrays;

public class SplashActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private static final int RC_SIGN_IN = 1;
    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                mFirebaseAuth = FirebaseAuth.getInstance();
                mAuthStateListener = new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            String username = firebaseUser.getDisplayName();
                            String uid = firebaseUser.getUid();
                            String email = firebaseUser.getEmail();
                            String photo_url = "http://www.africanleadershipawards.com/wp-content/uploads/2017/07/no-avatar-user.jpg";
                            Uri photo = firebaseUser.getPhotoUrl();
                            if (photo != null) {
                                photo_url = firebaseUser.getPhotoUrl().toString();
                            }
                            onSignedInInitialize(uid, username, email, photo_url);
                            addUser(uid, username, email, photo_url);
                            Intent i = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            startActivityForResult(
                                    AuthUI.getInstance()
                                            .createSignInIntentBuilder()
                                            .setIsSmartLockEnabled(false)
                                            .setAvailableProviders(
                                                    Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build(),

                                                            new AuthUI.IdpConfig.GoogleBuilder().build()))
                                            .setTheme(R.style.YellowTheme)
                                            .build(),
                                    RC_SIGN_IN);
                        }
                    }
                };
            }
        }, SPLASH_TIME_OUT);

    }

    @Override
    public void onPause() {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "User Signed in!!", Toast.LENGTH_SHORT).show();

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Sign in canceled!!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }


    public void onSignedInInitialize(String user_name, String user_id, String user_email, String user_image) {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("user_id", user_id);
        editor.putString("user_email", user_email);
        editor.putString("user_name", user_name);
        editor.putString("user_image", user_image);
        Log.e("user image", user_image);
        editor.apply();
    }

    public void addUser(final String uid, final String user_name, final String email, final String imgUrl) {
        final FirebaseCrud firebaseCurd = new FirebaseCrud(SplashActivity.this);
        DatabaseReference mUserReference = firebaseCurd.getmUsersReference().child(uid);
        mUserReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserModel userModel = dataSnapshot.getValue(UserModel.class);
                if (userModel == null) {
                    UserModel addUserModel = new UserModel(uid, user_name, email, imgUrl);
                    firebaseCurd.addUserModel(addUserModel);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Firebase", "Failed to read value.", error.toException());
            }
        });


    }
}
