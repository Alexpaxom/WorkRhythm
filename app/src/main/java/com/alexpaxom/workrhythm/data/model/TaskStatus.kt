package com.alexpaxom.workrhythm.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "Task_Statuses")
class TaskStatus(
    @ColumnInfo(name = "id", defaultValue = "1")
    var id: Long = 0,

    @ColumnInfo(name = "name", defaultValue = "not named")
    var name: String = "not named"
) {
    companion object {
        const val TABLE_NAME = "Task_Statuses"
    }
}