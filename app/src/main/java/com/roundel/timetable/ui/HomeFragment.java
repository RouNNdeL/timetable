package com.roundel.timetable.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.roundel.timetable.LoadingProgress;
import com.roundel.timetable.R;
import com.roundel.timetable.adpater.HomeListAdapter;
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
        int columns = getContext().getResources().getInteger(R.integer.columns_count);

        Bundle args = getArguments();
        mAuthToken = args.getString(ARGUMENT_TOKEN);
        mAuthType = args.getString(ARGUMENT_TOKEN_TYPE);

        mClient = new LibrusClient(getContext(), mAuthToken, mAuthType);


        mLayoutManager = new StaggeredGridLayoutManager(columns, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.home_recyclerView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.home_swipeRefreshLayout);

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
                final int type = ((HomeListAdapter.ViewHolder) viewHolder).getType();
                if(type == HomeItemsGroup.ITEM_TYPE_LUCKY_NUMBER || type == HomeItemsGroup.ITEM_LOADING_PROGRESS)
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

    private void addItem(Object item, boolean move)
    {
        mDataSet.add(item);
        if(move)
            mAdapter.notifyItemMoved(mDataSet.size() - 2, mDataSet.size() - 1);
        else
            mAdapter.notifyItemInserted(mDataSet.size() - 1);
    }

    private void fetchData(final boolean showRefresh)
    {
        mDataSet.clear();
        if(!showRefresh) mDataSet.add(new LoadingProgress());
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
                addItem(luckyNumber, !showRefresh);
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
            public void onSuccess(Me me)
            {
                addItem(me, !showRefresh);
                if(mDataSet.removeProgress())
                    mAdapter.notifyItemRemoved(mDataSet.size());
                mSwipeRefreshLayout.setRefreshing(false);
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
