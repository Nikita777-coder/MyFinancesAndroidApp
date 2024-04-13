package com.example.myfinances.stockproviders;

import com.example.myfinances.dto.MarketStock;
import com.example.myfinances.dto.UserOutData;
import com.example.myfinances.dto.UserStock;

import java.util.List;
import java.util.UUID;

public interface StockProvider {
    List<MarketStock> getStocks(String email);
    List<UserStock> getUserStocks(String email);
}
