package com.divine.yang.module_imageedit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.sankuai.waimai.router.annotation.RouterUri;

@RouterUri(scheme = "image_edit_scheme",host = "image_edit_host",path = "/image_edit_demo_main")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
