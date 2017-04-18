package com.sa.baseproject.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.sa.baseproject.R;


public class PreferenceUtils {

    private SharedPreferences sharedPreferences;
//    public static final String PREF_INTRO_SCREEN = "PREF_INTRO_SCREEN";


    public PreferenceUtils(Context mContext) {
        sharedPreferences = mContext.getSharedPreferences(mContext.getString(R.string.app_name), Context.MODE_PRIVATE);
    }

    /**
     * Getting the string from shared preference
     *
     * @param key
     * @return string
     */
    public String getString(final String key) {
        return sharedPreferences.getString(key, "");
    }

    /**
     * Putting the string values in shared preference
     *
     * @param key
     * @param value
     */
    public void putString(final String key, final String value) {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * Getting the int from shared preference
     *
     * @param key
     * @return string
     */
    public int getInt(final String key) {
        return sharedPreferences.getInt(key, -1);
    }

    /**
     * Putting the integer values in shared preference
     *
     * @param key
     * @param value
     */
    public void putInt(final String key, final int value) {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * Getting the boolean from shared preference
     *
     * @param key
     * @return string
     */
    public boolean getBoolean(final String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    /**
     * Putting the boolean values in shared preference
     *
     * @param key
     * @param value
     */
    public void putBoolean(final String key, final boolean value) {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * Clearing shared preference
     */
    public void clearPreference() {
        if (sharedPreferences != null) {
            sharedPreferences.edit().clear().apply();
        }
    }


}
