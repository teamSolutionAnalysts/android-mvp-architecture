package com.sa.baseproject.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sa on 06/04/17.
 */

public class PermissionUtils implements ActivityCompat.OnRequestPermissionsResultCallback {


    public final static String PERMISSION_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private Context context;
    private PermissionGranted permissionGranted;

    public PermissionUtils(final Context context) {
        this.context = context;
    }


    /**
     * Check the permission
     * Only pass the permission which you wants to check
     *
     * @param permission  Permission Name
     * @param requestCode Call Back for permission granted or not.
     * @return is permission granted or not.
     */
    public boolean checkPermission(final String permission, final int requestCode) {
        final int readPermissionCheck = ContextCompat.checkSelfPermission(context, permission);
        if (readPermissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{permission},
                        requestCode);
                return true;
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Add Multiple Permission in list and pass in the multi check permission dialog
     *
     * @param permissionList list of permission
     */
    public String[] checkPermissions(final ArrayList<String> permissionList) {
        List<String> arrPermission = new ArrayList<>();
        for (int i = 0; i < permissionList.size(); i++) {
            final String permission = permissionList.get(i);
            if (ContextCompat.checkSelfPermission(context,
                    permission) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                arrPermission.add(permission);
            }
        }
        String[] permissions = new String[arrPermission.size()];
        permissions = arrPermission.toArray(permissions);
        return permissions;
    }


    /**
     * Call where multiple permission require and only pass arraylist of permission.
     *
     * @param permissionList list of permission
     * @param requestCode    Call Back for permission granted or not.
     * @return is permission granted or not.
     */
    public boolean checkMultiplePermissions(final ArrayList<String> permissionList, final int requestCode) {
        String[] arrPermission = checkPermissions(permissionList);
        if (arrPermission.length > 0) {
            ActivityCompat.requestPermissions((Activity) context,
                    arrPermission,
                    requestCode);
            return true;
        }
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (permissionGranted != null) {
                        permissionGranted.onPermissionGranted(requestCode);
                    }
                } else {
                    DialogUtils.showSnackBar(context, "You are not able to pick photo from gallery");
                }
                break;

        }
    }


    /**
     * Set the interface where callback require.
     *
     * @param permissionGranted This is callback interface.
     */
    public void setPermissionGranted(final PermissionGranted permissionGranted) {
        this.permissionGranted = permissionGranted;
    }

    /**
     * This is callback interface.
     */
    public interface PermissionGranted {
        void onPermissionGranted(int requestCode);
    }


    public boolean checkPermission(final String permission){
        return ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED;
    }

}
