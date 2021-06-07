package com.divine.yang.nmjc_main.mvp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.divine.yang.nmjc_main.R;
import com.divine.yang.nmjc_main.mvp.viewholders.NotificationViewHolder;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Author: Divine
 * CreateDate: 2021/1/13
 * Describe:
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationViewHolder> {
    private Context mContext;
    private ArrayList<String> notifications;

    public NotificationAdapter(Context mContext, ArrayList<String> notifications) {
        this.mContext = mContext;
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.vp_notification_item, parent, false);
        NotificationViewHolder vh = new NotificationViewHolder(rootView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        holder.content.setText(notifications.get(position));
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }
}
