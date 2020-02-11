package com.example.moriah.model;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    SharedPreferences userNamePref;
    SharedPreferences.Editor userNameEditor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "Moriah-welcome";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    private static final String PREF_User_NAME = "UserName";
    private static final String UserName = "JohnDoe";


    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        userNamePref = context.getSharedPreferences(PREF_User_NAME, PRIVATE_MODE);
        editor = pref.edit();
        userNameEditor = userNamePref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setUserName(String name){
        userNameEditor.putString(UserName, name);
        userNameEditor.commit();
    }

    public String getUserName(){
        return userNamePref.getString(UserName, "JohnDoe");
    }

}
