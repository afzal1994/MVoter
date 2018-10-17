package com.example.mohdafzal.voter.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.mohdafzal.voter.R;
import com.example.mohdafzal.voter.adapter.NavigationAdapter;
import com.example.mohdafzal.voter.models.NavDrawerItem;
import com.example.mohdafzal.voter.utils.IContants;

import java.util.ArrayList;

public class BaseActivityNavigation extends AppCompatActivity { //changed from depricated ActionBarActivity
    public static int navCount;
    protected RelativeLayout _completeLayout, _activityLayout;
    Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    // nav drawer title
    private CharSequence mDrawerTitle;
    private Menu menuObject;
    // used to store app title
    private CharSequence mTitle;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavigationAdapter adapter;
    private RelativeLayout relative;
    private int user_option;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_navigation);
        // if (savedInstanceState == null) {
        // // on first time display view for first nav item
        // // displayView(0);
        // }
    }

    public void set(String[] navMenuTitles, TypedArray navMenuIcons) {
        SharedPreferences sharedPreferences = getSharedPreferences("userid", MODE_PRIVATE);
        user_option = sharedPreferences.getInt("user_option", 0);
        if (user_option == 6) {

            navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items_new); // load

            navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons_new);


        } else {
            ArrayList<String> strings = new ArrayList<>();
            navMenuTitles[0] = "Home";
            navMenuTitles[1] = IContants.getName(user_option);
            navMenuTitles[2] = "All Voters";
            navMenuTitles[3] = "Out Of Town";
            navMenuTitles[4] = "Voting Day";

            navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        }
        mTitle = mDrawerTitle = getTitle();
        relative = (RelativeLayout) findViewById(R.id.drawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        navDrawerItems = new ArrayList<NavDrawerItem>();

        // adding nav drawer items
        if (navMenuIcons == null) {
            for (int i = 0; i < navMenuTitles.length; i++) {
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[i]));
            }
        } else {
            for (int i = 0; i < navMenuTitles.length; i++) {
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[i],
                    navMenuIcons.getResourceId(i, -1)));
            }
        }

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // setting the nav drawer list adapter
        adapter = new NavigationAdapter(getApplicationContext(),
            navDrawerItems);
        mDrawerList.setAdapter(adapter);

        // enabling action bar app icon and behaving it as toggle button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setupDrawerToggle();

        // getSupportActionBar().setIcon(R.drawable.ic_drawer);
        //mDrawerToggle.syncState();

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
            toolbar, // nav menu toolbar instead of icon
            R.string.app_name, // nav drawer open - description for accessibility
            R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                supportInvalidateOptionsMenu();
                //mDrawerToggle.syncState();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                supportInvalidateOptionsMenu();
                //mDrawerToggle.syncState();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            if (mDrawerLayout.isDrawerOpen(relative)) {
                mDrawerLayout.closeDrawer(relative);
            } else {
                mDrawerLayout.openDrawer(relative);
            }
        }
        if (item.getItemId() == R.id.logout) {
            Intent intent = new Intent(this, LoginActivity.class);
            finish();
            finishAffinity();
            SharedPreferences.Editor editor = getSharedPreferences("Loginstatus", MODE_PRIVATE).edit();
            editor.putBoolean("loginstatus", false);
            editor.apply();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                startActivity(intent, ActivityOptions
                    .makeSceneTransitionAnimation(this).toBundle());
            } else {
                startActivity(intent);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    /***
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        // boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        // menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     */
    private void displayView(int position) {
        if (user_option != 6) {
            switch (position) {


                case 0:
                    navCount = 0;
                    adapter.notifyDataSetChanged();
                    if (!this.getClass().getSimpleName().matches("MainPage")) {
                        startActivity(new Intent(this, DashboardActivity.class));

                    }
                    break;
                case 1:
                    navCount = 1;
                    adapter.notifyDataSetChanged();

                    if (!this.getClass().getSimpleName().matches("UpvibhagActivity")) {
                        adapter.notifyDataSetChanged();
                        startActivity(new Intent(this, PostsHirarchyActivity.class));
                    }
                    break;
                case 2:
                    navCount = 2;
                    adapter.notifyDataSetChanged();

                    if (!this.getClass().getSimpleName().matches("Voterlist")) {
                        startActivity(new Intent(this, Voterlist.class));
                    }
                    break;
                case 3:
                    navCount = 3;
                    adapter.notifyDataSetChanged();

                    if (!this.getClass().getSimpleName().matches("OutOfTownActivity")) {
                        startActivity(new Intent(this, OutOfTownActivity.class));
                    }
                    break;
                case 4:
                    navCount = 4;
                    adapter.notifyDataSetChanged();

                    SharedPreferences sharedPreference2 = getSharedPreferences("userid", MODE_PRIVATE);
                    int user_id2 = sharedPreference2.getInt("user_option", 0);
                    if (!this.getClass().getSimpleName().matches("VotingDayActivity")) {
                        if (user_id2 != 6) {
                            startActivity(new Intent(this, VotingDayActivity.class));
                        } else {
                            startActivity(new Intent(this, PendingDoneVotersList.class));

                        }
                    }


                    break;


                //case 2:
                //  Intent intent2 = new Intent(this, ThirdActivity.class);
                //  startActivity(intent2);
                //  finish();
                //  break;
                // case 3:
                // Intent intent3 = new Intent(this, fourth.class);
                // startActivity(intent3);
                // finish();
                // break;
                // case 4:
                // Intent intent4 = new Intent(this, fifth.class);
                // startActivity(intent4);
                // finish();
                // break;
                // case 5:
                // Intent intent5 = new Intent(this, sixth.class);
                // startActivity(intent5);
                // finish();
                // break;
                default:
                    break;

            }
        } else {
            switch (position) {


                case 0:
                    navCount = 0;
                    adapter.notifyDataSetChanged();
                    if (!this.getClass().getSimpleName().matches("MainPage")) {
                        startActivity(new Intent(this, DashboardActivity.class));

                    }
                    break;
                case 1:
                    navCount = 1;
                    adapter.notifyDataSetChanged();

                    if (!this.getClass().getSimpleName().matches("Voterlist")) {
                        adapter.notifyDataSetChanged();
                        startActivity(new Intent(this, Voterlist.class));
                    }
                    break;
                case 2:
                    navCount = 2;
                    adapter.notifyDataSetChanged();

                    if (!this.getClass().getSimpleName().matches("AddNewVoter")) {
                        Intent intent = new Intent(this, AddNewVoterActivity.class);
                        intent.putExtra("voter_id", 0);
                        startActivity(intent);

                    }
                    break;

                case 3:
                    navCount = 3;
                    adapter.notifyDataSetChanged();
                    SharedPreferences sharedPreference2 = getSharedPreferences("userid", MODE_PRIVATE);
                    int user_id2 = sharedPreference2.getInt("user_option", 0);
                    if (!this.getClass().getSimpleName().matches("VotingDayActivity")) {
                        if (user_id2 != 6) {
                            startActivity(new Intent(this, VotingDayActivity.class));
                        } else {
                            startActivity(new Intent(this, PendingDoneVotersList.class));

                        }
                    }


                    break;


                //case 2:
                //  Intent intent2 = new Intent(this, ThirdActivity.class);
                //  startActivity(intent2);
                //  finish();
                //  break;
                // case 3:
                // Intent intent3 = new Intent(this, fourth.class);
                // startActivity(intent3);
                // finish();
                // break;
                // case 4:
                // Intent intent4 = new Intent(this, fifth.class);
                // startActivity(intent4);
                // finish();
                // break;
                // case 5:
                // Intent intent5 = new Intent(this, sixth.class);
                // startActivity(intent5);
                // finish();
                // break;
                default:
                    break;

            }
        }
        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        mDrawerList.setSelection(position);
        mDrawerLayout.closeDrawer(relative);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    void setupDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        //This is necessary to change the icon of the Drawer Toggle upon state change.
        mDrawerToggle.syncState();
    }

    private class SlideMenuClickListener implements
        ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }
}
