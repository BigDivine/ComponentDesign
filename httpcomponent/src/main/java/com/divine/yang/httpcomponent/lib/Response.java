package com.divine.yang.httpcomponent.lib;

import java.io.Serializable;

/**
 * Created by Zaifeng on 2018/2/28.
 * <p>
 * 使用retrofit2套餐访问网络和解析数据，
 * 不开混淆的情况下一切正常，
 * 开启混淆后访问网络得到数据解析后解析一直失败. 
 * 一开始怀疑配置文件有错误，改了好几次都不成功。
 * 最后发现是因为BaseRespones<T>这个类没有实现Serializable接口。
 * 这是个致命的坑，一定到实现这个接口才行。
 * <p>
 * 返回结果封装
 */

public class Response<T> implements Serializable {

    private int code; // 返回的code
    private T data; // 具体的数据结果
    private String msg; // message 可用来返回接口的说明

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
