package com.roundel.timetable.adpater;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.roundel.timetable.ui.SubjectGradesFragment;
import com.roundel.timetable.ui.SubjectInfoFragment;

/**
 * Created by Krzysiek on 2016-12-20.
 */
public class SubjectTabsAdapter extends FragmentStatePagerAdapter
{
    public static final String TAG = "SubjectTabsAdapter";

    private int amount;

    public SubjectTabsAdapter(FragmentManager fm, int amount)
    {
        super(fm);
        this.amount = amount;
    }

    @Override
    public Fragment getItem(int position)
    {
        switch(position)
        {
            case 0:
                return new SubjectInfoFragment();
            case 1:
                return new SubjectGradesFragment();
            default:
                return null;
        }

    }

    @Override
    public int getCount()
    {
        return amount;
    }
}
