package com.divine.yang.componentdesign;

public class SplashActivity extends com.divine.yang.module_splash.SplashActivity {
    @Override
    protected String getMainRouteUri() {
        return "main_scheme://main_host/main";
//        if (getIsFirstLaunchApp()) {
//            return RouterPaths.RouterTrade;
//        } else {
//            return RouterPaths.RouterLogin;
//        }
    }
}