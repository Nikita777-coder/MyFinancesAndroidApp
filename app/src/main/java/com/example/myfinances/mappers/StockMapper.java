package com.example.myfinances.mappers;

import com.example.myfinances.dto.MarketStock;
import com.example.myfinances.dto.UserStock;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StockMapper {
    StockMapper STOCK_MAPPER = Mappers.getMapper(StockMapper.class);
    @Mapping(target = "count", ignore = true)
    @Mapping(source = "latestPrice", target = "currentCostOfStock")
    UserStock marketStockToUserStock(MarketStock marketStock);
    @AfterMapping
    default void generateUserStockData(MarketStock source, @MappingTarget UserStock target) {
        target.setCount(ThreadLocalRandom.current().nextInt(1, 100));
        target.setCost(new BigDecimal(target.getCurrentCostOfStock() * target.getCount()));
        var value = new BigDecimal(ThreadLocalRandom.current().nextDouble(1_000, 30_000));

        BigDecimal cost = target.getCost();
        BigDecimal invested = cost.subtract(value);
        BigDecimal income = cost.subtract(invested).subtract(BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(0.25, 100)));

        target.setInvested(invested);
        target.setIncome(income);
        target.setDiffOfOneStockCost(ThreadLocalRandom.current().nextDouble(0.25, 100.35));
    }
}
