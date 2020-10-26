package com.divine.yang.camera2component;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Size;
import android.view.Display;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.divine.yang.basecomponent.base.BaseFragment;
import com.divine.yang.camera2component.imageselect.FileUtils;
import com.divine.yang.camera2component.imageselect.ImageUtils;
import com.divine.yang.camera2component.imageselect.PicSelectConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import static android.app.Activity.RESULT_OK;

/**
 * Create by ZeeLion on 2020/9/29
 */
public class Camera2Fragment extends BaseFragment implements TextureView.SurfaceTextureListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    // 相机预览布局，拍照界面头部布局，动作按钮布局
    private RelativeLayout mCamera2CameraLayout, mCamera2CameraHeader, mCamera2ButtonLayout;
    // 图像预览view
    private AutoFixTextureView mAutoFixTextureView;
    // 灰色覆盖
    private View mCamera2CameraGrayMark;
    // 相册预览
    private ImageView mCamera2ButtonImagePreview;
    // 单拍连拍单选
    private RadioGroup mCamera2CameraHeaderPicMode;
    // 单拍，连拍
    private RadioButton mCamera2CameraHeaderSingle, mCamera2CameraHeaderContinuous;
    // 文字覆盖，连拍照片张数，连拍完成按钮
    private TextView mCamera2CameraWordMark, mCamera2ButtonImageNum, mCamera2ButtonSubmit;
    // 打开闪光灯按钮，关闭按钮，进入相册按钮，拍照按钮
    private ImageButton mCamera2CameraHeaderOpenFlashLamp, mCamera2CameraHeaderClose, mCamera2ButtonAlbum, mCamera2ButtonTakePhoto;
    //camera2相关
    //cameraId，自定义的一个值
    private String mCameraId;
    private CameraManager mCameraManager;
    private ImageReader mImageReader;
    private CameraDevice mCameraDevice;
    private CaptureRequest.Builder mPreviewBuilder;
    private CameraCaptureSession mSession;
    private Handler mHandler;
    private Handler mainHandler;
    //图片保存路径
    private String mPicSavePath;
    //图片路径集合，连拍是需要数量限制
    private ArrayList<HashMap<String, String>> mPicPathList;
    //是否开启闪光灯,判断是连拍，还是单拍（true:单拍；false:连拍）,图片选择是否支持多选
    private boolean isOpenFlash = false, isSingle = true, isMultiSelect = false;
    //图片预览和保存相关
    //最佳图片保存尺寸，最佳图片预览尺寸，预览框的尺寸
    private Size mPicBestSaveSize, mPicBestPreviewSize, mPicTargetSize;
    //可用的图片保存尺寸集合，可用的图片预览尺寸
    private Size[] mPicSaveSize, mPicPreviewSize;
    //手机方向
    private int mDisplayRotation;
    //摄像头方向
    private int mCameraSensorOrientation = 0;
    //图片裁剪requestCode
    public final int REQ_IMAGE_EDIT = 2;
    //裁剪后的图片
    private File cropFile;
    //选择图片request code
    private final int SELECT_PIC_REQUEST_CODE = 1;

    //选择图片回调，发起图片识别请求
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mCamera2CameraGrayMark.setVisibility(View.VISIBLE);
        }
    };
    //相机camera2拍照图片处理
    private ImageReader.OnImageAvailableListener mOnImageAvailableListener = new ImageReader.OnImageAvailableListener() {
        @Override
        public void onImageAvailable(ImageReader reader) {
            File file = new File(FileUtils.getAppPath() + "/IMG_" + ImageUtils.setImageName() + ".jpg");
            mPicSavePath = file.getAbsolutePath();
            FileOutputStream fileOutputStream;
            try {    //进行相片存储
                fileOutputStream = new FileOutputStream(file);
                Image image = reader.acquireNextImage();
                ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                byte[] bytes = new byte[buffer.remaining()];
                buffer.get(bytes);//将image对象转化为byte，再转化为bitmap
                final Bitmap bitmapSource = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                if (bitmapSource != null) {
                    // 解决异常-java.lang.IllegalStateException: maxImages (7) has already been acquired, call #close before acquiring more.
                    Bitmap bitmap = Bitmap.createBitmap(bitmapSource, 0, 0, bitmapSource.getWidth(), bitmapSource.getHeight(), null, false);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
                    fileOutputStream.close();
                    // 释放image资源
                    image.close();
                }

                if (isSingle) {//单拍
                    ArrayList<String> imageList = new ArrayList<>();
                    imageList.add(mPicSavePath);
                    setPicResult(imageList);
                    //                    Uri sourceUri = Uri.fromFile(file);
                    //                    cropFile = new File(FileUtil.getAppPath() + "/IMG_" + ImageUtil.setImageName() + "_crop.jpg");
                    //                    mPicSavePath = cropFile.getAbsolutePath();
                    //                    Intent intent = new Intent(getContext(), MyIMGEditActivity.class);
                    //                    intent.putExtra(IMGEditActivity.EXTRA_IMAGE_URI, sourceUri);
                    //                    intent.putExtra(IMGEditActivity.EXTRA_IMAGE_SAVE_PATH, mPicSavePath);
                    //                    getActivity().startActivityForResult(intent, REQ_IMAGE_EDIT);
                } else {
                    mCamera2CameraHeaderSingle.setEnabled(false);
                    mCamera2CameraHeaderContinuous.setEnabled(false);
                    HashMap<String, String> map = new HashMap<>();
                    map.put("name", file.getName());
                    map.put("path", mPicSavePath);
                    mPicPathList.add(map);
                    if (mPicPathList.size() > 0) {
                        mCamera2ButtonImageNum.setVisibility(View.VISIBLE);
                        mCamera2ButtonImageNum.setText(mPicPathList.size() + "");
                        Bitmap bitmap = BitmapFactory.decodeFile(mPicPathList.get(mPicPathList.size() - 1).get("path"));
                        Matrix m = new Matrix();
                        m.postRotate(90);
                        mCamera2ButtonImagePreview.setVisibility(View.VISIBLE);
                        mCamera2ButtonImagePreview.setImageBitmap(Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true));
                        mCamera2ButtonAlbum.setVisibility(View.GONE);
                    } else {
                        mCamera2ButtonImagePreview.setVisibility(View.GONE);
                        mCamera2ButtonImageNum.setVisibility(View.GONE);
                        mCamera2ButtonImagePreview.setImageBitmap(null);
                        mCamera2ButtonAlbum.setVisibility(View.VISIBLE);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    };
    //相机camera2设备状态callback
    private CameraDevice.StateCallback deviceStateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice camera) {
            mCameraDevice = camera;
            try {
                takePreview();
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            closeCameraDevice();
        }

        @Override
        public void onError(CameraDevice camera, int error) {
            Toast.makeText(getActivity(), "打开摄像头失败", Toast.LENGTH_SHORT).show();
        }
    };
    //相机预览相关配置
    private CameraCaptureSession.StateCallback mSessionPreviewStateCallback = new CameraCaptureSession.StateCallback() {
        @Override
        public void onConfigured(@NonNull CameraCaptureSession session) {
            mSession = session;
            //配置完毕开始预览
            try {
                /**
                 * 设置你需要配置的参数
                 */
                //自动对焦
                mPreviewBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
                //打开闪光灯
                mPreviewBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);

                int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();
                CameraCharacteristics cameraCharacteristics = mCameraManager.getCameraCharacteristics(mCameraId);
                mPreviewBuilder.set(CaptureRequest.JPEG_ORIENTATION, getJpegOrientation(cameraCharacteristics, rotation));//使图片做顺时针旋转

                //无限次的重复获取图像
                mSession.setRepeatingRequest(mPreviewBuilder.build(), mSessionCaptureCallback, mHandler);

            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onConfigureFailed(@NonNull CameraCaptureSession session) {
            Toast.makeText(getActivity(), "配置失败", Toast.LENGTH_SHORT).show();
        }
    };
    //相机拍照事项
    private CameraCaptureSession.CaptureCallback mSessionCaptureCallback = new CameraCaptureSession.CaptureCallback() {
        @Override
        public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
            mSession = session;
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    mCamera2CameraGrayMark.setVisibility(View.GONE);
                }
            });
        }

        @Override
        public void onCaptureProgressed(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull CaptureResult partialResult) {
            mSession = session;
        }

        @Override
        public void onCaptureFailed(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull CaptureFailure failure) {
            super.onCaptureFailed(session, request, failure);
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    mCamera2CameraGrayMark.setVisibility(View.GONE);
                }
            });
        }
    };

    @Override
    public int setContentView() {
        return R.layout.fragment_camera2_layout;
    }


    @Override
    protected void initView(View view) {
        mCamera2CameraLayout = view.findViewById(R.id.camera2_camera_layout);
        mAutoFixTextureView = view.findViewById(R.id.camera2_camera_texture_view);
        mCamera2CameraGrayMark = view.findViewById(R.id.camera2_camera_gray_mark);

        mCamera2CameraHeader = view.findViewById(R.id.camera2_camera_header);
        mCamera2CameraHeaderOpenFlashLamp = view.findViewById(R.id.camera2_camera_header_open_flash_lamp);
        mCamera2CameraHeaderClose = view.findViewById(R.id.camera2_camera_header_close);

        mCamera2CameraHeaderPicMode = view.findViewById(R.id.camera2_camera_header_pic_mode);
        mCamera2CameraHeaderSingle = view.findViewById(R.id.camera2_camera_header_single);
        mCamera2CameraHeaderContinuous = view.findViewById(R.id.camera2_camera_header_continuous);

        mCamera2CameraWordMark = view.findViewById(R.id.camera2_camera_word_mark);

        mCamera2ButtonLayout = view.findViewById(R.id.camera2_button_layout);
        mCamera2ButtonImagePreview = view.findViewById(R.id.camera2_button_image_preview);
        mCamera2ButtonImageNum = view.findViewById(R.id.camera2_button_image_num);
        mCamera2ButtonAlbum = view.findViewById(R.id.camera2_button_album);
        mCamera2ButtonTakePhoto = view.findViewById(R.id.camera2_button_take_photo);
        mCamera2ButtonSubmit = view.findViewById(R.id.camera2_button_submit);

        mCamera2CameraWordMark.setVisibility(View.VISIBLE);
        mCamera2CameraGrayMark.setVisibility(View.VISIBLE);

        mCamera2ButtonAlbum.setOnClickListener(this);
        mCamera2ButtonTakePhoto.setOnClickListener(this);
        mCamera2ButtonSubmit.setOnClickListener(this);
        mCamera2CameraHeaderOpenFlashLamp.setOnClickListener(this);
        mCamera2CameraHeaderClose.setOnClickListener(this);

        mCamera2CameraHeaderSingle.setOnCheckedChangeListener(this);
        mCamera2CameraHeaderContinuous.setOnCheckedChangeListener(this);
        mAutoFixTextureView.setSurfaceTextureListener(this);
    }

    @Override
    protected void getData() {
        mPicPathList = new ArrayList<>();
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        mDisplayRotation = display.getRotation();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mAutoFixTextureView.isAvailable()) {
            initCameraAndPreview(mAutoFixTextureView.getWidth(), mAutoFixTextureView.getHeight());
        } else {
            mAutoFixTextureView.setSurfaceTextureListener(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        closeCameraDevice();
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.camera2_camera_header_close:
                getActivity().finish();
                break;
            case R.id.camera2_camera_header_open_flash_lamp:
                changeFlashState();
                break;
            case R.id.camera2_button_album:
                PicSelectConfig mPicSelectConfig = new PicSelectConfig.Builder()
                        // 是否记住上次选中记录
                        .rememberSelected(false)
                        .needCamera(false)
                        .multiSelect(isMultiSelect)
                        // 使用沉浸式状态栏
                        .statusBarColor(Color.parseColor("#3F51B5"))
                        .build();
                Intent intent = new Intent(getContext(), PicSelectActivity.class);
                intent.putExtra("config", mPicSelectConfig);
                startActivityForResult(intent, SELECT_PIC_REQUEST_CODE);
                break;
            case R.id.camera2_button_take_photo:
                // 拍照按钮监听
                takePicture();
                break;
            case R.id.camera2_button_submit:
                ArrayList<String> imageList = new ArrayList<>();
                for (int i = 0; i < mPicPathList.size(); i++) {
                    imageList.add(mPicPathList.get(i).get("path"));
                }
                setPicResult(imageList);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQ_IMAGE_EDIT:
                    closeCameraDevice();
                    break;
                case SELECT_PIC_REQUEST_CODE:
                    Bundle bundle = data.getBundleExtra("select_pics_bundle");
                    ArrayList<String> imageList = bundle.getStringArrayList("select_pics");
                    if (imageList.size() > 0) {
                        //                        if (!isMultiSelect) {
                        //                            mPicSavePath = imageList.get(0);
                        //                            mPicSavePath = ImageUtils.compressImage(mPicSavePath);
                        //                            cropFile = new File(FileUtils.getAppPath() + "/IMG_" + ImageUtils.setImageName() + "_crop.jpg");
                        //                            mPicSavePath = cropFile.getAbsolutePath();
                        //                    Intent intent = new Intent(getContext(), MyIMGEditActivity.class);
                        //                    intent.putExtra(IMGEditActivity.EXTRA_IMAGE_URI, sourceUri);
                        //                    intent.putExtra(IMGEditActivity.EXTRA_IMAGE_SAVE_PATH, mPicSavePath);
                        //                    getActivity().startActivityForResult(intent, REQ_IMAGE_EDIT);
                        //                        } else {
                        setPicResult(imageList);
                        //                        }
                    }
                    break;
            }
        }
    }

    private void setPicResult(ArrayList<String> data) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("select_pics", data);
        intent.putExtra("select_pics_bundle", bundle);
        getActivity().setResult(RESULT_OK, intent);
        closeCameraDevice();
        getActivity().finish();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        int i = compoundButton.getId();
        if (i == R.id.camera2_camera_header_single && b) {
            mCamera2ButtonSubmit.setVisibility(View.GONE);
            isSingle = true;
            mPicPathList.clear();
            isMultiSelect = false;
        }
        if (i == R.id.camera2_camera_header_continuous && b) {
            mCamera2ButtonSubmit.setVisibility(View.VISIBLE);
            mCamera2ButtonImageNum.setVisibility(View.GONE);
            isSingle = false;
            isMultiSelect = true;
        }
    }

    // TextureView的SurfaceTextureListener---------------------------------------
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        initCameraAndPreview(width, height);
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        //释放camera
        closeCameraDevice();
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }
    // ----------------------------------------------------------------------------

    //初始化相机和预览设置
    @TargetApi(19)
    public void initCameraAndPreview(int width, int height) {
        HandlerThread handlerThread = new HandlerThread("My First Camera2");
        handlerThread.start();
        mHandler = new Handler(handlerThread.getLooper());
        mainHandler = new Handler(getActivity().getMainLooper());//用来处理ui线程的handler，即ui线程
        try {
            // CameraCharacteristics.LENS_FACING_FRONT-后置摄像头； LENS_FACING_BACK-前置摄像头
            mCameraId = "" + CameraCharacteristics.LENS_FACING_FRONT;

            mCameraManager = (CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE);

            //获取StreamConfigurationMap，它是管理摄像头支持的所有输出格式和尺寸
            CameraCharacteristics mCameraCharacteristics = mCameraManager.getCameraCharacteristics(mCameraId);
            StreamConfigurationMap configurationMap = mCameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            mPicTargetSize = new Size(width, height);

            mPicSaveSize = configurationMap.getOutputSizes(ImageFormat.JPEG);          //保存照片尺寸
            mPicPreviewSize = configurationMap.getOutputSizes(SurfaceTexture.class);        //预览尺寸

            //获取摄像头方向
            mCameraSensorOrientation = mCameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
            boolean exchange = exchangeWidthAndHeight(mDisplayRotation, mCameraSensorOrientation);
            if (exchange) {
                mPicBestSaveSize = getBestSize(mPicTargetSize.getHeight(), mPicTargetSize.getWidth(), mPicTargetSize.getHeight(), mPicTargetSize.getWidth(), Arrays.asList(mPicSaveSize));
                mPicBestPreviewSize = getBestSize(mPicTargetSize.getHeight(), mPicTargetSize.getWidth(), mPicTargetSize.getHeight(), mPicTargetSize.getWidth(), Arrays.asList(mPicPreviewSize));
            } else {
                mPicBestSaveSize = getBestSize(mPicTargetSize.getWidth(), mPicTargetSize.getHeight(), mPicTargetSize.getWidth(), mPicTargetSize.getHeight(), Arrays.asList(mPicSaveSize));
                mPicBestPreviewSize = getBestSize(mPicTargetSize.getWidth(), mPicTargetSize.getHeight(), mPicTargetSize.getWidth(), mPicTargetSize.getHeight(), Arrays.asList(mPicPreviewSize));
            }
            //            Log.i("yzl", "mAutoFixTextureView-------Size-----------------------height:" + mAutoFixTextureView.getHeight() + "----------------------width:" + mAutoFixTextureView.getWidth() + "-----------");
            //            Log.i("yzl", "target-------Size-----------------------height:" + mPicTargetSize.getHeight() + "----------------------width:" + mPicTargetSize.getWidth() + "-----------");
            //            Log.i("yzl", "save--best-------Size-----------------------height:" + mPicBestSaveSize.getHeight() + "----------------------width:" + mPicBestSaveSize.getWidth() + "-----------");
            //            Log.i("yzl", "preview --best-------Size-----------------------height:" + mPicBestPreviewSize.getHeight() + "----------------------width:" + mPicBestPreviewSize.getWidth() + "-----------");

            mAutoFixTextureView.setAspectRation(mPicBestPreviewSize.getWidth(), mPicBestPreviewSize.getHeight());
            mAutoFixTextureView.getSurfaceTexture().setDefaultBufferSize(mPicBestPreviewSize.getWidth(), mPicBestPreviewSize.getHeight());
            // 设置ImageReader，用来读取拍摄图像的类
            mImageReader = ImageReader.newInstance(mPicBestSaveSize.getWidth(), mPicBestSaveSize.getHeight(), ImageFormat.JPEG,/*maxImages*/2);
            //这里必须传入mainHandler，因为涉及到了Ui操作
            mImageReader.setOnImageAvailableListener(mOnImageAvailableListener, mainHandler);
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(mContext, "未取得摄像头授权，请到手机设置中进行授权", Toast.LENGTH_SHORT).show();
                return;
            }
            mCameraManager.openCamera(mCameraId, deviceStateCallback, mHandler);
        } catch (CameraAccessException e) {
            Toast.makeText(getActivity(), "打开相机失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void changeFlashState() {
        try {
            isOpenFlash = !isOpenFlash;
            if (isOpenFlash) {
                mPreviewBuilder.set(CaptureRequest.FLASH_MODE, CaptureRequest.FLASH_MODE_TORCH);
            } else {
                mPreviewBuilder.set(CaptureRequest.FLASH_MODE, CaptureRequest.FLASH_MODE_OFF);
            }
            mSession.setRepeatingRequest(mPreviewBuilder.build(), mSessionCaptureCallback, mHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private Size getBestSize(int targetWidth, int targetHeight, int maxWidth, int maxHeight, List<Size> sizeList) {
        List<Size> bigEnough = new ArrayList<Size>();     //比指定宽高大的Size列表
        List<Size> notBigEnough = new ArrayList<Size>();  //比指定宽高小的Size列表

        for (Size size : sizeList) {
            //宽<=最大宽度  &&  高<=最大高度  &&  宽高比 == 目标值宽高比
            if (size.getWidth() <= maxWidth && size.getHeight() <= maxHeight
                    && size.getWidth() == size.getHeight() * targetWidth / targetHeight) {

                if (size.getWidth() >= targetWidth && size.getHeight() >= targetHeight)
                    bigEnough.add(size);
                else
                    notBigEnough.add(size);
            }
        }

        //选择bigEnough中最小的值  或 notBigEnough中最大的值
        Size size;
        if (bigEnough.size() > 0) {
            size = Collections.min(bigEnough, new CompareSizesByArea());
        } else if (notBigEnough.size() > 0) {
            size = Collections.max(notBigEnough, new CompareSizesByArea());
        } else {
            size = sizeList.get(0);
        }
        return size;
    }

    private class CompareSizesByArea implements Comparator<Size> {
        @Override
        public int compare(Size size1, Size size2) {
            return Long.signum(size1.getWidth() * size1.getHeight() - size2.getWidth() * size2.getHeight());
        }
    }

    /**
     * 根据提供的屏幕方向 [displayRotation] 和相机方向 [sensorOrientation] 返回是否需要交换宽高
     */
    private boolean exchangeWidthAndHeight(int displayRotation, int sensorOrientation) {
        boolean exchange = false;
        switch (displayRotation) {
            case Surface.ROTATION_0:
            case Surface.ROTATION_180:
                if (sensorOrientation == 90 || sensorOrientation == 270) {
                    exchange = true;
                }
                break;
            case Surface.ROTATION_90:
                if (sensorOrientation == 0 || sensorOrientation == 180) {
                    exchange = true;
                    break;
                }
            case Surface.ROTATION_270:
                if (sensorOrientation == 0 || sensorOrientation == 180) {
                    exchange = true;
                    break;
                }
        }
        return exchange;
    }

    //打开相机预览
    public void takePreview() throws CameraAccessException {
        mPreviewBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
        Surface surface = new Surface(mAutoFixTextureView.getSurfaceTexture());
        mPreviewBuilder.addTarget(surface);
        mCameraDevice.createCaptureSession(Arrays.asList(surface, mImageReader.getSurface()), mSessionPreviewStateCallback, mHandler);
    }

    //拍照
    public void takePicture() {
        try {
            CaptureRequest.Builder captureRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);//用来设置拍照请求的request
            captureRequestBuilder.addTarget(mImageReader.getSurface());
            // 自动对焦
            captureRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
            // 自动曝光
            captureRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);

            int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();
            CameraCharacteristics cameraCharacteristics = mCameraManager.getCameraCharacteristics(mCameraId);
            captureRequestBuilder.set(CaptureRequest.JPEG_ORIENTATION, getJpegOrientation(cameraCharacteristics, rotation));//使图片做顺时针旋转
            //            captureRequestBuilder.set(CaptureRequest.)
            CaptureRequest mCaptureRequest = captureRequestBuilder.build();
            mSession.capture(mCaptureRequest, mSessionCaptureCallback, mHandler);

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    //获取图片应该旋转的角度，使图片竖直
    private int getJpegOrientation(CameraCharacteristics c, int deviceOrientation) {
        if (deviceOrientation == android.view.OrientationEventListener.ORIENTATION_UNKNOWN)
            return 0;
        int sensorOrientation = c.get(CameraCharacteristics.SENSOR_ORIENTATION);

        // Round device orientation to a multiple of 90
        deviceOrientation = (deviceOrientation + 45) / 90 * 90;

        // LENS_FACING相对于设备屏幕的方向,LENS_FACING_FRONT相机设备面向与设备屏幕相同的方向
        boolean facingFront = c.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_FRONT;
        if (facingFront)
            deviceOrientation = -deviceOrientation;

        // Calculate desired JPEG orientation relative to camera orientation to make
        // the image upright relative to the device orientation
        int jpegOrientation = (sensorOrientation + deviceOrientation + 360) % 360;

        return jpegOrientation;
    }

    public void closeCameraDevice() {
        if (mCameraDevice != null) {
            //停止预览
            mCameraDevice.close();
            mCameraDevice = null;
        }
    }
}
