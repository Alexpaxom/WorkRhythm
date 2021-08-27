package com.alexpaxom.workrhythm.app.activites


import androidx.appcompat.app.AppCompatActivity
import com.alexpaxom.workrhythm.app.viewmodel.TaskStorageViewModel
import com.alexpaxom.workrhythm.app.adapters.SectionsPagerAdapter
import androidx.viewpager.widget.ViewPager
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import android.content.Intent
import android.view.View
import android.widget.TextView
import com.alexpaxom.workrhythm.R
import com.alexpaxom.workrhythm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private var viewModel: TaskStorageViewModel? = null
    var sectionsPagerAdapter: SectionsPagerAdapter? = null
    var viewPager: ViewPager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TaskStorageViewModel::class.java)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        viewPager = binding!!.viewPager
        viewPager!!.adapter = sectionsPagerAdapter
        val tabs = binding!!.tabs
        tabs.setupWithViewPager(viewPager)
    }

    fun onClickAddTask(view: View?) {
        val intent = Intent(this@MainActivity, FillTaskDataFormActivity::class.java)
        startActivity(intent)
    }

    fun onClickSetPrevStatus(view: View) {
        val item = view.parent as View
        val id_task = item.findViewById<View>(R.id.id_task_in_db) as TextView
        viewModel!!.setPrevStatus(id_task.text.toString().toLong())
    }

    fun onClickSetNextStatus(view: View) {
        val item = view.parent as View
        val id_task = item.findViewById<View>(R.id.id_task_in_db) as TextView
        viewModel!!.setNextStatus(id_task.text.toString().toLong())
    }

    fun onClickTaskItem(view: View) {
        val id_task = view.findViewById<View>(R.id.id_task_in_db) as TextView
        val intent = Intent(this@MainActivity, FillTaskDataFormActivity::class.java)
        intent.putExtra("task_id", id_task.text.toString().toLong())
        startActivity(intent)
    }
}