package com.divine.yang.basecomponent.base;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.divine.yang.basecomponent.utils.ActivitiesManager;
import com.divine.yang.basecomponent.getpermission.PermissionPageUtils;
import com.divine.yang.basecomponent.getpermission.PermissionUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * Create by BigDivine on 2020/10/10
 */
public abstract class BaseActivity extends AppCompatActivity {
    private final String TAG="Divine-BaseActivity";
    // activity manager class:activity管理类
    private ActivitiesManager activitiesManager;
    // current time by millisecond:当前时间毫秒数
    private long currentTimeMillis = 0;
    // need to request permissions dynamically:需要动态申请的权限
    private String[] requestPermissions;
    // requestCode for require permissions:请求权限的requestCode
    private final int REQUEST_PERMISSION_REQUEST_CODE = 1000;

    /**
     * 获取布局的id
     *
     * @return layoutResId like：R.layout.main_activity
     */
    public abstract int getContentViewId();

    public abstract boolean showToolbar();

    public abstract View getToolbar();

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
        Log.e(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());

        if (showToolbar()) {
            View mToolbar = getToolbar();
            getSupportActionBar().show();
            getSupportActionBar().setBackgroundDrawable(null);
            // 显示自定义视图
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            // 自定义视图
            getSupportActionBar().setCustomView(mToolbar);
            //to avoid the space in tab left and right side
            Toolbar parent = (Toolbar) mToolbar.getParent();
            parent.setContentInsetsAbsolute(0, 0);
        } else {
            getSupportActionBar().hide();
        }

        activitiesManager = ActivitiesManager.getInstance();
        activitiesManager.addActivity(this);

        requestPermissions = requestPermissions();
        //获取未授权的权限,并且弹框请求权限
        PermissionUtil.requestPermissions(this, requestPermissions, REQUEST_PERMISSION_REQUEST_CODE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        Log.e(TAG, "onCreateView");
        return super.onCreateView(name, context, attrs);
    }

    @Override
    protected void onStart() {
        Log.e(TAG, "onStart");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.e(TAG, "onRestart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.e(TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.e(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.e(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        activitiesManager.finishActivity();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.e(TAG, "onRequestPermissionsResult");
        if (requestCode == REQUEST_PERMISSION_REQUEST_CODE) {
            for (int result : grantResults) {
                if (result == PackageManager.PERMISSION_DENIED) {
                    new AlertDialog.Builder(this)
                            .setMessage("跳转到设置页面允许权限，否则无法正常使用。")
                            .setTitle("授权提示")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    new PermissionPageUtils(BaseActivity.this).jumpPermissionPage();
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .create()
                            .show();
                    break;
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onBackPressed() {
        Log.e(TAG, "onBackPressed");
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
