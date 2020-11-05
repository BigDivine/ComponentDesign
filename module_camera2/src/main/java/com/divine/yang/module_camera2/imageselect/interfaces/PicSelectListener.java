package com.divine.yang.module_camera2.imageselect.interfaces;

import java.io.File;
import java.io.Serializable;

/**
 * Author: Divine
 * CreateDate: 2020/10/20
 * Describe:
 */
public interface PicSelectListener extends Serializable {

    void onSingleImageSelected(String path);

    void onImageSelected(String path);

    void onImageUnselected(String path);

    void onCameraShot(File imageFile);

    void onPreviewChanged(int select, int sum, boolean visible);
}

