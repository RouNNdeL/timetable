package com.roundel.timetable.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roundel.timetable.NavigationDrawerItem;
import com.roundel.timetable.NavigationDrawerItems;
import com.roundel.timetable.R;
import com.roundel.timetable.adpater.NavigationDrawerAdapter;


import static com.roundel.timetable.NavigationDrawerItem.TYPE_DIVIDER;
import static com.roundel.timetable.NavigationDrawerItem.TYPE_EMPTY_SPACE;
import static com.roundel.timetable.NavigationDrawerItem.TYPE_ITEM;
import static com.roundel.timetable.NavigationDrawerItem.TYPE_SUB_HEADER;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SubjectInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubjectInfoFragment extends Fragment implements View.OnClickListener
{
    private NavigationDrawerItems mNavigationDrawerItems;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private NavigationDrawerAdapter mAdapter;

    public SubjectInfoFragment()
    {
        // Required empty public constructor
    }

    public static SubjectInfoFragment newInstance()
    {
        SubjectInfoFragment fragment = new SubjectInfoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        final View view = inflater.inflate(R.layout.fragment_subject_info, container, false);

        mNavigationDrawerItems = new NavigationDrawerItems();

        mNavigationDrawerItems.add(new NavigationDrawerItem(TYPE_EMPTY_SPACE));
        mNavigationDrawerItems.add(new NavigationDrawerItem(TYPE_ITEM, 0, "Marek Pawlowski", getContext().getDrawable(R.drawable.ic_person_white_24dp), false));
        mNavigationDrawerItems.add(new NavigationDrawerItem(TYPE_ITEM, 0, "5 lekcji w tygodniu", getContext().getDrawable(R.drawable.ic_event_white_24dp), false));
        mNavigationDrawerItems.add(new NavigationDrawerItem(TYPE_EMPTY_SPACE));
        mNavigationDrawerItems.add(new NavigationDrawerItem(TYPE_DIVIDER));
        mNavigationDrawerItems.add(new NavigationDrawerItem(TYPE_SUB_HEADER, "Oceny"));
        mNavigationDrawerItems.add(new NavigationDrawerItem(TYPE_ITEM, 0, "5 ocen", getContext().getDrawable(R.drawable.ic_grade_white_24dp), false));
        mNavigationDrawerItems.add(new NavigationDrawerItem(TYPE_ITEM, 0, "Srednia wazona: 4.45", getContext().getDrawable(R.drawable.ic_calculator_white_24dp), false));
        mNavigationDrawerItems.add(new NavigationDrawerItem(TYPE_EMPTY_SPACE));

        mRecyclerView = (RecyclerView) view.findViewById(R.id.subjectInfo_recyclerView);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new NavigationDrawerAdapter(mNavigationDrawerItems, getContext(), this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);


        return view;
    }

    @Override
    public void onClick(View view)
    {

    }
}
