package com.divine.yang.lib_http;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.divine.yang.lib_http.retrofit2.Retrofit2DemoActivity;
import com.sankuai.waimai.router.annotation.RouterUri;

@RouterUri(scheme = "http_scheme",host = "http_host",path = "/http_demo_main")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toPostRequest(View view) {
        startActivity(new Intent(this, Retrofit2DemoActivity.class));
    }
}
