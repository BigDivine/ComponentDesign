package com.divine.yang.basecomponent;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.divine.yang.basecomponent.getpermission.PermissionHelper;
import com.divine.yang.basecomponent.getpermission.PermissionInterface;
import com.divine.yang.basecomponent.getpermission.PermissionPageUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Create by BigDivine on 2019/2/25
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */
public abstract class BaseActivity extends AppCompatActivity {
    public abstract int getContentViewId();

    public abstract void initView();

    public abstract void getData();

    public abstract String[] requestPermissions();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e("yzl", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        Log.e("yzl", "onCreateView");
        return super.onCreateView(name, context, attrs);
    }

    @Override
    protected void onStart() {
        Log.e("yzl", "onStart");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.e("yzl", "onRestart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.e("yzl", "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.e("yzl", "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.e("yzl", "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.e("yzl", "onDestroy");
        super.onDestroy();
    }


}
