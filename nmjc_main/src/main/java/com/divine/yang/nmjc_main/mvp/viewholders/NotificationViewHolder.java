package com.divine.yang.nmjc_main.mvp.viewholders;

import android.view.View;
import android.widget.TextView;

import com.divine.yang.nmjc_main.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Author: Divine
 * CreateDate: 2021/1/13
 * Describe:
 */
public class NotificationViewHolder extends RecyclerView.ViewHolder {
    public TextView content;

    public NotificationViewHolder(@NonNull View itemView) {
        super(itemView);
        content = itemView.findViewById(R.id.notification_content);
    }
}
