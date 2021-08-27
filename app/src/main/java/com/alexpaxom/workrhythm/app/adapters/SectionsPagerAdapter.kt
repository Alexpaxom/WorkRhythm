package com.alexpaxom.workrhythm.app.adapters

import android.content.Context
import androidx.fragment.app.FragmentPagerAdapter
import com.alexpaxom.workrhythm.app.fragments.PageFragment
import com.alexpaxom.workrhythm.R
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.alexpaxom.workrhythm.data.model.Task

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val mContext: Context, fm: FragmentManager?) :
    FragmentPagerAdapter(
        fm!!
    ) {
    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PageFragment (defined as a static inner class below).
        return if (position == 0) {
            PageFragment(
                R.layout.tab_task_list_in_queue,
                Task.IN_QUEUE
            )
        } else if (position == 1) {
            PageFragment(R.layout.tab_task_list, Task.IN_WORK)
        } else if (position == 2) {
            PageFragment(R.layout.tab_task_list, Task.FINISHED)
        } else PageFragment()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 3 total pages.
        return 3
    }

    companion object {
        @StringRes
        private val TAB_TITLES =
            intArrayOf(R.string.tab_tasks, R.string.tab_in_work, R.string.tab_finished)
    }
}