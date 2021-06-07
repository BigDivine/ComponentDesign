package com.divine.yang.nmjc_main.mvp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.divine.yang.nmjc_main.R;
import com.divine.yang.nmjc_main.mvp.beans.BillBean;
import com.divine.yang.nmjc_main.mvp.viewholders.BillViewHolder;
import com.divine.yang.nmjc_main.mvp.viewholders.NotificationViewHolder;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Author: Divine
 * CreateDate: 2021/1/13
 * Describe:
 */
public class BillAdapter extends RecyclerView.Adapter<BillViewHolder> {
    private Context mContext;
    private ArrayList<BillBean> bills;

    public BillAdapter(Context mContext, ArrayList<BillBean> bills) {
        this.mContext = mContext;
        this.bills = bills;
    }

    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.rv_bill_item, parent, false);
        BillViewHolder vh = new BillViewHolder(rootView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull BillViewHolder holder, int position) {
        BillBean bill=bills.get(position);
//        holder.billTitle.setText(bill.getBillTitle());
//        holder.billState.setText(bill.getBillState());
//        holder.billDate.setText(bill.getBillDate());
//        holder.billJourneyNum.setText(bill.getBillJourneyNum());
//        holder.billAirCode.setText(bill.getBillAirCode());
        holder.billTitle.setText("客桥使用清单");
        holder.billState.setText("签字完成");
        holder.billDate.setText(String.format("航班时间：%s","2020-08-16"));
        holder.billJourneyNum.setText(String.format("航班号：%s","6"));
        holder.billAirCode.setText(String.format("机号：%s","2020"));
     }

    @Override
    public int getItemCount() {
        return bills.size();
    }
}
