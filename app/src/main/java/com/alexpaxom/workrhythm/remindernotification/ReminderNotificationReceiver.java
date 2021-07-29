package com.alexpaxom.workrhythm.remindernotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class ReminderNotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationFactory notificationFactory = new NotificationFactory(context);
        notificationFactory.notificationManager.notify(NotificationFactory.REMIND_NOTIFICATION_ID, notificationFactory.getReminderNotification());
    }
}