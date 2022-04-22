package com.alexpaxom.workrhythm.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.alexpaxom.workrhythm.data.model.Task
import com.alexpaxom.workrhythm.data.model.TaskDao
import com.alexpaxom.workrhythm.data.model.TaskStatus
import java.util.concurrent.Executors

@Database(entities = [Task::class, TaskStatus::class], version = 2, exportSchema = true)
abstract class AppSQLiteDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    private fun buildDatabase(context: Context): AppSQLiteDatabase {
        return Room.databaseBuilder(context, AppSQLiteDatabase::class.java, DATABASE_NAME)
            .addMigrations(MIGRATION_1_2)
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)

                    //pre-populate data
                    Executors.newSingleThreadExecutor().execute {
                        /*instance?.let {
                            it.userDao().insertUsers(DataGenerator.getUsers())
                        }*/
                    }
                }
            })
            .build()
    }

    companion object {
        const val DATABASE_NAME = "DB_App_Work_Rhythm"
    }
}

val MIGRATION_1_2 = object: Migration(1,2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // add new table `Task_Statuses`
        database.execSQL("""CREATE TABLE  IF NOT EXISTS `Task_Statuses`(
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `name` TEXT NOT NULL
                )""".trimIndent())

        // insert default values on table `Task_Statuses`
        defaultValuesForStatusTable().forEach {
            database.insert("Task_Statuses",  SQLiteDatabase.CONFLICT_FAIL, it)
        }

        // add new table `Task_Types`
        database.execSQL("""CREATE TABLE  IF NOT EXISTS `Task_Types`(
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `name` TEXT NOT NULL
                )""".trimIndent())

        // insert default values on table `Task_Types`
        defaultValuesForTypesTable().forEach {
            database.insert("Task_Types",  SQLiteDatabase.CONFLICT_FAIL, it)
        }

        // Recreate table for add new foreign keys - sqlite specific
        // add two fields `estimate` and `type_id`
        // add foreign keys on `status_id` and `type_id`
        database.execSQL("""CREATE TABLE  IF NOT EXISTS `Tasks_tmp`(
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `type_id` INTEGER NOT NULL DEFAULT 2,
                `title` TEXT NOT NULL DEFAULT 'Без заголовка',
                `description` TEXT NOT NULL DEFAULT 'Без заголовка',
                `priority` INTEGER NOT NULL DEFAULT 0,
                `status_id` INTEGER NOT NULL DEFAULT 1,
                `last_update_status` INTEGER NOT NULL DEFAULT 0,
                `elapsed_time` INTEGER NOT NULL DEFAULT 0,
                `estimate` INTEGER NOT NULL DEFAULT 0,
                FOREIGN KEY(`status_id`) REFERENCES `Task_Statuses`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE,
                FOREIGN KEY(`type_id`) REFERENCES `Task_Types`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE
                )""".trimIndent())

        database.execSQL("""INSERT INTO `Tasks_tmp`(
                `id`, `title`, `description`, `priority`, `status_id`, `status_id`, `last_update_status`, `elapsed_time`)
                SELECT
                `id`, `title`, `description`, `priority`, `status_id`, `status_id`, `last_update_status`, `elapsed_time`
                FROM `Tasks`""".trimIndent())


        // add new table `Tasks_Hierarchy`
        database.execSQL("""CREATE TABLE  IF NOT EXISTS `Tasks_Hierarchy`(
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `task_id` INTEGER NOT NULL,
                `parent_task_id` INTEGER,
                `task_path` TEXT NOT NULL
                )""".trimIndent())

        database.execSQL("""INSERT INTO `Tasks_Hierarchy`(
                task_id, task_path)
                SELECT
                `id`, '-' || id || '-' as task_path
                FROM `Tasks`""".trimIndent())

        //drop old table and rename new
        database.execSQL("DROP TABLE `Tasks`")
        database.execSQL("ALTER TABLE `Tasks_tmp` RENAME TO `Tasks`")
    }

    private fun defaultValuesForStatusTable(): List<ContentValues> {
        val defaultValues = ArrayList<ContentValues>()

        val value1 = ContentValues()
        value1.put("id", 1)
        value1.put("name", "IN_QUEUE")


        val value2 = ContentValues()
        value2.put("id", 2)
        value2.put("name", "IN_WORK")


        val value3 = ContentValues()
        value3.put("id", 3)
        value3.put("name", "FINISHED")

        defaultValues.add(value1)
        defaultValues.add(value2)
        defaultValues.add(value3)

        return defaultValues
    }

    private  fun defaultValuesForTypesTable(): List<ContentValues> {
        val defaultValues = ArrayList<ContentValues>()

        val value1 = ContentValues()
        value1.put("id", 1)
        value1.put("name", "Note")


        val value2 = ContentValues()
        value2.put("name", "Task")
        value2.put("id", 2)

        val value3 = ContentValues()
        value3.put("name", "Time Limited Task")
        value3.put("id", 3)

        defaultValues.add(value1)
        defaultValues.add(value2)
        defaultValues.add(value3)

        return defaultValues
    }
}