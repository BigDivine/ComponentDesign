package com.divine.yang.webviewcomponent.constant;

import android.os.Environment;

/**
 * Author: Divine
 * CreateDate: 2020/8/18
 * Describe:
 */
public class NmhkUrls extends Constants {
    //    内蒙机场--航空制单
    public static String version = "1.0";

    //yzl地址
    //    public static String baseIP = "10.1.13.41";
    //    public static String basePort=":8097/";
    //    public static String url = "";

    //lc地址
    //    public static String baseIP = "10.1.103.69";
    //    public static String basePort=":8097/";
    //    public static String url = "";

    //现场测试
    public static String baseIP = "58.18.166.138";
    public static String basePort = ":9100";
    public static String url = "/index.html#/?version=" + version;

    public static String base = "http://" + baseIP + basePort;
    //    public static String base = "http://10.2.41.203";//:version="1.0"

    //本应用专属目录
    //应用根目录
    public static final String JIUQI_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/jiuqi";
    //存储单据相关
    public static final String BILL_DIR = JIUQI_DIR + "/bill";
    //存储单据的签名
    public static final String BILL_SIGNATURE_DIR = BILL_DIR + "/signature";
    //存储单据的详情
    public static final String BILL_DETAIL_DIR = BILL_DIR + "/detail";
    //上传内蒙机场签名文件专属服务地址
    public static final String uploadSignatureUrl = "http://10.2.41.203:8888/"; //开发环境
    //    public static final String uploadSignatureUrl = "http://172.30.6.41:8888/"; //现场环境
}
