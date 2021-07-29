package com.alexpaxom.workrhythm.remindernotification;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.alexpaxom.workrhythm.R;

public class ForegroundHolderService extends IntentService {

    private Context context;

    public ForegroundHolderService() {
        super("ForegroundHolderService");
        context = this;
    }


    @Override
    public int onStartCommand(@Nullable @org.jetbrains.annotations.Nullable Intent intent, int flags, int startId) {

        NotificationFactory notificationFactory = new NotificationFactory(context);
        startForeground(NotificationFactory.FOREGROUND_NOTIFICATION_ID, notificationFactory.getForegroundNotification());
        return START_STICKY;
    }

    @Override
    protected void onHandleIntent(Intent intent) {}
}