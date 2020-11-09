package com.divine.yang.componentdesign;

import com.divine.yang.lib_base.base.BaseActivity;
import com.sankuai.waimai.router.annotation.RouterUri;

import androidx.viewpager2.widget.ViewPager2;

@RouterUri(scheme = "trade_scheme", host = "trade_host", path = "/trade_main")
public class TradeActivity extends BaseActivity {

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
    }

    @Override
    public void getData() {

    }

    @Override
    public String[] requestPermissions() {
        return new String[0];
    }
}