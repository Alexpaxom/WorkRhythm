package com.alexpaxom.workrhythm.app.fragments

import kotlin.jvm.JvmOverloads
import com.alexpaxom.workrhythm.R
import com.alexpaxom.workrhythm.app.viewmodel.TaskStorageViewModel
import com.alexpaxom.workrhythm.app.adapters.ListTasksAdapter
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexpaxom.workrhythm.data.model.Task

/**
 * A page fragment containing a simple view.
 */
class PageFragment @JvmOverloads constructor(
    private val layout_view: Int = R.layout.tab_task_list_in_queue,
    private val shown_status: Int = Task.IN_QUEUE
) : Fragment() {
    private var viewModel: TaskStorageViewModel? = null
    private var listTasksAdapter: ListTasksAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity()).get(
            TaskStorageViewModel::class.java
        )
        super.onCreate(savedInstanceState)
    }

    // Inflate the view for the fragment based on layout XML
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layout_view, container, false)
        val rwListTasks: RecyclerView
        rwListTasks = view.findViewById<View>(R.id.list_tasks) as RecyclerView
        listTasksAdapter = ListTasksAdapter(this, shown_status)
        val layoutManager = LinearLayoutManager(activity)
        rwListTasks.layoutManager = layoutManager
        rwListTasks.adapter = listTasksAdapter
        val liveDataTasksList = viewModel!!.getTasks(
            shown_status
        )
        liveDataTasksList!!.observe(
            viewLifecycleOwner,
            { taskList -> listTasksAdapter!!.reloadListData(taskList as List<Task>) })
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (listTasksAdapter != null) listTasksAdapter!!.onDestroyAdapter()
    }
}