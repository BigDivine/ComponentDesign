package com.divine.yang.module_camera2.imageselect.interfaces;

import com.divine.yang.module_camera2.imageselect.Camera2Image;

/**
 * Author: Divine
 * CreateDate: 2020/10/20
 * Describe:
 */
public interface OnItemClickListener {

    int onCheckedClick(int position, Camera2Image image);

    void onImageClick(int position, Camera2Image image);
}

