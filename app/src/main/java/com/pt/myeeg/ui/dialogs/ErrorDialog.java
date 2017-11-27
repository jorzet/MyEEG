package com.pt.myeeg.ui.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.NonNull;

import com.pt.myeeg.R;

/**
 * Created by ing_ragde on 05/09/17.
 */

public class ErrorDialog {
    AlertDialog.Builder builder;
    Context mContext;
    public ErrorDialog(Context context) {
        this.mContext = context;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
    }

    public void showErrorLogOut(){
        builder.setTitle("Alerta")
                .setMessage("No puede cerrar sesión durante una grabación")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(mContext.getDrawable(R.drawable.ic_alert))
                .show();
    }

    public void showErrorNewRecording(){
        builder.setTitle("Alerta")
                .setMessage("No puede iniciar otra grabación")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(mContext.getDrawable(R.drawable.ic_alert))
                .show();
    }
}
