package dev.gest.invest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StockPriceResponseDto {

    private QuoteResponse quoteResponse;

    @Getter
    @Setter
    public static class QuoteResponse {
        private List<Result> result;

    }

    @Getter
    @Setter
    public static class Result {
        private String symbol;
        private Double regularMarketPrice;

    }

}
