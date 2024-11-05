package dev.gest.invest.repository;

import dev.gest.invest.dto.InvestLineDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InvestLineRepositoryCustom {

    List<InvestLineDto> findAllInvestLinesByUser(UUID userId);
}
