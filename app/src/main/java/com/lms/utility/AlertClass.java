package com.lms.utility;

import android.content.Context;
import android.content.DialogInterface;
import android.view.ContextThemeWrapper;

import androidx.appcompat.app.AlertDialog;

import com.lms.R;


public class AlertClass {


    public static void BaseAlert_yesNo(Context mcontext, String appname,String message, String postivie_buton_text, String negative_button_text, boolean is_negative_show, DialogInterface.OnClickListener onClick_positive, DialogInterface.OnClickListener onClick_nagative) {

        AlertDialog mAlertDialog = new AlertDialog.Builder(new ContextThemeWrapper(mcontext, R.style.AppCompatAlertDialogStyle))
                .setTitle(appname)
                .setMessage(message)
                .setPositiveButton(postivie_buton_text, onClick_positive)
                .setNegativeButton(negative_button_text, onClick_nagative)
                .show();
    }


    public static void BaseAlert_done(Context mcontext,  String appname,String message, String postivie_buton_text, String negative_button_text, boolean is_negative_show, DialogInterface.OnClickListener onClick_positive, DialogInterface.OnClickListener onClick_nagative) {

        AlertDialog mAlertDialog = new AlertDialog.Builder(new ContextThemeWrapper(mcontext, R.style.AppCompatAlertDialogStyle))
                .setTitle(appname)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(postivie_buton_text, onClick_positive)
                .show();
    }
}
