package com.roundel.timetable.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.roundel.timetable.HomeListAdapter;
import com.roundel.timetable.R;
import com.roundel.timetable.api.APIException;
import com.roundel.timetable.api.LibrusClient;
import com.roundel.timetable.librus.HomeItemsGroup;
import com.roundel.timetable.librus.LuckyNumber;
import com.roundel.timetable.librus.Me;

public class HomeFragment extends Fragment
{
    public static final String ARGUMENT_TOKEN = "ARGUMENT_TOKEN";
    public static final String ARGUMENT_TOKEN_TYPE = "ARGUMENT_TOKEN_TYPE";

    private LibrusClient mClient;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private HomeItemsGroup mDataSet;
    private RecyclerView.LayoutManager mLayoutManager;
    private HomeListAdapter mAdapter;

    private String mAuthToken;
    private String mAuthType;

    public HomeFragment()
    {
    }

    public static HomeFragment newInstance()
    {
        HomeFragment fragment = new HomeFragment();
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
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        Bundle args = getArguments();
        mAuthToken = args.getString(ARGUMENT_TOKEN);
        mAuthType = args.getString(ARGUMENT_TOKEN_TYPE);

        mClient = new LibrusClient(getContext(), mAuthToken, mAuthType);


        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView = (RecyclerView) view.findViewById(R.id.main_recyclerView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.main_swipeRefreshLayout);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mDataSet = new HomeItemsGroup();

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT)
        {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir)
            {
                if(swipeDir == ItemTouchHelper.RIGHT)
                {
                    final int position = viewHolder.getLayoutPosition();

                    mDataSet.remove(position);
                    mRecyclerView.removeViewAt(position);
                    mAdapter.notifyItemRemoved(position);
                    //mAdapter.notifyItemRangeChanged(position, group.size());
                }
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
            {
                return false;
            }

            @Override
            public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder)
            {
                if(((HomeListAdapter.ViewHolder) viewHolder).getType() == HomeItemsGroup.ITEM_TYPE_LUCKY_NUMBER)
                    return 0;
                return super.getSwipeDirs(recyclerView, viewHolder);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);

        mAdapter = new HomeListAdapter(getContext(), mDataSet, new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int position = mRecyclerView.getChildAdapterPosition(view);
                Toast.makeText(getContext(), "Clicked on " + position, Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                fetchData(true);
            }
        });

        /*mSwipeRefreshLayout.setColorSchemeColors(
                getContext().getColor(R.color.refreshLayoutBlue),
                getContext().getColor(R.color.refreshLayoutRed),
                getContext().getColor(R.color.refreshLayoutGreen),
                getContext().getColor(R.color.refreshLayoutYellow)
        );*/
        mSwipeRefreshLayout.setColorSchemeColors(
                getContext().getColor(R.color.colorAccentDark)
        );
        fetchData(false);
        return view;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }

    private void fetchData(final boolean showRefresh)
    {
        mDataSet.clear();
        mAdapter.notifyDataSetChanged();

        final Context context = getContext();

        mClient.fetchLuckyNumber(new LibrusClient.LuckyNumberResponseListener()
        {
            @Override
            public void onStart()
            {
                if(showRefresh)
                    mSwipeRefreshLayout.setRefreshing(true);
            }

            @Override
            public void onSuccess(LuckyNumber luckyNumber)
            {
                mDataSet.add(luckyNumber);
                mAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(APIException exception)
            {
                mSwipeRefreshLayout.setRefreshing(false);
                APIException.displayErrorToUser(exception, context);
            }
        });
        mClient.fetchMe(new LibrusClient.MeResponseListener()
        {
            @Override
            public void onStart()
            {
                if(showRefresh)
                    mSwipeRefreshLayout.setRefreshing(true);
            }

            @Override
            public void onSuccess(Me m)
            {
                Toast.makeText(getContext(), m.getFirstName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(APIException exception)
            {
                mSwipeRefreshLayout.setRefreshing(false);
                APIException.displayErrorToUser(exception, context);
            }
        });
    }
}
