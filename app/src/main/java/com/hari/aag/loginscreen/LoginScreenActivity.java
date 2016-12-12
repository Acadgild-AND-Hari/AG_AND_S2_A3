package com.hari.aag.loginscreen;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginScreenActivity extends AppCompatActivity
        implements View.OnClickListener {

    private static final String LOG_TAG = LoginScreenActivity.class.getSimpleName();
    private static final String PREFS_NAME = LoginScreenActivity.class.getSimpleName();

    private String emailStr, passwordStr;
    private boolean isLoggedInBool = false;

    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String LOGIN = "isLoggedInBool";

    @BindView(R.id.id_email) EditText emailET;
    @BindView(R.id.id_password) EditText passwordET;
    @BindView(R.id.id_login) Button logInBtn;
    @BindView(R.id.id_user_info) TextView signUpTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        ButterKnife.bind(this);

        logInBtn.setOnClickListener(this);
        signUpTV.setOnClickListener(this);

        Log.d(LOG_TAG, "Inside - onCreate");
        readValuesFromPrefs();
        updateValueToUI();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "Inside - onPause");
        saveValuesToPrefs();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.id_login:
                if (isLoggedInBool) {
                    emailStr = "";
                    passwordStr = "";
                } else {
                    String emailStr1 = emailET.getText().toString();
                    if (emailStr1.isEmpty()){
                        Toast.makeText(this, "Email is Empty!", Toast.LENGTH_SHORT).show();
                        Log.d(LOG_TAG, "Email is Empty!");
                        break;
                    }

                    String passwordStr1 = emailET.getText().toString();
                    if (passwordStr1.isEmpty()){
                        Toast.makeText(this, "Password is Empty!", Toast.LENGTH_SHORT).show();
                        Log.d(LOG_TAG, "Password is Empty!");
                        break;
                    }

                    if ((!emailStr.isEmpty() && emailStr.equals(emailStr1)) ||
                            (!passwordStr.isEmpty() && passwordStr.equals(passwordStr1))){
                        Log.d(LOG_TAG, "Login Info same. Save Skipped!");
                        break;
                    }

                    emailStr = emailStr1;
                    passwordStr = passwordStr1;
                }

                isLoggedInBool = !isLoggedInBool;
                saveValuesToPrefs();
                updateValueToUI();
                break;
            case R.id.id_user_info:
                if (isLoggedInBool){
                    Toast.makeText(this, "Hello!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "SignUp not implemented!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void updateValueToUI(){
        if (isLoggedInBool){
            emailET.setVisibility(View.INVISIBLE);
            passwordET.setVisibility(View.INVISIBLE);

            logInBtn.setText(R.string.str_logout);
            signUpTV.setText(R.string.str_hello);
        } else {
            emailET.setText(emailStr);
            passwordET.setText(passwordStr);

            emailET.setVisibility(View.VISIBLE);
            passwordET.setVisibility(View.VISIBLE);

            logInBtn.setText(R.string.str_login);
            signUpTV.setText(R.string.str_sign_up);
        }
    }

    private void readValuesFromPrefs(){
        SharedPreferences mySharedPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        emailStr = mySharedPrefs.getString(EMAIL, "");
        passwordStr = mySharedPrefs.getString(PASSWORD, "");
        isLoggedInBool = mySharedPrefs.getBoolean(LOG_TAG, false);

        Log.d(LOG_TAG, "Values Read from Prefs.");
        dumpPrefValues();
    }

    private void saveValuesToPrefs(){
        SharedPreferences.Editor prefsEditor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();

        prefsEditor.putString(EMAIL, emailStr);
        prefsEditor.putString(PASSWORD, passwordStr);
        prefsEditor.putBoolean(LOG_TAG, isLoggedInBool);
        prefsEditor.commit();

        Log.d(LOG_TAG, "Values Saved to Prefs.");
        dumpPrefValues();
    }

    private void dumpPrefValues(){
        Log.d(LOG_TAG, EMAIL + " - " + emailStr);
        Log.d(LOG_TAG, PASSWORD + " - " + passwordStr);
        Log.d(LOG_TAG, LOGIN + " - " + isLoggedInBool);
    }

}
