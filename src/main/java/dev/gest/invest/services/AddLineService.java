package dev.gest.invest.services;

import dev.gest.invest.dto.AddLineDto;
import dev.gest.invest.dto.AssetUserInformationDto;
import dev.gest.invest.dto.InvestLineDto;
import dev.gest.invest.dto.PortfolioData;
import dev.gest.invest.model.InvestLine;
import dev.gest.invest.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class AddLineService {

    private final AssetRepository assetRepository;
    private final PortfolioRepository portfolioRepository;
    private final TradingOperationTypeRepository tradingOperationTypeRepository;
    private final InvestLineRepositoryCustom investLineRepositoryCustom;
    private final DashboardService dashboardService;
    private final InvestLineRepository investLineRepository;

    public AddLineService(
            AssetRepository assetRepository,
            PortfolioRepository portfolioRepository,
            TradingOperationTypeRepository tradingOperationTypeRepository,
            InvestLineRepositoryCustom investLineRepositoryCustom,
            DashboardService dashboardService,
            InvestLineRepository investLineRepository
    ) {
        this.assetRepository = assetRepository;
        this.portfolioRepository = portfolioRepository;
        this.tradingOperationTypeRepository = tradingOperationTypeRepository;
        this.investLineRepositoryCustom = investLineRepositoryCustom;
        this.dashboardService = dashboardService;
        this.investLineRepository = investLineRepository;
    }

    public String addLine(UUID userId, String tradingOperationType, AddLineDto addLineDto) {
        double assetNumber = Double.parseDouble(addLineDto.getAsset_number());
        double price = Double.parseDouble(addLineDto.getPrice());
        double fees = Double.parseDouble(addLineDto.getFees());

        if (!isDateOk(addLineDto.getDate())) {
            throw new IllegalArgumentException("Date is not valid");
        }
        UUID assetId = assetRepository.findAssetIdByName(addLineDto.getAsset_name())
                .orElseThrow(() -> new IllegalArgumentException("Asset not found"));

        UUID portfolioId = portfolioRepository.findPortfolioIdById(userId);

        UUID tradingTypeId = tradingOperationTypeRepository.findTradingOperationTypeIdByName(tradingOperationType);

        List<InvestLineDto> allLines = investLineRepositoryCustom.findAllInvestLinesByUser(userId);

        if (!allLines.isEmpty()) {

            PortfolioData assetInformationByUser = dashboardService.getAssetUserInformation(allLines);

            if ("sell".equalsIgnoreCase(tradingOperationType)) {
                AssetUserInformationDto asset = assetInformationByUser.getAssetUserInformation()
                        .stream()
                        .filter(a -> a.getAssetName().equalsIgnoreCase(addLineDto.getAsset_name()))
                        .findFirst()
                        .orElse(null);

                if (asset == null) {
                    throw new IllegalArgumentException("This asset does not exist in your portfolio");
                }

                if (asset.getQuantity() < assetNumber) {
                    throw new IllegalArgumentException("The quantity to be sold exceeds what you own");
                }

                if (assetNumber <= 0) {
                    throw new IllegalArgumentException("Quantity should be greater than 0");
                }
            }
        } else if (tradingOperationType.equalsIgnoreCase("sell")) {
            throw new IllegalArgumentException("You can not sell, you don't have any lines");
        }

        InvestLine newData = new InvestLine();
        newData.setAsset(assetId);
        newData.setPortfolio(portfolioId);
        newData.setAsset_number(BigDecimal.valueOf(assetNumber));
        newData.setPrice(BigDecimal.valueOf(price));
        newData.setFees(fees);
        newData.setDate(LocalDate.parse(addLineDto.getDate()));
        newData.setTradingOperationType(tradingTypeId);

        investLineRepository.save(newData);

        return "Job done";
    }

    private boolean isDateOk(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate inputDate = LocalDate.parse(date, formatter);
        LocalDate todayDate = LocalDate.now();

        return !inputDate.isAfter(todayDate);
    }
}
