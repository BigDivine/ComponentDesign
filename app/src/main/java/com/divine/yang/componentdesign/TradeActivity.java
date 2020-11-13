package com.divine.yang.componentdesign;

import android.util.Log;
import android.view.View;

import com.divine.yang.lib_base.base.BaseActivity;
import com.divine.yang.lib_common.SPUtils;
import com.sankuai.waimai.router.Router;
import com.sankuai.waimai.router.annotation.RouterUri;

import androidx.viewpager2.widget.ViewPager2;

@RouterUri(scheme = "trade_scheme", host = "trade_host", path = "/trade_main")
public class TradeActivity extends BaseActivity {
    private final String TAG = "divine_trade";

    @Override
    public int getContentViewId() {
        return R.layout.activity_trade;
    }

    @Override
    public boolean showToolbar() {
        return false;
    }

    @Override
    public void initView() {
        ViewPager2 vp = findViewById(R.id.trade_view);

        TradeAdapter adapter = new TradeAdapter(this);
        vp.setAdapter(adapter);
        Log.e(TAG, getPackageName());
        SPUtils mSPUtils = SPUtils.getInstance(this);
        mSPUtils.put("first_launch_app", false);
    }

    @Override
    public void getData() {

    }

    @Override
    public String[] requestPermissions() {
        return new String[0];
    }

    public void skipTrade(View view) {
        Router.startUri(this, RouterPaths.RouterMain);
        this.finish();
    }
}