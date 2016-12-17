package com.roundel.timetable.ui;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.roundel.timetable.GenericList;
import com.roundel.timetable.Grade;
import com.roundel.timetable.GradeGroup;
import com.roundel.timetable.HomeItemsGroup;
import com.roundel.timetable.HomeListAdapter;
import com.roundel.timetable.LuckyNumber;
import com.roundel.timetable.R;
import com.roundel.timetable.api.APIException;
import com.roundel.timetable.api.LuckyNumberTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private RecyclerView mRecyclerView;
    private HomeListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private String mAuthToken = null;
    private String mAuthType = null;

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
        mRecyclerView = (RecyclerView) findViewById(R.id.main_recyclerView);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        final HomeItemsGroup group = new HomeItemsGroup();
        final LuckyNumber luckyNumber = new LuckyNumber(12, new Date());
        final List<GradeGroup> gradeGroups = new ArrayList<>();
        final List<Grade> grades = new ArrayList<>();
        final List<Grade> grades2 = new ArrayList<>();

        grades.add(new Grade("4+", new Date(), 3, 100, 0xFFFF0000));
        grades.add(new Grade("5", new Date(), 1, 101, 0xFF0000FF));

        grades2.add(new Grade("3", new Date(), 3, 100, 0xFF00FF00));

        gradeGroups.add(new GradeGroup(grades, 103));
        gradeGroups.add(new GradeGroup(grades2, 11));
        group.add(luckyNumber);
        group.addAll(gradeGroups);

        Log.d(TAG, "Item at 0 type is "+group.getItemType(1));

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                if(swipeDir == ItemTouchHelper.RIGHT)
                {
                    final int position = viewHolder.getLayoutPosition();
                    Log.d(TAG, Integer.toString(position));

                    group.remove(position);
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
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);

        mAdapter = new HomeListAdapter(this, group);
        mRecyclerView.setAdapter(mAdapter);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

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

            fetchData();
        }
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

    private void fetchData()
    {
        final Context context = this;
        LuckyNumberTask task = new LuckyNumberTask(this, new LuckyNumberTask.GetLuckyNumberResponse()
        {
            @Override
            public void onStart()
            {
                Toast.makeText(MainActivity.this, "Fetching LuckyNumber", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(JSONObject result)
            {
                Log.d(TAG, "onSuccess");
                try
                {
                    int luckyNumber = result.getInt(LuckyNumberTask.JSON_LUCKY_NUMBER);
                    String luckyNumberDay = result.getString(LuckyNumberTask.JSON_LUCKY_NUMBER_DAY);
                    //TODO: Display the number in a proper way
                }
                catch(JSONException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(APIException e)
            {
                Log.d(TAG, "onFailure");
                APIException.displayErrorToUser(e, context);
            }
        });
        task.execute(mAuthToken, mAuthType);
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