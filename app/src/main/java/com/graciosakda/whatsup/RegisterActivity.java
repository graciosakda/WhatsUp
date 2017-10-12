package com.graciosakda.whatsup;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.android.volley.NoConnectionError;
import com.android.volley.VolleyError;
import com.graciosakda.whatsup.Api.RegisterApi;
import com.graciosakda.whatsup.Interfaces.AlertUtilsCallback;
import com.graciosakda.whatsup.Utilities.AlertUtils;
import com.graciosakda.whatsup.Utilities.Constants;
import com.graciosakda.whatsup.Utilities.DataTypeUtils;
import com.graciosakda.whatsup.Utilities.Validation;
import com.graciosakda.whatsup.Interfaces.RestApiCallback;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import static android.graphics.Color.TRANSPARENT;


public class RegisterActivity extends AppCompatActivity implements View.OnFocusChangeListener,
                                                                    TextView.OnEditorActionListener, View.OnClickListener,
                                                                    AdapterView.OnItemClickListener, RestApiCallback,
        AlertUtilsCallback {

    private static final String LOG_TAG = RegisterActivity.class.getSimpleName();
    private Button registerBtn;
    private ProgressBar progressBar;
    private DatePickerDialog birthdatePickerDialog;
    private EditText usernameEditTxt, emailEditTxt, passwordEditTxt, retypePasswprdEditTxt,birthdateEditTxt;
    private AutoCompleteTextView provinceAutocompTextView, municityAutocompTextView;
    private RadioGroup genderRadioGrp;
    private ArrayAdapter provinceAdapter, municityAdapter;
    private String gender;
    private Map<Integer, String>municities, provinces;
    private Map<String, String> registerParams;
    private RegisterApi registerApi;

    @SuppressLint("NewApi")
    private Calendar calendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener birthdateListener = new DatePickerDialog.OnDateSetListener() {
        @SuppressLint("NewApi")
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String dateFormat = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            birthdateEditTxt.setText(sdf.format(calendar.getTime()));
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressBar = (ProgressBar) findViewById(R.id.registerSpinner);
        setProgressBarVisible(true);
        initializeFields();
    }

    @SuppressLint("NewApi")
    private void initializeFields(){
        registerApi = new RegisterApi(getApplicationContext());
        //username
        usernameEditTxt = (EditText) findViewById(R.id.usernameEditTxt);
        usernameEditTxt.setOnFocusChangeListener(this);
        //email
        emailEditTxt = (EditText) findViewById(R.id.emailEditTxt);
        emailEditTxt.setOnFocusChangeListener(this);
        //password
        passwordEditTxt = (EditText) findViewById(R.id.passwordEditTxt);
        passwordEditTxt.setOnFocusChangeListener(this);
        retypePasswprdEditTxt = (EditText) findViewById(R.id.retypePasswordEditTxt);
        retypePasswprdEditTxt.setOnFocusChangeListener(this);
        //birthday
        birthdateEditTxt = (EditText) findViewById(R.id.birthdayEditTxt);
        birthdatePickerDialog = new DatePickerDialog(RegisterActivity.this,
                android.R.style.Theme_Holo_Light_Dialog,
                birthdateListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        birthdatePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(TRANSPARENT));
        birthdateEditTxt.setOnClickListener(this);
        //gender
        genderRadioGrp = (RadioGroup) findViewById(R.id.genderRadioGrp);
        genderRadioGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                RadioButton genderBtn = (RadioButton)findViewById(checkedId);
                gender = genderBtn.getText().toString();
            }
        });
        gender = "Male";
        //province
        provinceAutocompTextView = (AutoCompleteTextView) findViewById(R.id.provinceAutocompTxtView);
        provinceAutocompTextView.setOnFocusChangeListener(this);
        provinceAutocompTextView.setOnEditorActionListener(this);
        provinceAutocompTextView.setOnItemClickListener(this);
        //municity
        municityAutocompTextView = (AutoCompleteTextView) findViewById(R.id.municityAutocompTxtView);
        municityAutocompTextView.setOnItemClickListener(this);
        municityAutocompTextView.setOnFocusChangeListener(this);
        municityAutocompTextView.setOnEditorActionListener(this);
        //register button
        registerBtn = (Button) findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(this);
        registerParams = new HashMap<>();

        registerApi.populateProvinceAutocompleteTextView(this);
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
    public void onFocusChange(View view, boolean hasFocus) {
        if(!hasFocus){
            switch (view.getId()) {
                case R.id.usernameEditTxt:
                    if (!Validation.isValidUsername(usernameEditTxt.getText().toString())) {
                        usernameEditTxt.setError("Username exists/invalid.");
                    } else {
                        registerApi.checkUsername(usernameEditTxt.getText().toString(), this);
                    }
                    break;

                case R.id.emailEditTxt:
                    if (!Validation.isValidEmail(emailEditTxt.getText().toString())) {
                        emailEditTxt.setError("Email exists/invalid.");
                    }else{
                        registerApi.checkEmail(emailEditTxt.getText().toString(), this);
                    }
                    break;

                case R.id.passwordEditTxt:
                    if (!Validation.isValidPassword(passwordEditTxt.getText().toString())) {
                        passwordEditTxt.setError("Invalid Password");
                    }
                    break;

                case R.id.retypePasswordEditTxt:
                    if (!Validation.isValidPassword2(passwordEditTxt.getText().toString(), retypePasswprdEditTxt.getText().toString())) {
                        retypePasswprdEditTxt.setError("Password did not match!");
                    }
                    break;

                case R.id.provinceAutocompTxtView:
                    if (provinces.containsValue(provinceAutocompTextView.getText().toString())) {
                        registerApi.populateMunicityAutocompleteTextView(DataTypeUtils.getHashMapKeyByValue(provinces, provinceAutocompTextView.getText().toString()), this);
                    } else {
                        provinceAutocompTextView.setError("Province does not exist.");
                    }
                    break;
            }
        }else{
            switch (view.getId()){
                case R.id.provinceAutocompTxtView:
                    break;
            }
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        switch(actionId){
            case EditorInfo.IME_ACTION_DONE:
                switch(textView.getId()){
                    case R.id.municityAutocompTxtView:
                        if(!municities.containsValue(municityAutocompTextView.getText().toString())){
                            municityAutocompTextView.setError("Municipality/City not found.");
                        }
                        break;
                }
                break;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.birthdayEditTxt:
                birthdatePickerDialog.show();
                break;
            case R.id.registerBtn:
                registerParams.put("username", usernameEditTxt.getText().toString());
                registerParams.put("email", emailEditTxt.getText().toString());
                registerParams.put("password", passwordEditTxt.getText().toString());
                registerParams.put("birthdate", birthdateEditTxt.getText().toString());
                registerParams.put("gender", gender);
                registerParams.put("provinceId", Integer.toString(DataTypeUtils.getHashMapKeyByValue(provinces, provinceAutocompTextView.getText().toString())));
                registerParams.put("municityId", Integer.toString(DataTypeUtils.getHashMapKeyByValue(municities, municityAutocompTextView.getText().toString())));
                registerApi.registerUser(DataTypeUtils.mapToJsonObject(registerParams), this);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        if (adapterView.getAdapter().equals(provinceAutocompTextView.getAdapter()) ){
            registerApi.populateMunicityAutocompleteTextView(DataTypeUtils.getHashMapKeyByValue(provinces, adapterView.getItemAtPosition(position).toString()), this);
        }
    }

    @Override
    public void onResponse(int apiId, Object response) {
        try {
            if(response.equals("Success")){
                Log.i(LOG_TAG,"--- onResponse --- "+ response.toString());
            }else{
                switch (apiId) {
                    case Constants.PROVINCE_REST_API_ID:
                        provinces = DataTypeUtils.objectJsonArrayToMap(response);
                        provinceAdapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, provinces.values().toArray(new String[0]));
                        provinceAutocompTextView.setAdapter(provinceAdapter);
                        setProgressBarVisible(false);
                        break;

                    case Constants.MUNICITY_REST_API_ID:
                        municities = DataTypeUtils.objectJsonArrayToMap(response);
                        municityAdapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, municities.values().toArray(new String[0]));
                        municityAutocompTextView.setAdapter(municityAdapter);
                        break;

                    case Constants.REGISTER_REST_API_ID:
                        JSONObject responseJson = (JSONObject)response;
                        if(responseJson.getString("response").equals("Success")){
                            AlertUtils.showOKAppDialog(RegisterActivity.this, Constants.APP_NAME, "Registered successfully!\nPlease login your details", this, apiId);
                        }
                        break;
                }
            }
        }catch (Exception e){
            Log.i(LOG_TAG, "--- onResponse --- "+e.getMessage());
            AlertUtils.showOKAppDialog(RegisterActivity.this, Constants.APP_NAME, "Something is wrong!\nPlease try again later.", this, apiId);

        }
    }

    @Override
    public void onErrorResponse(int apiId, VolleyError volleyError) {
        try {
            if (volleyError instanceof NoConnectionError) {
                AlertUtils.showRetryCloseDialog(RegisterActivity.this, "ERROR", "Error in connection found.\nPlease check your", this, apiId);
            } else {
                Log.i(LOG_TAG, "--- onErrorResponse --- "+volleyError.networkResponse.statusCode);
                switch (volleyError.networkResponse.statusCode) {
                    case Constants.CONFLICT_STATUS_CODE:
                        switch (apiId) {
                            case Constants.USERNAME_REST_API_ID:
                                usernameEditTxt.setError("Username exists/invalid.");
                                break;
                            case Constants.EMAIL_REST_API_ID:
                                emailEditTxt.setError("Email exists/invalid");
                                break;
                        }
                        break;
                }
            }
        }catch (Exception e){
            Log.i(LOG_TAG, "--- onErrorResponse --- "+e.getMessage());
            AlertUtils.showOKAppDialog(RegisterActivity.this, Constants.APP_NAME, "Something is wrong!\nPlease try again later.", this, apiId);
        }
    }

    @Override
    public void onRetry(int appId) {
        switch (appId){
            case Constants.USERNAME_REST_API_ID:
                registerApi.checkUsername(usernameEditTxt.getText().toString(), this);
                break;
            case Constants.EMAIL_REST_API_ID:
                registerApi.checkEmail(emailEditTxt.getText().toString(), this);
                break;
            case Constants.PROVINCE_REST_API_ID:
                registerApi.populateProvinceAutocompleteTextView(this);
                break;
            case Constants.MUNICITY_REST_API_ID:
                registerApi.populateMunicityAutocompleteTextView(DataTypeUtils.getHashMapKeyByValue(provinces, provinceAutocompTextView.getText().toString()), this);
                break;
            case Constants.REGISTER_REST_API_ID:
                registerApi.registerUser(DataTypeUtils.mapToJsonObject(registerParams), this);
                break;
        }
    }

    @Override
    public void onCloseApp(int apiId) {
        switch (apiId) {
            case Constants.REGISTER_REST_API_ID:
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                this.finish();
                break;

            default:
                System.exit(0);
                break;
        }
    }

    @Override
    public void onCloseApp() {
        setProgressBarVisible(false);
    }
}
