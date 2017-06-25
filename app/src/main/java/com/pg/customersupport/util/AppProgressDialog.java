package com.pg.customersupport.util;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Utility to have a common application progress dialog
 * Attach to an activity and show/hide when required
 *
 * @author PG
 * @version 1.0
 * @see AppProgressDialog
 */
public class AppProgressDialog {
    private static ProgressDialog mProgressDialog;

    /**
     * Show progress dialog attached to the context of an activity
     *
     * @param context The context of the application
     * @param message The message to display on the progress dialog
     */
    public static void showProgressDialog(Context context, String message) {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage(message);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    /**
     * Hide progress dialog which is attached to an activity
     */
    public static void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }
}
