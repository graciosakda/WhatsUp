package com.graciosakda.whatsup.Api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.graciosakda.whatsup.Utilities.Constants;
import com.graciosakda.whatsup.Interfaces.RestApiCallback;
import com.graciosakda.whatsup.Utilities.AppSingleton;

import org.json.JSONObject;

/**
 * Created by graciosa.kda on 11/10/2017.
 */

public class LoginApi {
    private static Context appContext;
    private static String cancel_req_tag = "login";

    public LoginApi(Context appContext){
        this.appContext = appContext;
    }

    public static void loginUser(JSONObject params, final RestApiCallback restApiCallback){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                Constants.URL_LOGIN_USER, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        restApiCallback.onResponse(Constants.LOGIN_REST_API_ID, jsonObject);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                restApiCallback.onErrorResponse(Constants.LOGIN_REST_API_ID, volleyError);
            }
        });
        AppSingleton.getInstance(appContext).addToRequestQueue(jsonObjectRequest, cancel_req_tag);
    }
}
