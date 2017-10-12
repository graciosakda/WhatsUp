package com.graciosakda.whatsup.Utilities;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.graciosakda.whatsup.Interfaces.AlertUtilsCallback;

/**
 * Created by graciosa.kda on 31/08/2017.
 */

public class AlertUtils {

    public static void showRetryCloseDialog(final Context appContext, String title, String message, final AlertUtilsCallback alertUtilsCallback, final int apidId){
        AlertDialog alert = new AlertDialog.Builder(appContext).create();
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setCancelable(false);
        alert.setButton(AlertDialog.BUTTON_NEUTRAL, "RETRY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int alertButtonId) {
                alertUtilsCallback.onRetry(apidId);
                dialog.dismiss();
            }
        });
        alert.setButton(AlertDialog.BUTTON_NEGATIVE, "CLOSE APP", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int alertButtonId) {
                alertUtilsCallback.onCloseApp(apidId);
            }
        });
        alert.show();
    }

    public static void showOKAppDialog(final Context appContext, String title, String message, final AlertUtilsCallback alertUtilsCallback, final int apiId){
        AlertDialog alert = new AlertDialog.Builder(appContext).create();
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setCancelable(false);
        alert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int alertButtonId) {
                alertUtilsCallback.onCloseApp(apiId);
            }
        });
        alert.show();
    }

    public static void showOKAppDialog(final Context appContext, String title, String message, final AlertUtilsCallback alertUtilsCallback){
        AlertDialog alert = new AlertDialog.Builder(appContext).create();
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setCancelable(false);
        alert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int alertButtonId) {
                alertUtilsCallback.onCloseApp();
            }
        });
        alert.show();
    }
}
