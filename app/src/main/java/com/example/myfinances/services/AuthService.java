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
//        final Response<String>[] responseOut = new Response[]{null};
//
//        Callback<String> callback = new Callback<>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                responseOut[0] = response;
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//
//            }
//        };
//
//        signIn(signInRequest, callback);
//
//        return responseOut[0];

        try {
            var ans = AUTH_SERVICE_SERVICE.signIn(signInRequest).execute();
            return ans;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static void signIn(SignInRequest signInRequest, Callback<String> callback) {
        AUTH_SERVICE_SERVICE.signIn(signInRequest)
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Timber.i("signIn response got");
                        callback.onResponse(call, response);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Timber.e(t);
                        callback.onFailure(call, t);
                    }
                });
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
}
