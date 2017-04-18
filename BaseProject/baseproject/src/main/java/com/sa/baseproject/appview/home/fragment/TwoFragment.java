package com.sa.baseproject.appview.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sa.baseproject.R;
import com.sa.baseproject.base.AppActivity;
import com.sa.baseproject.base.AppFragment;
import com.sa.baseproject.base.AppFragmentState;
import com.sa.baseproject.utils.LocationUtils;


/**
 * Created by mavya.soni on 30/03/17.
 */

public class TwoFragment extends AppFragment {
    private Button btnNext;
    private LocationUtils locationUtils;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_2, container, false);
    }


    @Override
    public void initializeComponent(View view) {
        btnNext = (Button) view.findViewById(R.id.fragment_2_btn_next);
        final TextView tvLocation = (TextView) view.findViewById(R.id.fragment_2_tv_location);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ((AppActivity) getActivity()).getAppFragmentManager().addFragment(AppFragmentState.F_DETAILS, null, true);
                tvLocation.setText("Location = > Latitude : " + locationUtils.getLatitude() + " Longitude :  " + locationUtils.getLongitude());

            }
        });
        locationUtils = new LocationUtils(getActivity());
    }

    @Override
    public void trackScreen() {

    }


//    @Override
//    public void setBundle(Bundle bundle) {
//        if (bundle != null) {
//            Log.e("TAG", "ARGUMENTS  " + bundle.getString("key"));
//        }
//    }


    //If we are using viewpager we have to called webservice call in below method
    @Override
    protected void pageVisible() {
        Log.e("TAG", "TwoFragment");
    }

    public void switchData(final Object object) {
        if (object instanceof Bundle) {
            final Bundle bundle = (Bundle) object;
            Log.e("TAG", "ARGUMENTS  " + bundle.getString("key"));
            btnNext.setText(bundle.getString("key"));
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        locationUtils.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        locationUtils.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        locationUtils.onStop();
    }


}
