package com.divine.yang.lib_http.lib;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * author: Divine
 * 自定义解析工厂，供retrofit使用
 * <p>
 * date: 2018/12/18
 */
//public class DecodeConverterFactory extends Converter.Factory {
//    public static DecodeConverterFactory create() {
//        return create(new Gson());
//    }
//
//    public static DecodeConverterFactory create(Gson gson) {
//        return new DecodeConverterFactory(gson);
//    }
//
//    private final Gson gson;
//
//    private DecodeConverterFactory(Gson gson) {
//        if (gson == null) throw new NullPointerException("gson == null");
//        this.gson = gson;
//    }
//
//    @Override
//    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
//        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
//        return new DecodeResponseBodyConverter<>(adapter);
//    }
//
//    @Override
//    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
//        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
//        return new DecodeRequestBodyConverter<>(gson, adapter);
//    }
//}
