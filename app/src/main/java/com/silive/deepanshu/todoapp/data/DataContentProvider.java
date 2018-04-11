package com.silive.deepanshu.todoapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by deepanshu on 11/4/18.
 */

public class DataContentProvider extends ContentProvider {

    private DbHelper dbHelper;
    public static final int API_DATA = 100;
    public static final int API_DATA_ID = 101;
    public static final UriMatcher sUriMatcher =buildUriMatcher();
    public static UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(DbContract.AUTHORITY, DbContract.PATH_API_DATA, API_DATA);
        uriMatcher.addURI(DbContract.AUTHORITY, DbContract.PATH_API_DATA + "/#", API_DATA_ID);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {

        Context context = getContext();
        dbHelper = new DbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);


        Cursor retCursor;
        switch (match){
            case API_DATA:
                retCursor = db.query(DbContract.ApiData.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknow Uri: " + uri);

        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);

        Uri returnUri ;
        switch (match){
            case API_DATA:
                long id = db.insert(DbContract.ApiData.TABLE_NAME,null, values);
                if (id > 0){
                    returnUri = ContentUris.withAppendedId(DbContract.ApiData.CONTENT_URI, id);
                }
                else {
                    throw  new android.database.SQLException("Failed to add todo") ;
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknow Uri: " + uri);

        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int count = 0;
        Uri returnUri;
        switch (match){
            case API_DATA:
                String id = uri.getPathSegments().get(1);
                count = db.update(DbContract.ApiData.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknow Uri: " + uri);

        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
