package com.divine.yang.module_login;

import android.content.Context;
import android.util.Log;

import com.divine.yang.lib_common.SPUtils;
import com.sankuai.waimai.router.Router;
import com.sankuai.waimai.router.core.UriCallback;
import com.sankuai.waimai.router.core.UriInterceptor;
import com.sankuai.waimai.router.core.UriRequest;
import com.sankuai.waimai.router.core.UriResult;

import androidx.annotation.NonNull;

/**
 * Author: Divine
 * CreateDate: 2020/11/13
 * Describe:
 */
public class LoginUriInterceptor implements UriInterceptor {
    private final String TAG="LoginUriIntercept";
    @Override
    public void intercept(@NonNull UriRequest request, @NonNull UriCallback callback) {
//        Context mContext = request.getContext();
//        boolean firstLaunch = (boolean) SPUtils.getInstance(mContext).get("first_launch_app", false);
//        if (firstLaunch) {
//            Router.startUri(mContext, "trade_scheme://trade_host/trade_main");
//        } else {
            callback.onNext();
//        }
        callback.onComplete(UriResult.CODE_SUCCESS);
    }
}
