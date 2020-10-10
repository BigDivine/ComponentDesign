package com.divine.yang.httpcomponent.lib;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * Created by Zaifeng on 2018/2/28.
 * 对返回的数据进行处理，区分异常的情况。
 */

public class UploadResponseTransformer {

    public static <T> ObservableTransformer<? super T, ?> handleResult() {
        return new ObservableTransformer<T, T>() {

            @Override
            public ObservableSource apply(Observable<T> upstream) {

                ErrorResumeFunction errorResumeFunction = new ErrorResumeFunction<>();
                ResponseFunction responseFunction = new ResponseFunction();

                return upstream
                        .onErrorResumeNext(errorResumeFunction)
                        .flatMap(responseFunction);
            }
        };
    }


    /**
     * 非服务器产生的异常，比如本地无无网络请求，Json数据解析错误等等。
     *
     * @param <T>
     */
    private static class ErrorResumeFunction<T> implements Function<Throwable, ObservableSource<? extends T>> {

        @Override
        public ObservableSource<? extends T> apply(Throwable throwable) throws Exception {
            return Observable.error(CustomException.handleException(throwable));
        }
    }

    /**
     * 服务其返回的数据解析
     * 正常服务器返回数据和服务器可能返回的exception
     *
     * @param <T>
     */
    private static class ResponseFunction<T> implements Function<T, ObservableSource<T>> {

        @Override
        public ObservableSource<T> apply(T tResponse) throws Exception {
            //            int code = tResponse.;
            //            String message = tResponse.getMsg();
            //            if (code == 200) {
            return Observable.just(tResponse);
            //            } else {
            //                return Observable.error(new ApiException(code, message));
            //            }
        }
    }


}
