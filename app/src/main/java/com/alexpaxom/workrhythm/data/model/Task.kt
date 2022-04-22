package com.alexpaxom.workrhythm.data.model

import androidx.room.*
import java.util.*

@Entity(tableName = Task.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = TaskStatus::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("status_id"),
            onDelete = ForeignKey.CASCADE)
    ]
    )
class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo(name = "title", defaultValue = "Без заголовка")
    var title: String = "",

    @ColumnInfo(name = "description", defaultValue = "Без заголовка")
    var description: String = "",

    @ColumnInfo(name = "priority", defaultValue = "0")
    var priority: Int = 0,

    @ColumnInfo(name = "status_id", defaultValue = "1", index = true)
    var statusId: Int = 1,

    @ColumnInfo(name = "last_update_status", defaultValue = "0")
    var lastUpdateStatus: Long = 0,

    @ColumnInfo(name = "elapsed_time", defaultValue = "0")
    var elapsedTime: Long = 0,

    @ColumnInfo(name = "estimate", defaultValue = "0")
    var estimate: Long = 0,

    @ColumnInfo(name = "type_id", defaultValue = "2")
    var type: Long = 2
) {

    //getters
    val nextStatusId: Int?
        get() = if (statusId < FINISHED) {
            statusId + 1
        } else null
    val prevStatusId: Int?
        get() = if (statusId > IN_QUEUE) {
            statusId - 1
        } else null

    companion object {
        const val TABLE_NAME = "Tasks"
        const val IN_QUEUE = 1
        const val IN_WORK = 2
        const val FINISHED = 3
        const val ARCHIVED = 4
    }
}