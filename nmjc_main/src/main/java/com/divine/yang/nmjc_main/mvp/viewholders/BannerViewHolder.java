package com.divine.yang.nmjc_main.mvp.viewholders;

import android.view.View;
import android.widget.ImageView;

import com.divine.yang.nmjc_main.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Author: Divine
 * CreateDate: 2021/1/13
 * Describe:
 */
public class BannerViewHolder extends RecyclerView.ViewHolder {
    public ImageView banner;

    public BannerViewHolder(@NonNull View itemView) {
        super(itemView);
        banner = itemView.findViewById(R.id.home_swipe_img);
    }
}
