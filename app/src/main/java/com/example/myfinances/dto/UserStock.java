package com.example.myfinances.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserStock {
    private String symbol;
    private String companyName;
    private int count;
    private BigDecimal cost;
    private BigDecimal invested;
    private BigDecimal income;
    private double currentCostOfStock;
    private double diffOfOneStockCost;
    private double fraction;
}
