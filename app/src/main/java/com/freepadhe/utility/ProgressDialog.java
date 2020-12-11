package com.freepadhe.utility;


import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.freepadhe.R;


public class ProgressDialog {
    private static AlertDialog dialog;

    public static void showDialog(Context mcontxt) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mcontxt);
        final View dialogView = LayoutInflater.from(mcontxt).inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);

        dialog = dialogBuilder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialog.show();

    }

    public static void hideDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }

    }
}
