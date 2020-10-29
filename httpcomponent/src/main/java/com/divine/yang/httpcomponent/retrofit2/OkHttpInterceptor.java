package com.divine.yang.httpcomponent.retrofit2;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author: Divine
 * CreateDate: 2020/10/29
 * Describe:
 */
public class OkHttpInterceptor implements Interceptor {
    private final String TAG = "retrofit-httpIn";

    public OkHttpInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request mRequest = chain.request();
        long startTime = System.nanoTime();
        Log.d(TAG, String.format("Sending request %s on %s%n%s",
                                 mRequest.url(), chain.connection(), mRequest.headers()));

        Response mResponse = chain.proceed(mRequest);

        long endTime = System.nanoTime();
        Log.d(TAG, String.format("Received response for %s in %.1fms%n%s",
                                 mResponse.request().url(), (endTime - startTime) / 1e6d, mResponse.headers()));

        return mResponse;
    }
}
