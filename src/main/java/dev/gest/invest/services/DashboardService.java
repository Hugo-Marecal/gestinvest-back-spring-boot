package dev.gest.invest.services;

import dev.gest.invest.dto.AssetUserInformationDto;
import dev.gest.invest.dto.InvestLineDto;
import dev.gest.invest.dto.PortfolioData;
import dev.gest.invest.repository.InvestLineRepositoryCustom;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DashboardService {

    private final InvestLineRepositoryCustom investLineRepositoryCustom;

    public DashboardService(InvestLineRepositoryCustom investLineRepositoryCustom) {
        this.investLineRepositoryCustom = investLineRepositoryCustom;
    }


    public List<InvestLineDto> getInvestLinesByUser(UUID userId) {
        return investLineRepositoryCustom.findAllInvestLinesByUser(userId);
    }

    public PortfolioData getAssetUserInformation(List<InvestLineDto> allLines) {
        List<AssetUserInformationDto> assetUserInformationList = new ArrayList<>();
        double totalEstimatePortfolio = 0;
        double totalInvestment = 0;

        double cryptoTotal = 0;
        double stockTotal = 0;

        for (InvestLineDto line : allLines) {
            double buyQuantity = Double.parseDouble(String.valueOf(line.getAsset_number()));
            double priceInvest = Double.parseDouble(String.valueOf(line.getPrice_invest()));
            double assetPrice = truncateNumber(Double.parseDouble(String.valueOf(line.getAsset_price())));
            double feesPercent = line.getFees();
            String assetName = line.getAsset_name();
            String symbol = line.getSymbol();
            String category = line.getCategory_name();
            String transactionType = line.getTrading_operation_type();
            double totalEstimate = truncateToTwoDecimals(buyQuantity * assetPrice);

            double totalInvestLineWithoutFees = buyQuantity * priceInvest;
            double totalInvestLineWithFees = totalInvestLineWithoutFees - totalInvestLineWithoutFees * (feesPercent / 100);

            if ("buy".equals(transactionType)) {
                totalInvestment += totalInvestLineWithFees;
                AssetUserInformationDto existingAsset = findAssetBySymbol(assetUserInformationList, symbol);
                if (existingAsset != null) {
                    existingAsset.setQuantity(existingAsset.getQuantity() + buyQuantity);
                    existingAsset.setTotalInvestByAsset(existingAsset.getTotalInvestByAsset() + totalInvestLineWithFees);
                    existingAsset.setTotalEstimatedValueByAsset(existingAsset.getTotalEstimatedValueByAsset() + totalEstimate);
                    existingAsset.setAssetCategory(category);
                } else {
                    AssetUserInformationDto asset = new AssetUserInformationDto();
                    asset.setSymbol(symbol);
                    asset.setQuantity(buyQuantity);
                    asset.setTotalInvestByAsset(totalInvestLineWithFees);
                    asset.setTotalEstimatedValueByAsset(totalEstimate);
                    asset.setAssetCategory(category);
                    asset.setAssetName(assetName);
                    asset.setAssetPrice(assetPrice);
                    assetUserInformationList.add(asset);
                }
            } else if ("sell".equals(transactionType)) {
                totalInvestment -= totalInvestLineWithFees;
                AssetUserInformationDto existingAsset = findAssetBySymbol(assetUserInformationList, symbol);
                if (existingAsset != null) {
                    existingAsset.setQuantity(existingAsset.getQuantity() - buyQuantity);
                    existingAsset.setTotalInvestByAsset(existingAsset.getTotalInvestByAsset() - totalInvestLineWithFees);
                    existingAsset.setTotalEstimatedValueByAsset(existingAsset.getTotalEstimatedValueByAsset() - totalEstimate);
                }
            }
        }

        // Calculation of total portfolio
        for (InvestLineDto line : allLines) {
            double buyQuantity = Double.parseDouble(String.valueOf(line.getAsset_number()));
            double assetPrice = Double.parseDouble(String.valueOf(line.getAsset_price()));
            String transactionType = line.getTrading_operation_type();
            if ("buy".equals(transactionType)) {
                totalEstimatePortfolio += buyQuantity * assetPrice;
            } else if ("sell".equals(transactionType)) {
                totalEstimatePortfolio -= buyQuantity * assetPrice;
            }
        }

        // Percentage gain/loss calculation
        double gainOrLossPercent = truncateToTwoDecimals(((totalEstimatePortfolio - totalInvestment) / totalInvestment) * 100);
        double gainOrLossMoney = truncateToTwoDecimals(totalEstimatePortfolio - totalInvestment);
        String gainOrLossTotalPortfolio = totalEstimatePortfolio - totalInvestment > 0 ? "positive" : "negative";

        for (AssetUserInformationDto asset : assetUserInformationList) {
            if ("crypto".equals(asset.getAssetCategory())) {
                cryptoTotal += asset.getTotalEstimatedValueByAsset();
            } else if ("stock".equals(asset.getAssetCategory())) {
                stockTotal += asset.getTotalEstimatedValueByAsset();
            }
        }

        // Portfolio allocation
        double cryptoPercent = truncateToTwoDecimals((cryptoTotal / (cryptoTotal + stockTotal)) * 100);
        double stockPercent = truncateToTwoDecimals((stockTotal / (cryptoTotal + stockTotal)) * 100);

        for (AssetUserInformationDto asset : assetUserInformationList) {
            double gainOrLoss = asset.getTotalEstimatedValueByAsset() - asset.getTotalInvestByAsset();

            asset.setGainOrLossTotalByAsset(gainOrLoss > 0 ? "positive" : "negative");
        }

        totalEstimatePortfolio = truncateToTwoDecimals(totalEstimatePortfolio);

        // Setting up return information
        PortfolioData portfolioData = new PortfolioData();
        portfolioData.setTotalInvestment(totalInvestment);
        portfolioData.setTotalEstimatePortfolio(totalEstimatePortfolio);
        portfolioData.setGainOrLossTotalPortfolio(gainOrLossTotalPortfolio);
        portfolioData.setGainOrLossPercent(gainOrLossPercent);
        portfolioData.setGainOrLossMoney(gainOrLossMoney);
        portfolioData.setCryptoPercent(cryptoPercent);
        portfolioData.setStockPercent(stockPercent);
        portfolioData.setAssetUserInformation(assetUserInformationList);

        return portfolioData;
    }

    private AssetUserInformationDto findAssetBySymbol(List<AssetUserInformationDto> list, String symbol) {
        return list.stream().filter(asset -> asset.getSymbol().equals(symbol)).findFirst().orElse(null);
    }

    private double truncateToTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    private double truncateNumber(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}