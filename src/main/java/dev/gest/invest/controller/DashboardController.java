package dev.gest.invest.controller;

import dev.gest.invest.dto.AddLineDto;
import dev.gest.invest.dto.AssetCategoryProjection;
import dev.gest.invest.dto.InvestLineDto;
import dev.gest.invest.dto.PortfolioData;
import dev.gest.invest.model.User;
import dev.gest.invest.repository.AssetRepository;
import dev.gest.invest.services.AddLineService;
import dev.gest.invest.services.DashboardService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;
    private final AssetRepository assetRepository;
    private final AddLineService addLineService;

    public DashboardController(DashboardService dashboardService, AssetRepository assetRepository, AddLineService addLineService) {
        this.dashboardService = dashboardService;
        this.assetRepository = assetRepository;
        this.addLineService = addLineService;
    }

    @GetMapping("/")
    public PortfolioData getDashboardInfo(@AuthenticationPrincipal User user) {
        UUID userId = user.getId();

        List<InvestLineDto> allLines = dashboardService.getInvestLinesByUser(userId);

        if (allLines.isEmpty()) {
            return null;
        }

        return dashboardService.getAssetUserInformation(allLines);
    }

    @GetMapping("/modal")
    public List<AssetCategoryProjection> getAllAsset() {
        return assetRepository.findAllAssetNamesAndCategoryNames();
    }

    @PostMapping("/buy")
    public ResponseEntity<String> addBuyInvestmentLine(@AuthenticationPrincipal User user, @RequestBody @Valid AddLineDto addLineDto) {

        UUID userId = user.getId();
        String tradingOperationType = "buy";

        addLineService.addLine(userId, tradingOperationType, addLineDto);

        return ResponseEntity.status(HttpStatus.OK).body("Add a successful buy investment line");
    }

    @PostMapping("/sell")
    public ResponseEntity<String> addSellInvestmentLine(@AuthenticationPrincipal User user, @RequestBody @Valid AddLineDto addLineDto) {

        UUID userId = user.getId();
        String tradingOperationType = "sell";

        addLineService.addLine(userId, tradingOperationType, addLineDto);

        return ResponseEntity.status(HttpStatus.OK).body("Add a successful sell investment line");
    }
}
