package dev.gest.invest.controller;

import dev.gest.invest.dto.AssetCategoryProjection;
import dev.gest.invest.dto.InvestLineDto;
import dev.gest.invest.dto.PortfolioData;
import dev.gest.invest.model.User;
import dev.gest.invest.repository.AssetRepository;
import dev.gest.invest.repository.UserRepository;
import dev.gest.invest.services.DashboardService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;
    private final UserRepository userRepository;
    private final AssetRepository assetRepository;

    public DashboardController(DashboardService dashboardService, UserRepository userRepository, AssetRepository assetRepository) {
        this.dashboardService = dashboardService;
        this.userRepository = userRepository;
        this.assetRepository = assetRepository;
    }

    @GetMapping("/")
    public PortfolioData getDashboardInfo(@AuthenticationPrincipal User user) {
        UUID userId = user.getId();
        
        List<InvestLineDto> allLines = dashboardService.getInvestLinesByUser(userId);

        return dashboardService.getAssetUserInformation(allLines);
    }

    @GetMapping("/modal")
    public List<AssetCategoryProjection> getAllAsset() {
        return assetRepository.findAllAssetNamesAndCategoryNames();
    }
}
