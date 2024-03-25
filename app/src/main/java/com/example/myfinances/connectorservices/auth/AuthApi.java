package com.example.myfinances.connectorservices.auth;

import com.example.myfinances.connectorservices.auth.dto.ChangePasswordDto;
import com.example.myfinances.connectorservices.auth.dto.EmailVerificationRequest;
import com.example.myfinances.connectorservices.auth.dto.SignInRequest;
import com.example.myfinances.connectorservices.auth.dto.SignUpRequest;
import com.example.myfinances.connectorservices.auth.dto.UpdateUserDto;
import com.example.myfinances.connectorservices.auth.dto.UserOutData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AuthApi {
    @POST("auth/signin")
    Call<String> signIn(@Body SignInRequest signInRequest);
    @POST("auth/signup")
    Call<UserOutData> signUp(@Body SignUpRequest signUpRequest);
    @POST("email/send_verification_code")
    Call<String> sendVerificationCode(@Query("email") String email);
    @POST("email/verify")
    Call<String> verifyEmail(@Body EmailVerificationRequest emailVerificationRequest);
    @PATCH("user/update_profile")
    Call<UpdateUserDto> updateUser(@Body UpdateUserDto updateUserDto);
    @PATCH("user/update_password_profile")
    Call<Void> changePassword(@Body ChangePasswordDto changePasswordDto);
    @GET("email/is_email_exists")
    Call<Boolean> isEmailExists(@Query("email") String email);
}
