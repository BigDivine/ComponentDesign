package com.divine.yang.basecomponent;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Create by BigDivine on 2020/10/10
 */
public abstract class BaseActivity extends AppCompatActivity {
    // activity manager class:activity管理类
    private ActivitiesManager activitiesManager;
    // current time by millisecond:当前时间毫秒数
    private long currentTimeMillis = 0;

    /**
     * 获取布局的id
     *
     * @return layoutResId like：R.layout.main_activity
     */
    public abstract int getContentViewId();

    /**
     * 初始化控件
     */
    public abstract void initView();

    /**
     * 页面发出请求，获取页面的数据
     */
    public abstract void getData();

    /**
     * 需要动态获取的权限
     *
     * @return
     */
    public abstract String[] requestPermissions();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e("yzl", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        activitiesManager = ActivitiesManager.getInstance();
        activitiesManager.addActivity(this);
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
        activitiesManager.finishActivity();
    }

    @Override
    public void onBackPressed() {
        Log.e("yzl", "onBackPressed");
        currentTimeMillis = System.currentTimeMillis();
        if (activitiesManager.getActivityStack().size() == 1) {
            if (System.currentTimeMillis() - currentTimeMillis <= 2000) {
                activitiesManager.AppExit(this);
            } else {
                Toast.makeText(this, "再按一次返回键，退出应用", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onBackPressed();
        }
    }
}
