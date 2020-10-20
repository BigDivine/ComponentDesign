package com.divine.yang.componentdesign;

import android.view.View;

import com.divine.yang.basecomponent.base.BaseActivity;
import com.sankuai.waimai.router.Router;

public class MainActivity extends BaseActivity {

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public boolean showToolbar() {
        return false;
    }

    @Override
    public View getToolbar() {
        return null;
    }

    @Override
    public void initView() {

    }

    @Override
    public void getData() {

    }

    @Override
    public String[] requestPermissions() {
        return new String[0];
    }

    public void toBaseMain(View view) {
        Router.startUri(this, "base_scheme://base_host/base_component_main");
    }
}