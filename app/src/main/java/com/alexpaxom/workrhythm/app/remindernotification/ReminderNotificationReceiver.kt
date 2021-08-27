package com.alexpaxom.workrhythm.app.remindernotification


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ReminderNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationFactory = NotificationFactory(context)
        notificationFactory.notificationManager.notify(
            NotificationFactory.REMIND_NOTIFICATION_ID,
            notificationFactory.reminderNotification
        )
    }
}