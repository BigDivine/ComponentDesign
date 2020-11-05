package com.divine.yang.module_camera2;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Author: Divine
 * CreateDate: 2020/10/26
 * Describe:
 */
public class MainAdapter extends RecyclerView.Adapter<MainViewHolder> {
    private Context mContext;
    private final List<String> data;

    public MainAdapter(Context mContext, List<String> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.adapter_main_rv_item, null, false);
        MainViewHolder viewHolder = new MainViewHolder(rootView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.textView.setText(data.get(position));
        Glide.with(mContext).load(Uri.fromFile(new File(data.get(position)))).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return null != data ? data.size() : 0;
    }
}
