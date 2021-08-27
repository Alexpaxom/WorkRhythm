package com.alexpaxom.workrhythm.app.adapters.holders

import android.view.View
import com.alexpaxom.workrhythm.app.adapters.ListTasksAdapter
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.alexpaxom.workrhythm.helpers.TimeFormatter
import com.alexpaxom.workrhythm.R

class ListTasksHolder(private val listTasksAdapter: ListTasksAdapter, itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    private val taskName: TextView
    private val task_db_id: TextView
    private val task_additional_info: TextView
    private var lastUpdateTaskStatus: Long? = null
    private var elapsedTime: Long? = null
    fun bind(listIndex: Int) {
        elapsedTime = listTasksAdapter.data[listIndex].elapsedTime
        lastUpdateTaskStatus = listTasksAdapter.data[listIndex].lastUpdateStatus
        taskName.text = listTasksAdapter.data[listIndex].title
        task_db_id.text = listTasksAdapter.data[listIndex].id.toString()
        task_additional_info.text = TimeFormatter.intervalFromTime(elapsedTime!!)
    }

    fun updateTimeInTask() {
        listTasksAdapter.currTime_live!!.observe(
            listTasksAdapter.parentFragment,
            { cur_time -> updateElapsedTime(cur_time) })
    }

    fun updateElapsedTime(cur_time: Long?) {
        // Get previous time when task was "in work" and add current time "in work" status
        val fullElapsedTime = elapsedTime!! + (cur_time!! - lastUpdateTaskStatus!!)
        task_additional_info.text = TimeFormatter.intervalFromTime(fullElapsedTime)
    }

    init {
        taskName = itemView.findViewById(R.id.item_title)
        task_db_id = itemView.findViewById(R.id.id_task_in_db)
        task_additional_info = itemView.findViewById(R.id.item_additional_info)
    }
}