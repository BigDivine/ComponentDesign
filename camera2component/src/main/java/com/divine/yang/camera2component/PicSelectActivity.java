package com.divine.yang.camera2component;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.divine.yang.basecomponent.base.BaseFragmentActivity;
import com.divine.yang.camera2component.imageselect.Constant;
import com.divine.yang.camera2component.imageselect.FileUtils;
import com.divine.yang.camera2component.imageselect.ImageListCallback;
import com.divine.yang.camera2component.imageselect.PicSelectCallback;
import com.divine.yang.camera2component.imageselect.PicSelectConfig;
import com.divine.yang.camera2component.imageselect.PicSelectFragment;

import java.io.File;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

/**
 * Author: Divine
 * CreateDate: 2020/10/20
 * Describe:
 */
public class PicSelectActivity extends BaseFragmentActivity implements View.OnClickListener, PicSelectCallback {

    public static final String INTENT_RESULT = "result";
    private static final int IMAGE_CROP_CODE = 1;
    private static final int STORAGE_REQUEST_CODE = 1;

    private PicSelectConfig mPicSelectConfig;

    private RelativeLayout rlTitleBar;
    private TextView tvTitle;
    private Button btnConfirm;
    private ImageView ivBack;
    private String cropImagePath;

    private PicSelectFragment mPicSelectFragment;

    private ArrayList<String> result = new ArrayList<>();

    public static void startForResult(Activity activity, PicSelectConfig mPicSelectConfig, int RequestCode) {
        Intent intent = new Intent(activity, PicSelectActivity.class);
        intent.putExtra("config", mPicSelectConfig);
        activity.startActivityForResult(intent, RequestCode);
    }

    public static void startForResult(Fragment fragment, PicSelectConfig mPicSelectConfig, int RequestCode) {
        Intent intent = new Intent(fragment.getActivity(), PicSelectActivity.class);
        intent.putExtra("config", mPicSelectConfig);
        fragment.startActivityForResult(intent, RequestCode);
    }

