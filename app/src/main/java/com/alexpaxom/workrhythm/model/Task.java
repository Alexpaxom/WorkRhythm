package com.alexpaxom.workrhythm.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.alexpaxom.workrhythm.R;
import com.alexpaxom.workrhythm.ui.main.PageFragment;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.GregorianCalendar;


@Entity(tableName = "Tasks")
public class Task {
    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "title", defaultValue = "Без заголовка")
    private String title;

    @ColumnInfo(name = "description", defaultValue = "Без заголовка")
    private String description;

    @ColumnInfo(name = "priority", defaultValue = "0")
    private Integer priority = 0;

    @ColumnInfo(name = "status_id", defaultValue = "1")
    private Integer status_id = 1;

    @ColumnInfo(name = "last_update", defaultValue = "0")
    private Long last_update;

    @ColumnInfo(name = "elapsed_time", defaultValue = "0")
    private Long elapsed_time;

    public final static Integer IN_QUEUE = 1;
    public final static Integer IN_WORK = 2;
    public final static Integer FINISHED = 3;

    public final static Integer ARCHIVED = 4;


    @Ignore
    public Task() {
        this(null, "Без заголовка", "", 50, 1, new GregorianCalendar().getTimeInMillis(), (long) 0);
    }


    public Task(Long id, String title, String description, Integer priority, Integer status_id, Long last_update, Long elapsed_time) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status_id = status_id;
        this.last_update = last_update;
        this.elapsed_time = elapsed_time;
    }


    //setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public void setStatus_id(Integer status_id) {
        this.status_id = status_id;
    }

    public void setLast_update(Long last_update) {
        this.last_update = last_update;
    }

    public void setElapsed_time(Long elapsed_time) {
        this.elapsed_time = elapsed_time;
    }


    //getters

    public Integer getNextStatus_id() {
        if(status_id < Task.FINISHED) {
            return status_id + 1;
        }
        return null;
    }

    public Integer getPrevStatus_id() {
        if(status_id > Task.IN_QUEUE) {
            return status_id - 1;
        }
        return null;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Integer getPriority() {
        return priority;
    }

    public Integer getStatus_id() {
        return status_id;
    }

    public Long getLast_update() {
        return last_update;
    }

    public Long getElapsed_time() {
        return elapsed_time;
    }
}
