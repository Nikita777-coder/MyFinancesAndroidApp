package com.example.myfinances.connectorservices;

import com.example.myfinances.api.AuthApi;
import com.example.myfinances.dto.ChangePasswordDto;
import com.example.myfinances.dto.EmailVerificationRequest;
import com.example.myfinances.dto.FooUserRisk;
import com.example.myfinances.dto.MarketStock;
import com.example.myfinances.dto.SignInRequest;
import com.example.myfinances.dto.SignUpRequest;
import com.example.myfinances.dto.UpdateUserDto;
import com.example.myfinances.dto.UserOutData;
import com.example.myfinances.dto.UserStock;

import java.io.IOException;
import java.util.List;

import lombok.experimental.UtilityClass;
import retrofit2.Call;
import retrofit2.Response;

@UtilityClass
public class AuthConnectorService {
    private final static AuthApi AUTH_SERVICE_SERVICE = (new ConnectorService<>(AuthApi.class, "http://10.0.2.2:8080/")).getController();
    public static Response<UserOutData> signIn(SignInRequest signInRequest) {
        return makeRequest(AUTH_SERVICE_SERVICE.signIn(signInRequest));
    }
    public static Response<UserOutData> signUp(SignUpRequest signUpRequest) {
        return makeRequest(AUTH_SERVICE_SERVICE.signUp(signUpRequest));
    }
    public static Response<List<UserStock>> getUserStocks(String email) {
        return makeRequest(AUTH_SERVICE_SERVICE.getUserStocks(email));
    }
    public static Response<Void> saveUserStocks(List<UserStock> userStocks, String email) {
        return makeRequest(AUTH_SERVICE_SERVICE.saveUserStocks(userStocks, email));
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
    public static Response<UserOutData> changePassword(ChangePasswordDto changePasswordDto) {
        return makeRequest(AUTH_SERVICE_SERVICE.changePassword(changePasswordDto));
    }
    public static Response<List<MarketStock>> getMarketStocks() {
        return makeRequest(AUTH_SERVICE_SERVICE.getMarketStocks());
    }
    public static Response<Void> saveUserRisk(FooUserRisk userRisk) {
        return makeRequest(AUTH_SERVICE_SERVICE.saveUserRisk(userRisk));
    }
    public static Response<FooUserRisk> getUserRisk(String email) {
        return makeRequest(AUTH_SERVICE_SERVICE.getUserRisk(email));
    }
    public static Response<UserOutData> getUserDataByEmail(String email) {
        return makeRequest(AUTH_SERVICE_SERVICE.getUserData(email));
    }
    public static Response<Void> saveMarketStocks(List<MarketStock> marketStocks) {
        Response<Void> answer = null;

        try {
            answer = makeRequest(AUTH_SERVICE_SERVICE.saveMarketStocks(marketStocks));
        } catch (Exception ex) {
            var exc = ex;
        }

        return answer;
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
