package dev.gest.invest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssetUserInformationDto {
    private String symbol;
    private double quantity;
    private double totalInvestByAsset;
    private double totalEstimatedValueByAsset;
    private String assetCategory;
    private String assetName;
    private double assetPrice;
    private String gainOrLossTotalByAsset;
}
