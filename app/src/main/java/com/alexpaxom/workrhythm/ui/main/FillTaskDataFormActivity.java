package com.alexpaxom.workrhythm.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.alexpaxom.workrhythm.R;
import com.alexpaxom.workrhythm.model.Task;
import com.alexpaxom.workrhythm.viewmodel.TaskStorageViewModel;

import androidx.core.util.Consumer;

public class FillTaskDataFormActivity extends AppCompatActivity {
    private TaskStorageViewModel viewModel;
    private EditText title;
    private EditText descr;
    private TextView priority;
    private Intent intentData;
    private Task fillingTask;
    private ImageButton imageButton;
    private SeekBar seekBarPriority;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_task_data);

        viewModel = new ViewModelProvider(this).get(TaskStorageViewModel.class);
        intentData = getIntent();

        getFieldsFromForm();
        setListeners();

        if( !intentData.hasExtra("task_id") ) {
            imageButton.setVisibility(View.INVISIBLE);
            setFillTask(new Task());
        }
        else {
            Consumer<Task> taskConsumer = task -> setFillTask(task);
            viewModel.getTaskByIDAsync(intentData.getLongExtra("task_id", 0), taskConsumer);

        }
    }

    protected void getFieldsFromForm() {
        title = (EditText) findViewById(R.id.edit_text_task_title);
        descr = (EditText) findViewById(R.id.edit_text_task_desctiption);
        priority = (TextView)findViewById(R.id.text_view_priority);
        imageButton = (ImageButton)findViewById(R.id.delete_task);
        seekBarPriority = (SeekBar) findViewById(R.id.seek_bar_task_priority);
    }

    protected void setListeners() {
        seekBarPriority.setOnSeekBarChangeListener(new PrioritySetSeekBar());
    }

    public void setFillTask(Task task) {
        fillingTask = task;
        fillFormTask();
    }

    protected void fillFormTask() {
        title.setText(fillingTask.getTitle());
        descr.setText(fillingTask.getDescription());
        priority.setText(fillingTask.getPriority().toString());
        seekBarPriority.setProgress(fillingTask.getPriority());
    }

    public void onClickSaveTask(View view) {
        fillingTask.setTitle(title.getText().toString());
        fillingTask.setDescription(descr.getText().toString());
        fillingTask.setPriority(Integer.parseInt(priority.getText().toString()));

        viewModel.SaveTask(fillingTask);

        finish();
    }

    public void onClickBackFromSave(View view) {
        finish();
    }

    public void onClickDeleteTask(View view) {
        viewModel.DeleteTask(fillingTask);
        finish();
    }


    private class PrioritySetSeekBar implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            priority.setText(Integer.toString(progress));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    }

}
