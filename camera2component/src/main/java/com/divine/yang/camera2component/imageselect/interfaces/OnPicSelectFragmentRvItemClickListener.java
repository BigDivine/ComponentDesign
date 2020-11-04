package com.divine.yang.camera2component.imageselect.interfaces;

import android.view.View;

import com.divine.yang.camera2component.imageselect.Camera2Image;

/**
 * Author: Divine
 * CreateDate: 2020/10/22
 * Describe:
 */
public interface OnPicSelectFragmentRvItemClickListener {
    void onItemClick(View view,int position, Camera2Image item );
    int onItemCheckClick(View view,int position, Camera2Image item );
}
