package com.divine.yang.lib_http.retrofit2;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

/**
 * Author: Divine
 * CreateDate: 2020/11/2
 * Describe:
 */
public class CustomException {
    public static GeneralException handleExceptions(Throwable e) {
        GeneralException GEX;
        if (e instanceof ConnectException) {
            GEX = new GeneralException(ConstOfException.NETWORK_ERROR, e.getMessage());
        } else if (e instanceof HttpException) {
            //网络错误
            GEX = new GeneralException(ConstOfException.NETWORK_ERROR, e.getMessage());
        } else if (e instanceof UnknownHostException || e instanceof SocketTimeoutException) {
            //连接错误
            GEX = new GeneralException(ConstOfException.NETWORK_ERROR, e.getMessage());
        } else if (e instanceof GeneralException) {
            GEX = new GeneralException(ConstOfException.UNKNOWN_ERROR, ((GeneralException) e).getErrorMessage());
        } else {
            //未知错误
            GEX = new GeneralException(ConstOfException.UNKNOWN_ERROR, e.getMessage());
        }
        return GEX;
    }
}
