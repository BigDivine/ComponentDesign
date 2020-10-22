package com.divine.yang.camera2component;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.divine.yang.basecomponent.BaseDemoActivity;
import com.divine.yang.basecomponent.base.BaseActivity;
import com.divine.yang.basecomponent.getpermission.PermissionList;
import com.divine.yang.camera2component.imageselect.FileUtils;
import com.divine.yang.camera2component.imageselect.PicSelectConfig;
import com.divine.yang.camera2component.imageselect.PicSelectFragment;
import com.divine.yang.camera2component.imageselect.PicSelectStaticVariable;
import com.divine.yang.camera2component.imageselect.interfaces.PicSelectCallback;
import com.divine.yang.camera2component.imageselect.interfaces.PicSelectListener;

import java.io.File;
import java.util.ArrayList;

/**
 * Author: Divine
 * CreateDate: 2020/10/20
 * Describe:
 */
public class PicSelectActivity extends BaseActivity implements View.OnClickListener, PicSelectListener {

    private static final int IMAGE_CROP_CODE = 1;
    private String mCropImagePath;

    private RelativeLayout mPicSelectHeaderLayout;
    private TextView mPicSelectHeaderTitle;
    private Button mPicSelectHeaderConfirm;
    private ImageButton mPicSelectHeaderBack;

    private PicSelectConfig mPicSelectConfig;
    private PicSelectFragment mPicSelectFragment;
    private ArrayList<String> mPicSelectImagesResult = new ArrayList<>();

    private static PicSelectCallback mPicSelectCallback;

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
        View actionBar = LayoutInflater.from(this).inflate(com.divine.yang.basecomponent.R.layout.action_bar_layout, null, false);
        View actionBarRes = LayoutInflater.from(this).inflate(com.divine.yang.basecomponent.R.layout.action_bar_normal, null, false);
        LinearLayout actionBarContain = actionBar.findViewById(com.divine.yang.basecomponent.R.id.action_bar_res);
        actionBarContain.removeAllViews();
        actionBarContain.addView(actionBarRes);

