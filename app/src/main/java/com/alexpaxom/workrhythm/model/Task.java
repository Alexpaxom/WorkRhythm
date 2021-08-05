package com.alexpaxom.workrhythm.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


import com.alexpaxom.workrhythm.App;
import com.alexpaxom.workrhythm.R;

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
    private Integer statusId = 1;

    @ColumnInfo(name = "last_update_status", defaultValue = "0")
    private Long lastUpdateStatus;

    @ColumnInfo(name = "elapsed_time", defaultValue = "0")
    private Long elapsedTime;

    public final static Integer IN_QUEUE = 1;
    public final static Integer IN_WORK = 2;
    public final static Integer FINISHED = 3;

    public final static Integer ARCHIVED = 4;


    @Ignore
    public Task() {
            this(null, "", "", 50, 1, GregorianCalendar.getInstance().getTimeInMillis(), (long) 0);
    }


    public Task(Long id, String title, String description, Integer priority, Integer statusId, Long lastUpdateStatus, Long elapsedTime) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.statusId = statusId;
        this.lastUpdateStatus = lastUpdateStatus;
        this.elapsedTime = elapsedTime;
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

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public void setLastUpdateStatus(Long lastUpdateStatus) {
        this.lastUpdateStatus = lastUpdateStatus;
    }

    public void setElapsedTime(Long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }


    //getters

    public Integer getNextStatusId() {
        if(statusId < Task.FINISHED) {
            return statusId + 1;
        }
        return null;
    }

    public Integer getPrevStatusId() {
        if(statusId > Task.IN_QUEUE) {
            return statusId - 1;
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

    public Integer getStatusId() {
        return statusId;
    }

    public Long getLastUpdateStatus() {
        return lastUpdateStatus;
    }

    public Long getElapsedTime() {
        return elapsedTime;
    }
}
