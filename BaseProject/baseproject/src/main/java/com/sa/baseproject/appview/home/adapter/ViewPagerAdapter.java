package com.sa.baseproject.appview.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sa.baseproject.appview.home.fragment.FourFragment;
import com.sa.baseproject.appview.home.fragment.ThreeFragment;
import com.sa.baseproject.appview.home.fragment.TwoFragment;


/**
 * Created by mavya.soni on 03/04/17.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {


    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new TwoFragment();
        } else if (position == 1) {
            return new ThreeFragment();
        } else {
            return new FourFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
