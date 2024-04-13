package com.example.myfinances.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IEXCLoudStockProviderApi {
    @GET("data/CORE/IEX_TOPS")
    Call<ResponseBody> getRealTimeStocks(@Query("token") String token);
    @GET("data/core/quote/{symbol}")
    Call<ResponseBody> getStockInfo(@Path("symbol") String symbol, @Query("token") String token);
}
