package com.example.myfinances.services;

import com.example.myfinances.services.auth.AuthApi;
import com.example.myfinances.services.auth.dto.SignInRequest;
import com.example.myfinances.services.auth.dto.SignUpRequest;
import com.example.myfinances.services.auth.dto.UserOutData;

import lombok.experimental.UtilityClass;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

@UtilityClass
public class AuthService {
    private final static AuthApi AUTH_SERVICE_SERVICE = (new Service<AuthApi>(AuthApi.class)).getController();
    public static Response<String> signIn(SignInRequest signInRequest) {
        final Response<String>[] responseOut = new Response[]{null};

        AUTH_SERVICE_SERVICE.signIn(signInRequest)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Timber.i("signIn response got");
                        responseOut[0] = response;
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Timber.e(t);
                    }
                });

        return responseOut[0];
    }
    public static Response<UserOutData> signUp(SignUpRequest signUpRequest) {
        final Response<UserOutData>[] responseOut = new Response[]{null};

        AUTH_SERVICE_SERVICE.signUp(signUpRequest)
                .enqueue(new Callback<UserOutData>() {
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