package com.graciosakda.whatsup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.NoConnectionError;
import com.android.volley.VolleyError;
import com.graciosakda.whatsup.Interfaces.AlertUtilsCallback;
import com.graciosakda.whatsup.Utilities.Constants;
import com.graciosakda.whatsup.Utilities.DataTypeUtils;
import com.graciosakda.whatsup.Api.LoginApi;
import com.graciosakda.whatsup.Interfaces.RestApiCallback;
import com.graciosakda.whatsup.Utilities.AlertUtils;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener, AlertUtilsCallback, RestApiCallback{

    private static final String LOG_TAG = LoginActivity.class.getSimpleName();
    private EditText usernameEditTxt, passwordEditTxt;
    private View progressBar;
    private Button loginBtn;
    private LoginApi loginApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeFields();
    }

    private void initializeFields(){
        usernameEditTxt= (EditText) findViewById(R.id.login_username);
        passwordEditTxt = (EditText) findViewById(R.id.login_password);
        loginBtn = (Button) findViewById(R.id.login_loginBtn);
        loginBtn.setOnClickListener(this);
        progressBar = findViewById(R.id.loginSpinner);
        loginApi = new LoginApi(getApplicationContext());
    }

    private void setProgressBarVisible(boolean visible){
        if (visible){
            progressBar.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }else {
            progressBar.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    @Override
    public void onClick(View view) {
        setProgressBarVisible(true);

        String username = usernameEditTxt.getText().toString();
        String password = passwordEditTxt.getText().toString();

        if(username == null || password == null){
            AlertUtils.showOKAppDialog(LoginActivity.this, Constants.APP_NAME, "Invalid Username/Password.", this);
        }else{
            Map<String, String> loginParams = new HashMap<>();
            loginParams.put("username", username);
            loginParams.put("password", password);
            loginApi.loginUser(DataTypeUtils.mapToJsonObject(loginParams), this);
        }
    }

    @Override
    public void onRetry(int apiId) {

    }

    @Override
    public void onCloseApp(int apiId) {

    }

    @Override
    public void onCloseApp() {
        setProgressBarVisible(false);
        usernameEditTxt.setFocusable(true);
    }

    @Override
    public void onResponse(int apiId, Object response) {
        Log.i(LOG_TAG, "ok "+apiId+" "+response);
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onErrorResponse(int apiId, VolleyError volleyError) {
        try{
            if (volleyError instanceof NoConnectionError) {
                AlertUtils.showOKAppDialog(LoginActivity.this, "ERROR", "Error in connection found.\nPlease check your", this);
            } else {
                Log.i(LOG_TAG, "--- onErrorResponse --- "+volleyError.networkResponse.statusCode);
                AlertUtils.showOKAppDialog(LoginActivity.this, Constants.APP_NAME, "Incorrect Username/Password..", this);
            }
        }catch (Exception e) {
            Log.i(LOG_TAG, "--- onErrorResponse --- "+e.getMessage());
            AlertUtils.showOKAppDialog(LoginActivity.this, Constants.APP_NAME, "Something is wrong!\nPlease try again later.", this);
        }
    }
}

