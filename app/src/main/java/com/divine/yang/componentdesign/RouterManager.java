package com.divine.yang.componentdesign;

import android.content.Context;
import android.view.View;

import com.sankuai.waimai.router.Router;

/**
 * Author: Divine
 * CreateDate: 2020/11/9
 * Describe:
 */
public class RouterManager {
    private static RouterManager mRM;
    private Context mContext;

    private RouterManager(Context mContext) {
        this.mContext = mContext;
    }

    public static RouterManager getInstance(Context mContext) {
        if (null == mRM) {
            synchronized (RouterManager.class) {
                if (null == mRM) {
                    mRM = new RouterManager(mContext);

                }
            }
        }
        return mRM;
    }

    public void toBaseMain(View view) {
        Router.startUri(mContext, "base_scheme://base_host/base_demo_main");
    }

    public void toCamera2Main(View view) {
        Router.startUri(mContext, "camera2_scheme://camera2_host/camera2_demo_main");

    }

    public void toHttpMain(View view) {
        Router.startUri(mContext, "http_scheme://http_host/http_demo_main");
    }

    public void toImageEditMain(View view) {
        Router.startUri(mContext, "image_edit_scheme://image_edit_host/image_edit_demo_main");
    }

    public void toLoginMain(View view) {
        Router.startUri(mContext, "login_scheme://login_host/login_demo_main");
    }

    public void toSqliteMain(View view) {
        Router.startUri(mContext, "sqlite_scheme://sqlite_host/sqlite_demo_main");
    }

    public void toWebViewMain(View view) {
        Router.startUri(mContext, "webview_scheme://webview_host/webview_demo_main");
    }

    public void toWidgetMain(View view) {
        Router.startUri(mContext, "widget_scheme://widget_host/widget_demo_main");
    }
}
