package com.divine.yang.module_main;

import android.util.Log;

import com.sankuai.waimai.router.annotation.RouterUri;
import com.sankuai.waimai.router.core.UriCallback;
import com.sankuai.waimai.router.core.UriHandler;
import com.sankuai.waimai.router.core.UriRequest;

import androidx.annotation.NonNull;

/**
 * Author: Divine
 * CreateDate: 2020/11/12
 * Describe:
 */
@RouterUri(host = "main_host",scheme = "main_scheme",path = "/main",
        interceptors = MainRouteInterceptors.class)
public class MainUriHandler extends UriHandler {
    @Override
    protected boolean shouldHandle(@NonNull UriRequest request) {
        Log.e("aaa", request.getUri().toString());
        return false;
    }

    @Override
    protected void handleInternal(@NonNull UriRequest request, @NonNull UriCallback callback) {

    }
}
