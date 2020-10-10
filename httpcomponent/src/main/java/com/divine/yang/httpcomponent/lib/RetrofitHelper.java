package com.divine.yang.httpcomponent.lib;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * retrofit 初始化
 * <p>
 * by yzl
 */
public class RetrofitHelper {
    private static Retrofit mRetroFit;
    private static RetrofitHelper mRetrofitHelper;

    private RetrofitService retrofitService;

    private static String baseUrl;

    private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(30000, TimeUnit.MILLISECONDS)
            .readTimeout(30000, TimeUnit.MILLISECONDS)
            .writeTimeout(30000, TimeUnit.MILLISECONDS)
            .build();

    private RetrofitHelper(String baseUrl) {
        resetApp(baseUrl);
    }

    /**
     * 双DCL （double check lock），双锁式
     *
     * @return 返回单例
     */
    public static synchronized RetrofitHelper getInstance(String baseUrl) {
        if (null == mRetrofitHelper) {
            synchronized (RetrofitHelper.class) {
                if (null == mRetrofitHelper) {
                    mRetrofitHelper = new RetrofitHelper(baseUrl);
                }
            }
        }
        return mRetrofitHelper;
    }

    /**
     * 配置retrofit，baseUrl为服务器域名
     */
    private void resetApp(String baseUrl) {
        mRetroFit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                //                .addConverterFactory(GsonConverterFactory.create()) //返回的数据为json类型，所以在这个方法中传入Gson转换工厂
                .addConverterFactory(DecodeConverterFactory.create()) //返回的数据为json类型，所以在这个方法中传入Gson转换工厂
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //使Rxjava与retrofit结合
                .build();
    }

    public RetrofitService getService() {
        if (null == retrofitService) {
            synchronized (RetrofitService.class) {
                if (null == retrofitService) {
                    retrofitService = mRetroFit.create(RetrofitService.class);
                }
            }
        }
        return retrofitService;
    }
}
