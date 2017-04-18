package com.sa.baseproject.base;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sa.baseproject.utils.Constants;


/**
 * Base class for all the fragments used, manages common feature needed in the most of the fragments
 */
public abstract class AppFragment extends Fragment implements View.OnClickListener {

    protected abstract void initializeComponent(final View view);//to initialize the fragments components

    protected abstract void trackScreen();

    protected abstract void pageVisible();

    private long lastClickTime = 0;//To prevent double click

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        trackScreen();
        initializeComponent(view);
    }


    @Override
    public void onClick(View v) {
        /*
          Logic to Prevent the Launch of the Fragment Twice if User makes
          the Tap(Click) very Fast.
         */
        if (SystemClock.elapsedRealtime() - lastClickTime < Constants.MAX_CLICK_INTERVAL) {
            return;
        }
        lastClickTime = SystemClock.elapsedRealtime();
    }


    /**
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            pageVisible();
        }
    }


    public void switchData(final Object object){

    }
}
