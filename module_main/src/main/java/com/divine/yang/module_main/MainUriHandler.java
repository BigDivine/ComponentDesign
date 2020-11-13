package com.divine.yang.module_main;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.sankuai.waimai.router.Router;
import com.sankuai.waimai.router.annotation.RouterUri;
import com.sankuai.waimai.router.core.UriCallback;
import com.sankuai.waimai.router.core.UriHandler;
import com.sankuai.waimai.router.core.UriRequest;
import com.sankuai.waimai.router.core.UriResult;

import androidx.annotation.NonNull;

/**
 * Author: Divine
 * CreateDate: 2020/11/12
 * Describe:
 */
@RouterUri(scheme = "main_scheme", host = "main_host", path = "/main",
        interceptors = MainUriInterceptor.class)
public class MainUriHandler extends UriHandler {
    @Override
    protected boolean shouldHandle(@NonNull UriRequest request) {
        Log.e("aaa", request.getUri().toString());
        return true;
    }

    @Override
    protected void handleInternal(@NonNull UriRequest request, @NonNull UriCallback callback) {
        Log.e("bbb", request.getUri().toString());
        Context context = request.getContext();
        context.startActivity(new Intent(context, MainActivity.class));
        callback.onComplete(UriResult.CODE_SUCCESS);
        //        Router.startUri(request.getContext(), "login_scheme://login_host/login_main");

    }
}
