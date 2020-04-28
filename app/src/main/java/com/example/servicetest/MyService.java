package com.example.servicetest;

import android.app.DownloadManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.util.concurrent.BlockingDeque;

public class MyService extends Service {
    private DownloadBinder mBinder = new DownloadBinder();
    class DownloadBinder extends Binder{
        public void startDownload(){
            Log.d(TAG, "startDownload: start Download executed");
        }
        public int getProgress(){
            Log.d(TAG, "getProgress: executed");
            return 0;
        }
    }
    private static final String TAG = "MyService";

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: onCreate executed");
        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this,0,intent,0);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("This is  content title")
                .setContentText("This is content text")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .setContentIntent(pi)
                .build();
        startForeground(1,notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: executed");
        new Thread(new Runnable() {
            @Override
            public void run() {
            stopSelf();
            }
        });
        return super.onStartCommand(intent, flags, startId);

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: onDestory executed");
    }
}
