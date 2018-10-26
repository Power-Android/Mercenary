package com.power.mercenary.utils;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private static RetrofitManager mRetrofitManager;

    private final Retrofit mRetrofit;

    private static String BASE_URL = "http://yb.dashuibei.com/index.php/Home/";

    public RetrofitManager(String baseUrl) {

        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(bulidOkHttpClient())
                .build();


    }

    public static RetrofitManager getDefault() {

        return mRetrofitManager = new RetrofitManager(BASE_URL);

    }

    private OkHttpClient bulidOkHttpClient() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(5000, TimeUnit.MILLISECONDS)
                .writeTimeout(5000, TimeUnit.MILLISECONDS)
                .connectTimeout(5000, TimeUnit.MILLISECONDS)
                .build();

        return okHttpClient;
    }

    public <T> T create(Class<T> Clazz) {

        return mRetrofit.create(Clazz);

    }
}
