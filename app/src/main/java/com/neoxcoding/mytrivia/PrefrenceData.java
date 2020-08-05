package com.neoxcoding.mytrivia;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefrenceData {


    static final String CURRENT_USER = "current_user";
    static final String CURRENT_USER_COUNTRY = "current_user_country";
    static final String MUSIC_SETTINGS = "music_settings";
    static final String EFFECTS_SETTINGS = "effects_settings";




    public static SharedPreferences getSharedPreferences(Context ctx)
    {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }




    public static void setMusicSettings(Context ctx, String status)
    {

        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(MUSIC_SETTINGS, status);
        editor.commit();
    }


    public static String getMusicSettings(Context ctx)
    {

        return getSharedPreferences(ctx).getString(MUSIC_SETTINGS, "");
    }





    public static void setEffectsSettigns(Context ctx, String status)
    {

        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(EFFECTS_SETTINGS, status);
        editor.commit();
    }

    public static String getEffectsSettigns(Context ctx)
    {

        return getSharedPreferences(ctx).getString(EFFECTS_SETTINGS, "");
    }










    public static void setCurrentUserName(Context ctx, String name)
    {

        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(CURRENT_USER, name);
        editor.commit();
    }

    public static String getCurrentUserName(Context ctx)
    {

        return getSharedPreferences(ctx).getString(CURRENT_USER, "");
    }











    public static void setCurrentUserCountry(Context ctx, String country)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(CURRENT_USER_COUNTRY, country);
        editor.commit();
    }

    public static String getCurrentUserCountry(Context ctx)
    {
        return getSharedPreferences(ctx).getString(CURRENT_USER_COUNTRY, "");
    }

    public static void clearCurrentUser(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(CURRENT_USER);
        editor.remove(CURRENT_USER_COUNTRY);
        editor.commit();
    }
}
