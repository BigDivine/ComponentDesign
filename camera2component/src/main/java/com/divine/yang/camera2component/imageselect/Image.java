package com.divine.yang.camera2component.imageselect;

import java.io.Serializable;

/**
 * Author: Divine
 * CreateDate: 2020/10/20
 * Describe:
 */
public class Image implements Serializable {

    public String path;
    public String name;

    public Image(String path, String name) {
        this.path = path;
        this.name = name;
    }

    public Image() {

    }

    @Override
    public boolean equals(Object o) {
        try {
            Image other = (Image) o;
            return this.path.equalsIgnoreCase(other.path);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return super.equals(o);
    }
}