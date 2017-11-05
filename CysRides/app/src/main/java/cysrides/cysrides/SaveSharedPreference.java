package cysrides.cysrides;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class SaveSharedPreference {
    private static final String USERNAME_PASSWORD = "username";

    private static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    static void setUsernamePassword(Context context, String userName) {
        Editor editor = getSharedPreferences(context).edit();
        editor.putString(USERNAME_PASSWORD, userName);
        editor.apply();
    }

    public static String getUsernamePassword(Context context)
    {
        return getSharedPreferences(context).getString(USERNAME_PASSWORD, "");
    }

    public static void clearUsernamePassword(Context context)
    {
        Editor editor = getSharedPreferences(context).edit();
        editor.clear(); //clear all stored data
        editor.apply();
    }
}
