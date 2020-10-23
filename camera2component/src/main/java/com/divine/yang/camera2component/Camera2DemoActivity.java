package com.divine.yang.camera2component;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.divine.yang.basecomponent.base.BaseActivity;
import com.divine.yang.basecomponent.getpermission.PermissionList;
import com.sankuai.waimai.router.annotation.RouterUri;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

@RouterUri(path= "/camera2_demo_main")
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.e(TAG, "onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
        mCamera2Fragment.onActivityResult(requestCode, resultCode, data);
    }
}