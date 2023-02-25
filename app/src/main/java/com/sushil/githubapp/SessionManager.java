package com.sushil.githubapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


public class SessionManager {
    public static final String USER_NAME = "user_name";
    private static final String PREF_NAME = "userData";
    private static final String IS_LOGIN = "isLogin";
    private static final String KEY_FIRST_TIME = "first_time";
    private static final String KEY_AUTH_DETAILS = "auth_details";
    private static SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;


    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    public void createLoginSession(String aceessToken) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putBoolean(KEY_FIRST_TIME, true);
        editor.putString(KEY_AUTH_DETAILS, aceessToken);
        editor.commit();
    }
    public void createName(String userName) {
        editor.putString(USER_NAME, userName);
        editor.commit();
    }

    public String getLoginSession() {
        return pref.getString(KEY_AUTH_DETAILS, null);

    }
    public String getName() {
        return pref.getString(USER_NAME, null);

    }


    public void clearSession() {
        editor.clear();
        editor.commit();

    }

    public void logoutUser() {
        clearSession();
        Intent i = new Intent(_context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);

    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }
}









