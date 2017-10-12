package com.graciosakda.whatsup.Interfaces;

import com.android.volley.VolleyError;


/**
 * Created by graciosa.kda on 30/08/2017.
 */

public interface RestApiCallback {
    void onResponse(int apiId, Object response);
    void onErrorResponse(int apiId, VolleyError volleyError);
}
