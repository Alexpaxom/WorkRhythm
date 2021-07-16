package com.alexpaxom.workrhythm.viewmodel;


import androidx.core.util.Consumer;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alexpaxom.workrhythm.App;
import com.alexpaxom.workrhythm.data.AppSQLiteDatabase;
import com.alexpaxom.workrhythm.model.Task;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TaskStorageViewModel extends ViewModel {
    public LiveData<List<Task>> tasksInQueue;
    public LiveData<List<Task>> tasksInWork;
    public LiveData<List<Task>> tasksFinished;
    private final AppSQLiteDatabase db;

    public TaskStorageViewModel() {
        db = App.getInstance().getDatabase();

        tasksInQueue = db.taskDao().getAllByStatus(Task.IN_QUEUE);
        tasksInWork = db.taskDao().getAllByStatus(Task.IN_WORK);
        tasksFinished = db.taskDao().getAllByStatus(Task.FINISHED);
    }


    public LiveData<List<Task>> getTasks(Integer status) {
        if (status == Task.IN_QUEUE)
                return tasksInQueue;
        if (status == Task.IN_WORK)
            return tasksInWork;
        if (status == Task.FINISHED)
            return tasksFinished;

        return null; // TODO return LiveData with empty list
    }

    public void setNextStatus(Long task_id) {
        Executor dbUpdateExecutor = Executors.newSingleThreadExecutor();
        dbUpdateExecutor.execute(() ->{
            Task task = db.taskDao().getById(task_id);
            setStatus(task, task.getNextStatus_id());
        });
    }

    public void setPrevStatus(Long task_id) {
        Executor dbUpdateExecutor = Executors.newSingleThreadExecutor();
        dbUpdateExecutor.execute(() ->{
            Task task = db.taskDao().getById(task_id);
            setStatus(task, task.getPrevStatus_id());
        });
    }

    public void SaveTask(Task task) {
        Executor dbUpdateExecutor = Executors.newSingleThreadExecutor();
        dbUpdateExecutor.execute(() ->{
            if(task.getId() instanceof Long)
                db.taskDao().update(task);
            else
                db.taskDao().insert(task);
        });
    }

    public void DeleteTask(Task task) {
        Executor dbUpdateExecutor = Executors.newSingleThreadExecutor();
        dbUpdateExecutor.execute(() ->{
            if(task.getId() instanceof Long)
                db.taskDao().delete(task);
        });
    }

    private void setStatus(Task task, Integer new_status) {
        Long curTime = new GregorianCalendar().getTimeInMillis();

        if(task.getStatus_id() == Task.IN_WORK) {
            task.setElapsed_time(task.getElapsed_time() + curTime - task.getLast_update_status());
        }

        task.setLast_update_status(curTime);
        if(new_status instanceof Integer)
            task.setStatus_id(new_status);

        SaveTask(task);
    }

    public void getTaskByIDAsync(Long task_id, Consumer<Task> consumer) {
        Executor dbUpdateExecutor = Executors.newSingleThreadExecutor();
        dbUpdateExecutor.execute(() -> {
            consumer.accept(db.taskDao().getById(task_id));
        });
    }

}
