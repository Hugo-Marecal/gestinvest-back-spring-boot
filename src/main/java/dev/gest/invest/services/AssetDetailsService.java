package dev.gest.invest.services;

import dev.gest.invest.dto.AssetDetailsByUserDto;
import dev.gest.invest.dto.AssetLineByUserProjection;
import dev.gest.invest.dto.AssetLineDetailDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AssetDetailsService {

    public AssetDetailsByUserDto calculateDetails(List<AssetLineByUserProjection> data) {
        BigDecimal totalEstimateAsset = BigDecimal.ZERO;
        BigDecimal totalAssetNumber = BigDecimal.ZERO;

        // Initial data extraction
        String symbol = data.getFirst().getSymbol();
        String name = data.getFirst().getName();
        String local = data.getFirst().getLocal();
        BigDecimal price = new BigDecimal(data.getFirst().getPrice().toString());
        String categoryName = data.getFirst().getCategoryName();
        UUID assetId = data.getFirst().getAssetId();

        List<AssetLineDetailDto> assetLineDetailList = new ArrayList<>();

        for (AssetLineByUserProjection line : data) {
            UUID lineId = line.getId();
            String date = formatDateFr(line.getInvestDate());
            BigDecimal priceInvest = new BigDecimal(line.getPriceInvest().toString());
            BigDecimal buyQuantity = new BigDecimal(line.getAssetNumber().toString());
            BigDecimal fees = BigDecimal.valueOf(line.getFees());
            String operationType = line.getTradingOperationType();

            // Calculation of total investment line
            BigDecimal totalInvestLineWithoutFees = buyQuantity.multiply(priceInvest);
            BigDecimal totalInvestLineWithFees = totalInvestLineWithoutFees.subtract(totalInvestLineWithoutFees.multiply(fees).divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP));

            // Update total number of assets
            if ("buy".equals(operationType)) {
                totalAssetNumber = totalAssetNumber.add(buyQuantity);
                operationType = "Achat";
            } else if ("sell".equals(operationType)) {
                totalAssetNumber = totalAssetNumber.subtract(buyQuantity);
                operationType = "Vente";
            }

            // Add asset line details
            AssetLineDetailDto detail = new AssetLineDetailDto();
            detail.setLineId(lineId);
            detail.setDate(date);
            detail.setOperationType(operationType);
            detail.setBuyQuantity(truncateToTwoDecimals(buyQuantity));
            detail.setPriceInvest(truncateToTwoDecimals(priceInvest));
            detail.setFees(fees);
            detail.setTotalInvestLineWithFees(truncateToTwoDecimals(totalInvestLineWithFees));
            assetLineDetailList.add(detail);
        }

        // Calculate total asset value
        totalEstimateAsset = totalAssetNumber.multiply(price);

        // Removal of the “.PA” extension for European equities
        if ("stock".equals(categoryName)) {
            symbol = symbol.replaceAll("\\.PA$", "");
        }

        // Create final object with calculated details
        AssetDetailsByUserDto result = new AssetDetailsByUserDto();
        result.setTotalEstimateAsset(truncateToTwoDecimals(totalEstimateAsset));
        result.setTotalAssetNumber(truncateToEightDecimals(totalAssetNumber));
        result.setName(name);
        result.setSymbol(symbol);
        result.setLocal(local);
        result.setCategoryName(categoryName);
        result.setAssetId(assetId);
        result.setAssetLineDetail(assetLineDetailList);

        return result;
    }

    private String formatDateFr(String dateStr) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yy");

        LocalDate date = LocalDate.parse(dateStr, inputFormatter);

        return date.format(outputFormatter);
    }

    private BigDecimal truncateToTwoDecimals(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal truncateToEightDecimals(BigDecimal value) {
        return value.setScale(8, RoundingMode.HALF_UP);
    }
}
