package com.hari.aag.loginscreen;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogoutScreenActivity extends AppCompatActivity
        implements View.OnClickListener{

    private static final String LOG_TAG = LogoutScreenActivity.class.getSimpleName();
    private static final String PREFS_NAME = LogoutScreenActivity.class.getSimpleName();

    private String usernameStr;
    private boolean isLoggedInBool = false;

    @BindView(R.id.id_user_info) TextView userInfoTV;
    @BindView(R.id.id_logout) Button logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout_screen);
        ButterKnife.bind(this);

        logoutBtn.setOnClickListener(this);

        Bundle bundleData = getIntent().getExtras();
        if (bundleData != null) {
            usernameStr = bundleData.getString(Utility.USER_NAME);
        } else {
            readValuesFromPrefs();
        }

        Log.d(LOG_TAG, "Inside - onCreate");
        isLoggedInBool = Utility.readValuesFromPrefs(this);
        updateValueToUI();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "Inside - onPause");
        saveValuesToPrefs();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "Inside - onStop");
        saveValuesToPrefs();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.id_logout:
                isLoggedInBool = !isLoggedInBool;
                usernameStr = "";
                Utility.saveValuesToPrefs(this, isLoggedInBool);
                updateValueToUI();
                break;
        }
    }

    private void updateValueToUI(){
        if (isLoggedInBool) {
            String displayStr = String.format(getResources().getString(R.string.str_hello), usernameStr);
            userInfoTV.setText(displayStr);
        } else {
            finish();
        }
    }

    private void readValuesFromPrefs(){
        SharedPreferences mySharedPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        usernameStr = mySharedPrefs.getString(Utility.USER_NAME, "");

        Log.d(LOG_TAG, "Values Read from Prefs.");
        dumpPrefValues();
    }

    private void saveValuesToPrefs(){
        SharedPreferences.Editor prefsEditor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();

        prefsEditor.putString(Utility.USER_NAME, usernameStr);
        prefsEditor.commit();

        Log.d(LOG_TAG, "Values Saved to Prefs.");
        dumpPrefValues();
    }

    private void dumpPrefValues(){
        Log.d(LOG_TAG, Utility.USER_NAME + " - " + usernameStr);
    }
}
