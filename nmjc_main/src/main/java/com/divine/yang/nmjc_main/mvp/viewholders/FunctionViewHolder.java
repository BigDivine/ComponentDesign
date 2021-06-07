package com.divine.yang.nmjc_main.mvp.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.divine.yang.nmjc_main.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Author: Divine
 * CreateDate: 2021/1/13
 * Describe:
 */
public class FunctionViewHolder extends RecyclerView.ViewHolder {

    public ImageView icon;
    public TextView title;

    public FunctionViewHolder(@NonNull View itemView) {
        super(itemView);
        icon = itemView.findViewById(R.id.function_icon);
        title = itemView.findViewById(R.id.function_title);
    }
}
