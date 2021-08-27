package com.alexpaxom.workrhythm.app.remindernotification

import android.app.IntentService
import android.content.Context
import android.content.Intent
import com.alexpaxom.workrhythm.app.remindernotification.NotificationFactory

class ForegroundHolderService : IntentService("ForegroundHolderService") {
    private val context: Context
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notificationFactory = NotificationFactory(context)
        startForeground(
            NotificationFactory.FOREGROUND_NOTIFICATION_ID,
            notificationFactory.foregroundNotification
        )
        return START_STICKY
    }

    override fun onHandleIntent(intent: Intent?) {}

    init {
        context = this
    }
}