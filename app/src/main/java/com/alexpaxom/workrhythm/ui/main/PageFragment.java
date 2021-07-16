package com.alexpaxom.workrhythm.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexpaxom.workrhythm.R;
import com.alexpaxom.workrhythm.model.Task;
import com.alexpaxom.workrhythm.viewmodel.TaskStorageViewModel;

import java.util.List;


/**
 * A page fragment containing a simple view.
 */
public class PageFragment extends Fragment {

    private TaskStorageViewModel viewModel;
    private ListTasksAdapter listTasksAdapter;
    private int layout_view;
    private Integer shown_status;

    public PageFragment() {
        this(R.layout.tab_task_list_in_queue, Task.IN_QUEUE);
    }

    public PageFragment(int layout_view, int shown_status) {
        this.layout_view = layout_view;
        this.shown_status = shown_status;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(TaskStorageViewModel.class);
        super.onCreate(savedInstanceState);
    }


    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(layout_view, container, false);

        RecyclerView rwListTasks;

        rwListTasks = (RecyclerView) view.findViewById(R.id.list_tasks);
        listTasksAdapter = new ListTasksAdapter(this, shown_status);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rwListTasks.setLayoutManager(layoutManager);
        rwListTasks.setAdapter(listTasksAdapter);

        LiveData<List<Task>> liveDataTasksList = viewModel.getTasks(shown_status);

        liveDataTasksList.observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> taskList) {
                listTasksAdapter.reloadListData(taskList);
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(listTasksAdapter != null)
            listTasksAdapter.onDestroyAdapter();
    }
}