package com.divine.yang.camera2component;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.divine.yang.lib_base.base.BaseActivity;
import com.divine.yang.lib_base.base.BaseToolbar;
import com.divine.yang.lib_base.base.ToolbarClickListener;
import com.sankuai.waimai.router.annotation.RouterUri;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends BaseActivity implements ToolbarClickListener{
    private final int GET_PIC_REQUEST_CODE = 1;
    private ArrayList<String> imgData;
    private RecyclerView imgResult;
    private MainAdapter adapter;

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public boolean showToolbar() {
        return true;
    }

    @Override
    public void initView() {
        BaseToolbar baseToolbar = getBaseToolbar();
        baseToolbar.setTitle("选择图片demo");
        baseToolbar.setRightVisible(false);
        baseToolbar.setLeftText("退出");
        baseToolbar.setToolbarClickListener(this);

        Button btn = findViewById(R.id.camera2_button);
        imgResult = findViewById(R.id.camera2_rv);
        imgResult.setLayoutManager(new LinearLayoutManager(this));
        imgData = new ArrayList<>();
        adapter = new MainAdapter(this, imgData);
        imgResult.setAdapter(adapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivityForResult(new Intent(MainActivity.this, Camera2DemoActivity.class), GET_PIC_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GET_PIC_REQUEST_CODE:
                    Bundle bundle = data.getBundleExtra("select_pics_bundle");
                    imgData = bundle.getStringArrayList("select_pics");
                    adapter = new MainAdapter(this, imgData);
                    imgResult.setAdapter(adapter);
                    break;
            }
        }
    }

    @Override
    public void getData() {

    }

    @Override
    public String[] requestPermissions() {
        return new String[0];
    }

    @Override
    public void leftClick() {
        finish();
    }

    @Override
    public void centerClick() {

    }

    @Override
    public void rightClick() {

    }
}