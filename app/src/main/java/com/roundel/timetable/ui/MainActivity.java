package com.roundel.timetable.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.roundel.timetable.R;
import com.roundel.timetable.api.GetLuckyNumber;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
{
    private String mAuthToken = null;
    private String mAuthType = null;

    private String TAG = getClass().getSimpleName();

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(this);

        String token = preference.getString(getString(R.string.preference_token), "");
        String auth_type = preference.getString(getString(R.string.preference_auth_type), "Bearer");
        boolean logged_in = preference.getBoolean(getString(R.string.preference_logged_in), false);

        if(! logged_in)
        {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        mAuthToken = token;
        mAuthType = auth_type;
        Log.d(TAG, token + " type: " + auth_type);

        fetchData();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
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
        CoordinatorLayout layout = (CoordinatorLayout) findViewById(R.id.coordinatorLayoutMain);
        switch(item.getItemId())
        {
            case R.id.action_settings:
                Snackbar.make(viewPager, "Open settings Activity", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), "Home");
        adapter.addFragment(new TimetableFragment(), "Timetable");
        adapter.addFragment(new CalendarFragment(), "Calendar");
        adapter.addFragment(new GradesFragment(), "Grades");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter
    {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void fetchData()
    {
        GetLuckyNumber task = new GetLuckyNumber(this, new GetLuckyNumber.GetLuckyNumberResponse()
        {
            @Override
            public void onTaskStart()
            {
                Toast.makeText(MainActivity.this, "Fetching LuckyNumber", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTaskEnd(String result)
            {
                Log.d(TAG, "Task finished: " + result);
                JSONObject json;
                try
                {
                    json = (JSONObject) new JSONObject(result).get("LuckyNumber");
                    int luckyNumber = (int) json.get("LuckyNumber");
                    String luckyNumberDay = (String) json.get("LuckyNumberDay");
                    Toast.makeText(MainActivity.this,
                            String.format(Locale.getDefault(), "Luck number: %d for day: %s", luckyNumber, luckyNumberDay),
                            Toast.LENGTH_SHORT).show();
                }
                catch(JSONException e)
                {
                    e.printStackTrace();
                }
            }
        });
        task.execute(mAuthToken, mAuthType);
    }
}
