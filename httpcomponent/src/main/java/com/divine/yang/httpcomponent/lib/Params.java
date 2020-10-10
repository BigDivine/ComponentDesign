package com.divine.yang.httpcomponent.lib;

import okhttp3.MediaType;

/**
 * author: Divine
 * <p>
 * date: 2018/12/3
 */
public class Params {
    // RetrofitUtils
    public static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

    //CustomException
    // 未知错误
    public static final int UNKNOWN = 1000;
    // 解析错误
    public static final int PARSE_ERROR = 1001;
    // 网络错误
    public static final int NETWORK_ERROR = 1002;
    // 协议错误
    public static final int HTTP_ERROR = 1003;

    //手机号正则
    public static final String phone_code_regex = "^[1][3,4,5,7,8][0-9]{9}$";
    //社会统一信用代码正则，最后一位校验码是计算出来的，此处没有使用算法计算
    public static final String company_code_regex = "^[1,5,9,Y][1,2,3,9][0-9]{6}[0-9A-HJ-NP-RT-UW-Y]{10}$";
    //身份证号正则
    public static final String id_card_code_regex = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[A-Z])$";
}
