package com.pt.myeeg.ui.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;

import com.pt.myeeg.R;

/**
 * Created by Jorge Zepeda Tinoco on 05/09/17.
 * jorzet.94@gmail.com
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

    public void showErrorNotDevices(){
        builder.setTitle("Alerta")
                .setMessage("No tienes dispositivos asociados")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(mContext.getDrawable(R.drawable.ic_alert))
                .show();
    }

    public void showErroCanNotOpenSchedule() {
        builder.setTitle("Alerta")
                .setMessage("No puedes iniciar una grabacion anterior a la fecha actual")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(mContext.getDrawable(R.drawable.ic_alert))
                .show();
    }

    public void showErrorNotSheduleDate(String date, String time) {
        builder.setTitle("Alerta")
                .setMessage("Aun no es tiempo de tu grabacion, cita programada para el dia: "+ date + " a las: "+ time)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(mContext.getDrawable(R.drawable.ic_alert))
                .show();
    }

    public void showErrorNotAllowRecording() {
        builder.setTitle("Alerta")
                .setMessage("No se puede hacer una grabacion de citas pasadas")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(mContext.getDrawable(R.drawable.ic_alert))
                .show();
    }

    public void showErrorNotTimeSchedule(String time) {
        builder.setTitle("Alerta")
                .setMessage("Aun no es tiempo de tu grabacion, cita programada para las: "+ time)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(mContext.getDrawable(R.drawable.ic_alert))
                .show();
    }

    public void showErrorUpdateInfo(String error) {
        builder.setTitle("Error")
                .setMessage(error)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(mContext.getDrawable(R.drawable.ic_alert))
                .show();
    }

}
