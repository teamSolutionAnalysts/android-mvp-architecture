package com.sa.baseproject.utils;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.os.StatFs;

import java.text.DecimalFormat;


public class SDCardUtils {



    /**
     * Get the free space available on the device in SD Card.
     *
     * @return long- returns free space available on device SD Card.
     */
    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    public static long getFreeSpace() {
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long blockSize = 0;
        long availableBlocks = 0;
        if (VersionUtils.isPreJellyBeanMR2()) {
            blockSize = stat.getBlockSize();
            availableBlocks = stat.getAvailableBlocks();
        } else {
            blockSize = stat.getBlockSizeLong();
            availableBlocks = stat.getAvailableBlocksLong();
        }
        return availableBlocks * blockSize;
    }


    /**
     * Check if the SD Card is writable.
     *
     * @return boolean -true if the SD Card is writable ,else false if it is not.
     */
    public static boolean isSDCardWritable() {

        boolean mExternalStorageWriteable = false;

        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // We can read and write the media
            mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // We can only read the media

            mExternalStorageWriteable = false;
        } else {
            // Something else is wrong. It may be one of many other states,
            // but all we need
            // to know is we can neither read nor write
            mExternalStorageWriteable = false;
        }

        return mExternalStorageWriteable;

    }


    public static final String TAG = "FileUtil";
    public static final int BUFFER_SIZE = 1024;
    private static final int SDCARD_MIN_SIZE = 50;

    /**
     * @return true SDSDCARD_MIN_SIZE，false
     */
    public static boolean isSDCard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)
                && SDCARD_MIN_SIZE <= getFreeSpace()) {
            return true;
        } else {
            return false;
        }
    }


    private static DecimalFormat dFormater = new DecimalFormat("@##");

    public static boolean hasExternalMemory() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static long getTotalInternalMemory() {
        return getTotalMemory(Environment.getDataDirectory().getPath());
    }

    public static long getTotalExternalMemory() {
        if (hasExternalMemory()) {
            return getTotalMemory(Environment.getExternalStorageDirectory().getPath());
        } else {
            return 0;
        }

    }

    public static long getAvailableInternalMemory() {
        return getAvailableMemory(Environment.getDataDirectory().getPath());
    }

    public static long getAvailableExternalMemory() {
        if (hasExternalMemory()) {
            return getAvailableMemory(Environment.getExternalStorageDirectory().getPath());
        } else {
            return 0;
        }
    }

    @SuppressLint("NewApi")
    public static long getAvailableMemory(String path) {
        long size, blockSize;
        StatFs  fileStatus = new StatFs(path);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = fileStatus.getBlockSizeLong();
            size = fileStatus.getAvailableBlocksLong();
        } else {
            blockSize = fileStatus.getBlockSize();
            size = fileStatus.getAvailableBlocks();
        }

        size *= blockSize;
        return size;
    }

    @SuppressLint("NewApi")
    public static long getTotalMemory(String path) {
        long size, blockSize;
        StatFs  fileStatus = new StatFs(path);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = fileStatus.getBlockSizeLong();
            size = fileStatus.getBlockCountLong();
        } else {
            blockSize = fileStatus.getBlockSize();
            size = fileStatus.getBlockCount();
        }
        size *= blockSize;
        return size;

    }

    public static String formatMemorySize(long size1) {

        String toRet;
        String suffix = "b";
        double dSize = size1;
        if (dSize >= 1024.0) {
            dSize /= 1024.0;
            suffix = "KB";

            if (dSize >= 1024.0) {
                dSize /= 1024.0;
                suffix = "MB";

                if (dSize >= 1024.0) {
                    dSize /= 1024.0;
                    suffix = "GB";
                }
            }
        }
        toRet = dFormater.format(dSize) + " " + suffix;
        return toRet;
    }


    public static String getInternalMemoryStatus() {
        String toRet = formatMemorySize(getAvailableInternalMemory()) + "/" +
                formatMemorySize(getTotalInternalMemory());

        return toRet;
    }

    public static String getExternalMemoryStatus() {
        String toRet = formatMemorySize(getAvailableExternalMemory()) + "/" +
                formatMemorySize(getTotalExternalMemory());
        return toRet;
    }

    public static long getUsedSpace(String path) {
        long size = getTotalMemory(path) - getAvailableMemory(path);
        return size;
    }

    public static String getFormatedUsedSpace(String path) {
        return formatMemorySize(getUsedSpace(path));
    }


}
