package com.divine.yang.camera2component;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import com.divine.yang.lib_base.base.BaseActivity;
import com.divine.yang.lib_base.base.BaseToolbar;
import com.divine.yang.lib_base.base.ToolbarClickListener;
import com.divine.yang.lib_base.getpermission.PermissionList;
import com.divine.yang.commonutils.FileUtils;
import com.divine.yang.camera2component.imageselect.PicSelectConfig;
import com.divine.yang.camera2component.imageselect.PicSelectStaticVariable;
import com.divine.yang.camera2component.imageselect.interfaces.PicSelectListener;

import java.io.File;
import java.util.ArrayList;

import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * Author: Divine
 * CreateDate: 2020/10/20
 * Describe:
 */
public class PicSelectActivity extends BaseActivity implements PicSelectListener {

    private static final int IMAGE_CROP_CODE = 1;
    private String mCropImagePath;

    private ConstraintLayout mPicSelectHeaderLayout;

    private PicSelectConfig mPicSelectConfig;
    private PicSelectFragment mPicSelectFragment;
    private ArrayList<String> mPicSelectImagesResult = new ArrayList<>();

    @Override
    public int getContentViewId() {
        return R.layout.activity_pic_select_layout;
    }

    @Override
    public boolean showToolbar() {
        return true;
    }

    private BaseToolbar mBaseToolbar;

    @Override
    public void initView() {
        mPicSelectConfig = (PicSelectConfig) getIntent().getSerializableExtra("config");
        mPicSelectFragment = PicSelectFragment.instance();
        Bundle bundle = new Bundle();
        bundle.putSerializable("config", mPicSelectConfig);
        mPicSelectFragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.pic_select_images_frame, mPicSelectFragment, null)
                .commit();

        mBaseToolbar = getBaseToolbar();
        mBaseToolbar.setToolbarClickListener(new ToolbarClickListener() {
            @Override
            public void leftClick() {
                onBackPressed();

            }

            @Override
            public void centerClick() {

            }

            @Override
            public void rightClick() {
                if (PicSelectStaticVariable.mPicSelectImageList != null && !PicSelectStaticVariable.mPicSelectImageList.isEmpty()) {
                    exitPicSelectActivity();
                } else {
                    Toast.makeText(PicSelectActivity.this, "最少选择一张图片", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mPicSelectHeaderLayout = mBaseToolbar.getHeaderContainLayout();

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
            mBaseToolbar.setLeftDrawable(mPicSelectConfig.backResId);
        }
        mPicSelectHeaderLayout.setBackgroundColor(mPicSelectConfig.titleBgColor);
        mBaseToolbar.setTitle(mPicSelectConfig.title);
        mBaseToolbar.setTitleColor(mPicSelectConfig.titleColor);
        mBaseToolbar.setRightBgColor(mPicSelectConfig.btnBgColor);
        mBaseToolbar.setRightTextColor(mPicSelectConfig.btnTextColor);
        if (mPicSelectConfig.multiSelect) {
            if (!mPicSelectConfig.rememberSelected) {
                PicSelectStaticVariable.mPicSelectImageList.clear();
            }
            mBaseToolbar.setRightText(String.format("%1$s(%2$d/%3$d)", mPicSelectConfig.btnText, PicSelectStaticVariable.mPicSelectImageList.size(), mPicSelectConfig.maxNum));
        } else {
            PicSelectStaticVariable.mPicSelectImageList.clear();
            mBaseToolbar.setRightVisible(false);
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
        mBaseToolbar.setRightText(String.format("%1$s(%2$d/%3$d)", mPicSelectConfig.btnText, PicSelectStaticVariable.mPicSelectImageList.size(), mPicSelectConfig.maxNum));
    }

    @Override
    public void onImageUnselected(String path) {
        mBaseToolbar.setRightText(String.format("%1$s(%2$d/%3$d)", mPicSelectConfig.btnText, PicSelectStaticVariable.mPicSelectImageList.size(), mPicSelectConfig.maxNum));
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
            mBaseToolbar.setTitle(select + "/" + sum);
        } else {
            mBaseToolbar.setTitle(mPicSelectConfig.title);
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
        mPicSelectImagesResult.clear();
        mPicSelectImagesResult.addAll(PicSelectStaticVariable.mPicSelectImageList);
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("select_pics", mPicSelectImagesResult);
        intent.putExtra("select_pics_bundle", bundle);
        setResult(RESULT_OK, intent);
        if (!mPicSelectConfig.multiSelect) {
            PicSelectStaticVariable.mPicSelectImageList.clear();
        }
        finish();
    }
}