        View leftLayout = actionBarRes.findViewById(com.divine.yang.basecomponent.R.id.normal_action_bar_left);
        leftLayout.setVisibility(View.VISIBLE);
        leftLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PicSelectActivity.this, "left layout button click", Toast.LENGTH_SHORT).show();
                PicSelectActivity.this.finish();
            }
        });

        View centerLayout = actionBarRes.findViewById(com.divine.yang.basecomponent.R.id.normal_action_bar_center);
        centerLayout.setVisibility(View.VISIBLE);
        TextView headerTitle = centerLayout.findViewById(com.divine.yang.basecomponent.R.id.normal_action_bar_title);
        headerTitle.setText("照片");

        View rightLayout = actionBarRes.findViewById(com.divine.yang.basecomponent.R.id.normal_action_bar_right);
        rightLayout.setVisibility(View.VISIBLE);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        actionBar.setLayoutParams(params);

        return actionBar;
    }

    @Override
    public void initView() {
        mPicSelectConfig = (PicSelectConfig) getIntent().getSerializableExtra("config");

        mPicSelectFragment = PicSelectFragment.instance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.pic_select_images_frame, mPicSelectFragment, null)
                .commit();

        mPicSelectHeaderLayout = findViewById(R.id.pic_select_header_layout);
        mPicSelectHeaderTitle = findViewById(R.id.pic_select_header_title);
        mPicSelectHeaderConfirm = findViewById(R.id.pic_select_header_confirm);
        mPicSelectHeaderBack = findViewById(R.id.pic_select_header_back);
        mPicSelectHeaderConfirm.setOnClickListener(this);
        mPicSelectHeaderBack.setOnClickListener(this);

        if (mPicSelectConfig != null) {
            initConfig();
        }
        if (!FileUtils.isSdCardAvailable()) {
            Toast.makeText(this, "SD卡不可用", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 根据config配置页面
     */
    private void initConfig() {
        if (mPicSelectConfig.backResId != -1) {
            mPicSelectHeaderBack.setImageResource(mPicSelectConfig.backResId);
        }
        if (mPicSelectConfig.statusBarColor != -1) {
            StatusBarCompat.compat(this, mPicSelectConfig.statusBarColor);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                    && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            }
        }
        mPicSelectHeaderLayout.setBackgroundColor(mPicSelectConfig.titleBgColor);
        mPicSelectHeaderTitle.setTextColor(mPicSelectConfig.titleColor);
        mPicSelectHeaderTitle.setText(mPicSelectConfig.title);
        mPicSelectHeaderConfirm.setBackgroundColor(mPicSelectConfig.btnBgColor);
        mPicSelectHeaderConfirm.setTextColor(mPicSelectConfig.btnTextColor);
        if (mPicSelectConfig.multiSelect) {
            if (!mPicSelectConfig.rememberSelected) {
                PicSelectStaticVariable.mPicSelectImageList.clear();
            }
            mPicSelectHeaderConfirm.setText(String.format("%1$s(%2$d/%3$d)", mPicSelectConfig.btnText, PicSelectStaticVariable.mPicSelectImageList.size(), mPicSelectConfig.maxNum));
        } else {
            PicSelectStaticVariable.mPicSelectImageList.clear();
            mPicSelectHeaderConfirm.setVisibility(View.GONE);
        }
    }

    @Override
    public void getData() {
        mPicSelectConfig = (PicSelectConfig) getIntent().getSerializableExtra("config");
    }

    @Override
    public String[] requestPermissions() {
        return new String[]{PermissionList.WRITE_EXTERNAL_STORAGE};
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.pic_select_header_confirm) {
            if (PicSelectStaticVariable.mPicSelectImageList != null && !PicSelectStaticVariable.mPicSelectImageList.isEmpty()) {
                exitPicSelectActivity();
            } else {
                Toast.makeText(this, "最少选择一张图片", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.pic_select_header_back) {
            onBackPressed();
        }
    }

    @Override
    public void onSingleImageSelected(String path) {
        if (mPicSelectConfig.needCrop) {
            cropPicSelectImage(path);
        } else {
            PicSelectStaticVariable.mPicSelectImageList.add(path);
            exitPicSelectActivity();
        }
    }

    @Override
    public void onImageSelected(String path) {
        mPicSelectHeaderConfirm.setText(String.format("%1$s(%2$d/%3$d)", mPicSelectConfig.btnText, PicSelectStaticVariable.mPicSelectImageList.size(), mPicSelectConfig.maxNum));
    }

    @Override
    public void onImageUnselected(String path) {
        mPicSelectHeaderConfirm.setText(String.format("%1$s(%2$d/%3$d)", mPicSelectConfig.btnText, PicSelectStaticVariable.mPicSelectImageList.size(), mPicSelectConfig.maxNum));
    }

    @Override
    public void onCameraShot(File imageFile) {
        if (imageFile != null) {
            if (mPicSelectConfig.needCrop) {
                cropPicSelectImage(imageFile.getAbsolutePath());
            } else {
                PicSelectStaticVariable.mPicSelectImageList.add(imageFile.getAbsolutePath());
                mPicSelectConfig.multiSelect = false; // 多选点击拍照，强制更改为单选
                exitPicSelectActivity();
            }
        }
    }

    @Override
    public void onPreviewChanged(int select, int sum, boolean visible) {
        if (visible) {
            mPicSelectHeaderTitle.setText(select + "/" + sum);
        } else {
            mPicSelectHeaderTitle.setText(mPicSelectConfig.title);
        }
    }

    private void cropPicSelectImage(String imagePath) {
        File file = new File(FileUtils.createRootPath(this) + "/" + System.currentTimeMillis() + ".jpg");

        mCropImagePath = file.getAbsolutePath();
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
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_CROP_CODE && resultCode == RESULT_OK) {
            PicSelectStaticVariable.mPicSelectImageList.add(mCropImagePath);
            mPicSelectConfig.multiSelect = false; // 多选点击拍照，强制更改为单选
            exitPicSelectActivity();
        }
        super.onActivityResult(requestCode, resultCode, data);
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
            PicSelectStaticVariable.mPicSelectImageList.clear();
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    public void exitPicSelectActivity() {
        finish();
        mPicSelectImagesResult.clear();
        mPicSelectImagesResult.addAll(PicSelectStaticVariable.mPicSelectImageList);
        mPicSelectCallback.getImageList(mPicSelectImagesResult);
        if (!mPicSelectConfig.multiSelect) {
            PicSelectStaticVariable.mPicSelectImageList.clear();
        }
    }

    public static void setPicSelectCallback(PicSelectCallback mPicSelectCallback) {
        PicSelectActivity.mPicSelectCallback = mPicSelectCallback;
    }

    public PicSelectConfig getConfig() {
        return mPicSelectConfig;
    }
}
