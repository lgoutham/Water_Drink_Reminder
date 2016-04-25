package com.example.gautham.dr.main;

import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.gautham.dr.R;
import com.example.gautham.dr.calendar.CalendarFragment;
import com.example.gautham.dr.drinklog.DrinkLog;
import com.example.gautham.dr.reminder.Reminder;
import com.example.gautham.dr.settings.Settings;
import com.example.gautham.dr.waterchart.WaterChart;
import com.example.gautham.dr.weight.Weight;
import com.example.gautham.dr.weightreport.WeightReport;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private MenuItem navItem;
    Fragment fragment = null;
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

    private void disableNavigationViewScrollbars(NavigationView navigationView) {
        if (navigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        disableNavigationViewScrollbars(navigationView);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };

        if (savedInstanceState == null) {
            navigationView.getMenu().performIdentifierAction(R.id.drink_water, 0);
            toolbar.setTitle(R.string.drink_water);
        }
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_notification:
                Intent i = new Intent(this, Reminder.class);
                startActivity(i);
                break;
            case R.id.action_calendar:
                CalendarFragment calendarFragment = new CalendarFragment();
                calendarFragment.show(fragmentManager, "Calendar");
                break;
            case R.id.action_weight:
                Weight weight = new Weight();
                weight.show(fragmentManager, "Dialog");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        navItem = item;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        int id = navItem.getItemId();
        switch (id) {
            case R.id.drink_water:
                fragment = new ContentMainFragment();
                toolbar.setTitle(navItem.getTitle());
                fragmentTransaction.replace(R.id.container, fragment).addToBackStack(null).commit();
                drawerLayout.closeDrawers();
                return true;
            case R.id.drink_log:
                fragment = new DrinkLog();
                toolbar.setTitle(navItem.getTitle());
                fragmentTransaction.replace(R.id.container, fragment).addToBackStack(null).commit();
                drawerLayout.closeDrawers();
                return true;
            case R.id.water_chart:
                fragment = new WaterChart();
                toolbar.setTitle(navItem.getTitle());
                fragmentTransaction.replace(R.id.container, fragment).addToBackStack(null).commit();
                drawerLayout.closeDrawers();
                return true;
            case R.id.weight_report:
                fragment = new WeightReport();
                toolbar.setTitle(navItem.getTitle());
                fragmentTransaction.replace(R.id.container, fragment).addToBackStack(null).commit();
                drawerLayout.closeDrawers();
                return true;
            case R.id.reminder:
                Intent i = new Intent(this, Reminder.class);
                drawerLayout.closeDrawers();
                startActivity(i);
                return true;
            case R.id.action_settings:
                Intent settings = new Intent(this, Settings.class);
                drawerLayout.closeDrawers();
                startActivity(settings);
                return true;
            case R.id.remove_ads:

                return true;
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}