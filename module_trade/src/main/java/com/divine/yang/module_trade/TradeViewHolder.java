package com.divine.yang.module_trade;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Author: Divine
 * CreateDate: 2020/11/9
 * Describe:
 */
public class TradeViewHolder extends RecyclerView.ViewHolder {
    private ImageView tradeImg;

    public TradeViewHolder(@NonNull View itemView) {
        super(itemView);
        tradeImg = itemView.findViewById(R.id.trade_img);
    }

    public ImageView getTradeImg() {
        return tradeImg;
    }
}
