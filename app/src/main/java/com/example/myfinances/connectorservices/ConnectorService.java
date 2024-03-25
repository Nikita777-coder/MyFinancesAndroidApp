package com.example.myfinances.connectorservices;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

class ConnectorService<ApiClass> {
    private final Retrofit mRetrofit;
    private final Class<ApiClass> typeParameterClass;
    private ApiClass controller;
    public ConnectorService(Class<ApiClass> typeParameterClass, String baseUrl) {
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

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Timber.i("start build retrofit");
        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
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

