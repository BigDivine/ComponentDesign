package com.divine.yang.httpcomponent.lib;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Converter;

/**
 * author: Divine
 * <p>
 * date: 2018/12/18
 */
public class DecodeRequestBodyConverter<T> implements Converter<T, RequestBody> {
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    DecodeRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public RequestBody convert(T value) throws IOException {
        Buffer buffer = new Buffer();
        Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
        JsonWriter jsonWriter = gson.newJsonWriter(writer);//出现问题后让身份证三方（taicloudlib-1.4.5）更新Gson版本，
        // 或者自己解决引入三方Gson冲突的问题，解决后改成：JsonWriter jsonWriter = gson.newJsonWriter(writer)
        adapter.write(jsonWriter, value);
        jsonWriter.flush();
        return RetrofitUtils.ByteString2RequestBody(buffer.readByteString());
    }
}