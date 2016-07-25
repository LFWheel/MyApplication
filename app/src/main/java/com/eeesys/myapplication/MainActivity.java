package com.eeesys.myapplication;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.eeesys.myapplication.common.BaseActivity;
import com.eeesys.myapplication.common.StatusBarCompat;
import com.eeesys.myapplication.ui.activity.MyInfoActivity;
import com.eeesys.myapplication.ui.adapter.MyFragmentPagerAdapter;
import com.eeesys.myapplication.ui.fragment.HotFragment;
import com.eeesys.myapplication.ui.fragment.MainFragment;
import com.eeesys.myapplication.ui.fragment.RecentFragment;
import com.eeesys.myapplication.ui.fragment.RecomendFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    private String TAG = "MainActivity";
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setLayoutReourseId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setUpView() {
        toolbar = (Toolbar) $(R.id.toolbar);
        toolbar.setTitle("首页");
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) $(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) $(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
       // navigationView.setCheckedItem(R.id.nav_gallery);
        //setFragment(new MainFragment());
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_recomend) {
            // Handle the camera action
            setFragment(new RecomendFragment());
        } else if (id == R.id.nav_gallery) {
            setFragment(new MainFragment());
        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(this, MyInfoActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    private void setFragment(Fragment f){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,f)
                .commit();
    }
}
