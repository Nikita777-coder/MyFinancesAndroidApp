package com.example.myfinances.services.auth;

import com.example.myfinances.services.auth.dto.EmailVerificationRequest;
import com.example.myfinances.services.auth.dto.SignInRequest;
import com.example.myfinances.services.auth.dto.SignUpRequest;
import com.example.myfinances.services.auth.dto.UpdateUserDto;
import com.example.myfinances.services.auth.dto.UserOutData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

public interface AuthApi {
    @POST("auth/signin")
    Call<String> signIn(@Body SignInRequest signInRequest);
    @POST("auth/signup")
    Call<UserOutData> signUp(@Body SignUpRequest signUpRequest);
    @POST("email/send_verification_code")
    Call<String> sendVerificationCode(@Body String email);
    @POST("email/verify")
    Call<String> verifyEmail(@Body EmailVerificationRequest emailVerificationRequest);
    @PATCH("user/update_profile")
    Call<UpdateUserDto> updateUser(@Body UpdateUserDto updateUserDto);
}
