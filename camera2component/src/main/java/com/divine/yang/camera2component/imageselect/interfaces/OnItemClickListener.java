package com.divine.yang.camera2component.imageselect.interfaces;

import com.divine.yang.camera2component.imageselect.Image;

/**
 * Author: Divine
 * CreateDate: 2020/10/20
 * Describe:
 */
public interface OnItemClickListener {

    int onCheckedClick(int position, Image image);

    void onImageClick(int position, Image image);
}

