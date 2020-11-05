package com.divine.yang.module_webview;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.divine.yang.lib_base.base.BaseActivity;
import com.divine.yang.lib_base.utils.ActivityUtils;
import com.divine.yang.module_webview.client.WebViewInterface;
import com.divine.yang.module_webview.constant.NmhkUrls;
import com.divine.yang.module_webview.util.WebViewUtil;
import com.sankuai.waimai.router.annotation.RouterUri;

import java.io.File;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;


/**
 * @author yangzelong
 * @date 2019-06-21
 * @describe webView页
 */
@RouterUri(scheme = "webview_scheme", host = "webview_host", path = "/webview_demo_main")
public class WebViewActivity extends BaseActivity implements WebViewInterface, View.OnClickListener, View.OnLongClickListener {

    private RelativeLayout container;
    private WebView webView;
    private ProgressBar progressBar;
    private View netErrorLayout;
    private View netLoadingLayout;
    private Button btnToSetting;
    private ImageView btnBack;

    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> uploadMessage;
    private static final int REQUEST_SELECT_FILE = 100;
    private static final int FILE_CHOOSER_RESULT_CODE = 2;
    private static final int CAMERA_RESULT_CODE = 1;

    private PopupWindow selectPhotoOrCamera;
    private static final String imgName = "orcImgTemp.png";
    private static final String imgPath = Environment.getExternalStorageDirectory() + "/Pictures" + "/jiuqi/camera/";
    private static final String contentProviderPackageName = "com.jiuqi.gmt.test.provider";
    boolean isClickMask = true;

    private String serverUrl;


    private String getServerUrl() {
        String url;

        if (TextUtils.isEmpty(NmhkUrls.url)) {
            url = NmhkUrls.base;
        } else {
            url = NmhkUrls.base + NmhkUrls.url + "&t=" + System.currentTimeMillis();
        }
        return url;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_web_view;
    }

    @Override
    public boolean showToolbar() {
        return false;
    }

    @Override
    public void initView() {
        container = findViewById(R.id.layout_container);
        progressBar = findViewById(R.id.progressBar);
        webView = findViewById(R.id.web_view);
        netErrorLayout = findViewById(R.id.net_error);
        netLoadingLayout = findViewById(R.id.net_loading);
        btnBack = netErrorLayout.findViewById(R.id.back_btn);
        btnToSetting = netErrorLayout.findViewById(R.id.to_setting_btn);

        btnBack.setOnClickListener(this);
        btnToSetting.setOnClickListener(this);
        webView.setOnLongClickListener(this);
    }

    @Override
    public void getData() {
        serverUrl = getServerUrl();
        WebViewUtil.initWebView(this, this, webView, progressBar, container, netLoadingLayout);
        webView.loadUrl(serverUrl);
    }

    @Override
    public String[] requestPermissions() {
        return new String[0];
    }

    /**
     * 照片选择完成后回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 5.0以上使用uploadMessage；5.0一下使用mUploadMessage
        switch (resultCode) {
            case RESULT_OK:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (requestCode == REQUEST_SELECT_FILE) {
                        //选择文件
                        if (uploadMessage == null) {
                            return;
                        }
                        uploadMessage.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, data));
                        uploadMessage = null;
                    } else if (requestCode == CAMERA_RESULT_CODE) {
                        //拍照
                        if (null == uploadMessage) {
                            return;
                        }
                        File img = new File(imgPath, imgName);
                        Uri imgUri = FileProvider.getUriForFile(WebViewActivity.this, contentProviderPackageName, img);
                        Uri[] uris = new Uri[]{imgUri};
                        uploadMessage.onReceiveValue(uris);
                        uploadMessage = null;
                    }
                } else if (requestCode == FILE_CHOOSER_RESULT_CODE) {
                    //5.0以下选择文件
                    if (null == mUploadMessage) {
                        return;
                    }
                    Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
                    mUploadMessage.onReceiveValue(result);
                    mUploadMessage = null;
                } else if (requestCode == CAMERA_RESULT_CODE) {
                    //5.0以下拍照
                    if (null == mUploadMessage) {
                        return;
                    }
                    Uri imgUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), imgName));
                    mUploadMessage.onReceiveValue(imgUri);
                    mUploadMessage = null;
                } else
                    Toast.makeText(getBaseContext(), "选择图片失败", Toast.LENGTH_LONG).show();
                break;
            case RESULT_CANCELED:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (null == mUploadMessage) {
                        return;
                    }
                    uploadMessage.onReceiveValue(null);
                    uploadMessage = null;
                } else {
                    if (null == mUploadMessage) {
                        return;
                    }
                    mUploadMessage.onReceiveValue(null);
                    mUploadMessage = null;
                }
                break;
        }

    }

    /**
     * 物理按键返回键拦截
     */
    @Override
    public void onBackPressed() {
        String webUrl = webView.getUrl();
        if (webUrl.endsWith("/home") || webUrl.contains("login") || webUrl.endsWith("about:blank")) {
            this.finish();
        } else {
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.back_btn) {
            this.finish();
        } else if (viewId == R.id.to_setting_btn) {
            Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
            startActivity(intent);
        }
    }

