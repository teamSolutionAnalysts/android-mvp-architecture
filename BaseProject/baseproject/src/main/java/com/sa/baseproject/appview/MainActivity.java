package com.sa.baseproject.appview;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.sa.baseproject.R;
import com.sa.baseproject.base.AppActivity;
import com.sa.baseproject.base.AppFragmentState;


public class MainActivity extends AppActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    /**
     * Used to store the last screen title.
     */
    private CharSequence mTitle;

    @Override
    protected int defineLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void initializeComponents() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        final ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toogle);
        toogle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        toogle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getAppFragmentManager().addFragment(AppFragmentState.F_SIGNUP, null, false);
//        LogUtils.e("Constnat : " + Const.value);
    }

    @Override
    public void trackScreen() {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.navigation_item_1:
                mTitle = getString(R.string.title_home);
                break;
            case R.id.navigation_item_2:
                mTitle = getString(R.string.title_bookmarks);
                break;
            case R.id.navigation_item_3:
                mTitle = getString(R.string.title_favorite);
                break;
            case R.id.navigation_item_4:
                mTitle = getString(R.string.title_payment);
                break;
            case R.id.navigation_item_5:
                mTitle = getString(R.string.title_settings);
                break;
        }
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(mTitle);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:

                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
