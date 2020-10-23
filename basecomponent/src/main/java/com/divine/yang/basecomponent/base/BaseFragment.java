package com.divine.yang.basecomponent.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Author: Divine
 * CreateDate: 2020/9/29
 * Describe:
 */

public abstract class BaseFragment extends Fragment {
    private final String TAG = "D-BaseFragment";

    public Context mContext;

    // 可以对控件进行初始化
    protected abstract void initView(View view);

    // 对数据进行初始化
    protected abstract void getData();

    // 布局id
    public abstract int setContentView();

    @Override
    public void onAttach(Context context) {
        Log.e(TAG, "onAttach");
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");
        View view = inflater.inflate(setContentView(), null);

        initView(view);
        getData();
        return view;
    }

    @Override
    public void onStart() {
        Log.e(TAG, "onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.e(TAG, "onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.e(TAG, "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.e(TAG, "onStop");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
    }

    public void startActivity(Class c, Bundle bundle) {
        Intent intent = new Intent(getContext(), c);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void startActivityForResult(Class c, int code, Bundle bundle) {
        Intent intent = new Intent(getContext(), c);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, code);
    }
}
