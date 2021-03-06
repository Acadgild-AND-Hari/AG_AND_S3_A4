package com.hari.aag.loginscreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginScreenActivity extends AppCompatActivity
        implements View.OnClickListener {

    private static final String LOG_TAG = LoginScreenActivity.class.getSimpleName();
    private static final String PREFS_NAME = LoginScreenActivity.class.getSimpleName();

    private String usernameStr;
    private String passwordStr;
    private boolean isLoggedInBool = false;

    @BindView(R.id.id_username) EditText usernameET;
    @BindView(R.id.id_password) EditText passwordET;
    @BindView(R.id.id_login) Button logInBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        ButterKnife.bind(this);

        logInBtn.setOnClickListener(this);

        Log.d(LOG_TAG, "Inside - onCreate");
        isLoggedInBool = Utility.readValuesFromPrefs(this);
        readValuesFromPrefs();
        updateValueToUI();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "Inside - onPause");
        Utility.saveValuesToPrefs(this, isLoggedInBool);
        saveValuesToPrefs();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "Inside - onResume");
        isLoggedInBool = Utility.readValuesFromPrefs(this);
        readValuesFromPrefs();
        updateValueToUI();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.id_login:
                if (!isLoggedInBool) {
                    String usernameStr1 = usernameET.getText().toString();
                    if (usernameStr1.isEmpty()){
                        Toast.makeText(this, "Username is Empty!", Toast.LENGTH_SHORT).show();
                        Log.d(LOG_TAG, "Username is Empty!");
                        break;
                    }

                    String passwordStr1 = passwordET.getText().toString();
                    if (passwordStr1.isEmpty()){
                        Toast.makeText(this, "Password is Empty!", Toast.LENGTH_SHORT).show();
                        Log.d(LOG_TAG, "Password is Empty!");
                        break;
                    }

                    isLoggedInBool = !isLoggedInBool;
                    usernameStr = usernameStr1;
                    passwordStr = passwordStr1;
                    Utility.saveValuesToPrefs(this, isLoggedInBool);
                    updateValueToUI();
                }
                break;
        }
    }

    private void updateValueToUI(){
        if (isLoggedInBool){
            Intent logoutIntent = new Intent(this, LogoutScreenActivity.class);
            if (usernameStr != null && !usernameStr.isEmpty())
                logoutIntent.putExtra(Utility.USER_NAME, usernameStr);
            startActivity(logoutIntent);
        } else {
            usernameET.setText(usernameStr);
            passwordET.setText(passwordStr);
        }
    }

    private void readValuesFromPrefs(){
        SharedPreferences mySharedPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        usernameStr = mySharedPrefs.getString(Utility.USER_NAME, "");
        passwordStr = mySharedPrefs.getString(Utility.PASSWORD, "");

        Log.d(LOG_TAG, "Values Read from Prefs.");
        dumpPrefValues();
    }

    private void saveValuesToPrefs(){
        SharedPreferences.Editor prefsEditor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();

        prefsEditor.putString(Utility.USER_NAME, usernameStr);
        prefsEditor.putString(Utility.PASSWORD, passwordStr);
        prefsEditor.commit();

        Log.d(LOG_TAG, "Values Saved to Prefs.");
        dumpPrefValues();
    }

    private void dumpPrefValues(){
        Log.d(LOG_TAG, Utility.USER_NAME + " - " + usernameStr);
        Log.d(LOG_TAG, Utility.PASSWORD + " - " + passwordStr);
    }

}