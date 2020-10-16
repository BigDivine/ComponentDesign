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

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends BaseActivity {

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public boolean showToolbar() {
        return true;
    }

    @Override
    public View getToolbar() {

        View actionBar = LayoutInflater.from(this).inflate(R.layout.action_bar_layout, null, false);
        View actionBarRes = LayoutInflater.from(this).inflate(R.layout.action_bar_normal, null, false);
        LinearLayout actionBarContain = actionBar.findViewById(R.id.action_bar_res);
        actionBarContain.removeAllViews();
        actionBarContain.addView(actionBarRes);

        View leftLayout = actionBarRes.findViewById(R.id.normal_action_bar_left);
        leftLayout.setVisibility(View.VISIBLE);
        leftLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "left layout button click", Toast.LENGTH_SHORT).show();
                MainActivity.this.finish();
            }
        });

        View centerLayout = actionBarRes.findViewById(R.id.normal_action_bar_center);
        centerLayout.setVisibility(View.VISIBLE);
        TextView headerTitle = centerLayout.findViewById(R.id.normal_action_bar_title);
        headerTitle.setText("这是标题");

        View rightLayout = actionBarRes.findViewById(R.id.normal_action_bar_right);
        rightLayout.setVisibility(View.VISIBLE);
        rightLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "right layout button click", Toast.LENGTH_SHORT).show();
            }
        });

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        actionBar.setLayoutParams(params);


        return actionBar;
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
