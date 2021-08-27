package com.alexpaxom.workrhythm.app.activites

import androidx.appcompat.app.AppCompatActivity
import com.alexpaxom.workrhythm.app.viewmodel.TaskStorageViewModel
import com.google.android.material.textfield.TextInputEditText
import android.widget.TextView
import android.content.Intent
import android.widget.ImageButton
import android.widget.SeekBar
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.alexpaxom.workrhythm.R
import androidx.lifecycle.ViewModelProvider
import com.alexpaxom.workrhythm.helpers.TimeFormatter
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.core.util.Consumer
import com.alexpaxom.workrhythm.data.model.Task
import java.util.*

class FillTaskDataFormActivity : AppCompatActivity() {
    private var viewModel: TaskStorageViewModel? = null
    private var title: TextInputEditText? = null
    private var description: TextInputEditText? = null
    private var priority: TextView? = null
    private var fillingTask: Task? = null
    private var deleteTaskButton: ImageButton? = null
    private var seekBarPriority: SeekBar? = null
    private var formatElapsedTime: TextInputEditText? = null
    private var increaseElapsedTimeButton: Button? = null
    private var decreaseElapsedTimeButton: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_task_data)
        viewModel = ViewModelProvider(this).get(TaskStorageViewModel::class.java)
        fieldsFromForm
        setListeners()
        if (!intent.hasExtra("task_id")) {
            deleteTaskButton!!.visibility = View.INVISIBLE
            setFillTask(Task())
        } else {
            val taskConsumer = Consumer { task: Task? -> setFillTask(task) }
            viewModel!!.getTaskByIDAsync(intent.getLongExtra("task_id", 0), taskConsumer)
        }
    }

    // fields and buttons for "elapsed time"
    protected val fieldsFromForm: Unit
        protected get() {
            title = findViewById<View>(R.id.edit_text_task_title) as TextInputEditText
            description = findViewById<View>(R.id.edit_text_task_description) as TextInputEditText
            priority = findViewById<View>(R.id.text_view_priority) as TextView
            deleteTaskButton = findViewById<View>(R.id.delete_task) as ImageButton
            seekBarPriority = findViewById<View>(R.id.seek_bar_task_priority) as SeekBar

            // fields and buttons for "elapsed time"
            formatElapsedTime =
                findViewById<View>(R.id.edit_text_elapsed_hours) as TextInputEditText
            increaseElapsedTimeButton =
                findViewById<View>(R.id.button_increase_elapsed_time) as Button
            decreaseElapsedTimeButton =
                findViewById<View>(R.id.button_decrease_elapsed_time) as Button
        }

    protected fun setListeners() {
        seekBarPriority!!.setOnSeekBarChangeListener(PrioritySetSeekBar())
    }

    fun setFillTask(task: Task?) {
        fillingTask = task
        runOnUiThread { fillFormTask() }
    }

    protected fun fillFormTask() {
        title!!.setText(fillingTask!!.title)
        description!!.setText(fillingTask!!.description)
        priority!!.text = fillingTask!!.priority.toString()
        seekBarPriority!!.progress = fillingTask!!.priority
        val curTime = GregorianCalendar().timeInMillis
        formatElapsedTime!!.setText(TimeFormatter.intervalFromTime(fillingTask!!.elapsedTime))
        if (fillingTask!!.statusId == Task.IN_WORK) {
            formatElapsedTime!!.setText(TimeFormatter.intervalFromTime(fillingTask!!.elapsedTime + curTime - fillingTask!!.lastUpdateStatus))
            fillingTask!!.elapsedTime =
                fillingTask!!.elapsedTime + curTime - fillingTask!!.lastUpdateStatus
        }
        fillingTask!!.lastUpdateStatus = curTime
    }

    fun onClickSaveTask(view: View?) {
        fillingTask!!.title = title!!.text.toString()
        fillingTask!!.description = description!!.text.toString()
        fillingTask!!.priority = priority!!.text.toString().toInt()
        viewModel!!.SaveTask(fillingTask!!)
        finish()
    }

    fun onClickBackFromSave(view: View?) {
        finish()
    }

    fun onClickDeleteTask(view: View?) {
        viewModel!!.DeleteTask(fillingTask!!)
        finish()
    }

    private inner class PrioritySetSeekBar : OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            priority!!.text = Integer.toString(progress)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {}
        override fun onStopTrackingTouch(seekBar: SeekBar) {}
    }

    fun onClickDecreaseElapsedTime(view: View?) {
        var elapsed_time = fillingTask!!.elapsedTime
        if (elapsed_time < CHANGE_STEP_ELAPSED_TIME) elapsed_time =
            0.toLong() else elapsed_time -= CHANGE_STEP_ELAPSED_TIME
        fillingTask!!.elapsedTime = elapsed_time
        formatElapsedTime!!.setText(TimeFormatter.intervalFromTime(elapsed_time))
    }

    fun onClickIncreaseElapsedTime(view: View?) {
        val elapsed_time = fillingTask!!.elapsedTime + CHANGE_STEP_ELAPSED_TIME
        fillingTask!!.elapsedTime = elapsed_time
        formatElapsedTime!!.setText(TimeFormatter.intervalFromTime(elapsed_time))
    }

    companion object {
        private const val CHANGE_STEP_ELAPSED_TIME = 1000.toLong() * 60 * 5
    }
}