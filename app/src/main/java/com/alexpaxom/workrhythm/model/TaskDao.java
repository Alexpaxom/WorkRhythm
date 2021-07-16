package com.alexpaxom.workrhythm.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT *  FROM Tasks")
    List<Task> getAll();

    @Query("SELECT *  FROM Tasks WHERE status_id = :status_id")
    LiveData<List<Task>> getAllByStatus(Integer status_id);

    @Query("SELECT * FROM Tasks WHERE id = :taskId")
    Task getById(Long taskId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Long insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);
}
