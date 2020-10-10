package com.divine.yang.httpcomponent.lib;

import android.util.Log;

import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;

/**
 * Author: Divine
 * CreateDate: 2020/8/14
 * Describe:
 */
public class Api {
    public static void sendRequest(String serverUrl, JSONObject object, final ICallback<String> callback) {
        final CompositeDisposable mCompositeDisposable = new CompositeDisposable();
        RetrofitService service = RetrofitHelper.getInstance(serverUrl).getService();
        RequestBody requestBody = RetrofitUtils.String2RequestBody(object.optJSONObject("requestBody").toString());
        Observable<Response<String>> sendRequest = service.sendRequest(serverUrl+ object.optString("url"), object.optString("token"), requestBody);

        Disposable disposable = sendRequest.compose(SchedulerProvider.getInstance().applySchedulers())
                .compose((ObservableTransformer) ResponseTransformer.handleResult())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) {
                        callback.success(response);
                    }
                }, new Consumer<ApiException>() {
                    @Override
                    public void accept(ApiException throwable) {
                        String msg = throwable.getDisplayMessage();
                        callback.failed(msg);
                        mCompositeDisposable.dispose();

                    }
                });
        mCompositeDisposable.add(disposable);
    }
}
