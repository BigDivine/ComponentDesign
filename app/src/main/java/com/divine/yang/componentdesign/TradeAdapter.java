package com.divine.yang.componentdesign;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Author: Divine
 * CreateDate: 2020/11/9
 * Describe:
 */
public class TradeAdapter extends RecyclerView.Adapter<TradeViewHolder> {
    private Context mContext;

    public TradeAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public TradeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.adapter_vp_trade_layout, parent, false);
        TradeViewHolder mTradeViewHolder = new TradeViewHolder(rootView);
        return mTradeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TradeViewHolder holder, int position) {
        holder.getTradeImg().setImageResource(R.mipmap.ic_launcher);
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
