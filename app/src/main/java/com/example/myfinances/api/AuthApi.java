package com.example.myfinances.api;

import com.example.myfinances.dto.ChangePasswordDto;
import com.example.myfinances.dto.EmailVerificationRequest;
import com.example.myfinances.dto.FooUserRisk;
import com.example.myfinances.dto.MarketStock;
import com.example.myfinances.dto.SignInRequest;
import com.example.myfinances.dto.SignUpRequest;
import com.example.myfinances.dto.UpdateUserDto;
import com.example.myfinances.dto.UserOutData;
import com.example.myfinances.dto.UserStock;

import java.util.List;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AuthApi {
    @POST("auth/signin")
    Call<UserOutData> signIn(@Body SignInRequest signInRequest);
    @POST("foo-debug-funcs/save-market-stocks")
    Call<Void> saveMarketStocks(@Body List<MarketStock> marketStocks);
    @GET("foo-debug-funcs/get-market-stocks")
    Call<List<MarketStock>> getMarketStocks();
    @POST("foo-debug-funcs/save-user-stocks")
    Call<Void> saveUserStocks(@Body List<UserStock> stocks, @Query("email") String email);
    @GET("foo-debug-funcs/get-user-stocks")
    Call<List<UserStock>> getUserStocks(@Query("email") String email);
    @POST("auth/signup")
    Call<UserOutData> signUp(@Body SignUpRequest signUpRequest);
    @POST("email/send_verification_code")
    Call<String> sendVerificationCode(@Query("email") String email);
    @POST("email/verify")
    Call<String> verifyEmail(@Body EmailVerificationRequest emailVerificationRequest);
    @PATCH("user/update_profile")
    Call<UpdateUserDto> updateUser(@Body UpdateUserDto updateUserDto);
    @PATCH("user/update_password_profile")
    Call<UserOutData> changePassword(@Body ChangePasswordDto changePasswordDto);
    @GET("email/is_email_exists")
    Call<Boolean> isEmailExists(@Query("email") String email);
    @POST("foo-debug-funcs/save-user-risk")
    Call<Void> saveUserRisk(@Body FooUserRisk userRisk);
    @GET("foo-debug-funcs/get-user-risk")
    Call<FooUserRisk> getUserRisk(@Query("email") String email);
    @GET("user/get_user_by_email")
    Call<UserOutData> getUserData(@Query("email") String email);
}
