package com.hari.aag.loginscreen;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Hari Nivas Kumar R P on 12/13/2016.
 */

class Utility {
    private static final String LOG_TAG = Utility.class.getSimpleName();
    private static final String PREFS_NAME = Utility.class.getSimpleName();

    static final String USER_NAME = "username";
    private static final String IS_LOGGED_IN = "isLoggedIn";

    static boolean readValuesFromPrefs(Context context){
        boolean isLoggedInBool;

        SharedPreferences mySharedPrefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        isLoggedInBool = mySharedPrefs.getBoolean(IS_LOGGED_IN, false);

        Log.d(LOG_TAG, "Values Read from Prefs.");
        Log.d(LOG_TAG, IS_LOGGED_IN + " - " + isLoggedInBool);

        return isLoggedInBool;
    }

    static void saveValuesToPrefs(Context context, boolean isLoggedInBool){
        SharedPreferences.Editor prefsEditor = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();

        prefsEditor.putBoolean(IS_LOGGED_IN, isLoggedInBool);
        prefsEditor.commit();

        Log.d(LOG_TAG, "Values Saved to Prefs.");
        Log.d(LOG_TAG, IS_LOGGED_IN + " - " + isLoggedInBool);
    }
}
