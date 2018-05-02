package com.silive.deepanshu.todoapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.util.Log;

import com.silive.deepanshu.todoapp.data.DbContract;
import com.silive.deepanshu.todoapp.models.TodoModel;
import com.silive.deepanshu.todoapp.views.NotificationBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyService extends Service {
    private AlarmManager mAlarmManager;
    private Intent mNotificationReceiverIntent;
    private PendingIntent mNotificationReceiverPendingIntent;


    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        eventNotification();
                        Thread.sleep(((1000 * 60) * 60) * 2); // It checks api every 2 hours to see if there is any event coming up with in 24hr
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        Log.i("service","Off");
        super.onDestroy();
        if(mNotificationReceiverPendingIntent!=null)
        {mAlarmManager.cancel(mNotificationReceiverPendingIntent);}
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        return START_NOT_STICKY;
    }

    public void eventNotification(){
        String WHERE = null;
        String args[] = null;
        Cursor cursor = getContentResolver().query(DbContract.ApiData.CONTENT_URI,
                null,
                WHERE,
                args,
                DbContract.ApiData.COLUMN_DATE+" DESC");

        ArrayList<TodoModel> todoModels =  new ArrayList<>();
        while (cursor.moveToNext()){
            TodoModel listDataModel = new TodoModel(cursor.getInt(cursor.getColumnIndex(DbContract.ApiData._ID)),cursor.getString(cursor.getColumnIndex(DbContract.ApiData.COLUMN_NAME))
                    ,cursor.getString(cursor.getColumnIndex(DbContract.ApiData.COLUMN_KEYWORD)),
                    Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(DbContract.ApiData.COLUMN_NOTIFICATION))),
                    cursor.getString(cursor.getColumnIndex(DbContract.ApiData.COLUMN_DATE)));
            todoModels.add(listDataModel);
        }
        cursor.moveToFirst();
        cursor.close();
        for (int i = 0; i <todoModels.size(); i++){
            long currentTime = new Date().getTime();
            final Calendar datetime = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            try {
                datetime.setTime(sdf.parse(todoModels.get(i).getCreated_at()));
                long timeLeft = currentTime -  datetime.getTimeInMillis();
                if (Math.abs(timeLeft) < (((1000 * 60) * 60) * 24)){ // if time left is less than 24 hours, it's upcoming event
                    if (timeLeft <= 0){ // if time left is positive then the event passed already
                        // Show notifications for upcoming events
                        Log.e("Uppomming", "dffdfdfdfd");
                        NotificationBuilder notificationManager = new NotificationBuilder(getApplicationContext());
                        notificationManager.ShowNotification(todoModels.get(i).getId(), todoModels.get(i).getTitle(), todoModels.get(i).getCreated_at(),  todoModels.get(i).getKeyword());
                    }else{
                        Log.e("E NOTIFICATION", "EVENT PASSED ALREADY");
                        String WHERE2 = DbContract.ApiData._ID+" = ?";
                        String args2[] = new String[] {String.valueOf(todoModels.get(i).getId())};
                        int id = getContentResolver().delete(DbContract.ApiData.CONTENT_URI,
                                WHERE2,
                                args2);
                        Log.e("EVENT DELETED", id+"  " + todoModels.get(i).getId()+"");
                    }
                } else {
                    Log.e("E NOTIFICATION", "NO NOTIFICATIONS");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
