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
public class BillViewHolder extends RecyclerView.ViewHolder {
    public TextView billTitle, billState, billDate, billJourneyNum, billAirCode;

    public BillViewHolder(@NonNull View itemView) {
        super(itemView);
        billTitle = itemView.findViewById(R.id.home_bill_title);
        billState = itemView.findViewById(R.id.home_bill_state);
        billDate = itemView.findViewById(R.id.home_bill_date);
        billJourneyNum = itemView.findViewById(R.id.home_bill_journey_num);
        billAirCode = itemView.findViewById(R.id.home_bill_air_code);
    }
}
