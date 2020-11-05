package com.divine.yang.lib_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.sankuai.waimai.router.annotation.RouterUri;

@RouterUri(scheme = "sqlite_scheme",host = "sqlite_host",path = "/sqlite_demo_main")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
