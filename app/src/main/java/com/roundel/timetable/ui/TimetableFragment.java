package com.roundel.timetable.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roundel.timetable.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class TimetableFragment extends Fragment
{

    public TimetableFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_timetable, container, false);
    }
}
