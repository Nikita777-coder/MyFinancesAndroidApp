package com.example.myfinances.mappers;

import com.example.myfinances.dto.MarketStock;
import com.example.myfinances.dto.UserStock;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Mapper
public interface StockMapper {
    StockMapper STOCK_MAPPER = Mappers.getMapper(StockMapper.class);
    List<MarketStock> jsonListToMarketListStock(List<Map<String, Object>> data);
    List<String> getUserStockSymbols(List<UserStock> userStocks);
    @Mapping(target = "count", ignore = true)
    @Mapping(source = "latestPrice", target = "currentCostOfStock")
    UserStock marketStockToUserStock(MarketStock marketStock);
    @AfterMapping
    default void generateMarketStocksData(List<Map<String, Object>> source, @MappingTarget List<MarketStock> target) {
        for (int i = 0; i < target.size(); ++i) {
            int count = ThreadLocalRandom.current().nextInt(4, 20);
            target.get(i).setLatestPrice(target.get(i).getLatestPrice() * 95.8);
            target.get(i).setTotalCost(count * target.get(i).getLatestPrice());
            target.get(i).setGrowMoney(ThreadLocalRandom.current().nextDouble(-12.968, 15.12));
            target.get(i).setCount(count);
            target.get(i).setGrowPercentage(target.get(i).getGrowMoney() / target.get(i).getLatestPrice());
        }
    }
    @AfterMapping
    default void generateUserStockData(MarketStock source, @MappingTarget UserStock target) {
        target.setCount(ThreadLocalRandom.current().nextInt(1, 100));
        target.setCost(new BigDecimal(target.getCurrentCostOfStock() * target.getCount()));
        var value = new BigDecimal(ThreadLocalRandom.current().nextDouble(1_000, 30_000));
        target.setInvested(target.getCost().substract(value));
        target.setIncome(target.getCost().substract(target.getInvested).substract(new BigDecimal(ThreadLocalRandom.current().nextDouble(0.25, 100))));
        target.setDiffOfOneStockCost(ThreadLocalRandom.current().nextDouble(0.25, 100.35));

    }
}
