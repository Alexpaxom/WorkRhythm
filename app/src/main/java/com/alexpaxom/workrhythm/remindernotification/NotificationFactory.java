package com.alexpaxom.workrhythm.remindernotification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.alexpaxom.workrhythm.R;
import com.alexpaxom.workrhythm.ui.main.MainActivity;

public class NotificationFactory {

    public static final int FOREGROUND_NOTIFICATION_ID = 100;
    public static final String FOREGROUND_CHANNEL_ID = "com.alexpaxom.foreground.channel.ID";

    public static final int REMIND_NOTIFICATION_ID = 101;
    public static final String REMINDER_NOTIFICATION_CHANNEL_ID = "com.alexpaxom.reminder.notification.channel.ID";

    public NotificationManager notificationManager;

    private Context context;


    public NotificationFactory(Context context) {
        this.context = context;
        notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
    }

    public Notification getForegroundNotification() {


        Intent nt = new Intent();
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, nt, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, FOREGROUND_CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Work rhythm")
                        .setContentText("")
                        .setContentIntent(pendingIntent)
                        .setSound(null);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel(
                    FOREGROUND_CHANNEL_ID,
                    "Foreground notification channel",
                    NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(channel);
        }

        Notification notification = builder.build();
        notification.defaults = 0;

        return notification;
    }

    public Notification getReminderNotification() {
        Intent nt = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, nt, PendingIntent.FLAG_UPDATE_CURRENT);

        long[] vibrate_pattern = {0, 0, 500, 500, 500};

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, REMINDER_NOTIFICATION_CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Work rhythm. Check yourself.")
                        .setContentText("What do you do? :)")
                        .setContentIntent(pendingIntent)
                        .setSound(null)
                        .setVibrate(vibrate_pattern)
                        .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel(
                    REMINDER_NOTIFICATION_CHANNEL_ID,
                    "Reminder notification channel",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.enableVibration(true);
            notificationManager.createNotificationChannel(channel);
        }

        Notification notification = builder.build();
        notification.defaults = 0;

        return notification;
    }


}
