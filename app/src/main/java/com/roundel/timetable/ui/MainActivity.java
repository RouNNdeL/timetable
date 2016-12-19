package com.roundel.timetable.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.roundel.timetable.NavigationDrawerAdapter;
import com.roundel.timetable.NavigationDrawerItem;
import com.roundel.timetable.NavigationDrawerItems;
import com.roundel.timetable.R;
import com.roundel.timetable.api.LibrusClient;


import static com.roundel.timetable.NavigationDrawerItem.ID_ANNOUNCEMENTS;
import static com.roundel.timetable.NavigationDrawerItem.ID_CALENDAR;
import static com.roundel.timetable.NavigationDrawerItem.ID_CONTACT;
import static com.roundel.timetable.NavigationDrawerItem.ID_GRADES;
import static com.roundel.timetable.NavigationDrawerItem.ID_HOME;
import static com.roundel.timetable.NavigationDrawerItem.ID_SETTINGS;
import static com.roundel.timetable.NavigationDrawerItem.ID_TIMETABLE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private LibrusClient mClient = null;

    private NavigationDrawerItems mNavigationDrawerItems;
    private DrawerLayout mDrawerLayout;
    private RecyclerView mNavigationDrawerRecyclerView;
    private RecyclerView.LayoutManager mNavigationLayoutManager;
    private NavigationDrawerAdapter mNavigationDrawerAdapter;
    private String mAuthToken = null;
    private String mAuthType = null;
    private String TAG = getClass().getSimpleName();

    private Toolbar mToolbar;
    private CoordinatorLayout mCoordinatorLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayoutMain);

        setSupportActionBar(mToolbar);

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


            mNavigationLayoutManager = new LinearLayoutManager(this);
            mNavigationDrawerItems = new NavigationDrawerItems();

            mNavigationDrawerItems.add(new NavigationDrawerItem(NavigationDrawerItem.TYPE_EMPTY_SPACE));
            mNavigationDrawerItems.add(new NavigationDrawerItem(NavigationDrawerItem.TYPE_ITEM, ID_HOME, "Home", getDrawable(R.drawable.ic_home_white_24dp), true));
            mNavigationDrawerItems.add(new NavigationDrawerItem(NavigationDrawerItem.TYPE_ITEM, ID_CALENDAR, "Calendar", getDrawable(R.drawable.ic_event_white_24dp), true));
            mNavigationDrawerItems.add(new NavigationDrawerItem(NavigationDrawerItem.TYPE_ITEM, ID_GRADES, "Grades", getDrawable(R.drawable.ic_grade_white_24dp), true));
            mNavigationDrawerItems.add(new NavigationDrawerItem(NavigationDrawerItem.TYPE_ITEM, ID_ANNOUNCEMENTS, "Announcements", getDrawable(R.drawable.ic_announcement_white_24dp), true));
            mNavigationDrawerItems.add(new NavigationDrawerItem(NavigationDrawerItem.TYPE_ITEM, ID_TIMETABLE, "Timetable", getDrawable(R.drawable.ic_timetable_white_24dp), true));
            mNavigationDrawerItems.add(new NavigationDrawerItem(NavigationDrawerItem.TYPE_EMPTY_SPACE));
            mNavigationDrawerItems.add(new NavigationDrawerItem(NavigationDrawerItem.TYPE_DIVIDER));
            mNavigationDrawerItems.add(new NavigationDrawerItem(NavigationDrawerItem.TYPE_EMPTY_SPACE));
            mNavigationDrawerItems.add(new NavigationDrawerItem(NavigationDrawerItem.TYPE_ITEM, ID_CONTACT, "Contact", getDrawable(R.drawable.ic_mail_white_24dp), false));
            mNavigationDrawerItems.add(new NavigationDrawerItem(NavigationDrawerItem.TYPE_ITEM, ID_SETTINGS, "Settings", getDrawable(R.drawable.ic_settings_white_24dp), false));
            try
            {
                mNavigationDrawerItems.setEnabled(1);
            }
            catch(IndexOutOfBoundsException e)
            {
                e.printStackTrace();
            }

            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            mNavigationDrawerRecyclerView = (RecyclerView) findViewById(R.id.left_drawer);

            // Set the adapter for the list view
            mNavigationDrawerRecyclerView.setLayoutManager(mNavigationLayoutManager);
            mNavigationDrawerAdapter = new NavigationDrawerAdapter(mNavigationDrawerItems, this, this);
            mNavigationDrawerRecyclerView.setAdapter(mNavigationDrawerAdapter);

            mDrawerToggle = new ActionBarDrawerToggle(
                    this,
                    mDrawerLayout,
                    mToolbar,
                    R.string.drawer_open,
                    R.string.drawer_close
            );
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            // Set the drawer toggle as the DrawerListener
            mDrawerLayout.addDrawerListener(mDrawerToggle);
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

            HomeFragment newFragment = new HomeFragment();
            Bundle args = new Bundle();
            args.putString(HomeFragment.ARGUMENT_TOKEN, mAuthToken);
            args.putString(HomeFragment.ARGUMENT_TOKEN_TYPE, mAuthType);
            newFragment.setArguments(args);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, newFragment);
            //transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState)
    {
        super.onPostCreate(savedInstanceState, persistentState);
        mDrawerToggle.syncState();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggl
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //OnDrawerItemClick
    @Override
    public void onClick(View view)
    {
        int position = mNavigationDrawerRecyclerView.getChildLayoutPosition(view);
        if(mNavigationDrawerItems.get(position) != null)
        {
            NavigationDrawerItem item = mNavigationDrawerItems.get(position);
            final int enabled = mNavigationDrawerItems.getEnabled();
            if(item.getType() == NavigationDrawerItem.TYPE_ITEM)
            {

                //Toast.makeText(view.getContext(), "Clicked on " + position, Toast.LENGTH_SHORT).show();
                mNavigationDrawerItems.setEnabled(position);
                mNavigationDrawerAdapter.notifyDataSetChanged();
                mDrawerLayout.closeDrawers();

                if(enabled != position)
                {
                    switch(item.getId())
                    {
                        case ID_HOME:
                            HomeFragment newFragment = new HomeFragment();
                            Bundle args = new Bundle();
                            args.putString(HomeFragment.ARGUMENT_TOKEN, mAuthToken);
                            args.putString(HomeFragment.ARGUMENT_TOKEN_TYPE, mAuthType);
                            newFragment.setArguments(args);

                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_container, newFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            break;

                        case ID_CONTACT:
                            Intent sendIntent = new Intent();
                            sendIntent.setData(Uri.parse("mailto:rounndel@gmail.com"));
                            sendIntent.setAction(Intent.ACTION_SENDTO);
                            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Librus");
                            startActivity(Intent.createChooser(sendIntent, "Contact me"));
                    }
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.action_settings:
                final Snackbar snackbar = Snackbar.make(mCoordinatorLayout, "Open settings Activity", Snackbar.LENGTH_LONG);
                snackbar.show();
                return true;
            case R.id.action_log_out:
                logout();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
