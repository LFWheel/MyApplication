package com.eeesys.myapplication.service;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ASUS on 2016/7/20.
 */
public class ServiceGenerator {
    public static String BASE_URL = "http://kobe.tunnel.qydev.com/mamba/";
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();


    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> service){
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(service);
    }

    public static void changeApiBaseUrl(String newBaseApiUrl){
        BASE_URL = newBaseApiUrl;
        builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

    }
}
