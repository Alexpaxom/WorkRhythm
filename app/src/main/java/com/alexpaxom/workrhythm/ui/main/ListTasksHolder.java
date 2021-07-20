package com.alexpaxom.workrhythm.ui.main;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.alexpaxom.workrhythm.R;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

class ListTasksHolder extends RecyclerView.ViewHolder {

    private final ListTasksAdapter listTasksAdapter;
    private TextView taskName;
    private TextView task_db_id;
    private TextView task_additional_info;

    private Long lastUpdateTaskStatus;
    private Long elapsedTime;


    private static final int convertMillisecondsToDays = 86400000;


    public ListTasksHolder(ListTasksAdapter listTasksAdapter, @NonNull @NotNull View itemView) {
        super(itemView);
        this.listTasksAdapter = listTasksAdapter;

        taskName = itemView.findViewById(R.id.item_title);
        task_db_id = itemView.findViewById(R.id.id_task_in_db);
        task_additional_info = itemView.findViewById(R.id.item_additional_info);

    }

    void bind(int listIndex) {
        elapsedTime = listTasksAdapter.data.get(listIndex).getElapsedTime();
        lastUpdateTaskStatus = listTasksAdapter.data.get(listIndex).getLastUpdateStatus();

        taskName.setText(listTasksAdapter.data.get(listIndex).getTitle());
        task_db_id.setText(listTasksAdapter.data.get(listIndex).getId().toString());
        task_additional_info.setText(formatElapsedTime(elapsedTime));

    }

    public void updateTimeInTask() {
        this.listTasksAdapter.currTime_live.observe(this.listTasksAdapter.parentFragment, new Observer<Long>() {
            @Override
            public void onChanged(@Nullable Long cur_time) {
                updateElapsedTime(cur_time);
            }
        });
    }

    public void updateElapsedTime(Long cur_time) {
        // Get previous time when task was "in work" and add current time "in work" status
        Long fullElapsedTime = elapsedTime + (cur_time-lastUpdateTaskStatus);
        task_additional_info.setText(formatElapsedTime(fullElapsedTime));
    }

    private String formatElapsedTime(Long elapsedTime) {
        DateFormat df = new SimpleDateFormat(" HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        Integer elapsed_days = (int) (elapsedTime / convertMillisecondsToDays);
        return "Days: "+elapsed_days.toString() + " Hours:" +df.format(elapsedTime%convertMillisecondsToDays);
    }
}
