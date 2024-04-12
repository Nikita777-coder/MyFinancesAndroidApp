package forremove;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockMockData {
    private String stockTitle;
    private Integer stockCount;
    private BigDecimal income;
    private BigDecimal wasted;
    private BigDecimal diff;
    private double persentage;
    private double proportion;
}
