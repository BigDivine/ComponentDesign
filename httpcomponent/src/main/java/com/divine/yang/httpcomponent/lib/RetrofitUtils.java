package com.divine.yang.httpcomponent.lib;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * author: Divine
 * <p>
 * date: 2019/2/28
 */
public class RetrofitUtils {

    //    /**
    //     * 将string转换成retrofit请求传body对应的类型
    //     *
    //     * @param json
    //     * @return
    //     */
    //    public static RequestBody String2RequestBody(String json) {
    //        return RequestBody.create(Params.MEDIA_TYPE, json);
    //    }
    //
    //    /**
    //     * 将byteString（即byte转的string）转换成retrofit请求传body对应的类型
    //     *
    //     * @param json
    //     * @return
    //     */
    //    public static RequestBody ByteString2RequestBody(ByteString json) {
    //        return RequestBody.create(Params.MEDIA_TYPE, json);
    //    }

    /**
     * 创建file上传
     *
     * @param batchId
     * @param path
     * @return
     */
    public static List<MultipartBody.Part> createMultipart(String batchId, String path) {
        MultipartBody.Builder builder = new MultipartBody.Builder().
                setType(MultipartBody.FORM);
        File file = new File(path);
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        builder.addFormDataPart("batchId", batchId);
        builder.addFormDataPart("file", file.getName(), body);

        return builder.build().parts();
    }
}
