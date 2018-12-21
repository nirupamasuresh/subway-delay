package com.predict.nirupama.predict.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.predict.nirupama.predict.R;

public class Utils {

    private static final String TAG = Utils.class.getSimpleName();
    /**
     * Shows a snackbar without action
     * @param baseView - base layout to be used
     * @param message - message to be displayed - DEFAULT = Something went wrong
     * @param actionText - Action text to be displayed - DEFAULT = RETRY
     * @param callBacks - Call back used to indicate the action click
     */
    public static void showSnackBarWithAction(@NonNull View baseView, String message, String actionText, @NonNull final SnackBarCallBacks callBacks, final int requestCode){
        Log.d(TAG, "Showing snackbar");
        if(message == null || message.isEmpty()){
            message = "Something went wrong";
        }
        if(actionText == null){
            actionText = "RETRY";
        }
        Snackbar snackbar = Snackbar.make(baseView, message, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(actionText, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBacks.onActionClicked(requestCode);
            }
        });
        snackbar.show();

    }
    public interface SnackBarCallBacks{
        void onActionClicked(int requestCode);
    }

    /**
     * This function is used to create a generic progress dialog to be used across the app
     * @param mContext
     * @return
     */
    public static ProgressDialog createProgressDialog(Context mContext) {
        ProgressDialog dialog = new ProgressDialog(mContext);
        try {
            dialog.show();
        } catch (WindowManager.BadTokenException e) {

        }
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.progress_dialog);
        // dialog.setMessage(Message);
        return dialog;
    }

    /**
     * Shows a snackbar without action
     * @param baseView - base layout to be used
     * @param message - message to be displayed
     */
    public static void showSnackBar(@NonNull View baseView, String message){
        Snackbar.make(baseView, message, Snackbar.LENGTH_SHORT).show();
    }
}
