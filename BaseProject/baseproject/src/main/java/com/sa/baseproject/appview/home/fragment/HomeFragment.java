package com.sa.baseproject.appview.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sa.baseproject.R;
import com.sa.baseproject.appview.home.adapter.ViewPagerAdapter;
import com.sa.baseproject.base.AppFragment;


/**
 * Created by mavya.soni on 03/04/17.
 */

public class HomeFragment extends AppFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void initializeComponent(View view) {
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.fragment_home_vp);
        final TabLayout tabLayout = (TabLayout) view.findViewById(R.id.fragment_home_tb);
        final ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void trackScreen() {

    }

    @Override
    protected void pageVisible() {

    }
}
