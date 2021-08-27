package com.alexpaxom.workrhythm.app

import android.app.Application
import com.alexpaxom.workrhythm.data.AppSQLiteDatabase
import androidx.appcompat.app.AppCompatDelegate
import androidx.room.Room

class App: Application() {

    var instance: App? = null
    var database: AppSQLiteDatabase? = null
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
            //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        /*Intent service = new Intent(this, ForegroundHolderService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.startForegroundService(service);
        }

        AlarmManager am = (AlarmManager)this.getSystemService(this.ALARM_SERVICE);
        Intent intent = new Intent(this, ReminderNotificationReceiver.class);
        intent.setAction(REMINDER_NOTIFICATION_ID);
        if( PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_NO_CREATE) == null ) {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            am.setRepeating(AlarmManager.RTC_WAKEUP, GregorianCalendar.getInstance().getTimeInMillis(), 1000 * 60 * 15, pendingIntent);
        }
        else {
            Log.i("INFO", "Reminder is set");
        }*/
        database = Room.databaseBuilder(
            applicationContext,
            AppSQLiteDatabase::class.java, "DB_App_Work_Rhythm"
        ) //.allowMainThreadQueries()
            .build()
    }
}