package com.divine.yang.webviewcomponent.client;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.divine.yang.webviewcomponent.util.WebViewUtil;

import androidx.appcompat.app.AlertDialog;

/**
 * Author: Divine
 * CreateDate: 2020/8/18
 * Describe:
 */
public class MyWebChromeClient {
    public WebChromeClient mWebChromeClient;
    public WebView newWebView;

    public MyWebChromeClient(final Activity activity, final WebViewInterface webViewInterface, final ProgressBar progressBar, final RelativeLayout container, final View netLoadingLayout) {
        mWebChromeClient = new WebChromeClient() {
            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                newWebView = new WebView(activity);
                newWebView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                WebViewUtil.initWebView(activity, webViewInterface, newWebView, progressBar, container, netLoadingLayout);//初始化webview
                container.addView(newWebView);//把webview加载到activity界面上
                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;//以下的操作应该就是让新的webview去加载对应的url等操作。
                transport.setWebView(newWebView);
                resultMsg.sendToTarget();
                return true;
            }

            @Override
            public void onCloseWindow(WebView window) {
                if (newWebView != null) {
                    container.removeView(newWebView);//把webview加载到activity界面上
                }
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                Toast.makeText(activity, "alert", Toast.LENGTH_SHORT).show();
                //js Alert提示框拦截并修改样式
                AlertDialog.Builder b = new AlertDialog.Builder(activity);
                b.setTitle("提示");
                b.setMessage(message);
                b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                b.setCancelable(false);
                b.create().show();
                return true;
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
                //js Confirm提示框拦截并修改样式
                AlertDialog.Builder b = new AlertDialog.Builder(activity);
                b.setTitle("提示");
                b.setMessage(message);
                b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                b.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.cancel();
                    }
                });
                b.setCancelable(false);
                b.create().show();
                return true;
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                //js Prompt提示框拦截并修改样式
                Toast.makeText(activity, "Prompt提示框拦截,若样式不合适，请到原生代码进行定制", Toast.LENGTH_SHORT).show();
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }

            // For Lollipop 5.0+ Devices
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, final FileChooserParams fileChooserParams) {
                webViewInterface.showSelectPhotoOrCamera(filePathCallback, fileChooserParams, null);
                return true;
            }

            @Override
            public void onProgressChanged(final WebView view, final int newProgress) {
                //显示进度条
                progressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    //加载完毕隐藏进度条
                    progressBar.setVisibility(View.GONE);
                    netLoadingLayout.setVisibility(View.GONE);
                }
                super.onProgressChanged(view, newProgress);
            }
        };
    }

    public WebChromeClient getWebChromeClient() {
        return mWebChromeClient;
    }
}
