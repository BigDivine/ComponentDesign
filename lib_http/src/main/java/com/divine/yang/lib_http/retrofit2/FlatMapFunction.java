package com.divine.yang.lib_http.retrofit2;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Author: Divine
 * CreateDate: 2020/11/2
 * Describe:
 */
public class FlatMapFunction<T> implements Function<T, ObservableSource<T>> {
    @Override
    public ObservableSource apply(T t) throws GeneralException {
        return Observable.just(t);
    }
}
