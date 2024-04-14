package com.example.myfinances.stockproviders;

import com.example.myfinances.api.IEXCLoudStockProviderApi;
import com.example.myfinances.connectorservices.AuthConnectorService;
import com.example.myfinances.connectorservices.ConnectorService;
import com.example.myfinances.dto.MarketStock;
import com.example.myfinances.dto.UserStock;
import com.example.myfinances.mappers.StockMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class IEXCloudStockProvider implements StockProvider {
    private static StockProvider instance = null;
    private List<MarketStock> marketStocks;
    private static final IEXCLoudStockProviderApi connectorService = (new ConnectorService<>(IEXCLoudStockProviderApi.class, "https://api.iex.cloud/v1/")).getController();
    private final Gson gson = new Gson();
    private final StockMapper stockMapper = StockMapper.STOCK_MAPPER;
    private final Set<Integer> marketStocksIndexes = new HashSet<>(450);
    @Override
    public List<MarketStock> getStocks(String email) {
        List<MarketStock> stocks = marketStocks;
        checkStockMarketStocks();

        List<String> userStocks = getUserStockSymbols(getUStocks(email));
        filterStocks(stocks, userStocks);

        return stocks;
    }

    @Override
    public List<UserStock> getUserStocks(String email) {
        List<UserStock> userStocks = getUStocks(email);

        if (userStocks.isEmpty()) {
            checkStockMarketStocks();

            int countOfStocks = generatePseudoRandomInRange(3, 23);
            int stockPortfolioActionCount = 0;
            for (int i = 0; i < countOfStocks; ++i) {
                int stockIndex = generatePseudoRandomInRange(0, marketStocksIndexes.size());
                userStocks.add(stockMapper.marketStockToUserStock(marketStocks.get(stockIndex)));
                stockPortfolioActionCount += userStocks.get(userStocks.size() - 1).getCount();
                marketStocksIndexes.remove(stockIndex);
            }

            for (int i = 0; i < countOfStocks; ++i) {
                userStocks.get(i).setFraction(userStocks.get(i).getCount() / stockPortfolioActionCount * 100);
            }

            AuthConnectorService.saveUserStocks(userStocks, email);
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
        Type type = new TypeToken<List<Map<String, Object>>>() {}.getType();
        List<Map<String, Object>> b = gson.fromJson(jsonBody, type);
        return b;
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

            if (stockResponseBody.code() != 200) {
                var d = stockResponseBody;
            }

            var jsonBody = getRowBodyFromResponse(stockResponseBody);
            var ans = parseRandomResponse(jsonBody).get(0);
            stocksInfo.add(ans);

            try {
                Thread.sleep(ThreadLocalRandom.current().nextLong(200, 251));
            } catch (Exception ex) {
                var exc  = ex;
            }
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
        List<MarketStock> marketStocks1 = Objects.requireNonNull(AuthConnectorService.getMarketStocks().body());
        if (marketStocks1.isEmpty()) {
            var stocksInfo = getStocks();
            var stocks = jsonListToMarketListStock(stocksInfo);
            marketStocks1 = stocks;
            AuthConnectorService.saveMarketStocks(stocks);
        }

        fillMarketStockIndexes(marketStocks1.size());
        marketStocks = marketStocks1;
    }
    private List<String> getUserStockSymbols(List<UserStock> userStocks) {
        List<String> symbols = new ArrayList<>();

        for (var stock : userStocks) {
            symbols.add(stock.getSymbol());
        }

        return symbols;
    }
    private List<MarketStock> jsonListToMarketListStock(List<Map<String, Object>> data) {
        List<MarketStock> target = new ArrayList<>();

        for (int i = 0; i < data.size(); ++i) {
            var currentMap = data.get(i);

            String symbol = (String) currentMap.get("symbol");
            String companyName = (String) currentMap.get("companyName");
            int count = generatePseudoRandomInRange(4, 20);
            double latestPrice = (double) currentMap.get("latestPrice") * 95.8;
            double totalCost = count * latestPrice;
            double growMoney = generatePseudoRandomInRangeD(-12.968, 15.12);
            double growPercentage = growMoney / latestPrice;

            MarketStock marketStock = new MarketStock();
            marketStock.setCompanyName(companyName);
            marketStock.setSymbol(symbol);
            marketStock.setLatestPrice(latestPrice);
            marketStock.setTotalCost(totalCost);
            marketStock.setGrowMoney(growMoney);
            marketStock.setCount(count);
            marketStock.setGrowPercentage(growPercentage);

            target.add(marketStock);
        }

        return target;
    }
    private int generatePseudoRandomInRange(int minNum, int maxNum) {
        double random = Math.random();
        return minNum + (int)(random * ((maxNum - minNum) + 1));
    }
    private double generatePseudoRandomInRangeD(double minNum, double maxNum) {
        return minNum + Math.random() * ((maxNum - minNum) + 1);
    }
}
