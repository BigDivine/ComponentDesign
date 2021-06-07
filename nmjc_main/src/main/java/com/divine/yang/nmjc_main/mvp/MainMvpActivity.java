package com.divine.yang.nmjc_main.mvp;

import android.os.Handler;

import com.divine.yang.lib_base.base.BaseActivity;
import com.divine.yang.lib_http.retrofit2.Retrofit2Helper;
import com.divine.yang.lib_widget.widget.ItemDecorationHome;
import com.divine.yang.nmjc_main.R;
import com.divine.yang.nmjc_main.mvp.adapters.BannerAdapter;
import com.divine.yang.nmjc_main.mvp.adapters.BillAdapter;
import com.divine.yang.nmjc_main.mvp.adapters.FunctionAdapter;
import com.divine.yang.nmjc_main.mvp.adapters.NotificationAdapter;
import com.divine.yang.nmjc_main.mvp.beans.BillBean;
import com.divine.yang.nmjc_main.mvp.beans.HomeFunction;

import java.util.ArrayList;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

public class MainMvpActivity extends BaseActivity {
    private ViewPager2 homeBanner, homeNotification;
    private ArrayList<Integer> banners;
    private ArrayList<String> notifications;

    private RecyclerView homeFunctions;
    private ArrayList<HomeFunction> functions;

    private RecyclerView homeBills;
    private ArrayList<BillBean> bills;

    private int bannerIndex = 0;
    private int notificationIndex = 0;
    private Handler handler = new Handler();
    private Runnable bannerRun = new Runnable() {
        @Override
        public void run() {
            bannerIndex++;
            if (bannerIndex == banners.size()) {
                bannerIndex = 0;
            }
            if (homeNotification != null) {
                homeBanner.setCurrentItem(bannerIndex, true);
            }
        }
    };
    private Runnable notificationRun = new Runnable() {
        @Override
        public void run() {
            notificationIndex++;
            if (notificationIndex == notifications.size()) {
                notificationIndex = 0;
            }
            if (homeNotification != null) {
                homeNotification.setCurrentItem(notificationIndex, true);
            }
        }
    };
    private int vpChangeMillis = 1500;

    @Override
    public int getContentViewId() {
        return R.layout.activity_mvp_main;
    }

    @Override
    public boolean showToolbar() {
        return false;
    }

    @Override
    public void initView() {


        homeBanner = findViewById(R.id.home_banner);
        banners = new ArrayList<>();
        banners.add(R.mipmap.icon_banner_1);
        banners.add(R.mipmap.icon_banner_2);
        banners.add(R.mipmap.icon_banner_3);
        BannerAdapter bannerAdapter = new BannerAdapter(this, banners);
        homeBanner.setAdapter(bannerAdapter);
        handler.postDelayed(bannerRun, vpChangeMillis);
        homeBanner.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    handler.postDelayed(bannerRun, vpChangeMillis);
                }
            }
        });

        homeNotification = findViewById(R.id.home_notification);
        notifications = new ArrayList<>();
        notifications.add("小时候，说：我等你长大。");
        notifications.add("长大后，说：你怎么长不大。");
        notifications.add("你不知道的是，我只是在你面前长不大。");
        NotificationAdapter notificationAdapter = new NotificationAdapter(this, notifications);
        homeNotification.setAdapter(notificationAdapter);
        handler.postDelayed(notificationRun, vpChangeMillis);
        homeNotification.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    handler.postDelayed(notificationRun, vpChangeMillis);
                }
            }
        });

        homeFunctions = findViewById(R.id.home_functions);
        functions = new ArrayList<>();
        HomeFunction a = new HomeFunction();
        a.setTitle("开发中");
        a.setIcon("aaa");
        functions.add(a);
        functions.add(a);
        functions.add(a);
        functions.add(a);
        functions.add(a);
        functions.add(a);
        functions.add(a);
        functions.add(a);
        FunctionAdapter functionAdapter = new FunctionAdapter(this, functions);
        homeFunctions.setLayoutManager(new GridLayoutManager(this, 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }

        });
        homeFunctions.addItemDecoration(new ItemDecorationHome(this, 5, R.color.cardview_light_background));
        homeFunctions.setAdapter(functionAdapter);

        homeBills = findViewById(R.id.home_bills);
        bills = new ArrayList<>();
        BillBean b = new BillBean();
        bills.add(b);
        bills.add(b);
        BillAdapter billAdapter = new BillAdapter(this, bills);
        homeBills.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }

        });
        homeBills.addItemDecoration(new ItemDecorationHome(this, 10, R.color.cardview_light_background));
        homeBills.setAdapter(billAdapter);
    }
}
