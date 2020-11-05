package com.divine.yang.module_webview.client;

import android.net.Uri;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;

/**
 * Author: Divine
 * CreateDate: 2020/8/19
 * Describe:
 */
public interface WebViewInterface {
  void  showSelectPhotoOrCamera(ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams, ValueCallback uploadMsg);
}
