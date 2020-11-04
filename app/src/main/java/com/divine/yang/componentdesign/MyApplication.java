package com.divine.yang.componentdesign;

import android.app.Application;
import android.content.Context;

import com.sankuai.waimai.router.Router;
import com.sankuai.waimai.router.common.DefaultRootUriHandler;
import com.sankuai.waimai.router.components.DefaultLogger;
import com.sankuai.waimai.router.core.Debugger;

/**
 * Author: Divine
 * CreateDate: 2020/10/19
 * Describe:
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Context context = getApplicationContext();
        // 创建RootHandler
        DefaultRootUriHandler rootHandler = new DefaultRootUriHandler(context);
        // 初始化
        Router.init(rootHandler);

        Debugger.setLogger(DefaultLogger.INSTANCE);
        Debugger.setEnableDebug(true);
        Debugger.setEnableLog(true);

    }
}
