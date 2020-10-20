package com.divine.yang.camera2component.imageselect;

import java.io.Serializable;
import java.util.List;

/**
 * Author: Divine
 * CreateDate: 2020/10/20
 * Describe:
 */
public class Folder implements Serializable {

    public String name;
    public String path;
    public Image cover;
    public List<Image> images;

    public Folder() {

    }
    @Override
    public boolean equals(Object o) {
        try {
            Folder other = (Folder) o;
            return this.path.equalsIgnoreCase(other.path);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return super.equals(o);
    }
}