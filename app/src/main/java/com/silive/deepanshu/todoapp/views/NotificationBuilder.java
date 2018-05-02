package com.silive.deepanshu.todoapp.views;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.RemoteViews;

import com.silive.deepanshu.todoapp.R;

/**
 * Created by deepanshu on 3/5/18.
 */

public class NotificationBuilder {

    int requestID = (int) System.currentTimeMillis();
    private Context context;
    private String eventName;
    private long time;
    private RemoteViews remoteViews;
    private static final String CHANNEL_ID = "com.silive.deepanshu.todoapp.channel";


    //constructor
    public NotificationBuilder(Context context) {
        this.context = context;
    }


    public void ShowNotification(int id, String event_name, String t, String keyword) {


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(event_name)
                .setContentText(keyword)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(id, mBuilder.build());
    }
}
