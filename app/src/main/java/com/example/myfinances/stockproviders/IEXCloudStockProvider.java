package com.example.myfinances.stockproviders;

import com.example.myfinances.api.IEXCLoudStockProviderApi;
import com.example.myfinances.connectorservices.AuthConnectorService;
import com.example.myfinances.connectorservices.ConnectorService;
import com.example.myfinances.dto.MarketStock;
import com.example.myfinances.dto.UserStock;
import com.example.myfinances.mappers.StockMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class IEXCloudStockProvider implements StockProvider {
    private static StockProvider instance = null;
    private static final List<MarketStock> marketStocks = new ArrayList<>();
    private static final IEXCLoudStockProviderApi connectorService = (new ConnectorService<>(IEXCLoudStockProviderApi.class, "https://api.iex.cloud/v1/")).getController();
    private final Gson gson = new Gson();
    private final StockMapper stockMapper = StockMapper.STOCK_MAPPER;
    private static final Set<Integer> marketStocksIndexes = new HashSet<>(450);
    @Override
    public List<MarketStock> getStocks(String email) {
        List<MarketStock> stocks = marketStocks;
        checkStockMarketStocks();

        List<String> userStocks = stockMapper.getUserStockSymbols(getUStocks(email));
        filterStocks(stocks, userStocks);

        return stocks;
    }

    @Override
    public List<UserStock> getUserStocks(String email) {
        List<UserStock> userStocks = getUStocks(email);

        if (userStocks.isEmpty()) {
            checkStockMarketStocks();

            int countOfStocks = ThreadLocalRandom.current().nextInt(3, 23);
            int stockPortfolioActionCount = 0;
            for (int i = 0; i < countOfStocks; ++i) {
                int stockIndex = ThreadLocalRandom.current().nextInt(0, marketStocksIndexes.size());
                userStocks.add(stockMapper.marketStockToUserStock(marketStocks.get(stockIndex)));
                stockPortfolioActionCount += userStocks.get(userStocks.size() - 1).getCount();
                marketStocksIndexes.remove(stockIndex);
            }

            for (int i = 0; i < countOfStocks; ++i) {
                userStocks.get(i).setFraction(userStocks.get(i).getCount() / countOfStocks * 100);
            }

            AuthConnectorService.saveUserStocks(userStocks);
        }

        return userStocks;
    }

    public static StockProvider getInstance() {
        if (instance == null) {
            instance = new IEXCloudStockProvider();
        }

        return instance;
    }

    private <T> Response<T> makeRequest(Call<T> method) {
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
    private String getRowBodyFromResponse(Response<ResponseBody> responseBody) {
        try (var body = responseBody.body()) {
            return body.string();
        } catch (IOException ignore) {
            throw new RuntimeException(ignore);
        }
    }
    private List<Map<String, Object>> parseRandomResponse(String jsonBody) {
        Type type = new TypeToken<Map<String, Object>>() {}.getType();
        return gson.fromJson(jsonBody, type);
    }
    private Map<String, Object> parseRandomResponseSimple(String jsonBody) {
        Type type = new TypeToken<Map<String, Object>>() {}.getType();
        return gson.fromJson(jsonBody, type);
    }
    private List<Map<String, Object>> getRealStocks() {
        Response<ResponseBody> responseBody = makeRequest(connectorService.
                getRealTimeStocks("pk_c6dc755d13c44c82a2e85dfbe6c356b0")
        );

        String jsonData = getRowBodyFromResponse(responseBody);
        return parseRandomResponse(jsonData);
    }
    private List<Map<String, Object>> getStocksInfo(List<Map<String, Object>> realStocksInfo) {
        int size = Math.min(400, realStocksInfo.size());
        List<Map<String, Object>> stocksInfo = new ArrayList<>(size);

        for (int i = 0; i < size; ++i) {
            Response<ResponseBody> stockResponseBody = makeRequest(
                    connectorService.getStockInfo(realStocksInfo.get(i).get("symbol").toString(),
                            "pk_c6dc755d13c44c82a2e85dfbe6c356b0")
            );

            var jsonBody = getRowBodyFromResponse(stockResponseBody);
            var ans = parseRandomResponseSimple(jsonBody);
            ans.put("symbol", realStocksInfo.get(i).get("symbol"));
            stocksInfo.add(ans);
        }

        return stocksInfo;
    }
    private List<UserStock> getUStocks(String email) {
        return AuthConnectorService.getUserStocks(email).body();
    }
    private void filterStocks(List<MarketStock> marketStocks, List<String> userSymbols) {
        for (var stock : marketStocks) {
            int index = userSymbols.indexOf(stock.getSymbol());

            if (index != -1) {
                marketStocks.remove(index);
            }
        }
    }
    private void fillMarketStockIndexes(int size) {
        for (int i = 0; i < size; ++i) {
            marketStocksIndexes.add(i);
        }
    }
    private List<Map<String, Object>> getStocks() {
        var realStocks = getRealStocks();
        return getStocksInfo(realStocks);
    }
    private void checkStockMarketStocks() {
        if (marketStocks.isEmpty()) {
            var stocksInfo = getStocks();
            var stocks = stockMapper.jsonListToMarketListStock(stocksInfo);
            marketStocks.addAll(stocks);
            fillMarketStockIndexes(marketStocks.size());
        }
    }
}
