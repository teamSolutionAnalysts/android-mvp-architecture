package com.sa.baseproject.appview.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sa.baseproject.R;
import com.sa.baseproject.base.AppActivity;
import com.sa.baseproject.base.AppFragment;
import com.sa.baseproject.base.AppFragmentState;


/**
 * Created by mavya.soni on 30/03/17.
 *
 */

public class FourFragment extends AppFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_4, container, false);
    }

    @Override
    public void initializeComponent(View view) {
        final Button btnNext = (Button) view.findViewById(R.id.fragment_4_btn_next);
        btnNext.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ((AppActivity) getActivity()).getAppFragmentManager().clearAllFragment();

                final Bundle bundle = new Bundle();
                bundle.putString("key", "NAME");
                ((AppActivity) getActivity()).getAppFragmentManager().passDataBetweenFragment(AppFragmentState.F_TWO, bundle);
            }
        });
    }

    @Override
    public void trackScreen() {

    }

    //If we are using viewpager we have to called webservice call in below method
    @Override
    protected void pageVisible() {
        Log.e("TAG", "FourFragment");
    }
}
