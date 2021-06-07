package com.divine.yang.nmjc_main.mvp.models;

import com.divine.yang.lib_http.lib.RetrofitUtils;
import com.divine.yang.lib_http.retrofit2.Retrofit2Helper;
import com.divine.yang.nmjc_main.mvp.beans.HomeFunction;
import com.divine.yang.nmjc_main.mvp.http.Constant;

import java.util.ArrayList;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * Author: Divine
 * CreateDate: 2021/1/13
 * Describe:
 */
public class MainModel {
    public MainModel(){}
//    public ArrayList<HomeFunction> getFunctions(String params){
//        RequestBody body= RetrofitUtils.String2RequestBody(params);
//        Observable<String> observable = Retrofit2Helper.getInstance().getService().sendRequest(Constant.base, body);
//        observable.compose()
//    }
}
