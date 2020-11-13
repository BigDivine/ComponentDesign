package com.divine.yang.module_login;

import android.content.Context;
import android.content.Intent;

import com.sankuai.waimai.router.Router;
import com.sankuai.waimai.router.annotation.RouterUri;
import com.sankuai.waimai.router.core.UriCallback;
import com.sankuai.waimai.router.core.UriHandler;
import com.sankuai.waimai.router.core.UriRequest;
import com.sankuai.waimai.router.core.UriResult;

import androidx.annotation.NonNull;

/**
 * Author: Divine
 * CreateDate: 2020/11/13
 * Describe:
 */
@RouterUri(scheme = "login_scheme", host = "login_host", path = "/login_main",
        interceptors = LoginUriInterceptor.class)
public class LoginUriHandler extends UriHandler {
    @Override
    protected boolean shouldHandle(@NonNull UriRequest request) {
        return true;
    }

    @Override
    protected void handleInternal(@NonNull UriRequest request, @NonNull UriCallback callback) {
        if (null != request) {
            Router.startUri(request);
            callback.onComplete(UriResult.CODE_SUCCESS);
        } else {
            callback.onComplete(UriResult.CODE_ERROR);
        }
    }
}
