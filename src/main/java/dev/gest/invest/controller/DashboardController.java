package dev.gest.invest.controller;

import dev.gest.invest.dto.AddLineDto;
import dev.gest.invest.dto.AssetCategoryProjection;
import dev.gest.invest.dto.InvestLineDto;
import dev.gest.invest.dto.PortfolioData;
import dev.gest.invest.model.User;
import dev.gest.invest.repository.AssetRepository;
import dev.gest.invest.responses.ApiResponse;
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
    public ResponseEntity<PortfolioData> getDashboardInfo(@AuthenticationPrincipal User user) {
        UUID userId = user.getId();

        List<InvestLineDto> allLines = dashboardService.getInvestLinesByUser(userId);

        if (allLines.isEmpty()) {
            return null;
        }

        return ResponseEntity.ok(dashboardService.getAssetUserInformation(allLines));
    }

    @GetMapping("/modal")
    public ResponseEntity<List<AssetCategoryProjection>> getAllAsset() {
        return ResponseEntity.ok(assetRepository.findAllAssetNamesAndCategoryNames());
    }

    @PostMapping("/buy")
    public ResponseEntity<ApiResponse> addBuyInvestmentLine(@AuthenticationPrincipal User user, @RequestBody @Valid AddLineDto addLineDto) {

        UUID userId = user.getId();
        String tradingOperationType = "buy";

        addLineService.addLine(userId, tradingOperationType, addLineDto);

        ApiResponse response = new ApiResponse("success", "Successful addition of a purchase investment line");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/sell")
    public ResponseEntity<ApiResponse> addSellInvestmentLine(@AuthenticationPrincipal User user, @RequestBody @Valid AddLineDto addLineDto) {

        UUID userId = user.getId();
        String tradingOperationType = "sell";

        addLineService.addLine(userId, tradingOperationType, addLineDto);

        ApiResponse response = new ApiResponse("success", "Successful addition of a sales investment line");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
