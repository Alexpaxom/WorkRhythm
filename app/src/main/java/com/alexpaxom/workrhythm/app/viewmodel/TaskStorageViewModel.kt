package com.alexpaxom.workrhythm.app.viewmodel

import android.app.Application
import androidx.core.util.Consumer
import androidx.lifecycle.AndroidViewModel
import com.alexpaxom.workrhythm.app.App
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import com.alexpaxom.workrhythm.data.AppSQLiteDatabase
import com.alexpaxom.workrhythm.data.model.Task
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class TaskStorageViewModel(application: Application) : AndroidViewModel(application) {
    var tasksInQueue: LiveData<List<Task?>?>?
    var tasksInWork: LiveData<List<Task?>?>?
    var tasksFinished: LiveData<List<Task?>?>?
    private val db: AppSQLiteDatabase?
    fun getTasks(status: Int): LiveData<List<Task?>?>? {
        if (status === Task.IN_QUEUE) return tasksInQueue
        if (status === Task.IN_WORK) return tasksInWork
        return if (status === Task.FINISHED) tasksFinished else null
        // TODO return LiveData with empty list
    }

    fun setNextStatus(task_id: Long?) {
        val dbUpdateExecutor: Executor = Executors.newSingleThreadExecutor()
        dbUpdateExecutor.execute {
            val task = db!!.taskDao()!!.getById(task_id)
            setStatus(task!!, task.nextStatusId!!)
        }
    }

    fun setPrevStatus(task_id: Long?) {
        val dbUpdateExecutor: Executor = Executors.newSingleThreadExecutor()
        dbUpdateExecutor.execute {
            val task = db!!.taskDao()!!.getById(task_id)
            setStatus(task!!, task.prevStatusId!!)
        }
    }

    fun SaveTask(task: Task) {
        val dbUpdateExecutor: Executor = Executors.newSingleThreadExecutor()
        dbUpdateExecutor.execute {
            if (task.id is Long) db!!.taskDao()?.update(task) else db!!.taskDao()?.insert(task)
        }
    }

    fun DeleteTask(task: Task) {
        val dbUpdateExecutor: Executor = Executors.newSingleThreadExecutor()
        dbUpdateExecutor.execute { if (task.id is Long) db!!.taskDao()?.delete(task) }
    }

    private fun setStatus(task: Task, new_status: Int) {
        val curTime = GregorianCalendar().timeInMillis
        if (task.statusId === Task.IN_WORK) {
            task.elapsedTime = task.elapsedTime + curTime - task.lastUpdateStatus
        }
        task.lastUpdateStatus = curTime
        if (new_status is Int) task.statusId = new_status
        SaveTask(task)
    }

    fun getTaskByIDAsync(task_id: Long?, consumer: Consumer<Task?>) {
        val dbUpdateExecutor: Executor = Executors.newSingleThreadExecutor()
        dbUpdateExecutor.execute { consumer.accept(db!!.taskDao()?.getById(task_id)) }
    }

    init {
        db = (application as App).instance!!.database
        tasksInQueue = db!!.taskDao()?.getAllByStatus(Task.IN_QUEUE)
        tasksInWork = db.taskDao()?.getAllByStatus(Task.IN_WORK)
        tasksFinished = db.taskDao()?.getAllByStatus(Task.FINISHED)
    }
}