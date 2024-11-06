package dev.gest.invest.dto;

import java.math.BigDecimal;
import java.util.UUID;

public interface AssetLineByUserProjection {
    UUID getId();

    BigDecimal getPriceInvest();

    String getInvestDate();

    double getFees();

    BigDecimal getAssetNumber();

    String getTradingOperationType();

    String getCategoryName();

    String getLocal();

    String getName();

    String getSymbol();

    UUID getAssetId();

    BigDecimal getPrice();
}
