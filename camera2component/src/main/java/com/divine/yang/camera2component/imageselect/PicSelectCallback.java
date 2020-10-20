package com.divine.yang.camera2component.imageselect;

import java.io.File;
import java.io.Serializable;

/**
 * Author: Divine
 * CreateDate: 2020/10/20
 * Describe:
 */
public interface PicSelectCallback extends Serializable {

    void onSingleImageSelected(String path);

    void onImageSelected(String path);

    void onImageUnselected(String path);

    void onCameraShot(File imageFile);

    void onPreviewChanged(int select, int sum, boolean visible);
}

