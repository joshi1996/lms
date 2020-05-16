package com.lms.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.lms.GsonModel.Advertisment.AdvertismentModel;
import com.lms.GsonModel.Advertisment.Datum;
import com.lms.GsonModel.GetOTP;
import com.lms.GsonModel.lmsData;
import com.lms.GsonModel.settingdata.Data;

public class SharePrefs {

    public static SharedPreferences appSharedPrefs;
    private static Context mcontext;

    public static SharedPreferences getInstance(Context context) {
        appSharedPrefs = context.getSharedPreferences("livescorefantasy", Context.MODE_PRIVATE);
        mcontext = context;
        return appSharedPrefs;
    }

    public static lmsData getUserdetail(Context mcontext) {
        Gson gson = new Gson();
        String json = getInstance(mcontext).getString("userdetail", "");
        lmsData mUserDetails = gson.fromJson(json, lmsData.class);
        return mUserDetails;
    }

    public static void saveUserDetail(Context mcontext, lmsData mUserDetails) {

        SharedPreferences.Editor prefsEditor = getInstance(mcontext).edit();
        Gson gson = new Gson();
        String json = gson.toJson(mUserDetails);
        prefsEditor.putString("userdetail", json);
        prefsEditor.commit();
    }



      public static Data getSetting(Context mcontext) {
        Gson gson = new Gson();
        String json = getInstance(mcontext).getString("setting", "");
          Data mLovsDatum = gson.fromJson(json, Data.class);
        return mLovsDatum;
    }

    public static void saveSettings(Context mcontext, Data mLovsDatum) {

        SharedPreferences.Editor prefsEditor = getInstance(mcontext).edit();
        Gson gson = new Gson();
        String json = gson.toJson(mLovsDatum);
        prefsEditor.putString("setting", json);
        prefsEditor.commit();
    }




    public static AdvertismentModel getAdvertisment(Context mcontext) {
        Gson gson = new Gson();
        String json = getInstance(mcontext).getString("advert", "");
        AdvertismentModel mLovsDatum = gson.fromJson(json, AdvertismentModel.class);
        return mLovsDatum;
    }

    public static void saveAdvertisment(Context mcontext, AdvertismentModel mLovsDatum) {

        SharedPreferences.Editor prefsEditor = getInstance(mcontext).edit();
        Gson gson = new Gson();
        String json = gson.toJson(mLovsDatum);
        prefsEditor.putString("advert", json);
        prefsEditor.commit();
    }



    /* public static void clearLovsDatum(Context mcontext) {
        SharedPreferences.Editor editor = getInstance(mcontext).edit();
        editor.remove("LovsDatum").commit();

    }
*/
    public static void clearUserdetail(Context mcontext) {
        SharedPreferences.Editor editor = getInstance(mcontext).edit();
        editor.putString("userdetail", null);

        editor.commit();

    }

    public static void saveString(Context mcontext, String key, String value) {
        SharedPreferences.Editor prefsEditor = getInstance(mcontext).edit();
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }


    public static String getStringValue(Context mcontext, String key) {
        // doesn't go past the line below
        SharedPreferences settings = getInstance(mcontext);
        String restoredText = settings.getString(key, null);
        return restoredText;
    }


    public static void clearString(Context mcontext, String key) {
        SharedPreferences.Editor editor = getInstance(mcontext).edit();
        editor.remove(key).commit();

    }

    public static void setLocale(String locale) {
        SharedPreferences.Editor prefsEditor = getInstance(mcontext).edit();
        prefsEditor.putString("locale", locale);
        prefsEditor.commit();
    }


    public static String getLocale(Context mcontext) {
        // doesn't go past the line below
        SharedPreferences settings = getInstance(mcontext);
        String restoredText = settings.getString("locale", "en");
        return restoredText;
    }
}
