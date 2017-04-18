package com.sa.baseproject.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


import com.sa.baseproject.R;
import com.sa.baseproject.appview.home.fragment.FourFragment;
import com.sa.baseproject.appview.home.fragment.TwoFragment;

import java.util.Stack;

// Handling of fragment switch , adding fragment to stack or removing fragment from stack, setting top bar data

public class AppFragmentManager<T> {

    private final String TAG = "SwitchFragment";
    private final AppCompatActivity mainActivity;
    private int containerId;
    private FragmentManager fragmentManager;
    private FragmentTransaction ft;

    private Stack<Fragment> fragmentStack = new Stack<>();

    public AppFragmentManager(AppCompatActivity mainActivity, int containerId) {
        this.mainActivity = mainActivity;
        this.containerId = containerId;
        fragmentManager = mainActivity.getSupportFragmentManager();
    }

    // Common Handling of top bar for all fragments like header name, icon on top bar in case of moving to other fragment and coming back again
    private <T> void setUp(AppFragmentState currentState, T keys) {

        switch (currentState) {
            case F_ONE:
                mainActivity.getSupportActionBar().setTitle(R.string.one);
                mainActivity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white);
                break;
            case F_TWO:
                mainActivity.getSupportActionBar().setTitle(R.string.two);
                mainActivity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white);
                break;
            case F_THREE:
                mainActivity.getSupportActionBar().setTitle(R.string.three);
                mainActivity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white);
                break;
            case F_FOUR:
                mainActivity.getSupportActionBar().setTitle(R.string.four);
                mainActivity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white);
                break;
            case F_HOME:
                mainActivity.getSupportActionBar().setTitle(R.string.title_home);
                mainActivity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white);
                break;
            case F_DETAILS:
                mainActivity.getSupportActionBar().setTitle(R.string.details_screen);
                mainActivity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
                break;


        }


    }

    // called when fragment backpressed
    void notifyFragment(final boolean isAnimation) {
        if (fragmentStack.size() > 1) {
            popFragment(isAnimation);
        } else {
            this.mainActivity.finish();
        }
    }

    // Call For Fragment Switch
    private <T> void addFragmentInStack(AppFragmentState fragmentEnum, T keys, final boolean isAnimation) {
        ft = fragmentManager.beginTransaction();
        if (isAnimation) {
            ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        }
        final Fragment fragment = Fragment.instantiate(this.mainActivity, fragmentEnum.getFragment().getName());
        if (keys != null && keys instanceof Bundle) {
            fragment.setArguments((Bundle) keys);
        }
        ft.add(containerId, fragment, fragmentEnum.getFragment().getName());
        if (!fragmentStack.isEmpty()) {
            fragmentStack.lastElement().onPause();
            ft.hide(fragmentStack.lastElement());
        }
        fragmentStack.push(fragment);
        ft.commit();
        setUp(fragmentEnum, keys);
    }

    // When to resume last fragment and to pop only one fragment
    public void popFragment(boolean isAnimation) {
        ft = fragmentManager.beginTransaction();
        if (isAnimation) {
            ft.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        }
        fragmentStack.lastElement().onPause();
        ft.remove(fragmentStack.pop());
        fragmentStack.lastElement().onResume();
        ft.show(fragmentStack.lastElement());
        ft.commit();
        setUp(AppFragmentState.getValue(fragmentStack.lastElement().getClass()), null);
    }


    // When not to resume last fragment
    public <T> void popFragment(int numberOfFragment, final AppFragmentState appFragmentState, final T bundle) {
        ft = fragmentManager.beginTransaction();
        ft.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        for (int i = 0; i < numberOfFragment; i++) {
            if (!fragmentStack.isEmpty()) {
                fragmentStack.lastElement().onPause();
                ft.remove(fragmentStack.pop());
            }
        }
        final Fragment fragment = fragmentStack.lastElement();
        passDataBetweenFragment(appFragmentState, bundle);
        if (!fragmentStack.isEmpty())
            ft.show(fragment);
        ft.commit();
        setUp(AppFragmentState.getValue(fragmentStack.lastElement().getClass()), null);
    }

    // To bring already fragment in stack to top
    public <T> void moveFragmentToTop(AppFragmentState appFragmentState, final T object, final boolean isAnimation) {
        ft = fragmentManager.beginTransaction();
        if (isAnimation) {
            ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        }
        final Fragment fragment = getFragment(appFragmentState);
        passDataBetweenFragment(appFragmentState, object);
        final int position = fragmentStack.indexOf(fragment);
        if (position > -1) {
            fragmentStack.remove(position);
            fragmentStack.lastElement().onPause();
            ft.hide(fragmentStack.lastElement());
            fragmentStack.push(fragment);
            if (!fragmentStack.isEmpty()) {
                fragmentStack.lastElement().onResume();
                ft.show(fragmentStack.lastElement());
            }
            ft.commit();
        }
        setUp(appFragmentState, null);
    }

    public Stack<Fragment> getStack() {
        return fragmentStack;
    }

    public <T> void passDataBetweenFragment(final AppFragmentState appFragmentState, final T bundle) {
        final AppFragment fragment = (AppFragment) getFragment(appFragmentState);
        if (fragment != null) {
            fragment.switchData(bundle);
        }
//        switch (appFragmentState) {
//            case F_ONE:
//                break;
//            case F_TWO:
//                if (fragment instanceof TwoFragment) {
//                    ((TwoFragment) fragment).onPassingBundle(bundle);
//                }
//                break;
//            case F_THREE:
//                break;
//            case F_FOUR:
//                if (fragment instanceof FourFragment) {
//                    ((FourFragment) fragment).onPassingBundle(bundle);
//                }
//                break;
//        }
    }


    private Fragment getFragment(final AppFragmentState appFragmentState) {
        return fragmentManager.findFragmentByTag(appFragmentState.getFragment().getName());
    }

    public <T> void addFragment(final AppFragmentState fragmentEnum, T keys, final boolean isAnimation) {
        final Fragment availableFragment = getFragment(fragmentEnum);
        if (availableFragment != null) {
            moveFragmentToTop(fragmentEnum, keys, isAnimation);
        } else {
            addFragmentInStack(fragmentEnum, keys, isAnimation);
        }
    }

}
