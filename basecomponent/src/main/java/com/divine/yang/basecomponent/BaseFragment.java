package com.divine.yang.basecomponent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

    public Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(setContentView(), null);

        initView(view);
        setData();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    // 抽象 - 初始化方法，可以对控件进行初始化，也可以对数据进行初始化
    protected abstract void initView(View view);

    protected abstract void setData();

    /**
     * @return 布局id
     */
    public abstract int setContentView();


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
