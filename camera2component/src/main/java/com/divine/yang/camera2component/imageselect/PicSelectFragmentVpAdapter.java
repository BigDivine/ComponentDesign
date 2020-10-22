package com.divine.yang.camera2component.imageselect;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.divine.yang.camera2component.R;
import com.divine.yang.camera2component.imageselect.interfaces.OnPicSelectFragmentRvItemClickListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

/**
 * Author: Divine
 * CreateDate: 2020/10/22
 * Describe:
 */
class PicSelectFragmentVpAdapter extends PagerAdapter {
    private Activity activity;
    private List<Image> images;
    private PicSelectConfig config;
    private OnPicSelectFragmentRvItemClickListener listener;

    public PicSelectFragmentVpAdapter(Activity activity, List<Image> images, PicSelectConfig config) {
        this.activity = activity;
        this.images = images;
        this.config = config;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View rootView = LayoutInflater.from(activity).inflate(R.layout.adapter_pic_select_fragment_vp_item, container, false);
        ImageView mAdapterPicSelectFragmentVpItemImg = rootView.findViewById(R.id.adapter_pic_select_fragment_vp_item_img);
        ImageButton mAdapterPicSelectFragmentVpItemCheck = rootView.findViewById(R.id.adapter_pic_select_fragment_vp_item_check);
        if (config.multiSelect) {
            mAdapterPicSelectFragmentVpItemCheck.setVisibility(View.VISIBLE);
            final Image image = images.get(config.needCamera ? position + 1 : position);
            if (PicSelectStaticVariable.mPicSelectImageList.contains(image.path)) {
                mAdapterPicSelectFragmentVpItemCheck.setImageResource(R.mipmap.ic_checked);
            } else {
                mAdapterPicSelectFragmentVpItemCheck.setImageResource(R.mipmap.ic_uncheck);
            }
            mAdapterPicSelectFragmentVpItemCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int ret = listener.onItemCheckClick(view, position, image);
                        if (ret == 1) { // 局部刷新
                            if (PicSelectStaticVariable.mPicSelectImageList.contains(image.path)) {
                                mAdapterPicSelectFragmentVpItemCheck.setImageResource(R.mipmap.ic_checked);
                            } else {
                                mAdapterPicSelectFragmentVpItemCheck.setImageResource(R.mipmap.ic_uncheck);
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
