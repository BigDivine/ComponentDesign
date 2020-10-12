//package com.divine.yang.camera2component;
//
//import android.Manifest;
//import android.annotation.SuppressLint;
//import android.annotation.TargetApi;
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Color;
//import android.graphics.ImageFormat;
//import android.graphics.Matrix;
//import android.graphics.SurfaceTexture;
//import android.hardware.camera2.CameraAccessException;
//import android.hardware.camera2.CameraCaptureSession;
//import android.hardware.camera2.CameraCharacteristics;
//import android.hardware.camera2.CameraDevice;
//import android.hardware.camera2.CameraManager;
//import android.hardware.camera2.CaptureFailure;
//import android.hardware.camera2.CaptureRequest;
//import android.hardware.camera2.CaptureResult;
//import android.hardware.camera2.TotalCaptureResult;
//import android.hardware.camera2.params.StreamConfigurationMap;
//import android.media.Image;
//import android.media.ImageReader;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.HandlerThread;
//import android.os.Message;
//import android.util.Size;
//import android.view.Display;
//import android.view.Surface;
//import android.view.TextureView;
//import android.view.View;
//import android.widget.CompoundButton;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.gson.Gson;
//import com.jiuqi.image.base.BaseFragment;
//import com.jiuqi.image.bean.GetTokenBean;
//import com.jiuqi.image.bean.IdentificationBackBean;
//import com.jiuqi.image.bean.IdentificationBean;
//import com.jiuqi.image.bean.OcrScanBean;
//import com.jiuqi.image.config.ISListConfig;
//import com.jiuqi.image.http.ApiException;
//import com.jiuqi.image.http.Params;
//import com.jiuqi.image.http.ResponseTransformer;
//import com.jiuqi.image.http.RetrofitHelper;
//import com.jiuqi.image.http.SchedulerProvider;
//import com.jiuqi.image.imagelibrary.ISListActivity;
//import com.jiuqi.image.imagelibrary.OcrScanActivity;
//import com.jiuqi.image.imagelibrary.R;
//import com.jiuqi.image.inter.ImageListCallback;
//import com.jiuqi.image.utils.BitmapUtils;
//import com.jiuqi.image.utils.DESCryptography;
//import com.jiuqi.image.utils.DialogUtil;
//import com.jiuqi.image.utils.FileUtil;
//import com.jiuqi.image.utils.ImageUtil;
//import com.jiuqi.image.widget.AutoFixTextureView;
//import com.jiuqi.image.widget.OverCameraView;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.nio.ByteBuffer;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Objects;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.annotation.RequiresApi;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.FileProvider;
//import io.reactivex.ObservableTransformer;
//import io.reactivex.functions.Consumer;
//import me.minetsh.imaging.IMGEditActivity;
//import me.minetsh.imaging.MyIMGEditActivity;
//import okhttp3.MediaType;
//import okhttp3.RequestBody;
//
///**
// * Create by ZeeLion on 2020/9/29
//
// */
//public class CameraFragmentNew extends BaseFragment implements TextureView.SurfaceTextureListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener {
//    //图像预览view
//    private AutoFixTextureView mAutoFixTextureView;
//    //拍照按钮、相册选择按钮、二维码扫描按钮
//    private ImageButton pause_ib, Album_ib, scan_er_code_ib;
//    //打开闪光灯按钮
//    private ImageView open_flash_lamp;
//    //动作按钮布局layout
//    private RelativeLayout pause_rl;
//    //图片预览控件
//    private ImageView image_iv;
//    //连拍完成按钮、连拍图片数量展示
//    private TextView scan_er_code_complete_tv, image_num_tv;
//    //单拍、连拍
//    private RadioButton single, continuity;
//    //灰色覆盖
//    private View camera_gray_back;
//    //聚焦绿色边框
//    private OverCameraView over_camera_view;
//    //关闭按钮
//    private ImageView close_iv;
//    //单拍连拍group
//    private RadioGroup camera_radioGroup;
//    //拍照提示语：请保持发票主体清晰。只有铁建需要，其他不需要
//    private TextView camera_mark_word;
//    //承载该fragment的activity
//    private OcrScanActivity ocrScanActivity;
//    //camera2相关
//    //cameraId，自定义的一个值
//    private String mCameraId;
//    private CameraManager mCameraManager;
//    private ImageReader mImageReader;
//    private CameraDevice mCameraDevice;
//    private CaptureRequest.Builder mPreviewBuilder;
//    private CameraCaptureSession mSession;
//
//    private Handler mHandler;
//    private Handler mainHandler;
//    //获取用户token用于图片识别
//    private String token;
//    //图片保存路径
//    private String path;
//    //图片路径集合，连拍是需要数量限制
//    private ArrayList<HashMap<String, String>> pathList;
//    //图片选择是否支持多选
//    private boolean multiSelect = false;
//    //是否开启闪光灯
//    private boolean isOpenFlash = false;
//    //判断是连拍，还是单拍（false:单拍；true:连拍）
//    private boolean isSingle = false;
//
//    //图片选择callback
//    ImageListCallback mImageListCallback = new ImageListCallback() {
//        @Override
//        public void getImageList(ArrayList<String> imageList) {
//            if (imageList.size() > 0) {
//                if (!multiSelect) {
//                    path = imageList.get(0);
//                    path = ImageUtil.compressImage(path);
//                    if (getArguments().getBoolean("showHint")) {
//                        Uri sourceUri = Uri.fromFile(new File(path));
//                        cropFile = new File(FileUtil.getAppPath() + "/IMG_" + ImageUtil.setImageName() + "_crop.jpg");
//                        path = cropFile.getAbsolutePath();
//                        Intent intent = new Intent(getContext(), MyIMGEditActivity.class);
//                        intent.putExtra(IMGEditActivity.EXTRA_IMAGE_URI, sourceUri);
//                        intent.putExtra(IMGEditActivity.EXTRA_IMAGE_SAVE_PATH, path);
//                        getActivity().startActivityForResult(intent, REQ_IMAGE_EDIT);
//                    } else {
//                        List<IdentificationBean.ImagelistBean> imagelistBeans = new ArrayList<>();
//                        IdentificationBean.ImagelistBean imagelistBean = new IdentificationBean.ImagelistBean();
//                        imagelistBean.setFilename(ImageUtil.getImageName(path));
//                        imagelistBean.setImagedata(BitmapUtils.fileToBase64(path));
//                        imagelistBeans.add(imagelistBean);
//                        Message message = new Message();
//                        message.obj = imagelistBeans;
//                        handler.sendMessage(message);
//                    }
//                } else {
//                    pathList.clear();
//                    for (int i = 0; i < imageList.size(); i++) {
//                        HashMap<String, String> map = new HashMap<>();
//                        map.put("name", ImageUtil.getImageName(imageList.get(i)));
//                        map.put("path", ImageUtil.compressImage(imageList.get(i)));
//                        pathList.add(map);
//                    }
//                    continuePost("");
//                }
//            }
//        }
//    };
//    //选择图片回调，发起图片识别请求
//    @SuppressLint("HandlerLeak")
//    private Handler handler = new Handler() {
//        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            List<IdentificationBean.ImagelistBean> imagelistBeans = (List<IdentificationBean.ImagelistBean>) msg.obj;
//            if (Objects.requireNonNull(getActivity()).getIntent().getStringExtra("url").equals("1")) {
//                getIdentification(imagelistBeans, "camera");
//            } else {
//                getIdentificationBatch(imagelistBeans, "camera");
//            }
//        }
//    };
//    //相机camera2拍照图片处理
//    private ImageReader.OnImageAvailableListener mOnImageAvailableListener = new ImageReader.OnImageAvailableListener() {
//        @Override
//        public void onImageAvailable(ImageReader reader) {
//            File file = new File(FileUtil.getAppPath() + "/IMG_" + ImageUtil.setImageName() + ".jpg");
//            path = file.getAbsolutePath();
//            FileOutputStream fileOutputStream;
//            try {    //进行相片存储
//                fileOutputStream = new FileOutputStream(file);
//
//                Image image = reader.acquireNextImage();
//                ByteBuffer buffer = image.getPlanes()[0].getBuffer();
//                byte[] bytes = new byte[buffer.remaining()];
//                buffer.get(bytes);//将image对象转化为byte，再转化为bitmap
//                final Bitmap bitmapSource = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                //                Toast.makeText(getActivity(), "bitmapSource-------Size-----------------------height:" + bitmapSource.getHeight() + "----------------------width:" + bitmapSource.getWidth() + "-----------", Toast.LENGTH_SHORT).show();
//                //                Log.i("yzl", "bitmapSource-------Size-----------------------height:" + bitmapSource.getHeight() + "----------------------width:" + bitmapSource.getWidth() + "-----------");
//                if (bitmapSource != null) {
//                    // 解决异常-java.lang.IllegalStateException: maxImages (7) has already been acquired, call #close before acquiring more.
//                    Bitmap bitmap = Bitmap.createBitmap(bitmapSource, 0, 0, bitmapSource.getWidth(), bitmapSource.getHeight(), null, false);
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
//                    fileOutputStream.close();
//                    // 释放image资源
//                    image.close();
//                }
//
//                if (!isSingle) {//单拍
//                    if (mCameraDevice != null) {
//                        mCameraDevice.close();
//                    }
//                    if (getArguments().getBoolean("showHint")) {
//                        Uri sourceUri = Uri.fromFile(file);
//                        cropFile = new File(FileUtil.getAppPath() + "/IMG_" + ImageUtil.setImageName() + "_crop.jpg");
//                        path = cropFile.getAbsolutePath();
//                        Intent intent = new Intent(getContext(), MyIMGEditActivity.class);
//                        intent.putExtra(IMGEditActivity.EXTRA_IMAGE_URI, sourceUri);
//                        intent.putExtra(IMGEditActivity.EXTRA_IMAGE_SAVE_PATH, path);
//                        getActivity().startActivityForResult(intent, REQ_IMAGE_EDIT);
//                    } else {
//                        if (getActivity().getIntent().getStringExtra("url").equals("1")) {
//                            List<IdentificationBean.ImagelistBean> imagelistBeans = new ArrayList<>();
//                            IdentificationBean.ImagelistBean imagelistBean = new IdentificationBean.ImagelistBean();
//                            imagelistBean.setFilename(file.getName());
//                            imagelistBean.setImagedata(BitmapUtils.fileToBase64(path));
//                            imagelistBeans.add(imagelistBean);
//                            getIdentification(imagelistBeans, "camera");
//                        } else {
//                            IdentificationBean.ImagelistBean imagelistBean = new IdentificationBean.ImagelistBean();
//                            imagelistBean.setImgbase64(BitmapUtils.fileToBase64(path));
//                            ocrScanActivity.invoiceScannerCallBack.TakePhotoNoScan(imagelistBean);//单拍，不走识别
//                            getActivity().finish();
//                        }
//                    }
//
//                } else {
//                    single.setEnabled(false);
//                    continuity.setEnabled(false);
//                    HashMap<String, String> map = new HashMap<>();
//                    map.put("name", file.getName());
//                    map.put("path", path);
//                    pathList.add(map);
//                    if (pathList.size() > 0) {
//                        image_num_tv.setVisibility(View.VISIBLE);
//                        image_num_tv.setText(pathList.size() + "");
//                        Bitmap bitmap = BitmapFactory.decodeFile(pathList.get(pathList.size() - 1).get("path"));
//                        Matrix m = new Matrix();
//                        m.postRotate(90);
//                        image_iv.setVisibility(View.VISIBLE);
//                        image_iv.setImageBitmap(Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true));
//                        Album_ib.setVisibility(View.GONE);
//                    } else {
//                        image_iv.setVisibility(View.GONE);
//                        image_num_tv.setVisibility(View.GONE);
//                        image_iv.setImageBitmap(null);
//                        Album_ib.setVisibility(View.VISIBLE);
//                    }
//                    //                    if (mCameraDevice != null) {
//                    //                        //                    mCamera.startPreview();
//                    //                        takePreview();
//                    //                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//    };
//    //相机camera2设备状态callback
//    private CameraDevice.StateCallback deviceStateCallback = new CameraDevice.StateCallback() {
//        @Override
//        public void onOpened(CameraDevice camera) {
//            mCameraDevice = camera;
//            try {
//                takePreview();
//            } catch (CameraAccessException e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void onDisconnected(@NonNull CameraDevice camera) {
//            if (mCameraDevice != null) {
//                mCameraDevice.close();
//                mCameraDevice = null;
//            }
//        }
//
//        @Override
//        public void onError(CameraDevice camera, int error) {
//            Toast.makeText(getActivity(), "打开摄像头失败", Toast.LENGTH_SHORT).show();
//        }
//    };
//    //相机预览相关配置
//    private CameraCaptureSession.StateCallback mSessionPreviewStateCallback = new CameraCaptureSession.StateCallback() {
//        @Override
//        public void onConfigured(@NonNull CameraCaptureSession session) {
//            mSession = session;
//            //配置完毕开始预览
//            try {
//                /**
//                 * 设置你需要配置的参数
//                 */
//                //自动对焦
//                mPreviewBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
//                //打开闪光灯
//                mPreviewBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
//
//                int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();
//                CameraCharacteristics cameraCharacteristics = mCameraManager.getCameraCharacteristics(mCameraId);
//                mPreviewBuilder.set(CaptureRequest.JPEG_ORIENTATION, getJpegOrientation(cameraCharacteristics, rotation));//使图片做顺时针旋转
//
//                //无限次的重复获取图像
//                mSession.setRepeatingRequest(mPreviewBuilder.build(), mSessionCaptureCallback, mHandler);
//
//            } catch (CameraAccessException e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void onConfigureFailed(@NonNull CameraCaptureSession session) {
//            Toast.makeText(getActivity(), "配置失败", Toast.LENGTH_SHORT).show();
//        }
//    };
//    //相机拍照事项
//    private CameraCaptureSession.CaptureCallback mSessionCaptureCallback = new CameraCaptureSession.CaptureCallback() {
//        @Override
//        public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
//            mSession = session;
//            mainHandler.post(new Runnable() {
//                @Override
//                public void run() {
//                    camera_gray_back.setVisibility(View.GONE);
//                }
//            });
//        }
//
//        @Override
//        public void onCaptureProgressed(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull CaptureResult partialResult) {
//            mSession = session;
//        }
//
//        @Override
//        public void onCaptureFailed(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull CaptureFailure failure) {
//            super.onCaptureFailed(session, request, failure);
//            mainHandler.post(new Runnable() {
//                @Override
//                public void run() {
//                    camera_gray_back.setVisibility(View.GONE);
//                }
//            });
//        }
//    };
//
//    @Override
//    public int setContentView() {
//        return R.layout.fragment_camera_new_ll;
//    }
//
//    @Override
//    protected void initView(View view) {
//        mAutoFixTextureView = (AutoFixTextureView) view.findViewById(R.id.texture_view);
//        pause_ib = (ImageButton) view.findViewById(R.id.pause_ib_new);
//        Album_ib = (ImageButton) view.findViewById(R.id.Album_ib_new);
//        scan_er_code_ib = (ImageButton) view.findViewById(R.id.scan_er_code_ib_new);
//        open_flash_lamp = (ImageView) view.findViewById(R.id.open_flash_lamp_new);
//
//        image_iv = (ImageView) view.findViewById(R.id.image_iv_new);
//        close_iv = (ImageView) view.findViewById(R.id.close_iv_new);
//        scan_er_code_complete_tv = (TextView) view.findViewById(R.id.complete_tv_new);
//        image_num_tv = (TextView) view.findViewById(R.id.image_num_tv_new);
//        single = (RadioButton) view.findViewById(R.id.single_new);
//        continuity = (RadioButton) view.findViewById(R.id.continuity_new);
//        camera_radioGroup = (RadioGroup) view.findViewById(R.id.camera_radioGroup_new);
//        camera_gray_back = view.findViewById(R.id.camera_gray_back_new);
//        over_camera_view = (OverCameraView) view.findViewById(R.id.over_camera_view_new);
//        pause_rl = (RelativeLayout) view.findViewById(R.id.pause_rl_new);
//        camera_mark_word = (TextView) view.findViewById(R.id.camera_mark_word);
//
//    }
//
//    @Override
//    protected void setData() {
//        Bundle arguments = getArguments();
//        if (arguments.getBoolean("showHint")) {
//            camera_mark_word.setVisibility(View.VISIBLE);
//        } else {
//            camera_mark_word.setVisibility(View.GONE);
//        }
//
//        camera_gray_back.setVisibility(View.VISIBLE);
//
//        Album_ib.setOnClickListener(this);
//        pause_rl.setOnClickListener(this);
//        pause_ib.setOnClickListener(this);
//        scan_er_code_ib.setOnClickListener(this);
//        open_flash_lamp.setOnClickListener(this);
//        scan_er_code_complete_tv.setOnClickListener(this);
//        close_iv.setOnClickListener(this);
//
//        single.setOnCheckedChangeListener(this);
//        continuity.setOnCheckedChangeListener(this);
//        mAutoFixTextureView.setSurfaceTextureListener(this);
//
//        pathList = new ArrayList<>();
//        boolean encryptionINit = getActivity().getIntent().getBooleanExtra("encryptionINit", false);
//        if (encryptionINit) {
//            getTokenLogin();
//        } else {
//            getToken();
//        }
//        ocrScanActivity = (OcrScanActivity) getActivity();
//        //图片选择callback
//        ISListActivity.setImageListCallback(mImageListCallback);
//        Display display = getActivity().getWindowManager().getDefaultDisplay();
//        mDisplayRotation = display.getRotation();
//    }
//
//    Size targetSize;
//
//    @Override
//    public void onResume() {
//        super.onResume();
//
//        // When the screen is turned off and turned back on, the SurfaceTexture is already
//        // available, and "onSurfaceTextureAvailable" will not be called. In that case, we can open
//        // a camera and start preview from here (otherwise, we wait until the surface is ready in
//        // the SurfaceTextureListener).
//        if (mAutoFixTextureView.isAvailable()) {
//            initCameraAndPreview(mAutoFixTextureView.getWidth(), mAutoFixTextureView.getHeight());
//        } else {
//            mAutoFixTextureView.setSurfaceTextureListener(this);
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (mCameraDevice != null) {
//            mCameraDevice.close();
//            mCameraDevice = null;
//        }
//    }
//
//    @Override
//    public void onClick(View view) {
//        int i = view.getId();
//        if (view.getId() == R.id.pause_ib_new) {
//            //            拍照按钮监听
//            takePicture();
//        }
//        if (i == R.id.Album_ib_new) {
//            ISListConfig config = new ISListConfig.Builder().multiSelect(false)
//                    // 是否记住上次选中记录
//                    .rememberSelected(false).needCamera(false).multiSelect(multiSelect)
//                    // 使用沉浸式状态栏
//                    .statusBarColor(Color.parseColor("#3F51B5")).build();
//            Intent intent = new Intent(getContext(), ISListActivity.class);
//            intent.putExtra("config", config);
//            startActivity(intent);
//        }
//        if (i == R.id.open_flash_lamp_new) {
//            //            CameraUtil.getInstance(getActivity()).alterFlash(!isOpenFlash);
//
//            isOpenFlash = !isOpenFlash;
//            if (isOpenFlash) {
//                openFlash();
//            } else {
//                closeFlash();
//            }
//        }
//        if (i == R.id.complete_tv_new) {
//            continuePost("camera");
//        }
//        if (i == R.id.scan_er_code_ib_new) {//切换扫描到ScanFragment的,后来去掉了,先注释上了,这个切换不好用,虽然能切过来,但是拍照页面会重启
//
//        }
//
//        if (i == R.id.close_iv_new) {
//            getActivity().finish();
//        }
//    }
//
//    public static final int REQ_IMAGE_EDIT = 2;
//    File cropFile;
//
//
//    public void result(int requestCode, int resultCode, @Nullable Intent data) {
//        if (requestCode == REQ_IMAGE_EDIT) {
//            if (resultCode == Activity.RESULT_OK) {
//                if (mCameraDevice != null) {
//                    mCameraDevice.close();
//                }
//                if (getActivity().getIntent().getStringExtra("url").equals("1")) {
//                    List<IdentificationBean.ImagelistBean> imagelistBeans = new ArrayList<>();
//                    IdentificationBean.ImagelistBean imagelistBean = new IdentificationBean.ImagelistBean();
//                    imagelistBean.setFilename(cropFile.getName());
//                    imagelistBean.setImagedata(BitmapUtils.fileToBase64(cropFile.getAbsolutePath()));
//                    imagelistBeans.add(imagelistBean);
//                    getIdentification(imagelistBeans, "camera");
//                } else {
//                    List<IdentificationBean.ImagelistBean> imagelistBeans = new ArrayList<>();
//                    IdentificationBean.ImagelistBean imagelistBean = new IdentificationBean.ImagelistBean();
//                    imagelistBean.setFilename(cropFile.getName());
//                    imagelistBean.setImagedata(BitmapUtils.fileToBase64(cropFile.getAbsolutePath()));
//                    imagelistBeans.add(imagelistBean);
//                    getIdentificationBatch(imagelistBeans, "camera");
//                }
//            }
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode == 0) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                //用户同意，执行操作
//            }
//        }
//    }
//
//    @Override
//    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//        int i = compoundButton.getId();
//        if (i == R.id.single_new && b) {
//            scan_er_code_complete_tv.setVisibility(View.GONE);
//            isSingle = false;
//            pathList.clear();
//            multiSelect = false;
//        }
//        if (i == R.id.continuity_new && b) {
//            scan_er_code_complete_tv.setVisibility(View.VISIBLE);
//            image_num_tv.setVisibility(View.GONE);
//            isSingle = true;
//            multiSelect = true;
//        }
//    }
//
//    /**
//     * TextureView的SurfaceTextureListener
//     */
//    @Override
//    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
//        initCameraAndPreview(width, height);
//    }
//
//    @Override
//    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
//
//    }
//
//    @Override
//    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
//        //释放camera
//        if (mCameraDevice != null) {
//            mCameraDevice.close();
//            mCameraDevice = null;
//        }
//        return false;
//    }
//
//    @Override
//    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
//
//    }
//
//    private Size[] savePicSize;
//    private Size[] previewSize;
//    private int mDisplayRotation;                                                       //手机方向
//    private int mCameraSensorOrientation = 0;                                            //摄像头方向
//
//    //初始化相机和预览设置
//    @TargetApi(19)
//    public void initCameraAndPreview(int width, int height) {
//        HandlerThread handlerThread = new HandlerThread("My First Camera2");
//        handlerThread.start();
//        mHandler = new Handler(handlerThread.getLooper());
//        mainHandler = new Handler(getActivity().getMainLooper());//用来处理ui线程的handler，即ui线程
//        try {
//            // CameraCharacteristics.LENS_FACING_FRONT-后置摄像头； LENS_FACING_BACK-前置摄像头
//            mCameraId = "" + CameraCharacteristics.LENS_FACING_FRONT;
//
//            mCameraManager = (CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE);
//
//            //动态申请camera权限--不加会报错
//            if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 0);
//            }
//            //            List<Size> sizes = getCameraOutputSizes(mCameraId, SurfaceTexture.class);
//            //获取StreamConfigurationMap，它是管理摄像头支持的所有输出格式和尺寸
//            CameraCharacteristics mCameraCharacteristics = mCameraManager.getCameraCharacteristics(mCameraId);
//            StreamConfigurationMap configurationMap = mCameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
//
//            targetSize = new Size(width, height);
//
//            savePicSize = configurationMap.getOutputSizes(ImageFormat.JPEG);          //保存照片尺寸
//            previewSize = configurationMap.getOutputSizes(SurfaceTexture.class);        //预览尺寸
//
//            //获取摄像头方向
//            mCameraSensorOrientation = mCameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
//            boolean exchange = exchangeWidthAndHeight(mDisplayRotation, mCameraSensorOrientation);
//            if (exchange) {
//                bestSave = getBestSize(targetSize.getHeight(), targetSize.getWidth(), targetSize.getHeight(), targetSize.getWidth(), Arrays.asList(savePicSize));
//                bestPreview = getBestSize(targetSize.getHeight(), targetSize.getWidth(), targetSize.getHeight(), targetSize.getWidth(), Arrays.asList(previewSize));
//            } else {
//                bestSave = getBestSize(targetSize.getWidth(), targetSize.getHeight(), targetSize.getWidth(), targetSize.getHeight(), Arrays.asList(savePicSize));
//                bestPreview = getBestSize(targetSize.getWidth(), targetSize.getHeight(), targetSize.getWidth(), targetSize.getHeight(), Arrays.asList(previewSize));
//            }
//            //            Log.i("yzl", "mAutoFixTextureView-------Size-----------------------height:" + mAutoFixTextureView.getHeight() + "----------------------width:" + mAutoFixTextureView.getWidth() + "-----------");
//            //            Log.i("yzl", "target-------Size-----------------------height:" + targetSize.getHeight() + "----------------------width:" + targetSize.getWidth() + "-----------");
//            //            Log.i("yzl", "save--best-------Size-----------------------height:" + bestSave.getHeight() + "----------------------width:" + bestSave.getWidth() + "-----------");
//            //            Log.i("yzl", "preview --best-------Size-----------------------height:" + bestPreview.getHeight() + "----------------------width:" + bestPreview.getWidth() + "-----------");
//
//            mAutoFixTextureView.setAspectRation(bestPreview.getWidth(), bestPreview.getHeight());
//            mAutoFixTextureView.getSurfaceTexture().setDefaultBufferSize(bestPreview.getWidth(), bestPreview.getHeight());
//            // 设置ImageReader，用来读取拍摄图像的类
//            mImageReader = ImageReader.newInstance(bestSave.getWidth(), bestSave.getHeight(), ImageFormat.JPEG,/*maxImages*/2);
//            //这里必须传入mainHandler，因为涉及到了Ui操作
//            mImageReader.setOnImageAvailableListener(mOnImageAvailableListener, mainHandler);
//            mCameraManager.openCamera(mCameraId, deviceStateCallback, mHandler);
//        } catch (CameraAccessException e) {
//            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void openFlash() {
//        mPreviewBuilder.set(CaptureRequest.FLASH_MODE,
//                            CaptureRequest.FLASH_MODE_TORCH);
//        try {
//            mSession.setRepeatingRequest(mPreviewBuilder.build(), mSessionCaptureCallback, mHandler);
//        } catch (CameraAccessException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void closeFlash() {
//        mPreviewBuilder.set(CaptureRequest.FLASH_MODE,
//                            CaptureRequest.FLASH_MODE_OFF);
//        try {
//            mSession.setRepeatingRequest(mPreviewBuilder.build(), mSessionCaptureCallback, mHandler);
//        } catch (CameraAccessException e) {
//            e.printStackTrace();
//        }
//    }
//
//    Size bestSave;
//    Size bestPreview;
//
//    private Size getBestSize(int targetWidth, int targetHeight, int maxWidth, int maxHeight, List<Size> sizeList) {
//        List<Size> bigEnough = new ArrayList<Size>();     //比指定宽高大的Size列表
//        List<Size> notBigEnough = new ArrayList<Size>();  //比指定宽高小的Size列表
//
//        for (Size size : sizeList) {
//            //宽<=最大宽度  &&  高<=最大高度  &&  宽高比 == 目标值宽高比
//            if (size.getWidth() <= maxWidth && size.getHeight() <= maxHeight
//                    && size.getWidth() == size.getHeight() * targetWidth / targetHeight) {
//
//                if (size.getWidth() >= targetWidth && size.getHeight() >= targetHeight)
//                    bigEnough.add(size);
//                else
//                    notBigEnough.add(size);
//            }
//        }
//
//        //选择bigEnough中最小的值  或 notBigEnough中最大的值
//        Size size;
//        if (bigEnough.size() > 0) {
//            size = Collections.min(bigEnough, new CompareSizesByArea());
//        } else if (notBigEnough.size() > 0) {
//            size = Collections.max(notBigEnough, new CompareSizesByArea());
//        } else {
//            size = sizeList.get(0);
//        }
//        return size;
//    }
//
//    private class CompareSizesByArea implements Comparator<Size> {
//        @Override
//        public int compare(Size size1, Size size2) {
//            return java.lang.Long.signum(size1.getWidth() * size1.getHeight() - size2.getWidth() * size2.getHeight());
//        }
//    }
//
//    /**
//     * 根据提供的屏幕方向 [displayRotation] 和相机方向 [sensorOrientation] 返回是否需要交换宽高
//     */
//    private boolean exchangeWidthAndHeight(int displayRotation, int sensorOrientation) {
//        boolean exchange = false;
//        switch (displayRotation) {
//            case Surface.ROTATION_0:
//                if (sensorOrientation == 90 || sensorOrientation == 270) {
//                    exchange = true;
//                }
//                break;
//            case Surface.ROTATION_180:
//                if (sensorOrientation == 90 || sensorOrientation == 270) {
//                    exchange = true;
//                }
//                break;
//            case Surface.ROTATION_90:
//                if (sensorOrientation == 0 || sensorOrientation == 180) {
//                    exchange = true;
//                    break;
//                }
//            case Surface.ROTATION_270:
//                if (sensorOrientation == 0 || sensorOrientation == 180) {
//                    exchange = true;
//                    break;
//                }
//        }
//        return exchange;
//    }
//
//    //获取接近手机屏幕比例的图片大小
//    //    private Size getFixSize(List<Size> sizes) {
//    //        int heightDpi = FullHeightUtils.getHeightDpi(getActivity());
//    //        int widthDpi = FullHeightUtils.getWidthDpi(getActivity());
//    //        double optimalSize = heightDpi / (double) widthDpi;
//    //        Size sizeOpt = sizes.get(0);
//    //        int sizeFirstHeight = sizes.get(0).getHeight();
//    //        int sizeFirstWidth = sizes.get(0).getWidth();
//    //        double sizeFirstOptimalSize = sizeFirstHeight / (double) sizeFirstWidth;
//    //        double diffTemp = Math.abs(sizeFirstOptimalSize - optimalSize);
//    //
//    //        for (Size size : sizes) {
//    //            int sizeHeight = size.getHeight();
//    //            int sizeWidth = size.getWidth();
//    //            double sizeOptimalSize = sizeHeight / (double) sizeWidth;
//    //            double diff = Math.abs(sizeOptimalSize - optimalSize);
//    //            if (diff < diffTemp) {
//    //                sizeOpt = size;
//    //            }
//    //        }
//    //        //        return sizeOpt;
//    //        return sizes.get(0);
//    //    }
//
//    //打开相机预览
//    public void takePreview() throws CameraAccessException {
//        mPreviewBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
//        Surface surface = new Surface(mAutoFixTextureView.getSurfaceTexture());
//        mPreviewBuilder.addTarget(surface);
//        mCameraDevice.createCaptureSession(Arrays.asList(surface, mImageReader.getSurface()), mSessionPreviewStateCallback, mHandler);
//    }
//
//    //拍照
//    public void takePicture() {
//        try {
//            CaptureRequest.Builder captureRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);//用来设置拍照请求的request
//            captureRequestBuilder.addTarget(mImageReader.getSurface());
//            // 自动对焦
//            captureRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
//            // 自动曝光
//            captureRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
//
//            int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();
//            CameraCharacteristics cameraCharacteristics = mCameraManager.getCameraCharacteristics(mCameraId);
//            captureRequestBuilder.set(CaptureRequest.JPEG_ORIENTATION, getJpegOrientation(cameraCharacteristics, rotation));//使图片做顺时针旋转
//            //            captureRequestBuilder.set(CaptureRequest.)
//            CaptureRequest mCaptureRequest = captureRequestBuilder.build();
//            mSession.capture(mCaptureRequest, mSessionCaptureCallback, mHandler);
//
//        } catch (CameraAccessException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 根据输出类获取指定相机的输出尺寸列表，降序排序
//     *
//     * @param cameraId 相机id
//     * @param clz      输出类
//     * @return
//     */
//    public List<Size> getCameraOutputSizes(String cameraId, Class clz) {
//        //        try {
//        //            CameraCharacteristics characteristics = mCameraManager.getCameraCharacteristics(cameraId);
//        //            StreamConfigurationMap configs = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
//        //            List<Size> sizes = Arrays.asList(configs.getOutputSizes(clz));
//        //            Collections.sort(sizes, new Comparator<Size>() {
//        //                @Override
//        //                public int compare(Size o1, Size o2) {
//        //                    return o1.getWidth() * o1.getHeight() - o2.getWidth() * o2.getHeight();
//        //                }
//        //            });
//        //            Collections.reverse(sizes);
//        //            return sizes;
//        //        } catch (CameraAccessException e) {
//        //            e.printStackTrace();
//        //        }
//        //
//        return null;
//    }
//
//    //获取图片应该旋转的角度，使图片竖直
//    private int getJpegOrientation(CameraCharacteristics c, int deviceOrientation) {
//        if (deviceOrientation == android.view.OrientationEventListener.ORIENTATION_UNKNOWN)
//            return 0;
//        int sensorOrientation = c.get(CameraCharacteristics.SENSOR_ORIENTATION);
//
//        // Round device orientation to a multiple of 90
//        deviceOrientation = (deviceOrientation + 45) / 90 * 90;
//
//        // LENS_FACING相对于设备屏幕的方向,LENS_FACING_FRONT相机设备面向与设备屏幕相同的方向
//        boolean facingFront = c.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_FRONT;
//        if (facingFront)
//            deviceOrientation = -deviceOrientation;
//
//        // Calculate desired JPEG orientation relative to camera orientation to make
//        // the image upright relative to the device orientation
//        int jpegOrientation = (sensorOrientation + deviceOrientation + 360) % 360;
//
//        return jpegOrientation;
//    }
//
//
//    /**
//     * 获取登录token（传输未加密）
//     */
//    @SuppressLint("CheckResult")
//    public void getToken() {
//        RetrofitHelper.getInstance().getService().getToken(Params.userName).compose(SchedulerProvider.getInstance().<GetTokenBean>applySchedulers()).compose((ObservableTransformer) ResponseTransformer.handleResult()).subscribe(new Consumer<GetTokenBean>() {
//            @Override
//            public void accept(GetTokenBean getTokenBean) throws Exception {
//                token = getTokenBean.getToken();
//            }
//        }, new Consumer<ApiException>() {
//            @Override
//            public void accept(ApiException throwable) throws Exception {
//            }
//        });
//    }
//
//
//    /**
//     * 获取登录token（传输数据DES加密）
//     */
//    @SuppressLint("CheckResult")
//    public void getTokenLogin() {
//        String user = null;
//        try {
//            user = DESCryptography.encryptedDES(Params.userName);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        String json = "{\"session\":\"" + user + "\"}";
//        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
//        RetrofitHelper.getInstance().getService().getTokenDes(body).compose(SchedulerProvider.getInstance().<GetTokenBean>applySchedulers()).compose((ObservableTransformer) ResponseTransformer.handleResult()).subscribe(new Consumer<GetTokenBean>() {
//            @Override
//            public void accept(GetTokenBean getTokenBean) throws Exception {
//                token = getTokenBean.getToken();
//            }
//        }, new Consumer<ApiException>() {
//            @Override
//            public void accept(ApiException throwable) throws Exception {
//            }
//        });
//    }
//
//    /**
//     * 获取识别信息(旧版)
//     */
//    @SuppressLint("CheckResult")
//    public void getIdentification(List<IdentificationBean.ImagelistBean> imagelistBeans, String from) {
//        if (from.equals("camera")) {
//            camera_gray_back.setVisibility(View.VISIBLE);
//        }
//        DialogUtil.progressDialog(getContext(), "");
//        IdentificationBean identificationBean = new IdentificationBean();
//        if (getActivity().getIntent().getStringExtra("ocrflag") != null) {
//            identificationBean.setOcrflag(getActivity().getIntent().getStringExtra("ocrflag"));
//        }
//        if (getActivity().getIntent().getStringExtra("uploadtempflag") != null) {
//            identificationBean.setUploadtempflag(getActivity().getIntent().getStringExtra("uploadtempflag"));
//        }
//        identificationBean.setImagelist(imagelistBeans);
//        Gson gson = new Gson();
//        String json = gson.toJson(identificationBean);//将bean类转换成json字符串
//        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
//        //        RetrofitUrlManager.getInstance().putDomain("putFile", Params.BaseUrl);//更新服务器访问地址
//        RetrofitHelper.getInstance().getService().ocrScan(token, body).compose(SchedulerProvider.getInstance().<IdentificationBackBean>applySchedulers()).compose((ObservableTransformer) ResponseTransformer.handleResult()).subscribe(new Consumer<IdentificationBackBean>() {
//            @Override
//            public void accept(IdentificationBackBean o) throws Exception {
//                if (o.getSucessflag().equals("true")) {
//                    boolean result = true;
//                    for (int i = 0; i < o.getImagelist().size(); i++) {
//                        if (!o.getImagelist().get(i).isResultflag()) {
//                            result = false;
//                        }
//                    }
//                    if (result) {
//                        if (!isSingle) {
//                            for (int i = 0; i < o.getImagelist().size(); i++) {
//                                o.getImagelist().get(i).setImageFilePath(path);
//                            }
//                        } else {
//
//                            for (int i = 0; i < pathList.size(); i++) {
//                                for (int j = 0; j < o.getImagelist().size(); j++) {
//                                    if (o.getImagelist().get(j).getFilename().equals(pathList.get(i).get("name"))) {
//                                        o.getImagelist().get(j).setImageFilePath(pathList.get(i).get("path"));
//                                    }
//                                }
//                            }
//                            pathList.clear();
//                            single.setEnabled(true);
//                            continuity.setEnabled(true);
//                        }
//                        ocrScanActivity.invoiceScannerCallBack.TakePhotoMessage(o);
//                        camera_gray_back.setVisibility(View.GONE);
//                        getActivity().finish();
//                        DialogUtil.dismissDialog();
//                    } else {
//                        camera_gray_back.setVisibility(View.GONE);
//                        Toast.makeText(getContext(), "识别失败，请重试", Toast.LENGTH_SHORT).show();
//                        if (mCameraDevice != null) {
//                            takePreview();
//                        }
//                        DialogUtil.dismissDialog();
//                    }
//                } else {
//                    camera_gray_back.setVisibility(View.GONE);
//                    Toast.makeText(getContext(), "识别失败，请重试", Toast.LENGTH_SHORT).show();
//                    if (mCameraDevice != null) {
//                        takePreview();
//                    }
//                    DialogUtil.dismissDialog();
//                }
//
//            }
//        }, new Consumer<ApiException>() {
//            @Override
//            public void accept(ApiException throwable) throws Exception {
//                DialogUtil.dismissDialog();
//                camera_gray_back.setVisibility(View.GONE);
//                if (mCameraDevice != null) {
//                    takePreview();
//                }
//                Toast.makeText(getContext(), throwable.getDisplayMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    /**
//     * 获取识别信息(新版)
//     */
//    @SuppressLint("CheckResult")
//    public void getIdentificationBatch(List<IdentificationBean.ImagelistBean> imagelistBeans, String from) {
//        if (from.equals("camera")) {
//            camera_gray_back.setVisibility(View.VISIBLE);
//        }
//        DialogUtil.progressDialog(getContext(), "");
//        IdentificationBean identificationBean = new IdentificationBean();
//        if (getActivity().getIntent().getStringExtra("ocrflag") != null) {
//            identificationBean.setOcrflag(getActivity().getIntent().getStringExtra("ocrflag"));
//        }
//        if (getActivity().getIntent().getStringExtra("uploadtempflag") != null) {
//            identificationBean.setUploadtempflag(getActivity().getIntent().getStringExtra("uploadtempflag"));
//        }
//        if (getActivity().getIntent().getStringExtra("unitid") != null) {
//            identificationBean.setUnitid(getActivity().getIntent().getStringExtra("unitid"));
//        }
//        identificationBean.setImagelist(imagelistBeans);
//        Gson gson = new Gson();
//        String json = gson.toJson(identificationBean);//将bean类转换成json字符串
//        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
//        //        RetrofitUrlManager.getInstance().putDomain("putFile", Params.BaseUrl);//更新服务器访问地址
//        RetrofitHelper.getInstance().getService().ocrBatchScan(token, body)
//                .compose(SchedulerProvider.getInstance().<OcrScanBean>applySchedulers())
//                .compose((ObservableTransformer) ResponseTransformer.handleResult())
//                .subscribe(new Consumer<OcrScanBean>() {
//                    @Override
//                    public void accept(OcrScanBean o) throws Exception {
//                        if (o.getSucessflag().equals("true")) {
//                            if (!isSingle) {
//                            } else {
//                                pathList.clear();
//                                single.setEnabled(true);
//                                continuity.setEnabled(true);
//                            }
//                            ocrScanActivity.invoiceScannerCallBack.TakePhotoBatch(o);
//                            camera_gray_back.setVisibility(View.GONE);
//                            getActivity().finish();
//                            DialogUtil.dismissDialog();
//
//                        } else {
//                            camera_gray_back.setVisibility(View.GONE);
//                            Toast.makeText(getContext(), "识别失败，请重试", Toast.LENGTH_SHORT).show();
//                            if (mCameraDevice != null) {
//                                takePreview();
//                            }
//                            DialogUtil.dismissDialog();
//                        }
//
//                    }
//                }, new Consumer<ApiException>() {
//                    @Override
//                    public void accept(ApiException throwable) throws Exception {
//                        DialogUtil.dismissDialog();
//                        camera_gray_back.setVisibility(View.GONE);
//                        if (mCameraDevice != null) {
//                            takePreview();
//
//                        }
//                        Toast.makeText(getContext(), throwable.getDisplayMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//
//    public void continuePost(String from) {
//        if (mCameraDevice != null) {
//            //停止预览
//            mCameraDevice.close();
//        }
//        List<IdentificationBean.ImagelistBean> imagelistBeans = new ArrayList<>();
//        for (int i = 0; i < pathList.size(); i++) {
//            IdentificationBean.ImagelistBean imagelistBean = new IdentificationBean.ImagelistBean();
//            imagelistBean.setFilename(pathList.get(i).get("name"));
//            imagelistBean.setImagedata(BitmapUtils.fileToBase64(pathList.get(i).get("path")));
//            imagelistBeans.add(imagelistBean);
//        }
//        if (getActivity().getIntent().getStringExtra("url").equals("1")) {
//            getIdentification(imagelistBeans, "camera");
//        } else {
//            getIdentificationBatch(imagelistBeans, "camera");
//        }
//    }
//}
