package com.sa.baseproject.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;

import com.sa.baseproject.R;


/**
 * Created by altafhussain.shaikh on 4/24/2016.
 *
 */
public class ProgressUtils {

    private static Context context = null;
    private static ProgressUtils instance = null;
    private static ProgressDialog progressDialog = null;

    public ProgressUtils() {
    }

    public static ProgressUtils getInstance(Context ctx) {

        context = ctx;

        if (instance == null) {
            instance = new ProgressUtils();
        }

        return instance;
    }

    public boolean isShowing() {
        return progressDialog != null;
    }

    public void show() {
        if (progressDialog == null || !progressDialog.isShowing()) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setIndeterminate(true);
            progressDialog.setCanceledOnTouchOutside(false);
//            progressDialog.setMessage(context.getString(R.string.please_wait));
            progressDialog.setProgressStyle(android.R.attr.progressBarStyleSmall);
            progressDialog.show();
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            progressDialog.setContentView(R.layout.layout_progressdialog);
        }
    }

    public void forceShow() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(true);
        progressDialog.setIndeterminate(true);
//        progressDialog.setTitle(R.string.validating);
        progressDialog.setMessage(context.getString(R.string.please_wait));
        progressDialog.show();
    }

    public void close() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            progressDialog = null;
        }
    }


}
