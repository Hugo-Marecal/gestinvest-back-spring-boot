package dev.gest.invest.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class AssetLineDetailDto {
    private UUID lineId;
    private String date;
    private String operationType;
    private BigDecimal buyQuantity;
    private BigDecimal priceInvest;
    private BigDecimal fees;
    private BigDecimal totalInvestLineWithFees;
}
