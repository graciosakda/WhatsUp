package com.graciosakda.whatsup.Interfaces;

/**
 * Created by graciosa.kda on 03/09/2017.
 */

public interface AlertUtilsCallback {
    void onRetry(int apiId);
    void onCloseApp(int apiId);
    void onCloseApp();
}
