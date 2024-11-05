package dev.gest.invest.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class InvestLineDto {
    private String portfolio_name;
    private BigDecimal price_invest;
    private LocalDate invest_date;
    private double fees;
    private BigDecimal asset_number;
    private String trading_operation_type;
    private String category_name;
    private String asset_name;
    private String symbol;
    private BigDecimal asset_price;

    public InvestLineDto(
            String portfolio_name,
            BigDecimal price_invest,
            LocalDate invest_date,
            double fees,
            BigDecimal asset_number,
            String trading_operation_type,
            String category_name,
            String asset_name,
            String symbol,
            BigDecimal asset_price
    ) {
        this.portfolio_name = portfolio_name;
        this.price_invest = price_invest;
        this.invest_date = invest_date;
        this.fees = fees;
        this.asset_number = asset_number;
        this.trading_operation_type = trading_operation_type;
        this.category_name = category_name;
        this.asset_name = asset_name;
        this.symbol = symbol;
        this.asset_price = asset_price;
    }
}
