package com.roundel.timetable.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roundel.timetable.R;

/**
 * Created by Krzysiek on 2016-12-20.
 */
public class SubjectGradesFragment extends Fragment
{
    public static final String TAG = "SubjectGradesFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_subject_grades, container, false);
    }
}
