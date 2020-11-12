package com.divine.yang.lib_common;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;



/**
 * author: Divine
 * <p>
 * date: 2018/12/4
 */
public class DialogUtils {

    private static ProgressDialog progressDialog;

    private static Dialog dialog;

    /**
     * 日期选择dialog
     *
     * @param mContext
     * @param dateSetListener 时间选择监听
     * @param year            初始年
     * @param month           初始月
     * @param dayOfMonth      初始日
     */
    public static void showDatePickerDialog(Context mContext
            , DatePickerDialog.OnDateSetListener dateSetListener
            , int year
            , int month
            , int dayOfMonth) {

        DatePickerDialog mDatePickerDialog = new DatePickerDialog(mContext,
                                                                  DatePickerDialog.THEME_HOLO_LIGHT,
                                                                  dateSetListener,
                                                                  year,
                                                                  month,
                                                                  dayOfMonth);
        mDatePickerDialog.show();
    }

    /**
     * 日期选择dialog
     *
     * @param mContext
     * @param dateSetListener 时间选择监听
     * @param year            初始年
     * @param month           初始月
     * @param dayOfMonth      初始日
     * @param minDate         最小日期
     * @param maxDate         最大日期
     */
    public static void showDatePickerDialog(Context mContext
            , DatePickerDialog.OnDateSetListener dateSetListener
            , int year
            , int month
            , int dayOfMonth
            , long minDate
            , long maxDate) {

        DatePickerDialog mDatePickerDialog = new DatePickerDialog(mContext,
                                                                  DatePickerDialog.THEME_HOLO_LIGHT,
                                                                  dateSetListener,
                                                                  year,
                                                                  month,
                                                                  dayOfMonth);
        DatePicker datePicker = mDatePickerDialog.getDatePicker();
        if (minDate != 0) {
            datePicker.setMinDate(minDate);
        }
        datePicker.setMaxDate(maxDate);
        mDatePickerDialog.show();
    }

    /**
     * 时间选择器
     *
     * @param mContext
     * @param dateSetListener 时间选择完成监听
     * @param hourOfDay       初始小时
     * @param minute          初始分钟
     */
    public static void showTimePickerDialog(Context mContext, TimePickerDialog.OnTimeSetListener dateSetListener, int hourOfDay, int minute) {

        TimePickerDialog mDatePickerDialog = new TimePickerDialog(mContext,
                                                                  dateSetListener,
                                                                  hourOfDay,
                                                                  minute,
                                                                  false);
        mDatePickerDialog.show();
    }

    /**
     * 确定提示框
     *
     * @param context
     * @param text
     * @param confirm 确定按钮监听
     */
    public static void confimDeleteDialog(Context context, String text, DialogInterface.OnClickListener confirm) {
        new AlertDialog.Builder(context)
                .setTitle(text)
                .setPositiveButton("确定", confirm)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }
    public interface ExplainResult {
        void explainMessage(String message);
    }
    /**
     * 带输入框的弹框
     *
     * @param context
     * @param explainResult 接口回调，点击确定后，对输入框内的内容进行处理
     */
    public static void explainDialog(Context context, final ExplainResult explainResult) {
        dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_explain_layout, null);

        TextView explain_cancel = inflate.findViewById(R.id.explain_cancel);
        TextView explain_confirm = inflate.findViewById(R.id.explain_confirm);
        final EditText explain_content = inflate.findViewById(R.id.explain_content);
        explain_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        explain_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                explainResult.explainMessage(explain_content.getText().toString());
                dialog.dismiss();
            }
        });
        dialog.setContentView(inflate);
        Window window = dialog.getWindow();
        if (dialog != null && window != null) {
            window.getDecorView().setPadding(130, 0, 130, 0);
            WindowManager.LayoutParams attr = window.getAttributes();
            if (attr != null) {
                attr.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                attr.width = ViewGroup.LayoutParams.MATCH_PARENT;
                attr.gravity = Gravity.CENTER;//设置dialog 在布局中的位置
                window.setAttributes(attr);
            }
        }
        dialog.show();
    }

    /**
     * 是否删除确定弹框
     *
     * @param context
     * @param onClickListener
     */
    public static void deleteDialog(Context context, String deleteMsg, View.OnClickListener onClickListener) {
        dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_delete_layout, null);

        TextView delete_msg = inflate.findViewById(R.id.delete_msg);
        TextView delete_cancel = inflate.findViewById(R.id.delete_cancel);
        TextView delete_confirm = inflate.findViewById(R.id.delete_confirm);
        delete_msg.setText(deleteMsg);
        delete_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        delete_confirm.setOnClickListener(onClickListener);
        dialog.setContentView(inflate);
        Window window = dialog.getWindow();
        if (dialog != null && window != null) {
            window.getDecorView().setPadding(130, 0, 130, 0);
            WindowManager.LayoutParams attr = window.getAttributes();
            if (attr != null) {
                attr.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                attr.width = ViewGroup.LayoutParams.MATCH_PARENT;
                attr.gravity = Gravity.CENTER;//设置dialog 在布局中的位置
                window.setAttributes(attr);
            }
        }
        dialog.show();
    }

    /**
     * 身份证识别，弹框选择视频流识别或者拍照识别
     *
     * @param context
     * @param onClickListener
     */
    public static void chosePhotoDialog(Context context, View.OnClickListener onClickListener) {
        dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_choosephoto, null);
        TextView choosePhoto = inflate.findViewById(R.id.abroad_choosephoto);
        TextView takePhoto = inflate.findViewById(R.id.abroad_takephoto);
        TextView cancel = inflate.findViewById(R.id.abroad_choose_cancel);
        choosePhoto.setOnClickListener(onClickListener);
        takePhoto.setOnClickListener(onClickListener);
        cancel.setOnClickListener(onClickListener);
        dialog.setContentView(inflate);
        Window window = dialog.getWindow();
        if (dialog != null && window != null) {
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams attr = window.getAttributes();
            if (attr != null) {
                attr.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                attr.width = ViewGroup.LayoutParams.MATCH_PARENT;
                attr.gravity = Gravity.BOTTOM;//设置dialog 在布局中的位置
                window.setAttributes(attr);
            }
        }
        dialog.show();
    }

    /**
     * 显示loading 弹框
     *
     * @param context
     */
    public static void showProgressDialog(Context context) {
        progressDialog = new ProgressDialog(context, R.style.MyDialog);
        progressDialog.setMessage("加载中");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    /**
     * 隐藏loading弹框
     */
    public static void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    /**
     * 关闭自定义弹框
     */
    public static void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
