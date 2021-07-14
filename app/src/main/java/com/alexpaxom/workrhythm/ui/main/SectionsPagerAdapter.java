package com.alexpaxom.workrhythm.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.alexpaxom.workrhythm.R;
import com.alexpaxom.workrhythm.model.Task;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_tasks, R.string.tab_in_work, R.string.tab_finished};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PageFragment (defined as a static inner class below).

        if(position == 0) {
            return new PageFragment(R.layout.tab_task_list_in_queue, Task.IN_QUEUE);
        }
        else if(position == 1) {
            return new PageFragment(R.layout.tab_task_list, Task.IN_WORK);
        }
        else if(position == 2) {
            return new PageFragment(R.layout.tab_task_list, Task.FINISHED);
        }
        else
            return new PageFragment();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }
}