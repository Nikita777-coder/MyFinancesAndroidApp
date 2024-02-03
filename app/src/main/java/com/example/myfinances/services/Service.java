package com.example.myfinances.services;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class Service<ApiClass> {
    private final Retrofit mRetrofit;
    private final Class<ApiClass> typeParameterClass;
    private ApiClass controller;
    public Service(Class<ApiClass> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request originalRequest = chain.request();
                    Request newRequest = originalRequest.newBuilder().build();

                    return chain.proceed(newRequest);
                }).build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl("")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public ApiClass getController() {
        if (controller == null) {
            controller = mRetrofit.create(typeParameterClass);
        }

        return controller;
    }
}

