package com.divine.yang.module_camera2;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Author: Divine
 * CreateDate: 2020/10/26
 * Describe:
 */
public class MainViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;
    public TextView textView;
    public MainViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.adapter_main_item_image);
        textView=itemView.findViewById(R.id.adapter_main_item_text);
    }
}
