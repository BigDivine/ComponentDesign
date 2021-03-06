package com.divine.yang.module_camera2;

import android.os.Bundle;

import com.divine.yang.lib_base.base.BaseActivity;
import com.divine.yang.lib_base.getpermission.PermissionList;
import com.sankuai.waimai.router.annotation.RouterUri;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

@RouterUri(scheme = "camera2_scheme",host = "camera2_host",path = "/camera2_demo_main")
public class Camera2DemoActivity extends BaseActivity {
    private String TAG = "D-Camera2DemoActivity";
    private Camera2Fragment mCamera2Fragment;
    private FragmentManager mFm;

    @Override
    public int getContentViewId() {
        return R.layout.activity_camera2_demo;
    }

    @Override
    public boolean showToolbar() {
        return false;
    }

    @Override
    public void initView() {
        mCamera2Fragment = new Camera2Fragment();
        Bundle bundle = new Bundle();
        mCamera2Fragment.setArguments(bundle);
        mFm = getSupportFragmentManager();
        FragmentTransaction mFragmentTrans = mFm.beginTransaction();
        mFragmentTrans.add(R.id.camera2_frame_layout, mCamera2Fragment);
        mFragmentTrans.commit();
    }

    @Override
    public void getData() {
    }

    @Override
    public String[] requestPermissions() {
        return new String[]{PermissionList.WRITE_EXTERNAL_STORAGE, PermissionList.READ_EXTERNAL_STORAGE, PermissionList.CAMERA};
    }
}