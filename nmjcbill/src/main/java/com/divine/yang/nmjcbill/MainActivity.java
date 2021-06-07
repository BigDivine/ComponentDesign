package com.divine.yang.nmjcbill;

import com.divine.yang.module_splash.SplashActivity;
import com.divine.yang.nmjc_router.NmjcRouter;

public class MainActivity extends SplashActivity {

    @Override
    protected String getMainRouteUri() {
        return NmjcRouter.mainRouter;
    }
}