    /**
     * webview长按监听
     *
     * @param view
     * @return true表示消费掉事件
     */
    @Override
    public boolean onLongClick(View view) {
        return true;
    }

    /**
     * 弹出选择拍照还是选择照片的popwindow
     *
     * @param fileChooserParams
     * @param filePathCallback
     * @param uploadMsg
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void showSelectPhotoOrCamera(final ValueCallback<Uri[]> filePathCallback, final WebChromeClient.FileChooserParams fileChooserParams, final ValueCallback uploadMsg) {

        if (fileChooserParams.isCaptureEnabled()) {
            toCamera(filePathCallback, uploadMsg);
            return;
        }
        View contentView = LayoutInflater.from(this).inflate(R.layout.popup_select_photo_carema, null);
        TextView album = contentView.findViewById(R.id.select_img_album);
        TextView camera = contentView.findViewById(R.id.select_img_camera);
        TextView cancel = contentView.findViewById(R.id.select_img_cancel);

        selectPhotoOrCamera = new PopupWindow(this);
        selectPhotoOrCamera.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        selectPhotoOrCamera.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        selectPhotoOrCamera.setTouchable(true);
        selectPhotoOrCamera.setFocusable(true);
        selectPhotoOrCamera.setContentView(contentView);
        selectPhotoOrCamera.setBackgroundDrawable(new ColorDrawable(0));//new ColorDrawable(0)即为透明背景
        selectPhotoOrCamera.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ActivityUtils.setBackgroundAlpha(WebViewActivity.this, 1f);
                //解决点击选择图片按钮无法重复回调的问题。
                if (isClickMask) {
                    resetUploadMsg(filePathCallback, uploadMsg);
                }
                isClickMask = true;
            }
        });
        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isClickMask = false;
                toAlbum(filePathCallback, fileChooserParams, uploadMsg);
                selectPhotoOrCamera.dismiss();
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isClickMask = false;
                toCamera(filePathCallback, uploadMsg);
                selectPhotoOrCamera.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isClickMask = false;
                selectPhotoOrCamera.dismiss();
                //解决点击选择图片按钮无法重复回调的问题。
                resetUploadMsg(filePathCallback, uploadMsg);
                ActivityUtils.setBackgroundAlpha(WebViewActivity.this, 1f);
            }
        });
        selectPhotoOrCamera.showAtLocation(contentView.getRootView(), Gravity.BOTTOM, 0, 0);
        ActivityUtils.setBackgroundAlpha(this, 0.5f);
    }

    private void toCamera(ValueCallback<Uri[]> filePathCallback, ValueCallback uploadMsg) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (uploadMessage != null) {
                uploadMessage.onReceiveValue(null);
                uploadMessage = null;
            }
            uploadMessage = filePathCallback;
        } else {
            mUploadMessage = uploadMsg;
        }

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri imgUri;
        File img = new File(imgPath, imgName);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imgUri = FileProvider.getUriForFile(WebViewActivity.this, contentProviderPackageName, img);
        } else {
            imgUri = Uri.fromFile(img);
        }
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
        startActivityForResult(cameraIntent, CAMERA_RESULT_CODE);
    }

    private void toAlbum(ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams, ValueCallback uploadMsg) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (uploadMessage != null) {
                uploadMessage.onReceiveValue(null);
                uploadMessage = null;
            }
            uploadMessage = filePathCallback;
        } else {
            mUploadMessage = uploadMsg;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Intent intent = fileChooserParams.createIntent();
            startActivityForResult(intent, REQUEST_SELECT_FILE);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            startActivityForResult(Intent.createChooser(i, "File Browser"), FILE_CHOOSER_RESULT_CODE);
        }
    }

    private void resetUploadMsg(ValueCallback<Uri[]> filePathCallback, ValueCallback uploadMsg) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            uploadMessage = filePathCallback;
            if (uploadMessage == null) {
                return;
            }
            uploadMessage.onReceiveValue(null);
            uploadMessage = null;
        } else {
            mUploadMessage = uploadMsg;
            if (null == mUploadMessage) {
                return;
            }
            mUploadMessage.onReceiveValue(null);
            mUploadMessage = null;
        }
    }

}
