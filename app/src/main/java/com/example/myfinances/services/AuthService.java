package com.example.myfinances.services;

import com.example.myfinances.services.auth.AuthApi;
import com.example.myfinances.services.auth.dto.SignInRequest;
import com.example.myfinances.services.auth.dto.SignUpRequest;
import com.example.myfinances.services.auth.dto.UserOutData;

import java.io.IOException;

import lombok.experimental.UtilityClass;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

@UtilityClass
public class AuthService {
    private final static AuthApi AUTH_SERVICE_SERVICE = (new Service<>(AuthApi.class, "http://10.0.2.2:8080/")).getController();
    public static Response<String> signIn(SignInRequest signInRequest) {
        return makeRequest(AUTH_SERVICE_SERVICE.signIn(signInRequest));
    }
    public static Response<UserOutData> signUp(SignUpRequest signUpRequest) {
        final Response<UserOutData>[] responseOut = new Response[]{null};

        AUTH_SERVICE_SERVICE.signUp(signUpRequest)
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<UserOutData> call, Response<UserOutData> response) {
                        Timber.i("signUp response got");
                        responseOut[0] = response;
                    }

                    @Override
                    public void onFailure(Call<UserOutData> call, Throwable t) {
                        Timber.e(t);
                    }
                });

        return responseOut[0];
    }
    private static <T> Response<T> makeRequest(Call<T> method) {
        final Response<T>[] ans = new Response[1];

        Thread thread = new Thread(() -> {
            try {
                try {
                    ans[0] = method.execute();
                } catch (IOException | RuntimeException e) {
                    throw new RuntimeException(e);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException ignore) {

        }

        return ans[0];
    }
}
