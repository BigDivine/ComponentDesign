package com.divine.yang.httpcomponent.retrofit2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Author: Divine
 * CreateDate: 2020/11/2
 * Describe:
 */
public class RxJava2SchedulerTransformer {

    @Nullable
    private static RxJava2SchedulerTransformer INSTANCE;

    // Prevent direct instantiation.
    private RxJava2SchedulerTransformer() {
    }

    public static synchronized RxJava2SchedulerTransformer getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RxJava2SchedulerTransformer();
        }
        return INSTANCE;
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
