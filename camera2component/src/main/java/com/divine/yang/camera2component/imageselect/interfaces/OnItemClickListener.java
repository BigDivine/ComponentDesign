package com.divine.yang.camera2component.imageselect.interfaces;

import com.divine.yang.camera2component.imageselect.Camera2Image;

/**
 * Author: Divine
 * CreateDate: 2020/10/20
 * Describe:
 */
public interface OnItemClickListener {

    int onCheckedClick(int position, Camera2Image image);

    void onImageClick(int position, Camera2Image image);
}

