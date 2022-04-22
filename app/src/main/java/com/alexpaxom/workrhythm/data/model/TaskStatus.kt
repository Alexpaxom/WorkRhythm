package com.alexpaxom.workrhythm.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Task_Statuses")
class TaskStatus(
    // TODO replase Int to Long
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "name", defaultValue = "not named")
    var name: String = "not named"
) {
    companion object {
        const val TABLE_NAME = "Task_Statuses"
    }
}