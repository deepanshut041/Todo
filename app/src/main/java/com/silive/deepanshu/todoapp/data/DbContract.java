package com.silive.deepanshu.todoapp.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by deepanshu on 11/4/18.
 */

public class DbContract {
    public static final String AUTHORITY = "com.silive.deepanshu.todoapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_API_DATA = "todo_data";

    public static final class ApiData implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_API_DATA).build();

        public static final String TABLE_NAME = "todo_data";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_KEYWORD = "keyword";
        public static final String COLUMN_NOTIFICATION = "notification";
        public static final String COLUMN_DATE = "date";
    }
}
