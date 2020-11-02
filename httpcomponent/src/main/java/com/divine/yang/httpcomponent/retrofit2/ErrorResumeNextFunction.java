package com.divine.yang.httpcomponent.retrofit2;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Author: Divine
 * CreateDate: 2020/11/2
 * Describe:
 */
public class ErrorResumeNextFunction<T> implements Function<Throwable, ObservableSource<? extends T>> {
    @Override
    public ObservableSource<? extends T> apply(Throwable throwable) throws Exception {
        return Observable.error(CustomException.handleExceptions(throwable));
    }
}
