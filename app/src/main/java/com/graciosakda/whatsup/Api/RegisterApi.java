package com.graciosakda.whatsup.Api;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.graciosakda.whatsup.Utilities.Constants;
import com.graciosakda.whatsup.Interfaces.RestApiCallback;
import com.graciosakda.whatsup.Utilities.AppSingleton;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by graciosa.kda on 28/08/2017.
 */

public class RegisterApi {
    private static Context appContext;
    private static String cancel_req_tag = "register";
    public static final String URL_TEST = Constants.HOST +"/users/test";

    public RegisterApi(Context appContext){
        this.appContext = appContext;
    }

    public static void checkUsername(final String username, final RestApiCallback restApiCallback){
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Constants.URL_CHECK_FOR_USERNAME+ "?username="+username,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        restApiCallback.onResponse(Constants.USERNAME_REST_API_ID, response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        restApiCallback.onErrorResponse(Constants.USERNAME_REST_API_ID, volleyError);
                    }
                });

        AppSingleton.getInstance(appContext).addToRequestQueue(stringRequest, cancel_req_tag);
    }

    public static void checkEmail(final String email, final RestApiCallback restApiCallback){
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Constants.URL_CHECK_FOR_EMAIL + "?email=" + email,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        restApiCallback.onResponse(Constants.EMAIL_REST_API_ID, response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        restApiCallback.onErrorResponse(Constants.EMAIL_REST_API_ID, volleyError);
                    }
                });
        AppSingleton.getInstance(appContext).addToRequestQueue(stringRequest, cancel_req_tag);
    }

    public static void populateProvinceAutocompleteTextView(final RestApiCallback restApiCallback){
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(Request.Method.GET,
                Constants.URL_GET_PROVINCES, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        Log.i("JSON", jsonArray.toString());
                        restApiCallback.onResponse(Constants.PROVINCE_REST_API_ID, jsonArray);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        restApiCallback.onErrorResponse(Constants.PROVINCE_REST_API_ID, volleyError);
                    }
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(3000, 5, 5));
        AppSingleton.getInstance(appContext).addToRequestQueue(jsonArrayRequest, cancel_req_tag);

    }

    public static void populateMunicityAutocompleteTextView(final int provinceId, final RestApiCallback restApiCallback){
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(Request.Method.GET,
                Constants.URL_GET_MUNICITIES+"?id="+provinceId, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        Log.i("JSON", jsonArray.toString());
                        restApiCallback.onResponse(Constants.MUNICITY_REST_API_ID, jsonArray);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                restApiCallback.onErrorResponse(Constants.MUNICITY_REST_API_ID, volleyError);
            }
        });

        AppSingleton.getInstance(appContext).addToRequestQueue(jsonArrayRequest, cancel_req_tag);

    }

    public static void registerUser(JSONObject params, final RestApiCallback restApiCallback){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                Constants.URL_REGISTER_USER, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        restApiCallback.onResponse(Constants.REGISTER_REST_API_ID, jsonObject);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        restApiCallback.onErrorResponse(Constants.REGISTER_REST_API_ID, volleyError);
                    }
        });
        AppSingleton.getInstance(appContext).addToRequestQueue(jsonObjectRequest, cancel_req_tag);
    }

    public static void test(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_TEST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("RESPONSE", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.i("RESPONSE", "error"+volleyError);
                    }
                }){
        };

        AppSingleton.getInstance(appContext).addToRequestQueue(stringRequest, cancel_req_tag);

    }

}
