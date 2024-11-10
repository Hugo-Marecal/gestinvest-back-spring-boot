package dev.gest.invest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class CryptoPriceResponseDto {

    private Map<String, CryptoData> data;

    @Getter
    @Setter
    public static class CryptoData {
        private Map<String, Quote> quote;

    }

    @Getter
    @Setter
    public static class Quote {
        private double price;

    }

}
