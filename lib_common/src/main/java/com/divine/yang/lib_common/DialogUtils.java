package com.divine.yang.lib_common;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * author: Divine
 * <p>
 * date: 2018/12/4
 */
public class DialogUtils {

    private static ProgressDialog progressDialog;

    public static void showDatePickerDialog(Context mContext, DatePickerDialog.OnDateSetListener dateSetListener, int year, int month, int dayOfMonth) {

        DatePickerDialog mDatePickerDialog =
                new DatePickerDialog(mContext
                        , DatePickerDialog.THEME_HOLO_LIGHT
                        , dateSetListener
                        , year
                        , month
                        , dayOfMonth);
        mDatePickerDialog.show();
    }

    public static void showTimePickerDialog(Context mContext, TimePickerDialog.OnTimeSetListener dateSetListener, int year, int month) {

        TimePickerDialog mDatePickerDialog =
                new TimePickerDialog(mContext
                        , dateSetListener
                        , year
                        , month
                        , false);
        mDatePickerDialog.show();
    }

    public static void confimDeleteDialog(Context context, String text, DialogInterface.OnClickListener confirm) {
        new AlertDialog.Builder(context).setTitle(text)
                .setPositiveButton("确定", confirm)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public static void progressDialog(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("加载中");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public static void dismissDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
