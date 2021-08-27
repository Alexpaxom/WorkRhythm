package com.alexpaxom.workrhythm.data.model

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import java.util.*

@Entity(tableName = "Tasks")
class Task(//setters
    @field:PrimaryKey(autoGenerate = true) var id: Long?,
    @field:ColumnInfo(
        name = "title",
        defaultValue = "Без заголовка"
    ) var title: String,
    @field:ColumnInfo(
        name = "description",
        defaultValue = "Без заголовка"
    ) var description: String,
    priority: Int,
    statusId: Int,
    lastUpdateStatus: Long,
    elapsedTime: Long
) {

    @ColumnInfo(name = "priority", defaultValue = "0")
    var priority = 0

    @ColumnInfo(name = "status_id", defaultValue = "1")
    var statusId = 1

    @ColumnInfo(name = "last_update_status", defaultValue = "0")
    var lastUpdateStatus: Long

    @ColumnInfo(name = "elapsed_time", defaultValue = "0")
    var elapsedTime: Long

    @Ignore
    constructor() : this(
        null,
        "",
        "",
        50, 1,
        GregorianCalendar.getInstance().timeInMillis, 0.toLong()
    ) {
    }

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
        const val IN_QUEUE = 1
        const val IN_WORK = 2
        const val FINISHED = 3
        const val ARCHIVED = 4
    }

    init {
        this.priority = priority
        this.statusId = statusId
        this.lastUpdateStatus = lastUpdateStatus
        this.elapsedTime = elapsedTime
    }
}