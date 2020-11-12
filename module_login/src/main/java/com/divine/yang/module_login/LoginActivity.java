package com.divine.yang.module_login;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.divine.yang.lib_base.base.BaseActivity;
import com.divine.yang.lib_common.SPUtils;
import com.divine.yang.lib_widget.widget.EditTextWithClean;
import com.sankuai.waimai.router.annotation.RouterUri;

/**
 * Author: Divine
 * CreateDate: 2020/11/03
 * Describe: 登录模块界面
 */
@RouterUri(scheme = "login_scheme", host = "login_host", path = "/login_main")
public class LoginActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private static final String TAG = "LoginActivity";
    private EditTextWithClean user_edt;
    private EditTextWithClean pwd_edt;
    private CheckBox remember_user;
    private CheckBox remember_pwd;
    private Button login_btn;

    private boolean isRememberUser;

    private boolean isRememberPWD;
    private SPUtils mSPUtils;

    @Override
    public int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    public boolean showToolbar() {
        return false;
    }

    @Override
    public void initView() {
        user_edt = findViewById(R.id.user_edt);
        pwd_edt = findViewById(R.id.pwd_edt);
        remember_user = findViewById(R.id.remember_user);
        remember_pwd = findViewById(R.id.remember_pwd);
        login_btn = findViewById(R.id.login_btn);
        remember_user.setOnCheckedChangeListener(this);
        remember_pwd.setOnCheckedChangeListener(this);
        login_btn.setOnClickListener(this);

        mSPUtils = SPUtils.getInstance(this);
    }

    @Override
    public void getData() {
        String username = (String) mSPUtils.get("user_name", "");
        String password = (String) mSPUtils.get("user_pwd", "");

        user_edt.setText(username);
        pwd_edt.setText(password);
    }

    @Override
    public void onClick(View v) {
        Log.e("dinglp", isRememberUser + "");
        Log.e("dinglp", isRememberPWD + "");
//                startActivity(new Intent(this, NectarLauncher.class));
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int viewId = buttonView.getId();
        if (viewId == R.id.remember_user) {
            isRememberUser = isChecked;
        } else if (viewId == R.id.remember_pwd) {
            isRememberPWD = isChecked;
        }
    }
}
