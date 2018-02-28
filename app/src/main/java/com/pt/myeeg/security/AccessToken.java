package com.pt.myeeg.security;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Jorge Zepeda Tinoco on 16/08/17.
 * jorzet.94@gmail.com
 */

public class AccessToken {

    private static final String TOKEN_NAME_FILE = "com.eeg.pt1_v1.security.AccessToken";
    private static final String TOKEN_NAME = "token_name";

    public AccessToken(){}

    public static void setAccessToken(String token, Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences(AccessToken.TOKEN_NAME_FILE, Context.MODE_PRIVATE).edit();
        editor.putString(AccessToken.TOKEN_NAME, token);
        editor.apply();
    }
    public static String getCurrentAccessToken(Context context){
        SharedPreferences prefs = context.getSharedPreferences(AccessToken.TOKEN_NAME_FILE, Context.MODE_PRIVATE);
        return prefs.getString(AccessToken.TOKEN_NAME, null);
    }
}
