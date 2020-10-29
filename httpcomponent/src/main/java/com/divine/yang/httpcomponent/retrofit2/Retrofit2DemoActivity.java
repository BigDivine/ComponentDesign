package com.divine.yang.httpcomponent.retrofit2;

import android.os.Bundle;
import android.util.Log;

import com.divine.yang.httpcomponent.R;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class Retrofit2DemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit2_demo);

        Retrofit2Service retrofit2Service = Retrofit2Helper.getInstance().getService();

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), "{\"id\":\"33894312\"}");

        Observable<String> observable = retrofit2Service.sendRequest("http://10.1.13.41:3000/song/url/", body);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("del",d.isDisposed()+"");

                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("del",s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("del",e.toString());

                    }

                    @Override
                    public void onComplete() {
                        Log.e("del","onComplete");

                    }
                });
    }
}
