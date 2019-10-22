package com.conestoga.whereismyfood.utils;

import android.app.ProgressDialog;
import android.content.Context;

import androidx.annotation.Nullable;

/**
 * {@link ProgressDialogUtil} : constant progress dialog class used to show progress dialog in simpler way,
 * call method:
 * <i>showProgress(Context, String, String, boolean)</i> to show progress,
 * <i>dismissProgress() to dismiss it.</i>
 *
 * @author Harsh Patel
 * @Date : 10/21/2019
 * @see #showProgress(Context, String, String, boolean)
 * @see #dismissProgress()
 * @see ProgressDialog
 */

public class ProgressDialogUtil {
    /**
     * The constant progressDialog.
     */
    public static ProgressDialog progressDialog;

    private ProgressDialogUtil() {
    }

    /**
     * singleton method used to retrieve {@link ProgressDialog} instance.
     *
     * @param context : param of {@link Context} type
     * @return the instance
     * @Date : 10/21/2019
     * @author Harsh Patel
     * @see ProgressDialogUtil
     */
    public static ProgressDialog getInstance(Context context) {
        if (progressDialog != null) {
            synchronized (ProgressDialog.class) {
                return progressDialog;
            }
        }
        return progressDialog = new ProgressDialog(context);
    }

    /**
     * static method used to show progress using {@link ProgressDialog}.
     *
     * @param context    : param of {@link Context} type
     * @param title      : param of {@link String} type used for {@link ProgressDialog} title can be {@link Nullable}
     * @param message    : param of {@link String} type used for {@link ProgressDialog} message can be {@link Nullable}
     * @param cancelable : param of boolean used as cancelable flag to {@link ProgressDialog}
     * @Date : 10/21/2019
     * @author Harsh Patel
     * @see ProgressDialogUtil
     */
    public static void showProgress(Context context, @Nullable String title, @Nullable String message, boolean cancelable) {
        try {
            getInstance(context);
            if (title != null && title.equals("")) {
                progressDialog.setTitle("Loading...");
            } else {
                progressDialog.setTitle(title);
            }
            if (message != null && message.equals("")) {
                progressDialog.setMessage("Please Wait");
            } else {
                progressDialog.setMessage(message);
            }
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(cancelable);
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * static method to dismiss progress.
     *
     * @Date : 10/21/2019
     * @author Harsh Patel
     * @see ProgressDialogUtil
     */
    public static void dismissProgress() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        progressDialog = null;
    }
}
