package com.divine.yang.module_main;

import android.content.Context;

import com.divine.yang.lib_common.SPUtils;
import com.sankuai.waimai.router.Router;
import com.sankuai.waimai.router.core.UriCallback;
import com.sankuai.waimai.router.core.UriInterceptor;
import com.sankuai.waimai.router.core.UriRequest;
import com.sankuai.waimai.router.core.UriResult;

import androidx.annotation.NonNull;

/**
 * Author: Divine
 * CreateDate: 2020/11/12
 * Describe:
 */
public class MainRouteInterceptors implements UriInterceptor {

    @Override
    public void intercept(@NonNull UriRequest request, @NonNull UriCallback callback) {
        Context mContext = request.getContext();
        boolean firstLaunch = (boolean) SPUtils.getInstance(mContext).get("first_launcher_app", false);
        if (firstLaunch) {
            Router.startUri(mContext, "trade_host://trade_scheme/trade_main");
        } else {
            Router.startUri(mContext, "login_host://login_scheme/login_main");
        }
        callback.onComplete(UriResult.CODE_SUCCESS);
    }
}
