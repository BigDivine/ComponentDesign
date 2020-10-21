package com.divine.yang.camera2component.imageselect;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.divine.yang.basecomponent.base.BaseFragment;
import com.divine.yang.camera2component.PicSelectActivity;
import com.divine.yang.camera2component.R;
import com.divine.yang.camera2component.imageselect.interfaces.OnFolderChangeListener;
import com.divine.yang.camera2component.imageselect.interfaces.OnItemClickListener;
import com.divine.yang.camera2component.imageselect.interfaces.PicSelectListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

/**
 * Author: Divine
 * CreateDate: 2020/10/20
 * Describe:
 */
public class PicSelectFragment extends BaseFragment implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private RecyclerView mRvPicSelectFragmentImgList;
    private Button mBtnPicSelectFragmentBottomAlbumSelect;
    private View mRlPicSelectFragmentBottomLayout;
    private CustomViewPager mCvpPicSelectFragmentPicPreview;

    private PicSelectConfig mPicSelectConfig;
    private PicSelectListener mPicSelectListener;
    private List<Folder> mFolderList = new ArrayList<>();
    private List<Image> mImageList = new ArrayList<>();

    private ListPopupWindow folderPopupWindow;
    private FolderListAdapter folderListAdapter;

    private PreviewAdapter previewAdapter;
    private ImageListsAdapter imageListAdapter;

    private boolean hasFolderGened = false;

    private static final int LOADER_ALL = 0;
    private static final int LOADER_CATEGORY = 1;
    private static final int REQUEST_CAMERA = 5;

    private static final int CAMERA_REQUEST_CODE = 1;

    private File tempFile;

    public static PicSelectFragment instance() {
        PicSelectFragment fragment = new PicSelectFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initView(View view) {
        mRvPicSelectFragmentImgList = view.findViewById(R.id.pic_select_fragment_img_list);
        mBtnPicSelectFragmentBottomAlbumSelect = view.findViewById(R.id.pic_select_fragment_bottom_album_select);
        mRlPicSelectFragmentBottomLayout = view.findViewById(R.id.pic_select_fragment_bottom_layout);
        mCvpPicSelectFragmentPicPreview = view.findViewById(R.id.pic_select_fragment_pic_preview);

        mBtnPicSelectFragmentBottomAlbumSelect.setOnClickListener(this);
        mCvpPicSelectFragmentPicPreview.setOffscreenPageLimit(1);
        mCvpPicSelectFragmentPicPreview.addOnPageChangeListener(this);
    }

    @Override
    protected void setData() {

    }

    @Override
    public int setContentView() {
        return R.layout.fragment_pic_select_layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPicSelectConfig = ((PicSelectActivity) getActivity()).getConfig();
        mPicSelectListener = ((PicSelectActivity) getActivity());

        if (mPicSelectConfig == null) {
            Log.e("ImgSelFragment", "config 参数不能为空");
            return;
        }

        mBtnPicSelectFragmentBottomAlbumSelect.setText(mPicSelectConfig.allImagesText);

        mRvPicSelectFragmentImgList.setLayoutManager(new GridLayoutManager(mRvPicSelectFragmentImgList.getContext(), 3));
        mRvPicSelectFragmentImgList.addItemDecoration(new DividerGridItemDecoration(mRvPicSelectFragmentImgList.getContext()));
        if (mPicSelectConfig.needCamera)
            mImageList.add(new Image());

        imageListAdapter = new ImageListsAdapter(getActivity(), mImageList, mPicSelectConfig);
        imageListAdapter.setShowCamera(mPicSelectConfig.needCamera);
        imageListAdapter.setMutiSelect(mPicSelectConfig.multiSelect);
        mRvPicSelectFragmentImgList.setAdapter(imageListAdapter);
        imageListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public int onCheckedClick(int position, Image image) {
                return checkedImage(position, image);
            }

            @Override
            public void onImageClick(int position, Image image) {
                if (mPicSelectConfig.needCamera && position == 0) {
                    showCameraAction();
                } else {
                    if (mPicSelectConfig.multiSelect) {
                        mCvpPicSelectFragmentPicPreview.setAdapter((previewAdapter = new PreviewAdapter(getActivity(), mImageList, mPicSelectConfig)));
                        previewAdapter.setListener(new OnItemClickListener() {
                            @Override
                            public int onCheckedClick(int position, Image image) {
                                return checkedImage(position, image);
                            }

                            @Override
                            public void onImageClick(int position, Image image) {
                                hidePreview();
                            }
                        });
                        if (mPicSelectConfig.needCamera) {
                            mPicSelectListener.onPreviewChanged(position, mImageList.size() - 1, true);
                        } else {
                            mPicSelectListener.onPreviewChanged(position + 1, mImageList.size(), true);
                        }
                        mCvpPicSelectFragmentPicPreview.setCurrentItem(mPicSelectConfig.needCamera ? position - 1 : position);
                        mCvpPicSelectFragmentPicPreview.setVisibility(View.VISIBLE);
                    } else {
                        if (mPicSelectListener != null) {
                            mPicSelectListener.onSingleImageSelected(image.path);
                        }
                    }
                }
            }
        });

        folderListAdapter = new FolderListAdapter(getActivity(), mFolderList, mPicSelectConfig);

        getActivity().getSupportLoaderManager().initLoader(LOADER_ALL, null, mLoaderCallback);
    }

    private int checkedImage(int position, Image image) {
        if (image != null) {
            if (PicSelectStaticVariable.mPicSelectImageList.contains(image.path)) {
                PicSelectStaticVariable.mPicSelectImageList.remove(image.path);
                if (mPicSelectListener != null) {
                    mPicSelectListener.onImageUnselected(image.path);
                }
            } else {
                if (mPicSelectConfig.maxNum <= PicSelectStaticVariable.mPicSelectImageList.size()) {
                    Toast.makeText(getActivity(), String.format("最多选择%1$d张图片", mPicSelectConfig.maxNum), Toast.LENGTH_SHORT).show();
                    return 0;
                }

                PicSelectStaticVariable.mPicSelectImageList.add(image.path);
                if (mPicSelectListener != null) {
                    mPicSelectListener.onImageSelected(image.path);
                }
            }
            return 1;
        }
        return 0;
    }

    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallback = new LoaderManager.LoaderCallbacks<Cursor>() {

        private final String[] IMAGE_PROJECTION = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media._ID};

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            if (id == LOADER_ALL) {
                return new CursorLoader(getActivity(),
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                                        null, null, MediaStore.Images.Media.DATE_ADDED + " DESC");
            } else if (id == LOADER_CATEGORY) {
                return new CursorLoader(getActivity(),
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                                        IMAGE_PROJECTION[0] + " not like '%.gif%'", null, MediaStore.Images.Media.DATE_ADDED + " DESC");
            }
            return null;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            if (data != null) {
                int count = data.getCount();
                if (count > 0) {
                    List<Image> tempImageList = new ArrayList<>();
                    data.moveToFirst();
                    do {
                        String path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
                        String name = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]));
                        Image image = new Image(path, name);
                        tempImageList.add(image);
                        if (!hasFolderGened) {
                            File imageFile = new File(path);
                            File folderFile = imageFile.getParentFile();
                            if (folderFile == null || !imageFile.exists() || imageFile.length() < 10) {
                                continue;
                            }

                            Folder parent = null;
                            for (Folder folder : mFolderList) {
                                if (TextUtils.equals(folder.path, folderFile.getAbsolutePath())) {
                                    parent = folder;
                                }
                            }
                            if (parent != null) {
                                parent.images.add(image);
                            } else {
                                parent = new Folder();
                                parent.name = folderFile.getName();
                                parent.path = folderFile.getAbsolutePath();
                                parent.cover = image;

                                List<Image> imageList = new ArrayList<>();
                                imageList.add(image);

                                parent.images = imageList;
                                mFolderList.add(parent);
                            }
                        }
                    } while (data.moveToNext());

                    mImageList.clear();
                    if (mPicSelectConfig.needCamera)
                        mImageList.add(new Image());
                    mImageList.addAll(tempImageList);

                    imageListAdapter.notifyDataSetChanged();
                    folderListAdapter.notifyDataSetChanged();

                    hasFolderGened = true;
                }
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };

    private void createPopupFolderList(int width, int height) {
        folderPopupWindow = new ListPopupWindow(getActivity());
        folderPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#aaaaaa")));
        folderPopupWindow.setAdapter(folderListAdapter);
        folderPopupWindow.setContentWidth(width);
        folderPopupWindow.setWidth(width);
        folderPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        folderPopupWindow.setAnchorView(mRlPicSelectFragmentBottomLayout);
        folderPopupWindow.setModal(true);
        folderListAdapter.setOnFloderChangeListener(new OnFolderChangeListener() {
            @Override
            public void onChange(int position, Folder folder) {
                folderPopupWindow.dismiss();
                if (position == 0) {
                    getActivity().getSupportLoaderManager().restartLoader(LOADER_ALL, null, mLoaderCallback);
                    mBtnPicSelectFragmentBottomAlbumSelect.setText(mPicSelectConfig.allImagesText);
                } else {
                    mImageList.clear();
                    if (mPicSelectConfig.needCamera)
                        mImageList.add(new Image());
                    mImageList.addAll(folder.images);
                    imageListAdapter.notifyDataSetChanged();

                    mBtnPicSelectFragmentBottomAlbumSelect.setText(folder.name);
                }
            }
        });
        folderPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);
            }
        });
    }

    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getActivity().getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        WindowManager wm = getActivity().getWindowManager();
        final int size = wm.getDefaultDisplay().getWidth() / 3 * 2;
        if (v.getId() == mBtnPicSelectFragmentBottomAlbumSelect.getId()) {
            if (folderPopupWindow == null) {
                createPopupFolderList(size, size);
            }

            if (folderPopupWindow.isShowing()) {
                folderPopupWindow.dismiss();
            } else {
                folderPopupWindow.show();
                if (folderPopupWindow.getListView() != null) {
                    folderPopupWindow.getListView().setDivider(new ColorDrawable(ContextCompat.getColor(getActivity(), R.color.bottom_bg)));
                }
                int index = folderListAdapter.getSelectIndex();
                index = index == 0 ? index : index - 1;
                folderPopupWindow.getListView().setSelection(index);

                folderPopupWindow.getListView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            folderPopupWindow.getListView().getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        } else {
                            folderPopupWindow.getListView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                        int h = folderPopupWindow.getListView().getMeasuredHeight();
                        if (h > size) {
                            folderPopupWindow.setHeight(size);
                            folderPopupWindow.show();
                        }
                    }
                });
                setBackgroundAlpha(0.6f);
            }
        }
    }

    private void showCameraAction() {

        if (mPicSelectConfig.maxNum <= PicSelectStaticVariable.mPicSelectImageList.size()) {
            Toast.makeText(getActivity(), String.format("最多选择%1$d张图片", mPicSelectConfig.maxNum), Toast.LENGTH_SHORT).show();
            return;
        }

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
            return;
        }

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            tempFile = new File(FileUtils.createRootPath(getActivity()) + "/" + System.currentTimeMillis() + ".jpg");
            Log.e("D-picSelectFragment", tempFile.getAbsolutePath());
            FileUtils.createFile(tempFile);

            Uri uri = FileProvider.getUriForFile(getActivity(),
                                                 FileUtils.getApplicationId(getActivity()) + ".image_provider", tempFile);

            List<ResolveInfo> resInfoList = getActivity().getPackageManager()
                    .queryIntentActivities(cameraIntent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                getActivity().grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                        | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }

            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri); //Uri.fromFile(tempFile)
            startActivityForResult(cameraIntent, REQUEST_CAMERA);
        } else {
            Toast.makeText(getActivity(), "打开相机失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                if (tempFile != null) {
                    if (mPicSelectListener != null) {
                        mPicSelectListener.onCameraShot(tempFile);
                    }
                }
            } else {
                if (tempFile != null && tempFile.exists()) {
                    tempFile.delete();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                if (grantResults.length >= 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showCameraAction();
                } else {
                    Toast.makeText(getActivity(), "请打开相关权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (mPicSelectConfig.needCamera) {
            mPicSelectListener.onPreviewChanged(position + 1, mImageList.size() - 1, true);
        } else {
            mPicSelectListener.onPreviewChanged(position + 1, mImageList.size(), true);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public boolean hidePreview() {
        if (mCvpPicSelectFragmentPicPreview.getVisibility() == View.VISIBLE) {
            mCvpPicSelectFragmentPicPreview.setVisibility(View.GONE);
            mPicSelectListener.onPreviewChanged(0, 0, false);
            imageListAdapter.notifyDataSetChanged();
            return true;
        } else {
            return false;
        }
    }
}
