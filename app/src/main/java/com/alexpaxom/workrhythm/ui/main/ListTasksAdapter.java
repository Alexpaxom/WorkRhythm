package com.alexpaxom.workrhythm.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.alexpaxom.workrhythm.R;
import com.alexpaxom.workrhythm.model.Task;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;


public class ListTasksAdapter extends RecyclerView.Adapter<ListTasksHolder> {


    MutableLiveData<Long> currTime_live;
    Fragment parentFragment;
    List<Task> data = Collections.emptyList();
    Integer shown_status;
    Thread timer_thread;

    public ListTasksAdapter(Fragment parentFragment, Integer shown_status) {
        this.parentFragment = parentFragment;
        this.shown_status = shown_status;

        if(shown_status == Task.IN_WORK)
            updateTimeInTasks();
    }

    private void updateTimeInTasks() {
        currTime_live = new MutableLiveData<>();
        currTime_live.setValue(GregorianCalendar.getInstance().getTimeInMillis());


        timer_thread = new Thread(() -> {
            while(!Thread.interrupted()) {
                try {
                    Thread.sleep(1000);
                    currTime_live.postValue(GregorianCalendar.getInstance().getTimeInMillis());
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        timer_thread.start();
    }

    @NonNull
    @NotNull
    @Override
    public ListTasksHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_task, parent, false);


        return new ListTasksHolder(this, view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ListTasksHolder holder, int position) {
          holder.bind(position);
          if(shown_status == Task.IN_WORK)
            holder.updateTimeInTask();
    }

    public void reloadListData(List<Task> data) {
        this.data = data;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public void onDestroyAdapter() {
        if(timer_thread != null && !timer_thread.isInterrupted())
            timer_thread.interrupt();
    }

}
