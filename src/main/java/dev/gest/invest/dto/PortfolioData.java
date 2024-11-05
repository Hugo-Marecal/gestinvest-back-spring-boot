package dev.gest.invest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PortfolioData {
    private double totalInvestment;
    private double totalEstimatePortfolio;
    private String gainOrLossTotalPortfolio;
    private double gainOrLossPercent;
    private double gainOrLossMoney;
    private double cryptoPercent;
    private double stockPercent;
    private List<AssetUserInformationDto> assetUserInformation;
}
