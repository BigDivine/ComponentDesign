package com.divine.yang.componentdesign;

import android.view.View;

import com.divine.yang.lib_base.base.BaseActivity;
import com.divine.yang.lib_base.base.BaseToolbar;
import com.divine.yang.lib_base.getpermission.PermissionList;
import com.sankuai.waimai.router.Router;

public class AppActivity extends BaseActivity {

    @Override
    public int getContentViewId() {
        return R.layout.activity_app;
    }

    @Override
    public boolean showToolbar() {
        return true;
    }

    @Override
    public void initView() {
        BaseToolbar toolbar = getBaseToolbar();
        toolbar.setLeftVisible(false);
        toolbar.setRightVisible(false);
        toolbar.setTitle("导航");
    }

    @Override
    public void getData() {

    }

    @Override
    public String[] requestPermissions() {
        return new String[]{PermissionList.WRITE_EXTERNAL_STORAGE};
    }

    public void toBaseMain(View view) {
        Router.startUri(this, "base_scheme://base_host/base_demo_main");
    }

    public void toCamera2Main(View view) {
        Router.startUri(this, "camera2_scheme://camera2_host/camera2_demo_main");

    }

    public void toHttpMain(View view) {
        Router.startUri(this, "http_scheme://http_host/http_demo_main");
    }

    public void toImageEditMain(View view) {
        Router.startUri(this, "image_edit_scheme://image_edit_host/image_edit_demo_main");
    }

    public void toLoginMain(View view) {
        Router.startUri(this, "login_scheme://login_host/login_demo_main");
    }

    public void toSqliteMain(View view) {
        Router.startUri(this, "sqlite_scheme://sqlite_host/sqlite_demo_main");
    }

    public void toWebViewMain(View view) {
        Router.startUri(this, "webview_scheme://webview_host/webview_demo_main");
    }

    public void toWidgetMain(View view) {
        Router.startUri(this, "widget_scheme://widget_host/widget_demo_main");
    }
}