package com.divine.yang.lib_http.retrofit2;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.divine.yang.lib_http.R;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class Retrofit2DemoActivity extends AppCompatActivity {
    private TextView tvResponse;
    private CompositeDisposable mCompositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit2_demo);
        tvResponse = findViewById(R.id.http_response);
        Retrofit2Service retrofit2Service = Retrofit2Helper.getInstance().getService();

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), "{\"id\":\"33894312\"}");

        Observable<String> observable = retrofit2Service.sendRequest("http://10.1.13.41:3000/song/url/", body);

        mCompositeDisposable = new CompositeDisposable();

        Disposable disposable = observable.compose(RxJava2SchedulerTransformer.getInstance().applySchedulers())
                .compose((ObservableTransformer) RxJava2ResponseTransformer.handleResult())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e("del", "on success:" + s);
                        tvResponse.setText(s);
                    }
                }, new Consumer<GeneralException>() {
                    @Override
                    public void accept(GeneralException throwable) {
                        Log.e("del", "on error:" + throwable.getErrorMessage());
                        tvResponse.setText(throwable.getErrorMessage());
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.e("del", "on complete:");
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }
}
