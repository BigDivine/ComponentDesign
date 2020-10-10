package com.divine.yang.httpcomponent.lib;

/**
 * Author: Divine
 * CreateDate: 2020/8/17
 * Describe:
 */
public interface ICallback<T> {
    void success(T response);

    void failed(String e);
}
