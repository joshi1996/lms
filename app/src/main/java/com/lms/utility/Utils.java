package com.lms.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.widget.AppCompatButton;

import com.lms.R;
import com.lms.View.LoginActivity;
import com.lms.databinding.ActivityPdflayoutBinding;


public class Utils {



    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static void hideKeyboard(Activity activity) {

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


     public static void Relogin(int status,Context mcontext){

         if (status == 400) {
             SharePrefs.clearUserdetail(mcontext);
             Intent i = new Intent(mcontext, LoginActivity.class);
             i.putExtra("error", "Authorization has been denied for this request.");
             mcontext.startActivity(i);
         }
     }


    public static  ColorStateList IconTintList( Context mcontext) {


        int[][] states = new int[][] {
                new int[]{android.R.attr.state_pressed},
                new int[]{android.R.attr.state_focused},
                new int[]{android.R.attr.state_enabled}


        };

        int[] colors = new int[] {
                mcontext.getResources().getColor(R.color.black),
                mcontext.getResources().getColor(R.color.black),
                mcontext.getResources().getColor(R.color.black),

        };

        ColorStateList myList = new ColorStateList(states, colors);
        return myList;
    }


    public static int getDeviceWidth(Context mcontext) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)mcontext). getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        return width;
    }

    public static int getDeviceheight(Context mcontext) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)mcontext). getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
return height;
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        return capitalize(manufacturer) + " " + model;
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;

        StringBuilder phrase = new StringBuilder();
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c));
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase.append(c);
        }

        return phrase.toString();
    }


    public static void changeStatuscolor(Context mcontext) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window =((Activity) mcontext).getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
            if(SharePrefs.getSetting(mcontext)!=null)
                window.setStatusBarColor(SharePrefs.getSetting(mcontext).getThemeColor());
        }


    }
}
