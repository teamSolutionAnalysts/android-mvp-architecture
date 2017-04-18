package com.sa.baseproject.base;


import com.sa.baseproject.appview.home.fragment.DetailsFragment;
import com.sa.baseproject.appview.home.fragment.FourFragment;
import com.sa.baseproject.appview.home.fragment.HomeFragment;
import com.sa.baseproject.appview.home.fragment.ThreeFragment;
import com.sa.baseproject.appview.home.fragment.TwoFragment;
import com.sa.baseproject.appview.login.OneFragment;
import com.sa.baseproject.appview.signup.SignUpFragment;

public enum AppFragmentState {
    F_ONE(OneFragment.class),
    F_TWO(TwoFragment.class),
    F_THREE(ThreeFragment.class),
    F_FOUR(FourFragment.class),
    F_HOME(HomeFragment.class),
    F_DETAILS(DetailsFragment.class),
    F_SIGNUP(SignUpFragment.class);

    private Class<? extends AppFragment> fragment;
    private int level = 1;

    AppFragmentState() {

    }

    AppFragmentState(Class<? extends AppFragment> fragmentClass) {
        this.fragment = fragmentClass;
    }

    public Class<? extends AppFragment> getFragment() {
        return this.fragment;
    }

    public int getLevel() {
        return this.level;
    }

    // To get AppFragmentState  enum from class name
    public static AppFragmentState getValue(Class value) {
        for (AppFragmentState e : AppFragmentState.values()) {
            if (e.getFragment() == value) {
                return e;
            }
        }
        return AppFragmentState.F_ONE;// not found
    }

}
