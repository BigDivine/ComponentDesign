package com.divine.yang.httpcomponent.lib;

import android.net.ParseException;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;


/**
 * Created by Zaifeng on 2018/2/28.
 * 本地异常处理。包括解析异常等其他异常
 */

public class CustomException {


    public static ApiException handleException(Throwable e) {
        ApiException ex;
        if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            //解析错误
            ex = new ApiException(Params.PARSE_ERROR, e.getMessage());
            return ex;
        } else if (e instanceof ConnectException) {
            //网络错误
            ex = new ApiException(Params.NETWORK_ERROR, e.getMessage());
            return ex;
        } else if (e instanceof HttpException) {
            //网络错误
            ex = new ApiException(Params.NETWORK_ERROR, e.getMessage());
            return ex;
        } else if (e instanceof UnknownHostException || e instanceof SocketTimeoutException) {
            //连接错误
            ex = new ApiException(Params.NETWORK_ERROR, e.getMessage());
            return ex;
        } else if (e instanceof ApiException) {
            ex = new ApiException(Params.UNKNOWN, ((ApiException) e).getDisplayMessage());
            return ex;
        } else {
            //未知错误
            ex = new ApiException(Params.UNKNOWN, e.getMessage());
            return ex;
        }
    }
}
