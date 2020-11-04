package com.divine.yang.basecomponent;

import android.Manifest;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.divine.yang.basecomponent.base.BaseActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sankuai.waimai.router.annotation.RouterUri;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

@RouterUri(scheme = "base_scheme",host = "base_host",path = "/base_demo_main")
public class BaseDemoActivity extends BaseActivity {

    @Override
    public int getContentViewId() {
        return R.layout.activity_base_component;
    }

    @Override
    public boolean showToolbar() {
        return true;
    }

    @Override
    public void initView() {
        BottomNavigationView navView = findViewById(R.id.navigation);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_notifications, R.id.navigation_applications, R.id.navigation_user_info)
                //        R.id.navigation_home)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public void getData() {

    }

    @Override
    public String[] requestPermissions() {
        return new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
    }
}
