package com.divine.yang.nmjc_main;

import android.content.Context;
import android.content.Intent;

import com.divine.yang.nmjc_main.mvp.MainMvpActivity;
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
@RouterUri(scheme = "nmjc", host = "nmjc_host", path = "/main",        interceptors = MainUriInterceptor.class)
public class MainUriHandler extends UriHandler {
    @Override
    protected boolean shouldHandle(@NonNull UriRequest request) {
        return true;
    }

    @Override
    protected void handleInternal(@NonNull UriRequest request, @NonNull UriCallback callback) {
        Context context = request.getContext();
        context.startActivity(new Intent(context, MainMvpActivity.class));
        callback.onComplete(UriResult.CODE_SUCCESS);
    }
}
