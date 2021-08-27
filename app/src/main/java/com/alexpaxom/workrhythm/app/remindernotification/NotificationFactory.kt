package com.alexpaxom.workrhythm.app.remindernotification

import android.app.Notification
import android.app.NotificationManager
import android.content.Intent
import android.app.PendingIntent
import androidx.core.app.NotificationCompat
import com.alexpaxom.workrhythm.app.remindernotification.NotificationFactory
import com.alexpaxom.workrhythm.R
import android.os.Build
import android.app.NotificationChannel
import android.content.Context
import com.alexpaxom.workrhythm.app.activites.MainActivity

class NotificationFactory(private val context: Context) {
    @JvmField
    var notificationManager: NotificationManager
    val foregroundNotification: Notification
        get() {
            val nt = Intent()
            val pendingIntent =
                PendingIntent.getActivity(context, 0, nt, PendingIntent.FLAG_UPDATE_CURRENT)
            val builder = NotificationCompat.Builder(context, FOREGROUND_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Work rhythm")
                .setContentText("")
                .setContentIntent(pendingIntent)
                .setSound(null)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    FOREGROUND_CHANNEL_ID,
                    "Foreground notification channel",
                    NotificationManager.IMPORTANCE_LOW
                )
                notificationManager.createNotificationChannel(channel)
            }
            val notification = builder.build()
            notification.defaults = 0
            return notification
        }
    val reminderNotification: Notification
        get() {
            val nt = Intent(context, MainActivity::class.java)
            val pendingIntent =
                PendingIntent.getActivity(context, 0, nt, PendingIntent.FLAG_UPDATE_CURRENT)
            val vibrate_pattern = longArrayOf(0, 0, 500, 500, 500)
            val builder = NotificationCompat.Builder(context, REMINDER_NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Work rhythm. Check yourself.")
                .setContentText("What do you do? :)")
                .setContentIntent(pendingIntent)
                .setSound(null)
                .setVibrate(vibrate_pattern)
                .setAutoCancel(true)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    REMINDER_NOTIFICATION_CHANNEL_ID,
                    "Reminder notification channel",
                    NotificationManager.IMPORTANCE_HIGH
                )
                channel.enableVibration(true)
                notificationManager.createNotificationChannel(channel)
            }
            val notification = builder.build()
            notification.defaults = 0
            return notification
        }

    companion object {
        const val FOREGROUND_NOTIFICATION_ID = 100
        const val FOREGROUND_CHANNEL_ID = "com.alexpaxom.foreground.channel.ID"
        const val REMIND_NOTIFICATION_ID = 101
        const val REMINDER_NOTIFICATION_CHANNEL_ID =
            "com.alexpaxom.reminder.notification.channel.ID"
    }

    init {
        notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
}