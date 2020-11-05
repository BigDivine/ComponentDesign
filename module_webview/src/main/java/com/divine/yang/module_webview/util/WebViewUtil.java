package com.divine.yang.module_webview.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.divine.yang.module_webview.client.MyWebChromeClient;
import com.divine.yang.module_webview.client.MyWebViewClient;
import com.divine.yang.module_webview.client.WebViewInterface;
import com.divine.yang.module_webview.jsbridage.JSBridge;


/**
 * Author: Divine
 * CreateDate: 2020/8/18
 * Describe:
 */
public class WebViewUtil {
    private static MyWebViewClient mWebViewClient;
    private static MyWebChromeClient mWebChromeClient;

    public static void initWebView(Activity activity, WebViewInterface webViewInterface, WebView webview, ProgressBar progressBar, RelativeLayout container, View netLoadingLayout) {
        mWebViewClient = new MyWebViewClient(activity, netLoadingLayout);
        mWebChromeClient = new MyWebChromeClient(activity, webViewInterface, progressBar, container, netLoadingLayout);

        setWebSettings(activity, webview.getSettings());
        //注入jsbridge。用于js调用原生方法。
        addJavascriptInterfaces(activity, webview);

        webview.setHorizontalScrollBarEnabled(false);//水平不显示
        webview.setVerticalScrollBarEnabled(false); //垂直不显示
        webview.setWebViewClient(mWebViewClient.getWebViewClient());
        webview.setWebChromeClient(mWebChromeClient.getWebChromeClient());
    }

    /**
     * 添加js interface
     * <p>
     * 在vue中调用方式为：直接调用--android.方法名
     */
    private static void addJavascriptInterfaces(Activity activity, WebView webview) {
        webview.addJavascriptInterface(new JSBridge(activity, webview), "android");
    }

    /**
     * 配置websetting
     *
     * @param webSettings
     */
    private static void setWebSettings(Context context, WebSettings webSettings) {
        //调整图片至适合webview的大小
        webSettings.setUseWideViewPort(true);
        // 缩放至屏幕的大小
        webSettings.setLoadWithOverviewMode(true);
        //控制缩放比例，100为不响应系统字体
        webSettings.setTextZoom(100);
        //设置默认编码
        webSettings.setDefaultTextEncodingName("utf-8");
        ////设置自动加载图片
        webSettings.setLoadsImagesAutomatically(true);
        //多窗口
        webSettings.supportMultipleWindows();
        //允许访问文件
        webSettings.setAllowFileAccess(true);
        //开启javascript
        webSettings.setJavaScriptEnabled(true);
        //支持通过JS打开新窗口
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //支持手势对页面进行缩放
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        //提高渲染的优先级
        //        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        //支持内容重新布局
        //        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //webview中缓存
        //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        //            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //        } else {
        //                    webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        //        }
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(false);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setSupportMultipleWindows(true);// 设置允许开启多窗口

    }

}
