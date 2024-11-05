package dev.gest.invest.repository;

import dev.gest.invest.model.InvestLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InvestLineRepository extends JpaRepository<InvestLine, UUID> {
}
