package com.example.dam_m13_proyecto;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class ShowAlertDialog extends AppCompatActivity {
    private Context context;
    private String title, message;


    public ShowAlertDialog() {
    }

    public ShowAlertDialog(Context context, String title, String message) {
        this.context = context;
        this.title = title;
        this.message = message;
    }



    public void showAlertDialog(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Positive button click
                        // Close the dialog
                        dialog.dismiss();


                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Negative button click
                        // Close the dialog
                        dialog.dismiss();
                    }
                })
                .show();

    }
    public void showAlertDialogOkOnly(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Positive button click
                        // Close the dialog
                        dialog.dismiss();
                        Intent intent = new Intent(ShowAlertDialog.this, UserLoginRegister.class);
                        startActivity(intent);


                    }
                })
                .show();

    }
    public void showAlertDialogCancelOnly(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
               .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int which) {
                         // Negative button click
                          // Close the dialog
                         dialog.dismiss();
                     }
                 })
                .show();
    }
}
