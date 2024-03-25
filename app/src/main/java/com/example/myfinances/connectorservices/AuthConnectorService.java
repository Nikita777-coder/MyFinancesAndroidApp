package com.example.myfinances.connectorservices;

import com.example.myfinances.connectorservices.auth.AuthApi;
import com.example.myfinances.connectorservices.auth.dto.ChangePasswordDto;
import com.example.myfinances.connectorservices.auth.dto.EmailVerificationRequest;
import com.example.myfinances.connectorservices.auth.dto.SignInRequest;
import com.example.myfinances.connectorservices.auth.dto.SignUpRequest;
import com.example.myfinances.connectorservices.auth.dto.UpdateUserDto;
import com.example.myfinances.connectorservices.auth.dto.UserOutData;

import java.io.IOException;

import lombok.experimental.UtilityClass;
import retrofit2.Call;
import retrofit2.Response;

@UtilityClass
public class AuthConnectorService {
    private final static AuthApi AUTH_SERVICE_SERVICE = (new ConnectorService<>(AuthApi.class, "http://10.0.2.2:8080/")).getController();
    public static Response<String> signIn(SignInRequest signInRequest) {
        return makeRequest(AUTH_SERVICE_SERVICE.signIn(signInRequest));
    }
    public static Response<UserOutData> signUp(SignUpRequest signUpRequest) {
        return makeRequest(AUTH_SERVICE_SERVICE.signUp(signUpRequest));
    }
    public static Response<String> sendEmailVerificationCode(String email) {
        return makeRequest(AUTH_SERVICE_SERVICE.sendVerificationCode(email));
    }
    public static Response<String> verifyEmail(EmailVerificationRequest request) {
        return makeRequest(AUTH_SERVICE_SERVICE.verifyEmail(request));
    }
    public static Response<Boolean> isEmailExistsInApp(String email) {
        return makeRequest(AUTH_SERVICE_SERVICE.isEmailExists(email));
    }
    public static Response<UpdateUserDto> updateUser(UpdateUserDto updateUserDto) {
        return makeRequest(AUTH_SERVICE_SERVICE.updateUser(updateUserDto));
    }
    public static Response<Void> changePassword(ChangePasswordDto changePasswordDto) {
        return makeRequest(AUTH_SERVICE_SERVICE.changePassword(changePasswordDto));
    }
    private static <T> Response<T> makeRequest(Call<T> method) {
        final Response<T>[] ans = new Response[1];

        Thread thread = new Thread(() -> {
            try {
                try {
                    ans[0] = method.execute();
                } catch (IOException | RuntimeException ignore) {
                    ignore.printStackTrace();
                }
            } catch (Exception e) {
//                e.printStackTrace();
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
//            e.printStackTrace();
        }

        return ans[0];
    }
}
