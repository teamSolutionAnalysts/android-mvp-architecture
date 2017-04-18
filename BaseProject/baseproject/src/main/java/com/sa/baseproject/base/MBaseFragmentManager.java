package com.sa.baseproject.base;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

/**
 * Created by mavya.soni on 30/03/17.
 */

public class MBaseFragmentManager {

    private AppCompatActivity appCompatActivity;

    public MBaseFragmentManager(final AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
    }

    /**
     * Replace fragment into desire fragment container layout
     *
     * @param container    fragment container resource  id
     * @param nextFragment the fragment which now we want to replace fragment in same container
     * @throws IllegalStateException throws in case of transaction after activity saved its state
     */
    public void replaceFragment(final int container, final Fragment nextFragment) throws IllegalStateException {
        if (nextFragment == null) {
            return;
        }
        appCompatActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(container, nextFragment, nextFragment.getClass().getSimpleName())
                .commit();
    }

    /**
     * Replace fragment into desire fragment container layout
     *
     * @param container    fragment container resource  id
     * @param nextFragment the fragment which now we want to add above current fragment in same container
     * @throws IllegalStateException throws in case of transaction after activity saved its state
     */
    public void addFragment(final int container, final Fragment nextFragment) throws IllegalStateException {

        if (nextFragment == null) {
            return;
        }
        final Fragment hideFragment = appCompatActivity.getSupportFragmentManager().findFragmentById(container);
        appCompatActivity.getSupportFragmentManager()
                .beginTransaction()
                .add(container, nextFragment, nextFragment.getClass().getSimpleName())
                .hide(hideFragment)
                .addToBackStack(hideFragment.getClass().getSimpleName())
                .commit();

    }

    public void showFragment(final String fragmentName) {
        final FragmentManager supportFragmentManager = appCompatActivity.getSupportFragmentManager();
        for (int entry = 0; entry < supportFragmentManager.getBackStackEntryCount(); entry++) {
            final String tag = supportFragmentManager.getBackStackEntryAt(entry).getName();
            if (!TextUtils.isEmpty(tag) && !tag.equalsIgnoreCase(fragmentName)) {
                final Fragment showFragment = appCompatActivity.getSupportFragmentManager().findFragmentByTag(tag);
                if (showFragment != null) {
                    showFragment.setUserVisibleHint(false);
                }
            }
        }
        appCompatActivity.getSupportFragmentManager().popBackStack(fragmentName, FragmentManager.POP_BACK_STACK_INCLUSIVE);

    }

    public void clearAllFragment() {
        final FragmentManager supportFragmentManager = appCompatActivity.getSupportFragmentManager();
        for (int entry = 0; entry < supportFragmentManager.getBackStackEntryCount(); entry++) {
            final String tag = supportFragmentManager.getBackStackEntryAt(entry).getName();
            final Fragment showFragment = appCompatActivity.getSupportFragmentManager().findFragmentByTag(tag);
            if (showFragment != null && entry != 0) {
                showFragment.setUserVisibleHint(false);
            }
        }
        appCompatActivity.getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }


    /**
     * Show dialogFragment above fragment
     *
     * @param dialogFragment the dialog fragment which now we want to show.
     * @throws IllegalStateException throws in case of transaction after activity saved its state
     */
    public void showDialogFragment(final DialogFragment dialogFragment) throws IllegalStateException {
        if (dialogFragment == null) {
            return;
        }
        dialogFragment.show(appCompatActivity.getSupportFragmentManager(), dialogFragment.getClass().getSimpleName());
    }


}
