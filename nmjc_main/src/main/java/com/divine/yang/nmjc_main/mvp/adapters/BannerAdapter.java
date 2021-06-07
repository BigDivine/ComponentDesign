package com.divine.yang.nmjc_main.mvp.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.divine.yang.nmjc_main.R;
import com.divine.yang.nmjc_main.mvp.viewholders.BannerViewHolder;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Author: Divine
 * CreateDate: 2021/1/13
 * Describe:
 */
public class BannerAdapter extends RecyclerView.Adapter<BannerViewHolder> {
    private Context mContext;
    private ArrayList<Integer> banners;

    public BannerAdapter(Context mContext, ArrayList<Integer> banners) {
        this.mContext = mContext;
        this.banners = banners;
    }

    @NonNull
    @Override
    public BannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.vp_banner_item, parent, false);
        BannerViewHolder vh = new BannerViewHolder(rootView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull BannerViewHolder holder, int position) {
        int drawableId=banners.get(position);
        Glide.with(mContext).asBitmap().centerCrop().load(drawableId).into(holder.banner);
    }

    @Override
    public int getItemCount() {
        return banners.size();
    }
}
