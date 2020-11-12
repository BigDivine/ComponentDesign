package com.divine.yang.componentdesign;

import android.net.Uri;
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
@RouterUri(path = "toApp")
public class MyUriRouterHandler extends UriHandler {
    private String TAG = "MyUriRouterHandler";

    @Override
    protected boolean shouldHandle(@NonNull UriRequest request) {
        Uri uri = request.getUri();
        if (null != uri) {
            Log.e(TAG, uri.toString());
            return true;
        }
        return false;
    }

    @Override
    protected void handleInternal(@NonNull UriRequest request, @NonNull UriCallback callback) {
        Uri uri = request.getUri();
        if (null == uri) {
            Router.startUri(request.getContext(), RouterPaths.RouterLogin);
            callback.onComplete(UriResult.CODE_SUCCESS);
        } else {
//            callback.onComplete(UriResult.CODE_ERROR);
            callback.onComplete(UriResult.CODE_BAD_REQUEST);
        }
    }
}
