package com.alexpaxom.workrhythm;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.room.Room;

import com.alexpaxom.workrhythm.data.AppSQLiteDatabase;
import com.alexpaxom.workrhythm.remindernotification.ForegroundHolderService;
import com.alexpaxom.workrhythm.remindernotification.ReminderNotificationReceiver;

import java.util.GregorianCalendar;

public class App extends Application {

    private AppSQLiteDatabase db;
    private static App instance;
    private final static String REMINDER_NOTIFICATION_ID = "android.intent.action.REMINDER_NOTIFY";

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        Intent service = new Intent(this, ForegroundHolderService.class);
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
        }

        db = Room.databaseBuilder(getApplicationContext(),
                AppSQLiteDatabase.class, "DB_App_Work_Rhythm")
                //.allowMainThreadQueries()
                .build();

    }


    public AppSQLiteDatabase getDatabase() {
        return db;
    }

    public static App getInstance() {
        return instance;
    }

}
