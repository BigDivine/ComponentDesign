package com.divine.yang.module_camera2.imageselect;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.divine.yang.module_camera2.R;
import com.divine.yang.module_camera2.imageselect.interfaces.OnPicSelectFragmentRvItemClickListener;

import java.io.File;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

/**
 * Author: Divine
 * CreateDate: 2020/10/22
 * Describe:
 */
public class PicSelectFragmentVpAdapter extends PagerAdapter {
    private Context mContext;
    private List<Camera2Image> images;
    private PicSelectConfig config;
    private OnPicSelectFragmentRvItemClickListener listener;

    public PicSelectFragmentVpAdapter(Context mContext, List<Camera2Image> images, PicSelectConfig config) {
        this.mContext = mContext;
        this.images = images;
        this.config = config;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.adapter_pic_select_fragment_vp_item, container, false);
        ImageView mAdapterPicSelectFragmentVpItemImg = rootView.findViewById(R.id.adapter_pic_select_fragment_vp_item_img);
        ImageButton mAdapterPicSelectFragmentVpItemCheck = rootView.findViewById(R.id.adapter_pic_select_fragment_vp_item_check);
        if (config.multiSelect) {
            mAdapterPicSelectFragmentVpItemCheck.setVisibility(View.VISIBLE);
            final Camera2Image image = images.get(config.needCamera ? position + 1 : position);
            if (PicSelectStaticVariable.mPicSelectImageList.contains(image.path)) {
                mAdapterPicSelectFragmentVpItemCheck.setImageResource(R.mipmap.ic_check_true_blue_white);
            } else {
                mAdapterPicSelectFragmentVpItemCheck.setImageResource(R.mipmap.ic_check_false_blue_alpha_grey);
            }
            Glide.with(mContext).load(Uri.fromFile(new File(image.path))).into(mAdapterPicSelectFragmentVpItemImg);
            mAdapterPicSelectFragmentVpItemCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int ret = listener.onItemCheckClick(view, position, image);
                        if (ret == 1) { // 局部刷新
                            if (PicSelectStaticVariable.mPicSelectImageList.contains(image.path)) {
                                mAdapterPicSelectFragmentVpItemCheck.setImageResource(R.mipmap.ic_check_true_blue_white);
                            } else {
                                mAdapterPicSelectFragmentVpItemCheck.setImageResource(R.mipmap.ic_check_false_blue_alpha_grey);
                            }
                        }
                    }
                }
            });

            mAdapterPicSelectFragmentVpItemImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onItemClick(view, position, images.get(position));
                    }
                }
            });
        } else {
            mAdapterPicSelectFragmentVpItemCheck.setVisibility(View.GONE);
        }
        container.addView(rootView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return rootView;
    }

    @Override
    public int getCount() {
        if (config.needCamera)
            return images.size() - 1;
        else
            return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void setListener(OnPicSelectFragmentRvItemClickListener listener) {
        this.listener = listener;
    }
}
