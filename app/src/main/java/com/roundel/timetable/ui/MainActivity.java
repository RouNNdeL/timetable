package com.roundel.timetable.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.roundel.timetable.HomeListAdapter;
import com.roundel.timetable.NavigationDrawerAdapter;
import com.roundel.timetable.R;
import com.roundel.timetable.api.APIException;
import com.roundel.timetable.api.LibrusClient;
import com.roundel.timetable.api.LuckyNumberTask;
import com.roundel.timetable.items.HomeItemsGroup;
import com.roundel.timetable.items.LuckyNumber;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
{
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private HomeListAdapter mAdapter;
    private HomeItemsGroup mDataSet;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.LayoutManager mNavigationLayoutManager;

    private String mAuthToken = null;
    private String mAuthType = null;

    LibrusClient mClient = null;

    private String TAG = getClass().getSimpleName();

    private Toolbar toolbar;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayoutMain);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(this);

        String token = preference.getString(getString(R.string.preference_token), "");
        String auth_type = preference.getString(getString(R.string.preference_auth_type), "Bearer");
        boolean logged_in = preference.getBoolean(getString(R.string.preference_logged_in), false);

        if(!logged_in || TextUtils.isEmpty(token))
        {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        else
        {

            mAuthToken = token;
            mAuthType = auth_type;

            mClient = new LibrusClient(this, mAuthToken, mAuthType);

            mRecyclerView = (RecyclerView) findViewById(R.id.main_recyclerView);

            mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.main_swipeRefreshLayout);

            mRecyclerView.setHasFixedSize(true);

            mLayoutManager = new LinearLayoutManager(this);
            mNavigationLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);

            mDataSet = new HomeItemsGroup();

            String[] mPlanetTitles = getResources().getStringArray(R.array.planets);
            DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            RecyclerView mDrawerList = (RecyclerView) findViewById(R.id.left_drawer);

            mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);

            Window window = getWindow();
            window.setStatusBarColor(getColor(R.color.colorPrimaryDark));

            // Set the adapter for the list view
            String[] a = getResources().getStringArray(R.array.planets);
            mDrawerList.setLayoutManager(mNavigationLayoutManager);
            mDrawerList.setAdapter(new NavigationDrawerAdapter(a, this));
            /*
            Example data set

            final LuckyNumber luckyNumber = new LuckyNumber(12, new Date());
            final List<GradeGroup> gradeGroups = new ArrayList<>();
            final List<Grade> grades = new ArrayList<>();
            final List<Grade> grades2 = new ArrayList<>();

            grades.add(new Grade("4+", new Date(new Date().getTime()-13*24*60*60*1000), 3, 100, 0xFFFF0000));
            grades.add(new Grade("5", new Date(new Date().getTime()-34*24*60*60*1000), 1, 101, 0xFF0000FF));
            grades.add(new Grade("3", new Date(new Date().getTime()-54*24*60*60*1000), 1, 101, 0xFF0088FF));
            grades.add(new Grade("4-", new Date(new Date().getTime()-12*24*60*60*1000), 1, 101, 0xFF8800FF));

            grades2.add(new Grade("3", new Date(new Date().getTime()-11*24*60*60*1000), 3, 100, 0xFF44FF00));
            grades2.add(new Grade("1", new Date(new Date().getTime()-7*24*60*60*1000), 3, 100, 0xFF0FF000));
            grades2.add(new Grade("2+", new Date(new Date().getTime()-29*24*60*60*1000), 3, 100, 0xFF88FF00));
            grades2.add(new Grade("4-", new Date(new Date().getTime()-15*24*60*60*1000), 3, 100, 0xFF00FF88));

            gradeGroups.add(new GradeGroup(grades, 103));
            gradeGroups.add(new GradeGroup(grades2, 11));
            mDataSet.add(luckyNumber);
            mDataSet.addAll(gradeGroups);
            */

            ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT)
            {
                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir)
                {
                    if(swipeDir == ItemTouchHelper.RIGHT)
                    {
                        final int position = viewHolder.getLayoutPosition();
                        Log.d(TAG, Integer.toString(position));

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

            mAdapter = new HomeListAdapter(this, mDataSet);
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

            mSwipeRefreshLayout.setColorSchemeColors(
                    getColor(R.color.refreshLayoutBlue),
                    getColor(R.color.refreshLayoutRed),
                    getColor(R.color.refreshLayoutGreen),
                    getColor(R.color.refreshLayoutYellow));
        }
        fetchData(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.action_settings:
                final Snackbar snackbar = Snackbar.make(coordinatorLayout, "Open settings Activity", Snackbar.LENGTH_LONG);
                snackbar.show();
                return true;
            case R.id.action_log_out:
                logout();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchData(final boolean showRefresh)
    {
        mDataSet.clear();
        mAdapter.notifyDataSetChanged();

        final Context context = this;

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
    }

    private void logout()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(getString(R.string.preference_token));
        editor.putBoolean(getString(R.string.preference_logged_in), false);
        editor.apply();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
