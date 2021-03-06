package com.divine.yang.module_camera2.imageselect;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.divine.yang.module_camera2.R;
import com.divine.yang.module_camera2.imageselect.interfaces.OnPicSelectFragmentRvItemClickListener;

import java.io.File;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Author: Divine
 * CreateDate: 2020/10/22
 * Describe:
 */
public class PicSelectFragmentRvAdapter extends RecyclerView.Adapter<PicSelectFragmentRvViewHolder> {
    private Context mContext;
    private List<Camera2Image> data;
    private PicSelectConfig config;
    private boolean showCamera, multiSelect;
    private OnPicSelectFragmentRvItemClickListener listener;

    public PicSelectFragmentRvAdapter(Context mContext, List<Camera2Image> data, PicSelectConfig config) {
        this.mContext = mContext;
        this.data = data;
        this.config = config;
    }

    @NonNull
    @Override
    public PicSelectFragmentRvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.adapter_pic_select_fragment_rv_item, parent, false);
        PicSelectFragmentRvViewHolder mPicSelectFragmentRvViewHolder = new PicSelectFragmentRvViewHolder(rootView);
        return mPicSelectFragmentRvViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PicSelectFragmentRvViewHolder holder, int position) {
        Camera2Image itemData = this.data.get(position);
        if (position == 0 && showCamera) {
            holder.mAdapterPicSelectFragmentRvItemImg.setImageResource(R.mipmap.ic_camera_grey);
            holder.mAdapterPicSelectFragmentRvItemImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null)
                        listener.onItemClick(view, position, itemData);
                }
            });
            return;
        }

        if (multiSelect) {
            holder.mAdapterPicSelectFragmentRvItemCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int ret = listener.onItemCheckClick(view, position, itemData);
                        if (ret == 1) { // 局部刷新
                            if (PicSelectStaticVariable.mPicSelectImageList.contains(itemData.path)) {
                                holder.mAdapterPicSelectFragmentRvItemCheck.setImageResource(R.mipmap.ic_check_true_blue_white);
                            } else {
                                holder.mAdapterPicSelectFragmentRvItemCheck.setImageResource(R.mipmap.ic_check_false_blue_alpha_grey);
                            }
                        }
                    }
                }
            });
        }

        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null)
                    listener.onItemClick(view, position, itemData);
            }
        });
        //        if (isScrolling) {
        //            Glide.with(mContext).load(R.mipmap.ic_default_image).into(holder.mAdapterPicSelectFragmentRvItemImg);
        //        } else {
        Glide.with(mContext)
                .asBitmap()
                .placeholder(R.mipmap.ic_default_img_grey_alpha)
                //                    .load(BitmapFactory.decodeFile(itemData.path))
                .load(Uri.fromFile(new File(itemData.path)))
//                .override(100, 100)
                .into(holder.mAdapterPicSelectFragmentRvItemImg);
        //        }
        if (multiSelect) {
            holder.mAdapterPicSelectFragmentRvItemCheck.setVisibility(View.VISIBLE);
            if (PicSelectStaticVariable.mPicSelectImageList.contains(itemData.path)) {
                holder.mAdapterPicSelectFragmentRvItemCheck.setImageResource(R.mipmap.ic_check_true_blue_white);
            } else {
                holder.mAdapterPicSelectFragmentRvItemCheck.setImageResource(R.mipmap.ic_check_false_blue_alpha_grey);
            }
        } else {
            holder.mAdapterPicSelectFragmentRvItemCheck.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return null != this.data ? this.data.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && showCamera) {
            return 1;
        }
        return 0;
    }

    public void setShowCamera(boolean showCamera) {
        this.showCamera = showCamera;
    }

    public void setMultiSelect(boolean multiSelect) {
        this.multiSelect = multiSelect;
    }

    public void setListener(OnPicSelectFragmentRvItemClickListener listener) {
        this.listener = listener;
    }
}

