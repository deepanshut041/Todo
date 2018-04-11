package com.silive.deepanshu.todoapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by deepanshu on 11/4/18.
 */

public class DbHelper  extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "movieDb.db";
    public static final int DATABASE_VERSION = 2;

    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_MOVIEDB = " CREATE TABLE " + DbContract.ApiData.TABLE_NAME +
                "("+ DbContract.ApiData._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DbContract.ApiData.COLUMN_ID + " TEXT NOT NULL, " +
                DbContract.ApiData.COLUMN_NAME + " TEXT NOT NULL, " +
                DbContract.ApiData.COLUMN_KEYWORD + " TEXT NOT NULL," +
                DbContract.ApiData.COLUMN_NOTIFICATION + " BOOLEAN NOT NULL, " +
                DbContract.ApiData.COLUMN_DATE +  " TEXT NOT NULL, " +
                " UNIQUE ("+ DbContract.ApiData.COLUMN_NAME +
                ") ON CONFLICT IGNORE"
                +")";
        db.execSQL(SQL_MOVIEDB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DbContract.ApiData.TABLE_NAME);
        onCreate(db);
    }
}
