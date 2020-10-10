package com.divine.yang.httpcomponent.lib;


import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * retrofit 请求发出
 * <p>
 *
 * <p>
 * by yzl
 */
public interface RetrofitService {
    /*****************************登陆接口+修改密码+版本更新**********************************/
    //    /**
    //     * 登录接口
    //     *
    //     * @param json {"name":"admin","password":"123456"}
    //     * @return
    //     */
    //    @POST("user/appLogin.ht")
    //    Observable<Response<UserInfoForLoginBean>> login(@Query("json") String json);
    @POST("raserver/login")
    Observable<Response<String>> login(@Body RequestBody requestBody);

    @POST
    Observable<Response<String>> sendRequest(@Url String url, @Header("Authorization") String authorization, @Body RequestBody requestBody);
}
