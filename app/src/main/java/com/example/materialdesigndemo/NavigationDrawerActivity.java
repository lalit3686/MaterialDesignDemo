package com.example.materialdesigndemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by plalit on 6/20/2016.
 */
public class NavigationDrawerActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    //private NavigationView navigationView;
    private ListView navigationViewList;
    private Context context;
    private TextView textSelectedContent;
    private List<NavItem> mNavItems = new ArrayList<NavItem>();
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.navigation_drawer_activity);

        context = this;

        setupToolbar();
        //setUpNavigationDrawer();
        setUpContent();
        setUpCustomNavigationList();
        setUpActionBarDrawerToggleListener();
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    /*private void setUpNavigationDrawer(){
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigationView);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                item.setChecked(true);
                drawerLayout.closeDrawers();
                textSelectedContent.setText(item.getTitle());
                //startActivity(new Intent(context, NavigationDrawerActivity.class));
                return true;
            }
        });
    }*/

    private void setUpCustomNavigationList(){
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationViewList = (ListView) findViewById(R.id.navigationViewList);

        View view = LayoutInflater.from(this).inflate(R.layout.navigation_drawer_header, null);
        navigationViewList.addHeaderView(view);


        mNavItems.add(new NavItem("Home", "Meetup destination", R.drawable.ic_discuss));
        mNavItems.add(new NavItem("Preferences", "Change your preferences", R.drawable.ic_discuss));
        mNavItems.add(new NavItem("About", "Get to know about us", R.drawable.ic_discuss));

        DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems);
        navigationViewList.setAdapter(adapter);

        navigationViewList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                /**
                 * as we don't want to consider header as ListView 0th row
                 */
                if(position != 0){
                    drawerLayout.closeDrawers();
                    textSelectedContent.setText(mNavItems.get(position - 1).mTitle);
                }
            }
        });
    }

    private void setUpContent(){
        textSelectedContent = (TextView) findViewById(R.id.textSelectedContent);
    }

    private void setUpActionBarDrawerToggleListener(){
        CustomActionBarDrawerToggle actionBarDrawerToggle = new CustomActionBarDrawerToggle(this,
                drawerLayout,
                toolbar,
                R.string.app_name,
                R.string.app_name);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
    }

    private static class CustomActionBarDrawerToggle extends ActionBarDrawerToggle{

        public CustomActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar, @StringRes int openDrawerContentDescRes, @StringRes int closeDrawerContentDescRes) {
            super(activity, drawerLayout, toolbar, openDrawerContentDescRes, closeDrawerContentDescRes);
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            Snackbar.make(drawerView, "Drawer Closed", Snackbar.LENGTH_LONG).show();
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            Snackbar.make(drawerView, "Drawer Opened", Snackbar.LENGTH_LONG).show();
        }

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
        }

        @Override
        public void onDrawerStateChanged(int newState) {
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawers();
        }
        else{
            super.onBackPressed();
        }
    }
}
