package com.divine.yang.httpcomponent.lib;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zaifeng on 2018/2/28.
 * 线程切换
 */
public class SchedulerProvider {

    @Nullable
    private static SchedulerProvider INSTANCE;

    // Prevent direct instantiation.
    private SchedulerProvider() {
    }

    public static synchronized SchedulerProvider getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SchedulerProvider();
        }
        return INSTANCE;
    }


    @NonNull
    public Scheduler computation() {
        return Schedulers.computation();
    }


    @NonNull
    public Scheduler io() {
        return Schedulers.io();
    }


    @NonNull
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }

    @NonNull
    public <T> ObservableTransformer<T, T> applySchedulers() {
        return new ObservableTransformer<T, T>() {

            @Override
            public ObservableSource apply(Observable<T> upstream) {
                return upstream.subscribeOn(io())
                        .observeOn(ui());
            }
        };
    }
}
