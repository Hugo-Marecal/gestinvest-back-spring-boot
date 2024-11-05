package dev.gest.invest.controller;

import dev.gest.invest.dto.InvestLineDto;
import dev.gest.invest.dto.PortfolioData;
import dev.gest.invest.model.User;
import dev.gest.invest.repository.UserRepository;
import dev.gest.invest.services.DashboardService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class DashboardController {

    private final DashboardService dashboardService;
    private final UserRepository userRepository;

    public DashboardController(DashboardService dashboardService, UserRepository userRepository) {
        this.dashboardService = dashboardService;
        this.userRepository = userRepository;
    }

    @GetMapping("/dashboard")
    public PortfolioData getDashboardInfo() {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByEmail(currentUser.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        UUID userId = user.getId();
        List<InvestLineDto> allLines = dashboardService.getInvestLinesByUser(userId);

        return dashboardService.getAssetUserInformation(allLines);
    }
}
