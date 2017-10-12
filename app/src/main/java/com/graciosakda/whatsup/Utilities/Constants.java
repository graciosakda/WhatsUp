package com.graciosakda.whatsup.Utilities;

/**
 * Created by graciosa.kda on 28/08/2017.
 */

public class Constants {
    public static final String APP_NAME = "App Here!";

    public static final String HOST = "http://192.168.1.3:8080";
    public static final String URL_CHECK_FOR_USERNAME = HOST + "/users/username";
    public static final String URL_CHECK_FOR_EMAIL = HOST + "/users/email";
    public static final String URL_GET_PROVINCES = HOST + "/provinces/province";
    public static final String URL_GET_MUNICITIES = HOST + "/municities/municity";
    public static final String URL_REGISTER_USER = HOST + "/users/register";
    public static final String URL_LOGIN_USER = HOST + "/users/login";

    /*
     * Rest API custom id for RestApiCallback inteface
     * RegisterAPI = 1
     */

    public static final int USERNAME_REST_API_ID = 1001;
    public static final int EMAIL_REST_API_ID = 1002;
    public static final int PROVINCE_REST_API_ID = 1003;
    public static final int MUNICITY_REST_API_ID = 1004;
    public static final int REGISTER_REST_API_ID = 1005;
    public static final int LOGIN_REST_API_ID = 2001;

    //status codes
    public static final int OK_STATUS_CODE = 200;
    public static final int CONFLICT_STATUS_CODE = 409;


}
