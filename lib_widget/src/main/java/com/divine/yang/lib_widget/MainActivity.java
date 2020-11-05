package com.divine.yang.lib_widget;

import android.os.Bundle;

import com.sankuai.waimai.router.annotation.RouterUri;

import androidx.appcompat.app.AppCompatActivity;

@RouterUri(scheme = "widget_scheme",host = "widget_host",path = "/widget_demo_main")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

}
