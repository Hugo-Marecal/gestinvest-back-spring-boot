package dev.gest.invest.controller;

import dev.gest.invest.dto.AssetDetailsByUserDto;
import dev.gest.invest.dto.AssetLineByUserProjection;
import dev.gest.invest.model.User;
import dev.gest.invest.repository.InvestLineRepository;
import dev.gest.invest.services.AssetDetailsService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/assetdetails")
public class AssetDetailsController {

    private final InvestLineRepository investLineRepository;
    private final AssetDetailsService assetDetailsService;

    public AssetDetailsController(InvestLineRepository investLineRepository, AssetDetailsService assetDetailsService) {
        this.investLineRepository = investLineRepository;
        this.assetDetailsService = assetDetailsService;
    }

    @GetMapping("{symbol}")
    public AssetDetailsByUserDto getAssetDetails(@PathVariable String symbol, @AuthenticationPrincipal User user) {
        UUID userId = user.getId();

        List<AssetLineByUserProjection> assetDetails = investLineRepository.findAllAssetLinesByUserAndSymbol(userId, symbol);

        return assetDetailsService.calculateDetails(assetDetails);
    }

}
