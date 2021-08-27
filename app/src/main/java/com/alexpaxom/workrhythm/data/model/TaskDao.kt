package com.alexpaxom.workrhythm.data.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {
    @Query("SELECT *  FROM Tasks")
    fun all(): List<Task?>?

    @Query("SELECT *  FROM Tasks WHERE status_id = :statusId")
    fun getAllByStatus(statusId: Int?): LiveData<List<Task?>?>?

    @Query("SELECT * FROM Tasks WHERE id = :taskId")
    fun getById(taskId: Long?): Task?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(task: Task?): Long?

    @Update
    fun update(task: Task?)

    @Delete
    fun delete(task: Task?)
}