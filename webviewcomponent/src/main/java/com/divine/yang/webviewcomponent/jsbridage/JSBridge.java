package com.divine.yang.webviewcomponent.jsbridage;

import android.app.Activity;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

/**
 * @author yangzelong
 * @date 2019-06-21
 * @describe webView js调用的原生方法
 */
public class JSBridge {
    private JsMethods jsMethods;
    public JSBridge(Activity activity, WebView webView) {
        jsMethods = new JsMethods(activity, webView);
    }

    @JavascriptInterface
    public void runMethod(String methodName, String params) {
        switch (methodName) {
            case "setWebUrl":
                jsMethods.setWebUrl(params);
                break;
            case "webZoom":
                jsMethods.webZoom(params);
                break;
        }
    }
}
