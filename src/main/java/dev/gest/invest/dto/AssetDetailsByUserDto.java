package dev.gest.invest.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class AssetDetailsByUserDto {
    private BigDecimal totalEstimateAsset;
    private BigDecimal totalAssetNumber;
    private String name;
    private String symbol;
    private String local;
    private String categoryName;
    private UUID assetId;
    private List<AssetLineDetailDto> assetLineDetail;
}
