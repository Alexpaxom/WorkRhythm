package com.alexpaxom.workrhythm.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alexpaxom.workrhythm.R;
import com.alexpaxom.workrhythm.viewmodel.TaskStorageViewModel;
import com.google.android.material.tabs.TabLayout;

import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;


import com.alexpaxom.workrhythm.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private TaskStorageViewModel viewModel;
    SectionsPagerAdapter sectionsPagerAdapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(TaskStorageViewModel.class);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
    }

    public void onClickAddTask(View view) {
        Intent intent = new Intent(MainActivity.this, FillTaskDataFormActivity.class);
        startActivity(intent);
    }

    public void onClickSetPrevStatus(View view) {

        View item = (View) view.getParent();
        TextView id_task = (TextView) item.findViewById(R.id.id_task_in_db);
        viewModel.setPrevStatus(Long.parseLong( id_task.getText().toString() ));
    }

    public void onClickSetNextStatus(View view) {
        View item = (View) view.getParent();
        TextView id_task = (TextView) item.findViewById(R.id.id_task_in_db);
        viewModel.setNextStatus(Long.parseLong(id_task.getText().toString()));
    }

    public void onClickTaskItem(View view) {
        TextView id_task = (TextView) view.findViewById(R.id.id_task_in_db);
        Intent intent = new Intent(MainActivity.this, FillTaskDataFormActivity.class);
        intent.putExtra("task_id", Long.parseLong(id_task.getText().toString()));
        startActivity(intent);
    }

}