package com.sa.baseproject.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.sa.baseproject.R;


/**
 * Created by chintan.desai on 28/06/2016.
 *
 *
 */

public class AlertDialogUtils {

    private static AlertDialog alertDialog;

    public static void showErrorDialog(Context context, String message) {
        if (alertDialog != null && alertDialog.isShowing())
            return;
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.app_name));
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
    }

    public static boolean isShowing() {
        if (alertDialog != null && alertDialog.isShowing()) {
            return true;
        } else {
            return false;
        }
    }

    public static void dismiss() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
            alertDialog = null;
        }
    }
}
