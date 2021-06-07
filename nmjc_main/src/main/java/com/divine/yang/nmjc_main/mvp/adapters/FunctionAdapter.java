package com.divine.yang.nmjc_main.mvp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.divine.yang.nmjc_main.R;
import com.divine.yang.nmjc_main.mvp.beans.HomeFunction;
import com.divine.yang.nmjc_main.mvp.viewholders.FunctionViewHolder;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Author: Divine
 * CreateDate: 2021/1/13
 * Describe:
 */
public class FunctionAdapter extends RecyclerView.Adapter<FunctionViewHolder> {
    private Context mContext;
    private ArrayList<HomeFunction> functions;

    public FunctionAdapter(Context mContext, ArrayList<HomeFunction> functions) {
        this.mContext = mContext;
        this.functions = functions;
    }

    @NonNull
    @Override
    public FunctionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.rv_function_item, parent, false);
        FunctionViewHolder vh = new FunctionViewHolder(rootView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull FunctionViewHolder holder, int position) {
        HomeFunction function = functions.get(position);
        holder.title.setText(function.getTitle());
//        Glide.with(mContext).asBitmap().load(function.getIcon()).into(holder.icon);
        Glide.with(mContext).asBitmap().load(R.mipmap.icon_function_more).into(holder.icon);
    }

    @Override
    public int getItemCount() {
        return functions.size();
    }
}
