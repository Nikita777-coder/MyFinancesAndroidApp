package com.example.myfinances.services;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

class Service<ApiClass> {
    private final Retrofit mRetrofit;
    private final Class<ApiClass> typeParameterClass;
    private ApiClass controller;
    public Service(Class<ApiClass> typeParameterClass) {
        Timber.i("start init Service");
        this.typeParameterClass = typeParameterClass;
        Timber.i("assign class in Service init");

        Timber.i("start build HttpClient");
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request originalRequest = chain.request();
                    Request newRequest = originalRequest.newBuilder().build();

                    return chain.proceed(newRequest);
                }).build();
        Timber.i("OkHttpClient built");

        Timber.i("start build retrofit");
        mRetrofit = new Retrofit.Builder()
                .baseUrl("")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Timber.i("retrofit built");
    }
    public ApiClass getController() {
        if (controller == null) {
            controller = mRetrofit.create(typeParameterClass);
        }

        return controller;
    }
}

