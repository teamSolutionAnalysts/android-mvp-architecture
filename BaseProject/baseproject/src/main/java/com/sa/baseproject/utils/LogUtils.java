package com.sa.baseproject.utils;

import android.util.Log;

import com.sa.baseproject.BuildConfig;


/**
 * Created by mavya.soni on 01/04/17.
 */

public class LogUtils {

    private static String TAG = "TAG";

    public static void setTag(String TAG) {
        LogUtils.TAG = TAG;
    }

    /**
     * Log.e()
     *
     * @param message which is display in logcat.
     */
    public static void e(String message) {
        if (isDebugMode())
            Log.e(LogUtils.TAG, message);
    }

    /**
     * Log.v()
     *
     * @param message which is display in logcat.
     */
    public static void v(String message) {
        if (isDebugMode())
            Log.v(LogUtils.TAG, message);
    }

    /**
     * Log.w()
     *
     * @param message which is display in logcat.
     */
    public static void w(String message) {
        if (isDebugMode())
            Log.w(LogUtils.TAG, message);
    }

    /**
     * Log.d()
     *
     * @param message which is display in logcat.
     */
    public static void d(String message) {
        if (isDebugMode())
            Log.d(LogUtils.TAG, message);
    }

    /**
     * Log.i()
     *
     * @param message which is display in logcat.
     */
    public static void i(String message) {
        if (isDebugMode())
            Log.i(LogUtils.TAG, message);
    }

    private static boolean isDebugMode() {
        return BuildConfig.DEBUG;
    }
}
