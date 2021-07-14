package com.alexpaxom.workrhythm.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexpaxom.workrhythm.R;
import com.alexpaxom.workrhythm.model.Task;
import com.alexpaxom.workrhythm.model.TaskDao;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;


public class ListTasksAdapter extends RecyclerView.Adapter<ListTasksAdapter.ListTasksHolder> {

    List<Task> data = Collections.emptyList();
    ListTasksAdapter() {
    }

    @NonNull
    @NotNull
    @Override
    public ListTasksAdapter.ListTasksHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_task, parent, false);

        return new ListTasksHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ListTasksHolder holder, int position) {
        holder.bind(position);
    }

    public void reloadListData(List<Task> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ListTasksHolder extends RecyclerView.ViewHolder {

        TextView taskName;
        TextView task_db_id;
        TextView task_db_status;

        public ListTasksHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            taskName = itemView.findViewById(R.id.item_title);
            task_db_id = itemView.findViewById(R.id.id_task_in_db);
        }

        void bind(int listIndex) {
            taskName.setText(data.get(listIndex).getTitle());
            task_db_id.setText(data.get(listIndex).getId().toString());
        }
    }
}
