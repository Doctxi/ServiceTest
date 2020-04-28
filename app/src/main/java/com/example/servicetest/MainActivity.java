package com.example.servicetest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.IntentService;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.print.PrinterId;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ThemedSpinnerAdapter;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private MyService.DownloadBinder downloadBinder ;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (MyService.DownloadBinder)service;
            downloadBinder.startDownload();
            downloadBinder.getProgress();
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button stop = (Button)findViewById(R.id.stop_service);
        Button sbt = (Button)findViewById(R.id.start_service);
        Button bind=(Button)findViewById(R.id.bind);
        Button unbind=(Button)findViewById(R.id.unbind);
        final Button startservice = (Button)findViewById(R.id.start_intent_service);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stopIntent;
                stopIntent = new Intent(MainActivity.this,MyService.class);
                stopService(stopIntent);
            }
        });
        sbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent;
                startIntent = new Intent(MainActivity.this,MyService.class);
                startService(startIntent);
            }
        });
        bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bindIntent = new Intent(MainActivity.this,MyService.class);
                bindService(bindIntent,connection,BIND_AUTO_CREATE);
            }
        });
        unbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            unbindService(connection);
            }
        });
        startservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Thread id is"+ Thread.currentThread().getId());
                Intent intent = new Intent(MainActivity.this,MyIntentService.class);
                startService(intent);
            }
        });
    }

}
