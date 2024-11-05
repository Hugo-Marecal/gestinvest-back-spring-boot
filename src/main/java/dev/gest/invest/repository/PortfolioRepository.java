package dev.gest.invest.repository;

import dev.gest.invest.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, UUID> {

    @Query("SELECT p.id FROM Portfolio p WHERE userId= :userId")
    UUID findPortfolioIdById(@Param("userId") UUID userId);
}
