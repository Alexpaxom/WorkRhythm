package com.alexpaxom.workrhythm.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.alexpaxom.workrhythm.model.Task;
import com.alexpaxom.workrhythm.model.TaskDao;

@Database(entities = {Task.class}, version = 1, exportSchema = false)
public abstract class AppSQLiteDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
}
