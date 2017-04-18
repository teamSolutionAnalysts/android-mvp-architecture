package com.sa.baseproject.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sa.baseproject.R;


public class DialogUtils {

    /**
     * Show toast
     *
     * @param context Application/Activity context
     * @param message Message which is display in toast.
     */
    public static void toast(Context context, String message) {
        if (context != null && !TextUtils.isEmpty(message)) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Show Default dialog.
     *
     * @param context Application/Activity Context for creating dialog.
     * @param title   Title of dialog
     * @param message Message of dialog
     */
    public static void dialog(Context context, String title, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(context.getString(android.R.string.ok), new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        if (!dialog.isShowing())
            dialog.show();
    }

    /**
     * Show default dialog
     *
     * @param context Application/Activity Context
     * @param message Message of dialog
     */
    public static void dialog(Context context, String message) {
        dialog(context, context.getString(R.string.app_name), message);
    }

    /**
     * Show Snack Bar
     *
     * @param context Application/Activity context
     * @param message Message which is display in toast.
     */
    public static void showSnackBar(Context context, String message) {
        if (context != null && !TextUtils.isEmpty(message)) {
            final Snackbar snackbar = Snackbar.make(((Activity) context).findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
            final View view = snackbar.getView();
            final FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
            final TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
            final Typeface font = Typeface.createFromAsset(context.getAssets(), FontUtils.AVENIR_REGULAR);
            tv.setTypeface(font);
            tv.setTextSize(16);
            tv.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
            view.setLayoutParams(params);
            snackbar.show();
        }
    }


}
