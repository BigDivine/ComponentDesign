package com.divine.yang.lib_http.retrofit2;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

/**
 * Author: Divine
 * CreateDate: 2020/11/2
 * Describe:
 */
public class RxJava2ResponseTransformer {
    public static <T> ObservableTransformer<T, T> handleResult() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {

                return upstream.onErrorResumeNext(new ErrorResumeNextFunction())
                        .flatMap(new FlatMapFunction());
            }
        };
    }

}
