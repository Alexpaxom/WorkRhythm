package com.alexpaxom.workrhythm.app.adapters

import androidx.recyclerview.widget.RecyclerView
import com.alexpaxom.workrhythm.app.adapters.holders.ListTasksHolder
import androidx.lifecycle.MutableLiveData
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.alexpaxom.workrhythm.R
import com.alexpaxom.workrhythm.data.model.Task
import java.util.*

class ListTasksAdapter(var parentFragment: Fragment, var shown_status: Int) :
    RecyclerView.Adapter<ListTasksHolder>() {
    @JvmField
    var currTime_live: MutableLiveData<Long>? = null
    @JvmField
    var data: List<Task> = emptyList()
    var timer_thread: Thread? = null
    private fun updateTimeInTasks() {
        currTime_live = MutableLiveData()
        currTime_live!!.value = GregorianCalendar.getInstance().timeInMillis
        timer_thread = Thread {
            while (!Thread.interrupted()) {
                try {
                    Thread.sleep(1000)
                    currTime_live!!.postValue(GregorianCalendar.getInstance().timeInMillis)
                } catch (ex: InterruptedException) {
                    Thread.currentThread().interrupt()
                }
            }
        }
        timer_thread!!.start()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListTasksHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_task, parent, false)
        return ListTasksHolder(this, view)
    }

    override fun onBindViewHolder(holder: ListTasksHolder, position: Int) {
        holder.bind(position)
        if (shown_status === Task.IN_WORK) holder.updateTimeInTask()
    }

    fun reloadListData(data: List<Task>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun onDestroyAdapter() {
        if (timer_thread != null && !timer_thread!!.isInterrupted) timer_thread!!.interrupt()
    }

    init {
        if (shown_status === Task.IN_WORK) updateTimeInTasks()
    }
}