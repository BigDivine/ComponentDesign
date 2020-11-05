package com.divine.yang.module_webview.jsbridage;

import android.app.Activity;
import android.os.Handler;
import android.webkit.WebView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author: Divine
 * <p>
 * date: 2019/10/24
 */
public class JsMethods {
    private Activity activity;

    private WebView webView;

    public JsMethods(Activity activity, WebView webView) {
        this.activity = activity;
        this.webView = webView;
    }

    public void setWebUrl(String url) {
        webView.loadUrl(url);
    }

    public void webZoom(String zoomIndex) {
        //控制缩放比例，不响应系统字体
        final int zoom = Integer.parseInt(zoomIndex);
        new Handler(activity.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                webView.getSettings().setTextZoom(zoom);
            }
        });

    }

    /**
     * 去除数据换行
     *
     * @param src
     * @return
     */
    public String replaceBlank(String src) {
        String dest = "";
        if (src != null) {
            Pattern pattern = Pattern.compile("\t|\r|\n|\\s*");
            Matcher matcher = pattern.matcher(src);
            dest = matcher.replaceAll("");
        }
        return dest;
    }
}
