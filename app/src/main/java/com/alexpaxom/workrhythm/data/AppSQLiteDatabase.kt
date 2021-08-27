package com.alexpaxom.workrhythm.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alexpaxom.workrhythm.data.model.Task
import com.alexpaxom.workrhythm.data.model.TaskDao

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class AppSQLiteDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao?
}