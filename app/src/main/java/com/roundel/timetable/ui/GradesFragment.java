package com.roundel.timetable.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roundel.timetable.R;
import com.roundel.timetable.adpater.SubjectListAdapter;
import com.roundel.timetable.api.LibrusClient;
import com.roundel.timetable.librus.Subject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GradesFragment extends Fragment implements View.OnClickListener
{

    public static final String ARGUMENT_TOKEN_TYPE = "ARGUMENT_TOKEN_TYPE";
    public static final String ARGUMENT_TOKEN = "ARGUMENT_TOKEN";
    private LibrusClient mClient;
    private String mAuthToken;
    private String mAuthType;
    private List<Subject> mDataSet;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private SubjectListAdapter mAdapter;


    public GradesFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.fragment_grades, container, false);

        Bundle args = getArguments();
        mAuthToken = args.getString(ARGUMENT_TOKEN);
        mAuthType = args.getString(ARGUMENT_TOKEN_TYPE);

        mClient = new LibrusClient(getContext(), mAuthToken, mAuthType);

        mDataSet = new ArrayList<>();
        mDataSet.add(new Subject(100, "Matematyka", 1, "Mat", false, false));
        mDataSet.add(new Subject(101, "Polski", 2, "J.Pol", false, false));
        mDataSet.add(new Subject(102, "Podstawy przedsiebiorczosci", 3, "PP", false, false));
        mDataSet.add(new Subject(102, "Podstawy przedsiebiorczosci", 3, "PP", false, false));
        mDataSet.add(new Subject(102, "Podstawy przedsiebiorczosci", 3, "PP", false, false));
        mDataSet.add(new Subject(102, "Podstawy przedsiebiorczosci", 3, "PP", false, false));
        mDataSet.add(new Subject(102, "Podstawy przedsiebiorczosci", 3, "PP", false, false));
        mDataSet.add(new Subject(102, "Podstawy przedsiebiorczosci", 3, "PP", false, false));
        mDataSet.add(new Subject(102, "Podstawy przedsiebiorczosci", 3, "PP", false, false));
        mDataSet.add(new Subject(102, "Podstawy przedsiebiorczosci", 3, "PP", false, false));
        mDataSet.add(new Subject(102, "Podstawy przedsiebiorczosci", 3, "PP", false, false));
        mDataSet.add(new Subject(102, "Podstawy przedsiebiorczosci", 3, "PP", false, false));
        mDataSet.add(new Subject(102, "Podstawy przedsiebiorczosci", 3, "PP", false, false));

        mAdapter = new SubjectListAdapter(getContext(), mDataSet, this);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.grades_recyclerView);

        mLayoutManager = new LinearLayoutManager(getContext());

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onClick(View view)
    {
        Intent intent = new Intent(getContext(), SubjectActivity.class);
        Pair<View, String> p1 = Pair.create(view.findViewById(R.id.gradeGroupSubject), "test1");
        Pair<View, String> p2 = Pair.create(view.findViewById(R.id.gradeGroupGradeCount), "test2");
        Pair<View, String> p3 = Pair.create(view.findViewById(R.id.gradeGroupTeacher), "test3");
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), p1);
        //ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity());
        startActivity(intent, options.toBundle());
        //startActivity(intent);
    }
}
