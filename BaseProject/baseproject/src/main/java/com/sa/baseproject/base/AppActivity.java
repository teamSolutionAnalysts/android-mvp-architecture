package com.sa.baseproject.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sa.baseproject.R;
import com.sa.baseproject.utils.PermissionUtils;


public abstract class AppActivity extends AppCompatActivity {
    protected abstract int defineLayoutResource();//Activity's layout can be declare here

    protected abstract void initializeComponents();//Activity's components initialization here

    public abstract void trackScreen();

    private AppFragmentManager appFragmentManager;


    private PermissionUtils permissionUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        appFragmentManager = new AppFragmentManager(this, R.id.activity_main_container);
        permissionUtils = new PermissionUtils(this);
        super.onCreate(savedInstanceState);
        setContentView(defineLayoutResource());
        initializeComponents();
        trackScreen();
    }


    @Override
    public void onBackPressed() {
        appFragmentManager.notifyFragment(true);
    }

    public AppFragmentManager getAppFragmentManager() {
        return appFragmentManager;
    }


    public PermissionUtils getPermissionUtils() {
        return permissionUtils;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}
