package com.alexpaxom.workrhythm;

import android.app.Application;
import android.util.Log;

import androidx.room.Room;

import com.alexpaxom.workrhythm.data.AppSQLiteDatabase;

public class App extends Application {

    private AppSQLiteDatabase db;
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

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
