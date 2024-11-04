package dev.gest.invest.services;

import dev.gest.invest.dto.InvestLineDto;
import dev.gest.invest.repository.InvestLineRepositoryCustom;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class DashboardService {

    private final InvestLineRepositoryCustom investLineRepositoryCustom;

    public DashboardService(InvestLineRepositoryCustom investLineRepositoryCustom) {
        this.investLineRepositoryCustom = investLineRepositoryCustom;
    }

    @Transactional
    public List<InvestLineDto> getInvestLinesByUser(UUID userId) {
        return investLineRepositoryCustom.findAllInvestLinesByUser(userId);
    }
}
