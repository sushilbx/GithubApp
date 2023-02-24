package com.sushil.githubapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.OAuthCredential;
import com.google.gson.Gson;


public class SessionManager {

    public static final String KEY_USER_DETAILS = "user_details";
    public static final String KEY_CART_LIST = "cart_list";
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
    public void createLoginSession(FirebaseUser firebaseUser) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putBoolean(KEY_FIRST_TIME, true);
        editor.putString(KEY_AUTH_DETAILS, new Gson().toJson(firebaseUser));
        editor.commit();
    }

    public FirebaseUser getLoginSession() {
        return new Gson().fromJson(pref.getString(KEY_AUTH_DETAILS, ""), FirebaseUser.class);
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

    public Boolean getFirstTime() {
        return pref.getBoolean(KEY_FIRST_TIME, false);
    }

    public void setFirstTime(Boolean firstTime) {
        editor.putBoolean(KEY_FIRST_TIME, firstTime);
        editor.commit();
    }

    public boolean getFirstTimeLaunch() {
        return pref.getBoolean(KEY_FIRST_TIME, true);
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(KEY_FIRST_TIME, isFirstTime);
        editor.commit();
    }

}









