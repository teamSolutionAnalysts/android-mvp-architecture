package com.sa.baseproject.appview.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sa.baseproject.R;
import com.sa.baseproject.base.AppFragment;


/**
 * Created by mavya.soni on 05/04/17.
 */

public class DetailsFragment extends AppFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    protected void initializeComponent(View view) {
    }

    @Override
    protected void trackScreen() {

    }

    @Override
    protected void pageVisible() {

    }

}
