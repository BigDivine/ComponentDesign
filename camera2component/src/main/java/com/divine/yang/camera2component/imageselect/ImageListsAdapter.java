//package com.divine.yang.camera2component.imageselect;
//
//import android.content.Context;
//import android.view.View;
//import android.widget.ImageView;
//
//import com.bumptech.glide.Glide;
//import com.jiuqi.image.config.ISListConfig;
//import com.jiuqi.image.imagelibrary.R;
//import com.jiuqi.image.inter.OnItemClickListener;
//import com.yuyh.easyadapter.recyclerview.EasyRVAdapter;
//import com.yuyh.easyadapter.recyclerview.EasyRVHolder;
//
//import java.util.List;
//
///**
// * Create by dinglp on 2019/4/28
// * 站在顶峰，看世界
// * 落在谷底，思人生
// */
//public class ImageListsAdapter extends EasyRVAdapter<Image> {
//
//    private boolean showCamera;
//    private boolean mutiSelect;
//
//    private PicSelectConfig mPicSelectConfig;
//    private Context context;
//    private OnItemClickListener listener;
//
//    public ImageListsAdapter(Context context, List<Image> list, PicSelectConfig mPicSelectConfig) {
//        super(context, list, R.layout.item_img_sel, R.layout.item_img_sel_take_photo);
//        this.context = context;
//        this.mPicSelectConfig = mPicSelectConfig;
//    }
//
//    @Override
//    protected void onBindData(final EasyRVHolder viewHolder, final int position, final Image item) {
//
//        if (position == 0 && showCamera) {
//            ImageView iv = viewHolder.getView(R.id.ivTakePhoto);
//            iv.setImageResource(R.drawable.ic_take_photo);
//            iv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (listener != null)
//                        listener.onImageClick(position, item);
//                }
//            });
//            return;
//        }
//
//        if (mutiSelect) {
//            viewHolder.getView(R.id.ivPhotoCheaked).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (listener != null) {
//                        int ret = listener.onCheckedClick(position, item);
//                        if (ret == 1) { // 局部刷新
//                            if (Constant.imageList.contains(item.path)) {
//                                viewHolder.setImageResource(R.id.ivPhotoCheaked, R.drawable.ic_checked);
//                            } else {
//                                viewHolder.setImageResource(R.id.ivPhotoCheaked, R.drawable.ic_uncheck);
//                            }
//                        }
//                    }
//                }
//            });
//        }
//
//        viewHolder.setOnItemViewClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (listener != null)
//                    listener.onImageClick(position, item);
//            }
//        });
//
//        final ImageView iv = viewHolder.getView(R.id.ivImage);
////        ISNav.getInstance().displayImage(context, item.path, iv);
//        Glide.with(context).load(item.path).asBitmap().into(iv);
//
//        if (mutiSelect) {
//            viewHolder.setVisible(R.id.ivPhotoCheaked, true);
//            if (Constant.imageList.contains(item.path)) {
//                viewHolder.setImageResource(R.id.ivPhotoCheaked, R.drawable.ic_checked);
//            } else {
//                viewHolder.setImageResource(R.id.ivPhotoCheaked, R.drawable.ic_uncheck);
//            }
//        } else {
//            viewHolder.setVisible(R.id.ivPhotoCheaked, false);
//        }
//    }
//
//    public void setShowCamera(boolean showCamera) {
//        this.showCamera = showCamera;
//    }
//
//    public void setMutiSelect(boolean mutiSelect) {
//        this.mutiSelect = mutiSelect;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        if (position == 0 && showCamera) {
//            return 1;
//        }
//        return 0;
//    }
//
//    public void setOnItemClickListener(OnItemClickListener listener) {
//        this.listener = listener;
//    }
//}