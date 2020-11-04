package com.divine.yang.camera2component.imageselect;

import java.io.Serializable;
import java.util.List;

/**
 * Author: Divine
 * CreateDate: 2020/10/20
 * Describe:
 */
public class Camera2Folder implements Serializable {

    public String name;
    public String path;
    public Camera2Image cover;
    public List<Camera2Image> images;

    public Camera2Folder() {

    }
    @Override
    public boolean equals(Object o) {
        try {
            Camera2Folder other = (Camera2Folder) o;
            return this.path.equalsIgnoreCase(other.path);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return super.equals(o);
    }
}