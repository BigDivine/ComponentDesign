package com.divine.yang.module_camera2.imageselect;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.divine.yang.module_camera2.R;

import org.w3c.dom.Text;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Author: Divine
 * CreateDate: 2020/10/22
 * Describe:
 */
public class PicSelectFragmentPopRvViewHolder extends RecyclerView.ViewHolder {
    public ImageView mAdapterPicSelectFragmentPopRvItemImg, mAdapterPicSelectFragmentPopRvItemSelect;
    public TextView mAdapterPicSelectFragmentPopRvItemTitle, mAdapterPicSelectFragmentPopRvItemNum;
    private View itemView;

    public PicSelectFragmentPopRvViewHolder(@NonNull View itemView) {
        super(itemView);
        mAdapterPicSelectFragmentPopRvItemImg = itemView.findViewById(R.id.adapter_pic_select_fragment_pop_rv_item_img);
        mAdapterPicSelectFragmentPopRvItemSelect = itemView.findViewById(R.id.adapter_pic_select_fragment_pop_rv_item_select);
        mAdapterPicSelectFragmentPopRvItemTitle = itemView.findViewById(R.id.adapter_pic_select_fragment_pop_rv_item_title);
        mAdapterPicSelectFragmentPopRvItemNum = itemView.findViewById(R.id.adapter_pic_select_fragment_pop_rv_item_num);
        this.itemView = itemView;
    }

    public View getItemView() {
        return this.itemView;
    }
}
