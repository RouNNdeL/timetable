package com.roundel.timetable.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roundel.timetable.R;
import com.roundel.timetable.adpater.GradeGroupAdapter;
import com.roundel.timetable.librus.GradeGroup;

import java.util.Date;

/**
 * Created by Krzysiek on 2016-12-20.
 */
public class SubjectGradesFragment extends Fragment implements View.OnClickListener
{
    public static final String TAG = "SubjectGradesFragment";
    private RecyclerView mRecyclerView;
    private GradeGroup mDataSet;
    private LinearLayoutManager mLayoutManager;
    private GradeGroupAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.fragment_subject_grades, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.subjectGrades_recyclerView);

        mDataSet = new GradeGroup(100);
        mDataSet.add(new GradeGroup.Grade("4", new Date(), 2, 2, 0xFFFF8800));
        mDataSet.add(new GradeGroup.Grade("4+", new Date(), 2, 2, 0xFFFF8888));
        mDataSet.add(new GradeGroup.Grade("3-", new Date(), 2, 2, 0xFFFF0000));
        mDataSet.add(new GradeGroup.Grade("2", new Date(), 2, 2, 0xFF8888FF));
        mDataSet.add(new GradeGroup.Grade("1", new Date(), 2, 2, 0xFF0088FF));

        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new GradeGroupAdapter(getContext(), mDataSet, this);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        return view;
    }

    @Override
    public void onClick(View view)
    {

    }
}
