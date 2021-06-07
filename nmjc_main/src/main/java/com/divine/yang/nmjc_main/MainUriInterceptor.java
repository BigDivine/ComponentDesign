package com.divine.yang.nmjc_main;

import android.content.Context;

import com.divine.yang.lib_common.SPUtils;
import com.divine.yang.nmjc_router.NmjcRouter;
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
public class MainUriInterceptor implements UriInterceptor {
    private final String TAG="MainUriInterceptor";

    @Override
    public void intercept(@NonNull UriRequest request, @NonNull UriCallback callback) {
        Context mContext = request.getContext();
        boolean isLogin = (boolean) SPUtils.getInstance(mContext).get("is_login", false);
         if (!isLogin) {
            Router.startUri(mContext, NmjcRouter.loginRouter);
        } else {
            callback.onNext();
        }
        callback.onComplete(UriResult.CODE_SUCCESS);
    }
}
