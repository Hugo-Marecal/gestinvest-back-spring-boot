package dev.gest.invest.repository;

import dev.gest.invest.dto.InvestLineDto;

import java.util.List;
import java.util.UUID;

public interface InvestLineRepositoryCustom {

    List<InvestLineDto> findAllInvestLinesByUser(UUID userId);
}
