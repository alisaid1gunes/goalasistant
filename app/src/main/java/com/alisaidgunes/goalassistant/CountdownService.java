package com.alisaidgunes.goalassistant;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.ankushgrover.hourglass.Hourglass;

import java.util.Locale;

import static  com.alisaidgunes.goalassistant.App.CHANNEL_ID;
public class CountdownService extends Service {
    Hourglass hourglass5;
    Notification notification;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("inputExtra");

        Intent notificationIntent = new Intent(this,PromodoroFragment.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setContentText("1")
                .setOnlyAlertOnce(true)
                .build();

        final NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, notification);

        startForeground(1, notification);


        //do heavy work on a background thread
        //stopSelf();
        hourglass5 = new Hourglass(300000, 1000) {
            @Override
            public void onTimerTick(long timeRemaining) {
                notificationManager.notify(1, update(notification, String.format(Locale.getDefault(), "%02d:%02d", (int) (timeRemaining / 1000) / 60, (int) (timeRemaining / 1000) % 60),pendingIntent));


            }

            @Override
            public void onTimerFinish() {




            }
        };
        hourglass5.startTimer();
        return START_NOT_STICKY;
    }
    Notification update(Notification notification,String text,PendingIntent pendingIntent){
                notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setContentText(text)
                .setOnlyAlertOnce(true)

                .build();

        return  notification;
    }
}