    public static void startForResult(android.app.Fragment fragment, PicSelectConfig mPicSelectConfig, int RequestCode) {
        Intent intent = new Intent(fragment.getActivity(), PicSelectActivity.class);
        intent.putExtra("config", mPicSelectConfig);
        fragment.startActivityForResult(intent, RequestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_select_layout);


        // Android 6.0 checkSelfPermission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                              STORAGE_REQUEST_CODE);
        } else {
            mPicSelectFragment = PicSelectFragment.instance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fmImageList, mPicSelectFragment, null)
                    .commit();
        }

        initView();
        if (!FileUtils.isSdCardAvailable()) {
            Toast.makeText(this, "SD卡不可用", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_pic_select_layout;
    }

    @Override
    public boolean showToolbar() {
        return false;
    }

    @Override
    public View getToolbar() {
        return null;
    }
    @Override
    public void initView() {
        rlTitleBar = (RelativeLayout) findViewById(R.id.rlTitleBar);
        tvTitle = (TextView) findViewById(R.id.tvTitle);

        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(this);

        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);

        if (mPicSelectConfig != null) {
            if (mPicSelectConfig.backResId != -1) {
                ivBack.setImageResource(mPicSelectConfig.backResId);
            }

            if (mPicSelectConfig.statusBarColor != -1) {
                StatusBarCompat.compat(this, mPicSelectConfig.statusBarColor);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                        && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                }
            }
            rlTitleBar.setBackgroundColor(mPicSelectConfig.titleBgColor);
            tvTitle.setTextColor(mPicSelectConfig.titleColor);
            tvTitle.setText(mPicSelectConfig.title);
            btnConfirm.setBackgroundColor(mPicSelectConfig.btnBgColor);
            btnConfirm.setTextColor(mPicSelectConfig.btnTextColor);

            if (mPicSelectConfig.multiSelect) {
                if (!mPicSelectConfig.rememberSelected) {
                    Constant.imageList.clear();
                }
                btnConfirm.setText(String.format("%1$s(%2$d/%3$d)", mPicSelectConfig.btnText, Constant.imageList.size(), mPicSelectConfig.maxNum));
            } else {
                Constant.imageList.clear();
                btnConfirm.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void getData() {
        mPicSelectConfig = (PicSelectConfig) getIntent().getSerializableExtra("config");

    }

    @Override
    public String[] requestPermissions() {
        return new String[0];
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnConfirm) {
            if (Constant.imageList != null && !Constant.imageList.isEmpty()) {
                exit();
            } else {
                Toast.makeText(this,"最少选择一张图片", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.ivBack) {
            onBackPressed();
        }
    }

    @Override
    public void onSingleImageSelected(String path) {
        if (mPicSelectConfig.needCrop) {
            crop(path);
        } else {
            Constant.imageList.add(path);
            exit();
        }
    }

    @Override
    public void onImageSelected(String path) {
        btnConfirm.setText(String.format("%1$s(%2$d/%3$d)", mPicSelectConfig.btnText, Constant.imageList.size(), mPicSelectConfig.maxNum));
    }

    @Override
    public void onImageUnselected(String path) {
        btnConfirm.setText(String.format("%1$s(%2$d/%3$d)", mPicSelectConfig.btnText, Constant.imageList.size(), mPicSelectConfig.maxNum));
    }

    @Override
    public void onCameraShot(File imageFile) {
        if (imageFile != null) {
            if (mPicSelectConfig.needCrop) {
                crop(imageFile.getAbsolutePath());
            } else {
                Constant.imageList.add(imageFile.getAbsolutePath());
                mPicSelectConfig.multiSelect = false; // 多选点击拍照，强制更改为单选
                exit();
            }
        }
    }

    @Override
    public void onPreviewChanged(int select, int sum, boolean visible) {
        if (visible) {
            tvTitle.setText(select + "/" + sum);
        } else {
            tvTitle.setText(mPicSelectConfig.title);
        }
    }

    private void crop(String imagePath) {
        File file = new File(FileUtils.createRootPath(this) + "/" + System.currentTimeMillis() + ".jpg");

        cropImagePath = file.getAbsolutePath();
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(getImageContentUri(new File(imagePath)), "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", mPicSelectConfig.aspectX);
        intent.putExtra("aspectY", mPicSelectConfig.aspectY);
        intent.putExtra("outputX", mPicSelectConfig.outputX);
        intent.putExtra("outputY", mPicSelectConfig.outputY);
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, IMAGE_CROP_CODE);
    }

    public Uri getImageContentUri(File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                                           .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    public PicSelectConfig getConfig() {
        return mPicSelectConfig;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_CROP_CODE && resultCode == RESULT_OK) {
            Constant.imageList.add(cropImagePath);
            mPicSelectConfig.multiSelect = false; // 多选点击拍照，强制更改为单选
            exit();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private static ImageListCallback imageListCallback;

    public static void setImageListCallback(ImageListCallback imageListCallback) {
        PicSelectActivity.imageListCallback = imageListCallback;
    }

    public void exit() {
        finish();
        result.clear();
        result.addAll(Constant.imageList);
        imageListCallback.getImageList(result);
        if (!mPicSelectConfig.multiSelect) {
            Constant.imageList.clear();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case STORAGE_REQUEST_CODE:
                if (grantResults.length >= 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.fmImageList,PicSelectFragment.instance(), null)
                            .commitAllowingStateLoss();
                } else {
                    Toast.makeText(this,"请打开存储空间权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("config", mPicSelectConfig);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mPicSelectConfig = (PicSelectConfig) savedInstanceState.getSerializable("config");
    }

    @Override
    public void onBackPressed() {
        if (mPicSelectFragment == null || !mPicSelectFragment.hidePreview()) {
            Constant.imageList.clear();
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
